/*******************************************************************************************************
 *
 * msi.gama.runtime.HeadlessListener.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gama.runtime;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import msi.gama.common.interfaces.IConsoleDisplayer;
import msi.gama.common.interfaces.IDisplayCreator;
import msi.gama.common.interfaces.IDisplaySurface;
import msi.gama.common.interfaces.IGamaView;
import msi.gama.common.interfaces.IGui;
import msi.gama.common.interfaces.IStatusDisplayer;
import msi.gama.kernel.experiment.IExperimentPlan;
import msi.gama.kernel.experiment.ITopLevelAgent;
import msi.gama.kernel.model.IModel;
import msi.gama.kernel.simulation.SimulationAgent;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.metamodel.shape.IShape;
import msi.gama.outputs.IDisplayOutput;
import msi.gama.outputs.LayeredDisplayOutput;
import msi.gama.outputs.display.NullDisplaySurface;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.util.GamaColor;
import msi.gaml.architecture.user.UserPanelStatement;
import msi.gaml.operators.Strings;
import msi.gaml.statements.test.CompoundSummary;
import msi.gaml.types.IType;
import ummisco.gama.dev.utils.DEBUG;

public class HeadlessListener implements IGui {

	static Logger LOGGER = LogManager.getLogManager().getLogger("");
	// static Level LEVEL = Level.ALL;
	final ThreadLocal<BufferedWriter> outputWriter = new ThreadLocal<>();

	static {

		if (GAMA.isInHeadLessMode()) {

			for (final Handler h : LOGGER.getHandlers()) {
				h.setLevel(Level.ALL);
			}
			LOGGER.setLevel(Level.ALL);
		}
		GAMA.setHeadlessGui(new HeadlessListener());
	}

	private static void log(final String s) {
		DEBUG.LOG(s);
	}

	@Override
	public Map<String, Object> openUserInputDialog(final IScope scope, final String title,
			final Map<String, Object> initialValues, final Map<String, IType<?>> types) {
		return initialValues;
	}

	public void registerJob(final BufferedWriter w) {
		this.outputWriter.set(w);
	}

	public BufferedWriter leaveJob() {
		final BufferedWriter res = this.outputWriter.get();
		this.outputWriter.remove();
		return res;
	}

	@Override
	public boolean copyToClipboard(final String text) {
		return false;
	}

	@Override
	public void openUserControlPanel(final IScope scope, final UserPanelStatement panel) {}

	@Override
	public void closeDialogs(final IScope scope) {}

	@Override
	public IAgent getHighlightedAgent() {
		return null;
	}

	@Override
	public void setHighlightedAgent(final IAgent a) {}

	@Override
	public IGamaView showView(final IScope scope, final String viewId, final String name, final int code) {
		return null;
	}

	@Override
	public void tell(final String title, final String message) {
		log(title + " " + message);
	}

	@Override
	public void error(final String error) {
		log("Error: " + error);
	}

	@Override
	public void showParameterView(final IScope scope, final IExperimentPlan exp) {}

	@Override
	public void runtimeError(final IScope scope, final GamaRuntimeException g) {
		log("Runtime error: " + g.getMessage());
	}

	@Override
	public boolean confirmClose(final IExperimentPlan experiment) {
		return true;
	}

	@Override
	public void prepareForExperiment(final IScope scope, final IExperimentPlan exp) {}

	@Override
	public boolean openSimulationPerspective(final IModel model, final String id) {
		return true;
	}

	// @SuppressWarnings ("rawtypes") static Map<String, Class> displayClasses = null;

	@Override
	public IDisplaySurface getDisplaySurfaceFor(final LayeredDisplayOutput output) {

		IDisplaySurface surface = null;
		final IDisplayCreator creator = DISPLAYS.get("image");
		if (creator != null) {
			surface = creator.create(output);
			surface.outputReloaded();
		} else
			return new NullDisplaySurface();
		return surface;
	}

	@Override
	public void editModel(final Object eObject) {}

	@Override
	public void updateParameterView(final IScope scope, final IExperimentPlan exp) {}

	@Override
	public void setSelectedAgent(final IAgent a) {}

	@Override
	public void cleanAfterExperiment() {
		// DEBUG.LOG("[Headless] Clean after experiment.");
		try {
			outputWriter.get().flush();
			outputWriter.get().close();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method updateSpeedDisplay()
	 *
	 * @see msi.gama.common.interfaces.IGui#updateSpeedDisplay(java.lang.Double)
	 */
	@Override
	public void updateSpeedDisplay(final IScope scope, final Double d, final boolean notify) {}

	/**
	 * Method closeSimulationViews()
	 *
	 * @see msi.gama.common.interfaces.IGui#closeSimulationViews(boolean)
	 */
	@Override
	public void closeSimulationViews(final IScope scope, final boolean andOpenModelingPerspective,
			final boolean immediately) {}

	/**
	 * Method getFrontmostSimulationState()
	 *
	 * @see msi.gama.common.interfaces.IGui#getExperimentState()
	 */
	@Override
	public String getExperimentState(final String uid) {
		return RUNNING; // ???
	}

	/**
	 * Method updateSimulationState()
	 *
	 * @see msi.gama.common.interfaces.IGui#updateExperimentState(java.lang.String)
	 */
	@Override
	public void updateExperimentState(final IScope scope, final String state) {}

	/**
	 * Method updateSimulationState()
	 *
	 * @see msi.gama.common.interfaces.IGui#updateExperimentState()
	 */
	@Override
	public void updateExperimentState(final IScope scope) {}

	@Override
	public void updateViewTitle(final IDisplayOutput output, final SimulationAgent agent) {}

	@Override
	public void openWelcomePage(final boolean b) {}

	@Override
	public void updateDecorator(final String string) {}

	IStatusDisplayer status = new IStatusDisplayer() {

		@Override
		public void resumeStatus() {}

		@Override
		public void waitStatus(final String string) {}

		@Override
		public void informStatus(final String string) {}

		@Override
		public void errorStatus(final String message) {}

		@Override
		public void setSubStatusCompletion(final double status) {}

		@Override
		public void setStatus(final String msg, final GamaColor color) {}

		@Override
		public void informStatus(final String message, final String icon) {}

		@Override
		public void setStatus(final String msg, final String icon) {}

		@Override
		public void beginSubStatus(final String name) {}

		@Override
		public void endSubStatus(final String name) {}

		@Override
		public void neutralStatus(final String string) {}

	};

	IConsoleDisplayer console = new IConsoleDisplayer() {

		@Override
		public void debugConsole(final int cycle, final String s, final ITopLevelAgent root, final GamaColor color) {
			informConsole(s, root);
		}

		@Override
		public void debugConsole(final int cycle, final String s, final ITopLevelAgent root) {
			informConsole(s, root);
		}

		@Override
		public void informConsole(final String s, final ITopLevelAgent root, final GamaColor color) {
			informConsole(s, root);
		}

		@Override
		public void informConsole(final String s, final ITopLevelAgent root) {
			DEBUG.LOG(s);
			if (outputWriter.get() != null) {
				try {
					outputWriter.get().write(s + Strings.LN);
					// outputWriter.get().flush();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void showConsoleView(final ITopLevelAgent agent) {}

		@Override
		public void eraseConsole(final boolean setToNull) {}

	};

	@Override
	public IStatusDisplayer getStatus(final IScope scope) {
		return status;
	}

	@Override
	public IConsoleDisplayer getConsole() {
		return console;
	}

	@Override
	public void clearErrors(final IScope scope) {}

	@Override
	public void run(final String taskName, final Runnable opener, final boolean asynchronous) {
		if (opener != null) {
			if (asynchronous) {
				new Thread(opener).start();
			} else {
				opener.run();
			}
		}
	}

	@Override
	public void setFocusOn(final IShape o) {}

	@Override
	public void applyLayout(final IScope scope, final Object layout) {}

	@Override
	public void displayErrors(final IScope scope, final List<GamaRuntimeException> list) {}

	@Override
	public GamaPoint getMouseLocationInModel() {
		return new GamaPoint(0, 0);
	}

	@Override
	public void setMouseLocationInModel(final GamaPoint modelCoordinates) {}

	@Override
	public void exit() {
		System.exit(0);
	}

	@Override
	public void openInteractiveConsole(final IScope scope) {}

	@Override
	public IGamaView.Test openTestView(final IScope scope, final boolean remainOpen) {
		// final String pathToFile = scope.getModel().getFilePath().replace(scope.getModel().getWorkingPath(), "");
		// log("----------------------------------------------------------------");
		// log(" Running tests declared in " + pathToFile);
		// log("----------------------------------------------------------------");
		return null;
	}

	@Override
	public void displayTestsResults(final IScope scope, final CompoundSummary<?, ?> summary) {
		log(summary.toString());
	}

	@Override
	public void endTestDisplay() {}

	@Override
	public boolean toggleFullScreenMode() {
		return false;
	}

	@Override
	public void refreshNavigator() {

	}

	@Override
	public boolean isInDisplayThread() {
		return false;
	}

	@Override
	public <T> T getUIService(final Class<T> clazz) {
		// Should return null !
		return null;
	}

	@Override
	public boolean confirm(final String title, final String msg) {
		return true;
	}

	@Override
	public int runUI() {
		return 0;
	}

	@Override
	public void failureExit(final String string) {
		error(string);
		System.exit(0);
	}

	@Override
	public int openPickWorkspaceDialog() {
		return 0;
	}

	@Override
	public void clearInitialLayout(final boolean b) {}

	@Override
	public void runInWorkspace(final Consumer<IProgressMonitor> r) {
		r.accept(null);
	}

	@Override
	public IPath openSelectContainerDialog(final String title, final String msg) {
		return null;
	}

}
