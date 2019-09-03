package msi.gama.kernel.experiment;

import java.util.List;
import java.util.function.Supplier;

import msi.gama.common.interfaces.experiment.IExperimentPlan;
import msi.gama.common.interfaces.outputs.IOutput;
import msi.gama.common.interfaces.outputs.IOutputManager;

/**
 * A class initialized by the ummisco.gama.outputs plugin to provide some basic experiment outputs
 *
 * @author drogoul
 *
 */
public class ExperimentOutputsFactory {

	public interface FileSupplier {
		IOutput.FileBased get(String file, String expr, List<String> columns, IExperimentPlan exp);
	}

	private static Supplier<IOutputManager.Simulation> SIMULATIONS;
	private static Supplier<IOutputManager.Experiment> EXPERIMENTS;
	private static FileSupplier FILES;

	public static IOutputManager.Simulation getSimulationOutputManager() {
		return SIMULATIONS.get();
	}

	public static void setSimulationOutputManagerSupplier(final Supplier<IOutputManager.Simulation> supplier) {
		SIMULATIONS = supplier;
	}

	public static IOutputManager.Experiment getExperimentOutputManager() {
		return EXPERIMENTS.get();
	}

	public static void setExperimentOutputManagerSupplier(final Supplier<IOutputManager.Experiment> supplier) {
		EXPERIMENTS = supplier;
	}

	public static void setFileOutputSupplier(final FileSupplier supplier) {
		FILES = supplier;
	}

	public static IOutput.FileBased getFileOutput(final String file, final String expr, final List<String> columns,
			final IExperimentPlan exp) {
		return FILES.get(file, expr, columns, exp);
	}

}
