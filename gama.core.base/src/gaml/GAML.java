/*******************************************************************************************************
 *
 * gama.util.GAML.java, in plugin gama.core, is part of the source code of the GAMA modeling and simulation platform (v.
 * 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;

import gama.GAMA;
import gama.common.interfaces.IAgent;
import gama.common.interfaces.IContainer;
import gama.common.interfaces.IModel;
import gama.common.interfaces.experiment.ITopLevelAgent;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IExecutionContext;
import gama.runtime.scope.IScope;
import gaml.compilation.GamlCompilationError;
import gaml.compilation.factories.DescriptionFactory;
import gaml.compilation.factories.ModelFactory;
import gaml.compilation.interfaces.IGamlEcoreUtils;
import gaml.compilation.interfaces.IGamlModelBuilder;
import gaml.descriptions.ExperimentDescription;
import gaml.descriptions.IDescription;
import gaml.descriptions.ModelDescription;
import gaml.expressions.GamlExpressionFactory;
import gaml.expressions.IExpression;
import gaml.expressions.IExpressionFactory;

/**
 * Class GAML. Static support for various GAML constructs and functions
 *
 * @author drogoul
 * @since 16 mai 2013
 *
 */
public class GAML {

	public volatile static IExpressionFactory expressionFactory = null;
	public volatile static ModelFactory modelFactory = null;
	private volatile static IGamlEcoreUtils gamlEcoreUtils = null;
	private volatile static IGamlModelBuilder modelBuilder = null;

	public static <T> T notNull(final IScope scope, final T object) {
		return notNull(scope, object, "Error: nil value detected");
	}

	public static <T> T notNull(final IScope scope, final T object, final String error) {
		if (object == null)
			throw GamaRuntimeException.error(error, scope);
		return object;
	}

	@SuppressWarnings ("rawtypes")
	public static <T extends IContainer> T emptyCheck(final IScope scope, final T container) {
		if (notNull(scope, container).isEmpty(scope))
			throw GamaRuntimeException.error("Error: the container is empty", scope);
		return container;
	}

	/**
	 *
	 * Parsing and compiling GAML utilities
	 *
	 */

	public static ModelFactory getModelFactory() {
		if (modelFactory == null) {
			modelFactory = DescriptionFactory.getModelFactory();
		}
		return modelFactory;
	}

	public static IExpressionFactory getExpressionFactory() {
		if (expressionFactory == null) {
			expressionFactory = new GamlExpressionFactory();
		}
		return expressionFactory;
	}

	public static Object evaluateExpression(final String expression, final IAgent a) throws GamaRuntimeException {
		if (a == null)
			return null;
		if (expression == null || expression.isEmpty())
			throw GamaRuntimeException.error("Enter a valid expression", a.getScope());
		final IExpression expr = compileExpression(expression, a, true);
		if (expr == null)
			return null;
		final IScope scope = a.getScope().copy("in temporary expression evaluator");
		final Object o = scope.evaluate(expr, a).getValue();
		GAMA.releaseScope(scope);
		return o;
	}

	public static IExpression compileExpression(final String expression, final IAgent agent,
			final boolean onlyExpression) throws GamaRuntimeException {
		if (agent == null)
			throw GamaRuntimeException.error("Agent is nil", GAMA.getRuntimeScope());
		final IExecutionContext tempContext = agent.getScope().getExecutionContext();
		return compileExpression(expression, agent, tempContext, onlyExpression);
	}

	public static IExpression compileExpression(final String expression, final IAgent agent,
			final IExecutionContext tempContext, final boolean onlyExpression) throws GamaRuntimeException {
		if (agent == null)
			throw GamaRuntimeException.error("Agent is nil", tempContext.getScope());
		final IDescription context = agent.getSpecies().getDescription();
		try {
			return getExpressionFactory().createExpr(expression, context, tempContext);
		} catch (final Throwable e) {
			// Maybe it is a statement instead ?
			if (!onlyExpression) {
				try {
					return getExpressionFactory().createTemporaryActionForAgent(agent, expression, tempContext);
				} catch (final Throwable e2) {
					throw GamaRuntimeException.create(e2, tempContext.getScope());
				}
			} else
				throw GamaRuntimeException.create(e, tempContext.getScope());
		}
	}

	public static ModelDescription getModelContext() {
		if (GAMA.getFrontmostController() == null)
			return null;
		return (ModelDescription) GAMA.getFrontmostController().getExperiment().getModel().getDescription();
	}

	public static ExperimentDescription getExperimentContext(final IAgent a) {
		if (a == null)
			return null;
		final IScope scope = a.getScope();
		final ITopLevelAgent agent = scope.getExperiment();
		if (agent == null)
			return null;
		return (ExperimentDescription) agent.getSpecies().getDescription();
	}

	public static void registerGamlEcoreUtils(final IGamlEcoreUtils utils) {
		gamlEcoreUtils = utils;
	}

	public static void registerGamlModelBuilder(final IGamlModelBuilder builder) {
		modelBuilder = builder;
	}

	public static IGamlEcoreUtils getEcoreUtils() {
		return gamlEcoreUtils;
	}

	public static IModel compile(final URI uri, final List<GamlCompilationError> errors) {
		return modelBuilder.compile(uri, errors);
	}

	public static IModel compile(final URL url, final List<GamlCompilationError> errors) {
		return modelBuilder.compile(url, errors);
	}

	public static void loadBuildContext(final List<URL> allURLs) {
		modelBuilder.loadURLs(allURLs);
	}

	/**
	 * @param object
	 * @return
	 */
	public static IModel findModelIn(final Object object) {
		if (object instanceof IModel)
			return (IModel) object;
		if (object instanceof IFile) {
			final IFile file = (IFile) object;
			try {
				if (file.findMaxProblemSeverity(IMarker.PROBLEM, true,
						IResource.DEPTH_ZERO) == IMarker.SEVERITY_ERROR) {
					GAMA.getGui().error("Model " + file.getFullPath() + " has errors and cannot be launched");
					return null;
				}
			} catch (final CoreException e) {
				e.printStackTrace();
			}
			final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			return findModelIn(uri);
		}
		if (object instanceof URI) {
			final URI uri = (URI) object;
			final List<GamlCompilationError> errors = new ArrayList<>();
			final IModel model = GAML.compile(uri, errors);
			if (model == null) {
				GAMA.getGui().error("File " + uri.lastSegment() + " cannot be built because of " + errors.size()
						+ " compilation errors");
			}
			return model;
		}

		return null;
	}

}
