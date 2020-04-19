/*******************************************************************************************************
 *
 * gama.runtime.GAMA.java, in plugin gama.core, is part of the source code of the GAMA modeling and simulation platform
 * (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama;

import static gama.dev.utils.DEBUG.PAD;
import static gama.dev.utils.DEBUG.TIMER_WITH_EXCEPTIONS;
import static java.lang.Thread.currentThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import gama.common.interfaces.IBenchmarkable;
import gama.common.interfaces.IModel;
import gama.common.interfaces.IStartupProgress;
import gama.common.interfaces.experiment.IExperimentController;
import gama.common.interfaces.experiment.IExperimentPlan;
import gama.common.interfaces.experiment.IParameter;
import gama.common.interfaces.gui.IGui;
import gama.common.preferences.GamaPreferences;
import gama.common.util.PoolUtils;
import gama.common.util.RandomUtils;
import gama.dev.utils.DEBUG;
import gama.dev.utils.DEBUG.RunnableWithException;
import gama.kernel.experiment.ExperimentAgent;
import gama.kernel.experiment.ExperimentPlan;
import gama.kernel.experiment.ParametersSet;
import gama.kernel.root.PlatformAgent;
import gama.kernel.simulation.SimulationAgent;
import gama.runtime.HeadlessListener;
import gama.runtime.benchmark.Benchmark;
import gama.runtime.benchmark.StopWatch;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.exceptions.GamaRuntimeException.GamaRuntimeFileException;
import gama.runtime.scope.IScope;
import gaml.GAML;
import gaml.compilation.interfaces.ISymbol;
import gaml.compilation.kernel.GamaMetaModel;

/**
 * Written by drogoul Modified on 23 nov. 2009
 *
 * In GUI Mode, for the moment, only one controller allowed at a time (controllers[0])
 *
 * @todo Description
 */
public class GAMA {

	static {
		DEBUG.ON();
	}

	public final static String VERSION = "GAMA 2.0";
	public static final String _WARNINGS = "warnings";
	private static volatile PlatformAgent agent;
	private static Benchmark benchmarkAgent;
	private static boolean isInHeadlessMode;
	private static IGui regularGui;
	private static IGui headlessGui = new HeadlessListener();
	// hqnghi: add several controllers to have multi-thread experiments
	private final static List<IExperimentController> controllers = new CopyOnWriteArrayList<>();

	public static List<IExperimentController> getControllers() {
		return controllers;
	}

	public static IExperimentController getFrontmostController() {
		return controllers.isEmpty() ? null : controllers.get(0);
	}

	/**
	 * New control architecture
	 */

	/**
	 * Create a GUI experiment that replaces the current one (if any). Returns the newly created IExperimentPlan or null
	 * if it cannot be opened
	 *
	 * @param id
	 * @param model
	 */
	public static IExperimentPlan runGuiExperiment(final String id, final IModel model) {
		// DEBUG.OUT("Launching experiment " + id + " of model " + model.getFilePath());
		final IExperimentPlan newExperiment = model.getExperiment(id);
		if (newExperiment == null) {
			DEBUG.OUT("No experiment " + id + " in model " + model.getFilePath());
			return null;
		}
		IExperimentController controller = getFrontmostController();
		if (controller != null) {
			final IExperimentPlan existingExperiment = controller.getExperiment();
			if (existingExperiment != null) {
				controller.getScheduler().pause();
				if (!getGui().confirmClose(existingExperiment))
					return null;
			}
		}
		controller = newExperiment.getController();
		if (controllers.size() > 0) {
			closeAllExperiments(false, false);
		}

		if (getGui().openSimulationPerspective(model, id)) {
			controllers.add(controller);
			startBenchmark(newExperiment);
			controller.userOpen();
		} else {
			// we are unable to launch the perspective.
			DEBUG.ERR("Unable to launch simulation perspective for experiment " + id + " of model "
					+ model.getFilePath());
			return null;
		}
		return newExperiment;
	}

	public static IExperimentPlan runModel(final Object object, final String exp, final boolean headless) {
		final IModel model = GAML.findModelIn(object);
		if (model == null)
			return null;
		if (headless)
			return addHeadlessExperiment(model, exp, null, null);
		else
			return runGuiExperiment(exp, model);
	}

	// /**
	// * Add a sub-experiment to the current GUI experiment
	// *
	// * @param id
	// * @param model
	// */
	// public static void addGuiExperiment(final IExperimentPlan experiment) {
	//
	// }

	public static void openExperimentFromGamlFile(final IExperimentPlan experiment) {
		experiment.getController().directOpenExperiment();
	}

