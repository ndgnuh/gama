/*********************************************************************************************
 *
 *
 * 'HeadlessStatement.java', in plugin 'gama.core.headless', is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.core.headless.command;

import java.io.File;

import gama.core.headless.job.ExperimentJob;
import gama.core.headless.runtime.LocalSimulationRuntime;
import gama.core.headless.runtime.SimulationRuntime;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.facet;
import gama.processor.annotations.GamlAnnotations.facets;
import gama.processor.annotations.GamlAnnotations.inside;
import gama.processor.annotations.GamlAnnotations.symbol;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.descriptions.IDescription;
import msi.gaml.operators.Cast;
import msi.gaml.statements.AbstractStatement;
import msi.gaml.types.IType;

@symbol (
		name = IKeywords.RUNSIMULARTION,
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
						name = IKeywords.END,
						type = IType.INT,
						optional = true),
				@facet (
						name = IKeywords.CORE,
						type = IType.INT,
						optional = true),
				@facet (
						name = IKeywords.WITHSEED,
						type = IType.INT,
						optional = true),
				// @facet(name = IKeywords.OUT, type = IType.STRING, optional = true),
				@facet (
						name = IKeywords.WITHOUTPUTS,
						type = IType.MAP,
						optional = true),
				@facet (
						name = IKeywords.WITHPARAMS,
						type = IType.MAP,
						optional = true) },
		omissible = IKeywords.EXPERIMENT)
public class HeadlessStatement extends AbstractStatement {
	private final int numberOfThread = 4;
	private final SimulationRuntime processorQueue;
	private Integer maxSimulationID = 0;

	public String getSimulationId() {
		return String.valueOf(maxSimulationID++);
	}

	public HeadlessStatement(final IDescription desc) {
		super(desc);
		processorQueue = new LocalSimulationRuntime(this.numberOfThread);
	}

	private String retrieveModelFileAbsolutePath(final IScope scope, final String filename) {
		if (filename.charAt(0) == '/') { return filename; }
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

		// final GamaMap<String, ?> outputs = Cast.asMap(scope, getFacetValue(scope, IKeywords.WITHOUTPUTS), false);

		if (this.hasFacet(IKeywords.WITHSEED)) {
			seed = Cast.asInt(scope, getFacetValue(scope, IKeywords.WITHSEED));
		}

		final long lseed = seed;

		// DEBUG.OUT("chemin du fichier" + new File(scope.getModel().getFilePath()).getParentFile().getAbsolutePath());

		final ExperimentJob sim = new ExperimentJob(this.getSimulationId(), modelPath, expName, 1000, "", lseed);

		this.processorQueue.pushSimulation(sim);

		return null;
	}

}
