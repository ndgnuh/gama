package gama.core.headless.runtime;

import java.io.File;
import java.io.IOException;

import gama.core.headless.core.GamaHeadlessException;
import gama.common.interfaces.IModel;
import gama.common.interfaces.experiment.IExperimentPlan;

public interface RuntimeContext {
	public IExperimentPlan buildExperimentPlan(String expName, IModel mdl);

	public IModel loadModel(File fl) throws IOException, GamaHeadlessException;

}
