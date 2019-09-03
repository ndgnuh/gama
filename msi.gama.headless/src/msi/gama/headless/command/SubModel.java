package msi.gama.headless.command;

import java.io.File;
import java.io.IOException;

import msi.gama.common.interfaces.IModel;
import msi.gama.common.interfaces.experiment.IExperimentAgent;
import msi.gama.headless.core.Experiment;
import msi.gama.headless.core.GamaHeadlessException;
import msi.gama.headless.core.HeadlessSimulationLoader;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.types.IType;
import ummisco.gama.processor.IConcept;
import ummisco.gama.processor.IOperatorCategory;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.no_test;
import ummisco.gama.processor.GamlAnnotations.operator;

public class SubModel {

	private static String retrieveModelFileAbsolutePath(final IScope scope, final String filename) {
		if (filename.charAt(0) == '/')
			return filename;
		return new File(scope.getModel().getFilePath()).getParentFile().getAbsolutePath() + "/" + filename;
	}

	@operator (
			value = IKeywords.STEPSUBMODEL,
			can_be_const = true,
			category = IOperatorCategory.FILE,
			concept = { IConcept.HEADLESS })
	@doc (
			value = "Load a submodel",
			comment = "loaded submodel")
	@no_test
	public static Integer stepSubModel(final IScope scope, final IExperimentAgent expName) {
		final Experiment exp = (Experiment) scope.getVarValue(expName.toString());
		return ((int) exp.step());
	}

	@operator (
			value = IKeywords.EVALUATESUBMODEL,
			can_be_const = true,
			category = IOperatorCategory.FILE,
			concept = { IConcept.HEADLESS })
	@doc (
			value = "Load a submodel",
			comment = "loaded submodel")
	@no_test
	public static Object evaluateSubModel(final IScope scope, final IExperimentAgent expName, final String expression) {
		final Experiment exp = (Experiment) scope.getVarValue(expName.toString());
		return exp.evaluateExpression(expression);
	}

	@operator (
			value = IKeywords.LOADSUBMODEL,
			//can_be_const = true,
			type=IType.AGENT
			//category = IOperatorCategory.FILE,
			//concept = { IConcept.HEADLESS }
			)
	@doc (
			value = "Load a submodel",
			comment = "loaded submodel")
	@no_test
	public static IExperimentAgent loadSubModel(final IScope scope, final String expName, final String mdp) {
		final int seed = 0;
		String modelPath = mdp;
		if (modelPath != null && !modelPath.isEmpty()) {
			modelPath = retrieveModelFileAbsolutePath(scope, modelPath);
		} else {
			// no model specified, this caller model path is used.
			modelPath = scope.getModel().getFilePath();
		}

		final long lseed = seed;

		IModel mdl = null;
		try {
			mdl = HeadlessSimulationLoader.loadModel(new File(modelPath));
		} catch (final IOException e) {
			throw GamaRuntimeException.error("Sub model file not found!", scope);
		} catch (final GamaHeadlessException e) {
			throw GamaRuntimeException.error("Sub model file cannot be built", scope);
		}
		final Experiment exp = new Experiment(mdl);
		exp.setup(expName, lseed);
		final IExperimentAgent aa = exp.getSimulation().getExperiment();
		// String varName = exp.toString();
		scope.addVarWithValue(aa.toString(), exp);
		return aa;
	}

}
