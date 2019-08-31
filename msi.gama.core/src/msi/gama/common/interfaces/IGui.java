/*******************************************************************************************************
 *
 * msi.gama.common.interfaces.IGui.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gama.common.interfaces;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import msi.gama.common.interfaces.IDisplayCreator.DisplayDescription;
import msi.gama.kernel.experiment.IExperimentPlan;
import msi.gama.kernel.model.IModel;
import msi.gama.kernel.simulation.SimulationAgent;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.metamodel.shape.IShape;
import msi.gama.outputs.IDisplayOutput;
import msi.gama.outputs.LayeredDisplayOutput;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.util.GamaMapFactory;
import msi.gaml.architecture.user.UserPanelStatement;
import msi.gaml.statements.test.CompoundSummary;
import msi.gaml.types.IType;

/**
 * The interface IGui. Represents objects that act on behalf of a concrete GUI implementation (RCP, Headless, etc.)
 *
 * @author drogoul
 * @since 18 dec. 2011
 *
 */
public interface IGui {

	int ERROR = 0;
	int WAIT = 1;
	int INFORM = 2;
	int NEUTRAL = 3;
	int USER = 4;

	// Equivalence of SWT constants
	int MouseDown = 3;
	int MouseUp = 4;
	int MouseMove = 5;
	int MouseEnter = 6;
	int MouseExit = 7;
	int DragDetect = 29;
	int MouseHover = 32;

	Map<String, DisplayDescription> DISPLAYS = GamaMapFactory.createUnordered();
	String MONITOR_VIEW_ID = "ummisco.gama.application.view.MonitorView";
	String INTERACTIVE_CONSOLE_VIEW_ID = "ummisco.gama.application.view.InteractiveConsoleView";
	String AGENT_VIEW_ID = "ummisco.gama.application.view.AgentInspectView";
	String TABLE_VIEW_ID = "ummisco.gama.application.view.TableAgentInspectView";
	String LAYER_VIEW_ID = "ummisco.gama.application.view.LayeredDisplayView";
	String GL_LAYER_VIEW_ID = "ummisco.gama.application.view.OpenGLDisplayView";
	String GL_LAYER_VIEW_ID2 = "ummisco.gama.application.view.OpenGLDisplayView2";

	String ERROR_VIEW_ID = "ummisco.gama.application.view.ErrorView";
	String TEST_VIEW_ID = "ummisco.gama.application.view.TestView";
	String PARAMETER_VIEW_ID = "ummisco.gama.application.view.ParameterView";

	String NAVIGATOR_VIEW_ID = "msi.gama.gui.view.GamaNavigator";
	String NAVIGATOR_LIGHTWEIGHT_DECORATOR_ID = "msi.gama.application.decorator";
	String CONSOLE_VIEW_ID = "ummisco.gama.application.view.ConsoleView";
	String USER_CONTROL_VIEW_ID = "msi.gama.views.userControlView";

	String PAUSED = "STOPPED";
	String FINISHED = "FINISHED";
	String RUNNING = "RUNNING";
	String NOTREADY = "NOTREADY";

	String NONE = "NONE";
	String PERSPECTIVE_MODELING_ID = "msi.gama.application.perspectives.ModelingPerspective";

	IStatusDisplayer getStatus(IScope scope);

	IConsoleDisplayer getConsole();

	IGamaView showView(IScope scope, String viewId, String name, int code);

	void tell(String title, String message);

	void error(String error);

	void showParameterView(IScope scope, IExperimentPlan exp);

	void clearErrors(IScope scope);

	void runtimeError(final IScope scope, GamaRuntimeException g);

	boolean confirmClose(IExperimentPlan experiment);

	boolean copyToClipboard(String text);

	boolean openSimulationPerspective(IModel model, String experimentId);

	IDisplaySurface getDisplaySurfaceFor(final LayeredDisplayOutput output);

	Map<String, Object> openUserInputDialog(IScope scope, String title, Map<String, Object> initialValues,
			Map<String, IType<?>> types);

	void openUserControlPanel(IScope scope, UserPanelStatement panel);

	void closeDialogs(IScope scope);

	IAgent getHighlightedAgent();

	void setHighlightedAgent(IAgent a);

	void setSelectedAgent(IAgent a);

	void updateParameterView(IScope scope, IExperimentPlan exp);

	void prepareForExperiment(IScope scope, IExperimentPlan exp);

	void cleanAfterExperiment();

	void editModel(Object eObject);

	void updateSpeedDisplay(IScope scope, Double d, boolean notify);

	void closeSimulationViews(IScope scope, boolean andOpenModelingPerspective, boolean immediately);

	String getExperimentState(String uid);

	void updateExperimentState(IScope scope, String state);

	void updateExperimentState(IScope scope);

	void updateViewTitle(IDisplayOutput output, SimulationAgent agent);

	void openWelcomePage(boolean b);

	void updateDecorator(String string);

	void run(String taskName, Runnable opener, boolean asynchronous);

	void runInWorkspace(final Consumer<IProgressMonitor> r);

	void setFocusOn(IShape o);

	void applyLayout(IScope scope, Object layout);

	void displayErrors(IScope scope, List<GamaRuntimeException> newExceptions);

	GamaPoint getMouseLocationInModel();

	void setMouseLocationInModel(GamaPoint modelCoordinates);

	<T> T getUIService(Class<T> clazz);

	/**
	 * The main UI loop
	 *
	 * @return
	 */
	int runUI();

	void exit();

	void openInteractiveConsole(IScope scope);

	// Tests

	IGamaView.Test openTestView(IScope scope, boolean remainOpen);

	void displayTestsResults(IScope scope, CompoundSummary<?, ?> summary);

	void endTestDisplay();

	/**
	 * Tries to put the frontmost display in full screen mode or in normal view mode if it is already in full screen
	 *
	 * @return true if the toggle has succeeded
	 */
	boolean toggleFullScreenMode();

	void refreshNavigator();

	boolean isInDisplayThread();

	boolean confirm(String title, String msg);

	void failureExit(String string);

	int openPickWorkspaceDialog();

	void clearInitialLayout(boolean b);

	IPath openSelectContainerDialog(final String title, final String msg);

}
