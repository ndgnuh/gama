package gama.core.headless.command;

import java.io.File;
import java.io.IOException;

import gama.core.headless.core.Experiment;
import gama.core.headless.core.GamaHeadlessException;
import gama.core.headless.core.HeadlessSimulationLoader;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.facet;
import gama.processor.annotations.GamlAnnotations.facets;
import gama.processor.annotations.GamlAnnotations.inside;
import gama.processor.annotations.GamlAnnotations.symbol;
import msi.gama.common.interfaces.IModel;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.descriptions.IDescription;
import msi.gaml.operators.Cast;
import msi.gaml.statements.AbstractStatement;
import msi.gaml.types.IType;

@symbol (
		name = IKeywords.STARTSIMULATION,
		kind = ISymbolKind.SEQUENCE_STATEMENT,
		with_sequence = true,
		concept = { IConcept.HEADLESS })
@inside (
		kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SINGLE_STATEMENT, ISymbolKind.SPECIES, ISymbolKind.MODEL })
@facets (
		value = { @facet (
				name = IKeywords.MODEL,
				type = IType.STRING,
				optional = false),
				@facet (
						name = IKeywords.EXPERIMENT,
						type = IType.STRING,
						optional = false),
				@facet (
						name = IKeywords.WITHSEED,
						type = IType.INT,
						optional = true),
				@facet (
						name = IKeywords.WITHPARAMS,
						type = IType.MAP,
						optional = true) },
		omissible = IKeywords.EXPERIMENT)
public class StartSimulation extends AbstractStatement {

	public StartSimulation(final IDescription desc) {
		super(desc);
		// TODO Auto-generated constructor stub
	}

	private String retrieveModelFileAbsolutePath(final IScope scope, final String filename) {
		if (filename.charAt(0) == '/')
			return filename;
		return new File(scope.getModel().getFilePath()).getParentFile().getAbsolutePath() + "/" + filename;
	}

	@Override
	protected Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		int seed = 0;
		final String expName = Cast.asString(scope, getFacetValue(scope, IKeywords.EXPERIMENT));
		String modelPath = Cast.asString(scope, getFacetValue(scope, IKeywords.MODEL));
		if (modelPath != null && !modelPath.isEmpty()) {
			modelPath = retrieveModelFileAbsolutePath(scope, modelPath);
		} else {
			// no model specified, this caller model path is used.
			modelPath = scope.getModel().getFilePath();
		}

		if (this.hasFacet(IKeywords.WITHSEED))
			seed = Cast.asInt(scope, getFacetValue(scope, IKeywords.WITHSEED));

		final long lseed = seed;

		IModel mdl = null;
		try {
			mdl = HeadlessSimulationLoader.loadModel(new File(modelPath));
		} catch (final IOException e) {
			throw GamaRuntimeException.error("Sub model file not found!", scope);
		} catch (final GamaHeadlessException e) {
			throw GamaRuntimeException.error("Sub model file cannot be loaded", scope);
		}
		final Experiment exp = new Experiment(mdl);
		exp.setup(expName, lseed);
		final String varName = exp.toString();
		scope.addVarWithValue(varName, exp);
		return varName;
	}

}
