package gama.core.headless.core;

import gama.core.headless.job.ExperimentJob.ListenedVariable;
import gama.core.headless.job.ExperimentJob.OutputType;

public interface IRichExperiment extends IExperiment{
	public RichOutput getRichOutput(final ListenedVariable v);
	public OutputType getTypeOf(final String name);
}