	/**
	 * Add an experiment
	 *
	 * @param id
	 * @param model
	 */
	public static synchronized IExperimentPlan addHeadlessExperiment(final IModel model, final String expName,
			final ParametersSet params, final Double seed) {

		final ExperimentPlan currentExperiment = (ExperimentPlan) model.getExperiment(expName);

		if (currentExperiment == null)
			throw GamaRuntimeException.error("Experiment " + expName + " cannot be created", getRuntimeScope());
		currentExperiment.setHeadless(true);
		if (params != null) {
			for (final Map.Entry<String, Object> entry : params.entrySet()) {

				final IParameter.Batch v = currentExperiment.getParameterByTitle(entry.getKey());
				if (v != null) {
					currentExperiment.setParameterValueByTitle(currentExperiment.getExperimentScope(), entry.getKey(),
							entry.getValue());
				} else {
					currentExperiment.setParameterValue(currentExperiment.getExperimentScope(), entry.getKey(),
							entry.getValue());
				}

			}
		}
		currentExperiment.open(seed);
		controllers.add(currentExperiment.getController());
		return currentExperiment;

	}

	// public static void closeFrontmostExperiment() {
	// final IExperimentController controller = getFrontmostController();
	// if (controller == null || controller.getExperiment() == null) { return; }
	// controller.close();
	// controllers.remove(controller);
	// }

	public static void closeExperiment(final IExperimentPlan experiment) {
		if (experiment == null)
			return;
		closeController(experiment.getController());
	}

	public static void closeAllExperiments(final boolean andOpenModelingPerspective, final boolean immediately) {
		for (final IExperimentController controller : new ArrayList<>(controllers)) {
			closeController(controller);
		}
		getGui().closeSimulationViews(null, andOpenModelingPerspective, immediately);
		PoolUtils.WriteStats();

	}

	private static void closeController(final IExperimentController controller) {
		if (controller == null)
			return;
		stopBenchmark(controller.getExperiment());
		controller.close();
		controllers.remove(controller);
	}

	/**
	 *
	 * Access to experiments and their components
	 *
	 */

	public static SimulationAgent getSimulation() {
		final IExperimentController controller = getFrontmostController();
		if (controller == null || controller.getExperiment() == null)
			return null;
		return controller.getExperiment().getCurrentSimulation();
	}

	public static IExperimentPlan getExperiment() {
		final IExperimentController controller = getFrontmostController();
		if (controller == null)
			return null;
		return controller.getExperiment();
	}

	public static IModel getModel() {
		final IExperimentController controller = getFrontmostController();
		if (controller == null || controller.getExperiment() == null)
			return GamaMetaModel.INSTANCE.getAbstractModelSpecies();
		return controller.getExperiment().getModel();
	}

	/**
	 *
	 * Exception and life-cycle related utilities
	 *
	 */

	public static boolean reportError(final IScope scope, final GamaRuntimeException g,
			final boolean shouldStopSimulation) {
		final IExperimentController controller = getFrontmostController();
		if (controller == null || controller.getExperiment() == null || controller.isDisposing()
				|| controller.getExperiment().getAgent() == null)
			return false;
		// DEBUG.LOG("report error : " + g.getMessage());
		// Returns whether or not to continue
		if (!(g instanceof GamaRuntimeFileException) && scope != null && !scope.reportErrors()) {
			// AD: we still throw exceptions related to files (Issue #1281)
			g.printStackTrace();
			return true;
		}
		if (scope != null && scope.getGui() != null) {
			scope.getGui().runtimeError(scope, g);
		}
		g.setReported();

		final boolean isError = !g.isWarning() || controller.getExperiment().getAgent().getWarningsAsErrors();
		final boolean shouldStop =
				isError && shouldStopSimulation && GamaPreferences.Runtime.CORE_REVEAL_AND_STOP.getValue();
		return !shouldStop;
	}

	public static void reportAndThrowIfNeeded(final IScope scope, final GamaRuntimeException g,
			final boolean shouldStopSimulation) {

		if (getExperiment() == null && !(g instanceof GamaRuntimeFileException) && scope != null
				&& !scope.reportErrors()) {
			// AD: we still throw exceptions related to files (Issue #1281)
			g.printStackTrace();
			return;
		}

		DEBUG.LOG("reportAndThrowIfNeeded : " + g.getMessage());
		if (scope != null && scope.getAgent() != null) {
			final String name = scope.getAgent().getName();
			if (!g.getAgentsNames().contains(name)) {
				g.addAgent(name);
			}
		}
		if (scope != null) {
			scope.setCurrentError(g);
		}
		final boolean isInTryMode = scope != null && scope.isInTryMode();
		if (isInTryMode)
			throw g;
		else {
			final boolean shouldStop = !reportError(scope, g, shouldStopSimulation);
			if (shouldStop) {
				if (isInHeadLessMode())
					throw g;
				pauseFrontmostExperiment();
				throw g;
			}
		}
	}

	public static void startPauseFrontmostExperiment() {
		for (final IExperimentController controller : controllers) {
			controller.startPause();
		}
	}

	public static void stepFrontmostExperiment() {
		for (final IExperimentController controller : controllers) {
			controller.userStep();
		}
	}

	public static void stepBackFrontmostExperiment() {
		for (final IExperimentController controller : controllers) {
			controller.stepBack();
		}
	}

	public static void pauseFrontmostExperiment() {
		for (final IExperimentController controller : controllers) {
			// Dont block display threads (see #
			if (getGui().isInDisplayThread()) {
				controller.userPause();
			} else {
				controller.directPause();
			}
		}
	}

