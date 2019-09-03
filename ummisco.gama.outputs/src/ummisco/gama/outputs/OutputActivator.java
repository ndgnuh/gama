package ummisco.gama.outputs;
import static msi.gama.kernel.experiment.ExperimentOutputsFactory.setExperimentOutputManagerSupplier;
import static msi.gama.kernel.experiment.ExperimentOutputsFactory.setFileOutputSupplier;
import static msi.gama.kernel.experiment.ExperimentOutputsFactory.setSimulationOutputManagerSupplier;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import msi.gama.runtime.GAMA;

public class OutputActivator implements BundleActivator {

	public OutputActivator() {
		GAMA.initializeAtStartup("Initializing Experiment outputs", () -> {
			setExperimentOutputManagerSupplier(() -> ExperimentOutputManager.createEmpty());
			setSimulationOutputManagerSupplier(() -> SimulationOutputManager.createEmpty());
			setFileOutputSupplier((n, e, c, p) -> new FileOutput(n, e, c, p));
		});
	}

	@Override
	public void start(final BundleContext context) throws Exception {}

	@Override
	public void stop(final BundleContext context) throws Exception {}

}
