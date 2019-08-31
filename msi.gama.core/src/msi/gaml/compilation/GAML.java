/*******************************************************************************************************
 *
 * msi.gama.util.GAML.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gaml.compilation;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;

import com.google.common.collect.Multimap;

import msi.gama.common.interfaces.IGamlDescription;
import msi.gama.common.util.TextBuilder;
import msi.gama.kernel.experiment.ITopLevelAgent;
import msi.gama.kernel.model.IModel;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.runtime.GAMA;
import msi.gama.runtime.IExecutionContext;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.util.IContainer;
import msi.gaml.descriptions.ExperimentDescription;
import msi.gaml.descriptions.IDescription;
import msi.gaml.descriptions.ModelDescription;
import msi.gaml.expressions.GamlExpressionFactory;
import msi.gaml.expressions.IExpression;
import msi.gaml.expressions.IExpressionFactory;
import msi.gaml.factories.DescriptionFactory;
import msi.gaml.factories.ModelFactory;
import msi.gaml.operators.Strings;

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
	// private volatile static IGamlResourceInfoProvider infoProvider = null;
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

	private static String[] HTML_TAGS =
			{ "<br/>", "<br>", "<b>", "</b>", "<i>", "</i>", "<ul>", "</ul>", "<li>", "</li>" };
	private static String[] REPLACEMENTS = { Strings.LN, Strings.LN, "", "", "", "", "", "", Strings.LN + "- ", "" };

	public static String toText(final String s) {
		if (s == null)
			return "";
		return breakStringToLines(StringUtils.replaceEach(s, HTML_TAGS, REPLACEMENTS), 120, Strings.LN);
	}

	/**
	 * Indicates that a String search operation yielded no results.
	 */
	public static final int NOT_FOUND = -1;

	/**
	 * Version of lastIndexOf that uses regular expressions for searching. By Tomer Godinger.
	 *
	 * @param str
	 *            String in which to search for the pattern.
	 * @param toFind
	 *            Pattern to locate.
	 * @return The index of the requested pattern, if found; NOT_FOUND (-1) otherwise.
	 */
	public static int lastIndexOfRegex(final String str, final String toFind) {
		final Pattern pattern = Pattern.compile(toFind);
		final Matcher matcher = pattern.matcher(str);

		// Default to the NOT_FOUND constant
		int lastIndex = NOT_FOUND;

		// Search for the given pattern
		while (matcher.find()) {
			lastIndex = matcher.start();
		}

		return lastIndex;
	}

	/**
	 * Finds the last index of the given regular expression pattern in the given string, starting from the given index
	 * (and conceptually going backwards). By Tomer Godinger.
	 *
	 * @param str
	 *            String in which to search for the pattern.
	 * @param toFind
	 *            Pattern to locate.
	 * @param fromIndex
	 *            Maximum allowed index.
	 * @return The index of the requested pattern, if found; NOT_FOUND (-1) otherwise.
	 */
	public static int lastIndexOfRegex(final String str, final String toFind, final int fromIndex) {
		// Limit the search by searching on a suitable substring
		return lastIndexOfRegex(str.substring(0, fromIndex), toFind);
	}

	/**
	 * Breaks the given string into lines as best possible, each of which no longer than <code>maxLength</code>
	 * characters. By Tomer Godinger.
	 *
	 * @param str
	 *            The string to break into lines.
	 * @param maxLength
	 *            Maximum length of each line.
	 * @param newLineString
	 *            The string to use for line breaking.
	 * @return The resulting multi-line string.
	 */
	public static String breakStringToLines(final String s, final int maxLength, final String newLineString) {
		String str = s;
		try (TextBuilder sb = TextBuilder.create()) {
			while (str.length() > maxLength) {
				// Attempt to break on whitespace first,
				int breakingIndex = lastIndexOfRegex(str, "\\s", maxLength);

				// Then on other non-alphanumeric characters,
				if (breakingIndex == NOT_FOUND) {
					breakingIndex = lastIndexOfRegex(str, "[^a-zA-Z0-9]", maxLength);
				}

				// And if all else fails, break in the middle of the word
				if (breakingIndex == NOT_FOUND) {
					breakingIndex = maxLength;
				}

				// Append each prepared line to the builder
				sb.append(str.substring(0, breakingIndex + 1));
				sb.append(newLineString);

				// And start the next line
				str = str.substring(breakingIndex + 1);
			}

			// Check if there are any residual characters left
			if (str.length() > 0) {
				sb.append(str);
			}

			// Return the resulting string
			return sb.toString();
		}
	}

	public static String getDocumentationOn(final String query) {
		final String keyword = StringUtils.removeEnd(StringUtils.removeStart(query.trim(), "#"), ":");
		final Multimap<GamlIdiomsProvider<?>, IGamlDescription> results = GamlIdiomsProvider.forName(keyword);
		if (results.isEmpty())
			return "No result found";
		try (TextBuilder sb = TextBuilder.create()) {
			final int max = results.keySet().stream().mapToInt(each -> each.name.length()).max().getAsInt();
			final String separator = StringUtils.repeat("—", max + 6).concat(Strings.LN);
			results.asMap().forEach((provider, list) -> {
				sb.append("").append(separator).append("|| ");
				sb.append(StringUtils.rightPad(provider.name, max));
				sb.append(" ||").append(Strings.LN).append(separator);
				for (final IGamlDescription d : list) {
					sb.append("== ").append(toText(d.getTitle())).append(Strings.LN)
							.append(toText(provider.document(d))).append(Strings.LN);
				}
			});

			return sb.toString();
		}

		//
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

	// public static void registerInfoProvider(final IGamlResourceInfoProvider info) {
	// infoProvider = info;
	// }

	public static void registerGamlEcoreUtils(final IGamlEcoreUtils utils) {
		gamlEcoreUtils = utils;
	}

	public static void registerGamlModelBuilder(final IGamlModelBuilder builder) {
		modelBuilder = builder;
	}

	public static IGamlEcoreUtils getEcoreUtils() {
		return gamlEcoreUtils;
	}

	// public static GamlFileInfo getInfo(final URI uri, final long stamp) {
	// return infoProvider.getInfo(uri, stamp);
	// }
	//
	// public static ISyntacticElement getContents(final URI uri) {
	// return infoProvider.getContents(uri);
	// }

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