	public static void resumeFrontmostExperiment() {
		for (final IExperimentController controller : controllers) {
			controller.userStart();
		}
	}

	public static void reloadFrontmostExperiment() {
		final IExperimentController controller = getFrontmostController();
		if (controller != null) {
			controller.userReload();
		}
	}

	public static void startFrontmostExperiment() {
		final IExperimentController controller = getFrontmostController();
		if (controller != null) {
			controller.userStart();
		}
	}

	public static boolean isPaused() {
		final IExperimentController controller = getFrontmostController();
		if (controller == null || controller.getExperiment() == null)
			return true;
		return controller.getScheduler().paused;

	}

	/**
	 *
	 * Scoping utilities
	 *
	 */

	public static void releaseScope(final IScope scope) {
		if (scope != null) {
			scope.clear();
		}
	}

	private static IScope copyRuntimeScope(final String additionalName) {
		final IScope scope = getRuntimeScope();
		if (scope != null)
			return scope.copy(additionalName);
		return null;
	}

	public static IScope getRuntimeScope() {
		// If GAMA has not yet been loaded, we return null
		final IExperimentController controller = getFrontmostController();
		if (controller == null || controller.getExperiment() == null)
			return getPlatformAgent().getScope();
		final ExperimentAgent a = controller.getExperiment().getAgent();
		if (a == null || a.dead())
			return controller.getExperiment().getExperimentScope();
		final SimulationAgent s = a.getSimulation();
		if (s == null || s.dead())
			return a.getScope();
		return s.getScope();
	}

	public static RandomUtils getCurrentRandom() {
		final IScope scope = getRuntimeScope();
		if (scope == null)
			return new RandomUtils();
		return scope.getRandom();
	}

	public interface InScope<T> {

		public abstract static class Void implements InScope<Object> {

			@Override
			public Object run(final IScope scope) {
				process(scope);
				return null;
			}

			public abstract void process(IScope scope);
		}

		T run(IScope scope);
	}

	public static <T> T run(final InScope<T> r) {
		try (IScope scope = copyRuntimeScope(" in temporary scope block")) {
			return r.run(scope);
		}
	}

	/**
	 * Allows to update all outputs after running an experiment
	 *
	 * @param r
	 */
	public static final void runAndUpdateAll(final Runnable r) {
		r.run();
		// SimulationAgent sim = getSimulation();
		// if(sim.isPaused(sim.getScope()))
		getExperiment().refreshAllOutputs();
	}

	public static IGui getGui() {
		// either a headless listener or a fully configured gui
		if (isInHeadlessMode || regularGui == null)
			return headlessGui;
		else
			return regularGui;
	}

	public static IGui getHeadlessGui() {
		return headlessGui;
	}

	public static IGui getRegularGui() {
		return regularGui;
	}

	/**
	 * @param IGui
	 *            gui
	 */
	public static void setHeadlessGui(final IGui g) {
		headlessGui = g;
	}

	public static void setRegularGui(final IGui g) {
		regularGui = g;
	}

	/**
	 * @return
	 */
	public static boolean isInHeadLessMode() {
		return isInHeadlessMode;
	}

	/**
	 *
	 */
	public static IGui setHeadLessMode() {
		isInHeadlessMode = true;
		final IGui gui = new HeadlessListener();
		setHeadlessGui(gui);
		return gui;
	}

	public static void relaunchFrontmostExperiment() {
		// Needs to be done: recompile the model and runs the previous
		// experiment if any

	}

	/**
	 * Access to the one and only 'gama' agent
	 *
	 * @return the platform agent, or creates it if it doesn't exist
	 */
	public static PlatformAgent getPlatformAgent() {
		if (agent == null) {
			agent = new PlatformAgent();
		}
		return agent;
	}

	/**
	 *
	 * Benchmarking utilities
	 *
	 */
	public static StopWatch benchmark(final IScope scope, final Object symbol) {
		if (benchmarkAgent == null || symbol == null || scope == null)
			return StopWatch.NULL;
		if (symbol instanceof IBenchmarkable)
			return benchmarkAgent.record(scope, (IBenchmarkable) symbol);
		if (symbol instanceof ISymbol)
			return benchmarkAgent.record(scope, ((ISymbol) symbol).getDescription());
		return StopWatch.NULL;
	}

	public static void startBenchmark(final IExperimentPlan experiment) {
		if (experiment.shouldBeBenchmarked()) {
			benchmarkAgent = new Benchmark(experiment);
		}
	}

	public static void stopBenchmark(final IExperimentPlan experiment) {
		if (benchmarkAgent != null) {
			benchmarkAgent.saveAndDispose(experiment);
		}
		benchmarkAgent = null;
	}

	private static IStartupProgress monitor;

	public static void setStartupMonitor(final IStartupProgress splash) {
		monitor = splash;
	}

	public static <T extends Throwable> void initializeAtStartup(final String title,
			final RunnableWithException<T> runnable) throws T {
		TIMER_WITH_EXCEPTIONS(PAD("> GAMA: " + title, 55) + PAD("[" + currentThread().getName() + "]", 20) + "in",
				runnable);
		if (monitor != null) {
			monitor.add(title);
		}

	}
}
