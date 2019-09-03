package msi.gama.headless.runtime;

import java.io.File;
import java.io.IOException;

import msi.gama.common.interfaces.IModel;
import msi.gama.common.interfaces.experiment.IExperimentPlan;
import msi.gama.headless.core.GamaHeadlessException;

public interface RuntimeContext {
	public IExperimentPlan buildExperimentPlan(String expName, IModel mdl);

	public IModel loadModel(File fl) throws IOException, GamaHeadlessException;

}
