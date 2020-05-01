/*******************************************************************************************************
 *
 * gama.ui.base.utils.SwtGui.java, in plugin gama.ui.base.shared, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.ui.base.utils;

import static java.lang.System.setProperty;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.services.ISourceProviderService;

import gama.GAMA;
import gama.common.interfaces.IAgent;
import gama.common.interfaces.IModel;
import gama.common.interfaces.experiment.IExperimentController;
import gama.common.interfaces.experiment.IExperimentPlan;
import gama.common.interfaces.experiment.IParameter;
import gama.common.interfaces.gui.IConsoleDisplayer;
import gama.common.interfaces.gui.IGamaView;
import gama.common.interfaces.gui.IGamaView.Error;
import gama.common.interfaces.gui.IGamaView.Parameters;
import gama.common.interfaces.gui.IGamaView.Test;
import gama.common.interfaces.gui.IGamaView.User;
import gama.common.interfaces.gui.IGui;
import gama.common.interfaces.gui.IStatusDisplayer;
import gama.common.interfaces.outputs.IDisplayCreator.DisplayDescription;
import gama.common.interfaces.outputs.IDisplayOutput;
import gama.common.interfaces.outputs.IDisplaySurface;
import gama.common.interfaces.outputs.IOutputManager;
import gama.common.preferences.GamaPreferences;
import gama.core.application.bundles.GamaBundleLoader;
import gama.core.outputs.InspectDisplayOutput;
import gama.dev.utils.DEBUG;
import gama.kernel.experiment.ExperimentAgent;
import gama.kernel.simulation.SimulationAgent;
import gama.metamodel.shape.GamaPoint;
import gama.metamodel.shape.IShape;
import gama.runtime.ISimulationStateProvider;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.ui.base.ApplicationWorkbenchAdvisor;
import gama.ui.base.GamaUIPreferences;
import gama.ui.base.PickWorkspaceDialog;
import gama.ui.base.Splash;
import gama.ui.base.dialogs.Messages;
import gama.ui.base.interfaces.IDisplayLayoutManager;
import gama.ui.base.interfaces.IOpenGLInitializer;
import gama.ui.base.interfaces.IRefreshHandler;
import gama.ui.base.interfaces.IRuntimeExceptionHandler;
import gama.ui.base.interfaces.ISpeedDisplayer;
import gama.ui.base.interfaces.IUserDialogFactory;
import gama.ui.base.parameters.EditorsDialog;
import gama.util.map.GamaMapFactory;
import gama.util.map.IMap;
import gaml.architecture.user.UserPanelStatement;
import gaml.compilation.interfaces.ISymbol;
import gaml.statements.test.CompoundSummary;

/**
 * Written by drogoul Modified on 6 mai 2011
 *
 * @todo Description
 *
 */
public class SwtGui implements IGui {

	public volatile static boolean ALL_TESTS_RUNNING;
	public static final String CLEAR_WORKSPACE = "clearWorkspace";

	private IAgent highlightedAgent;
	private GamaPoint mouseLocationInModel;
	public static Splash splash = new Splash();

	public SwtGui() {
		Display.setAppName("Gama");
		Display.setAppVersion(GAMA.VERSION);
		GamaUIPreferences.initializePrefs();
	}

	@Override
	public boolean confirmClose(final IExperimentPlan exp) {
		if (exp == null || !GamaPreferences.Runtime.CORE_ASK_CLOSING.getValue())
			return true;
		PerspectiveHelper.getInstance().switchToSimulationPerspective();
		return Messages.question("Close simulation confirmation", "Do you want to close experiment '" + exp.getName()
				+ "' of model '" + exp.getModel().getName() + "' ?");
	}

	@Override
	public void tell(final String title, final String msg) {
		Messages.tell(title, msg);
	}

	@Override
	public void error(final String err) {
		Messages.error(err);
	}

	@Override
	public boolean confirm(final String title, final String msg) {
		return Messages.confirm(title, msg);
	}

	@Override
	public void runtimeError(final IScope scope, final GamaRuntimeException g) {
		if (g.isReported())
			return;
		if (GAMA.getFrontmostController() != null && GAMA.getFrontmostController().isDisposing())
			return;
		final IRuntimeExceptionHandler handler = getRuntimeExceptionHandler();
		if (!handler.isRunning()) {
			handler.start();
		}
		handler.offer(g);
		g.setReported();
	}

	@Override
	public void displayErrors(final IScope scope, final List<GamaRuntimeException> exceptions) {
		if (exceptions == null) {
			WorkbenchHelper.hideView(ERROR_VIEW_ID);
		} else {
			final IGamaView.Error v = (Error) showView(scope, ERROR_VIEW_ID, null, IWorkbenchPage.VIEW_ACTIVATE);
			if (v != null) {
				v.displayErrors();
			}
		}
	}

	@Override
	public IGamaView.Test openTestView(final IScope scope, final boolean allTests) {
		ALL_TESTS_RUNNING = allTests;
		final IGamaView.Test v = (Test) showView(scope, TEST_VIEW_ID, null, IWorkbenchPage.VIEW_ACTIVATE);
		if (v != null) {
			v.startNewTestSequence(allTests);
		}
		return v;
	}

	@Override
	public void displayTestsResults(final IScope scope, final CompoundSummary<?, ?> summary) {
		final IGamaView.Test v = (Test) WorkbenchHelper.getPage().findView(TEST_VIEW_ID);
		if (v != null) {
			v.addTestResult(summary);
		}
	}

	@Override
	public void endTestDisplay() {
		final IGamaView.Test v = (Test) WorkbenchHelper.getPage().findView(TEST_VIEW_ID);
		if (v != null) {
			v.finishTestSequence();
		}
		getUIService(IRefreshHandler.class).refreshNavigator();
	}

	@Override
	public void clearErrors(final IScope scope) {
		final IRuntimeExceptionHandler handler = getRuntimeExceptionHandler();
		handler.clearErrors();
	}

	private Object internalShowView(final String viewId, final String secondaryId, final int code) {
		if (GAMA.getFrontmostController() != null && GAMA.getFrontmostController().isDisposing())
			return null;
		final Object[] result = new Object[1];
		WorkbenchHelper.run(() -> {
			try {
				final IWorkbenchPage page = WorkbenchHelper.getPage();
				if (page != null) {
					page.zoomOut();
					final String second = secondaryId == null ? null
							: secondaryId + "@@@" + String.valueOf(System.currentTimeMillis());
					// The goal here is to address #2441 by randomizing the ids of views.
					// DEBUG.LOG("Opening view " + viewId + " " + second);
					result[0] = page.showView(viewId, second, code);
				}
			} catch (final Exception e) {
				result[0] = e;
			}
		});
		return result[0];
	}

	@Override
	public boolean copyToClipboard(final String text) {
		WorkbenchHelper.asyncRun(() -> {
			final Clipboard clipboard = new Clipboard(WorkbenchHelper.getDisplay());
			final TextTransfer textTransfer = TextTransfer.getInstance();
			final Transfer[] transfers = new Transfer[] { textTransfer };
			final Object[] data = new Object[] { text };
			clipboard.setContents(data, transfers);
			clipboard.dispose();
		});
		return true;
	}

	@Override
	public void openWelcomePage(final boolean ifEmpty) {
		WebHelper.openWelcomePage(ifEmpty);
	}

	@Override
	public IGamaView showView(final IScope scope, final String viewId, final String secondaryId, final int code) {

		Object o = internalShowView(viewId, secondaryId, code);
		if (o instanceof IWorkbenchPart) {
			if (o instanceof IGamaView)
				return (IGamaView) o;
			o = GamaRuntimeException.error("Impossible to open view " + viewId, GAMA.getRuntimeScope());
		}
		if (o instanceof Throwable) {
			GAMA.reportError(GAMA.getRuntimeScope(), GamaRuntimeException.create((Exception) o, GAMA.getRuntimeScope()),
					false);
		}
		return null;
	}

	public void hideMonitorView() {
		final IGamaView m = (IGamaView) WorkbenchHelper.findView(MONITOR_VIEW_ID, null, false);
		if (m != null) {
			m.reset();
			WorkbenchHelper.hideView(MONITOR_VIEW_ID);
		}
	}

	@Override
	public final boolean openSimulationPerspective(final IModel model, final String experimentName) {
		return PerspectiveHelper.getInstance().openSimulationPerspective(model, experimentName);
	}

	@Override
	public IDisplaySurface getDisplaySurfaceFor(final IDisplayOutput.Layered output) {
		IDisplaySurface surface = null;
		final String keyword = output.getData().getDisplayType();
		GamaBundleLoader.loadAllDisplays();
		DisplayDescription creator = DISPLAYS.get(keyword);
		if (creator != null) {
			surface = creator.create(output);
			surface.outputReloaded();
		} else
			throw GamaRuntimeException.error("Display " + keyword + " is not defined anywhere.", output.getScope());
		return surface;
	}

	@Override
	public Map<String, Object> openUserInputDialog(final IScope scope, final String title,
			final List<IParameter> parameters) {
		final IMap<String, Object> result = GamaMapFactory.createUnordered();
		for (final IParameter p : parameters) {
			result.put(p.getName(), p.getInitialValue(scope));
		}
		WorkbenchHelper.run(() -> {
			final EditorsDialog dialog = new EditorsDialog(scope, WorkbenchHelper.getShell(), parameters, title);
			if (dialog.open() == Window.OK) {
				result.putAll(dialog.getValues());
			}
		});
		return result;
	}

	@Override
	public void openUserControlPanel(final IScope scope, final UserPanelStatement panel) {
		WorkbenchHelper.run(() -> {
			IGamaView.User part = null;
			part = (User) showView(scope, USER_CONTROL_VIEW_ID, null, IWorkbenchPage.VIEW_CREATE);
			if (part != null) {
				part.initFor(scope, panel);
			}
			scope.setOnUserHold(true);
			try {
				WorkbenchHelper.getPage().showView(USER_CONTROL_VIEW_ID);
			} catch (final PartInitException e) {
				e.printStackTrace();
			}
		});

	}

	@Override
	public void closeDialogs(final IScope scope) {

		WorkbenchHelper.run(() -> {
			final IUserDialogFactory userDialogFactory = getUIService(IUserDialogFactory.class);
			if (userDialogFactory != null) {
				userDialogFactory.closeUserDialog();
			}
			WorkbenchHelper.hideView(USER_CONTROL_VIEW_ID);

		});

	}

	@Override
	public IAgent getHighlightedAgent() {
		return highlightedAgent;
	}

	@Override
	public void setHighlightedAgent(final IAgent a) {
		highlightedAgent = a;
	}

	private void editModelInternal(final Object object) {
		if (object instanceof URI) {
			final URI uri = (URI) object;
			final IModelEditor opener = getUIService(IModelEditor.class);
			opener.open(uri, true);
		} else if (object instanceof EObject) {
			editModelInternal(EcoreUtil.getURI((EObject) object));
		} else if (object instanceof String) {
			final IWorkspace workspace = ResourcesPlugin.getWorkspace();
			final IFile file = workspace.getRoot().getFile(new Path((String) object));
			editModelInternal(file);
		} else if (object instanceof IFile) {
			final IFile file = (IFile) object;
			if (!file.exists()) {
				DEBUG.LOG("File " + file.getFullPath().toString() + " does not exist in the workspace");
				return;
			}
			try {
				IDE.openEditor(WorkbenchHelper.getPage(), (IFile) object);
			} catch (final PartInitException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void editModel(final Object eObject) {
		GAMA.getGui().run("Opening editor", () -> editModelInternal(eObject), true);
	}

	@Override
	public void updateParameterView(final IScope scope, final IExperimentPlan exp) {

		WorkbenchHelper.run(() -> {
			if (!exp.hasParametersOrUserCommands())
				return;
			final IGamaView.Parameters view =
					(Parameters) showView(scope, PARAMETER_VIEW_ID, null, IWorkbenchPage.VIEW_ACTIVATE);
			view.addItem(exp);
			view.updateItemValues();

		});
	}

	@Override
	public void showParameterView(final IScope scope, final IExperimentPlan exp) {

		WorkbenchHelper.run(() -> {
			if (!exp.hasParametersOrUserCommands())
				return;
			final IGamaView.Parameters view =
					(Parameters) showView(scope, PARAMETER_VIEW_ID, null, IWorkbenchPage.VIEW_VISIBLE);
			if (view != null) {
				view.addItem(exp);
			}
		});
	}

	/**
	 * Method setSelectedAgent()
	 *
	 * @see msi.gama.common.interfaces.gui.IGui#setSelectedAgent(msi.gama.common.interfaces.IAgent)
	 */
	@Override
	public void setSelectedAgent(final IAgent a) {
		WorkbenchHelper.asyncRun(() -> {
			if (WorkbenchHelper.getPage() == null)
				return;
			if (a == null)
				return;
			try {
				final InspectDisplayOutput output = new InspectDisplayOutput(a);
				output.launch(a.getScope());
			} catch (final GamaRuntimeException g) {
				g.addContext("In opening the agent inspector");
				GAMA.reportError(GAMA.getRuntimeScope(), g, false);
			}
			final IViewReference r = WorkbenchHelper.getPage().findViewReference(IGui.AGENT_VIEW_ID, "");
			if (r != null) {
				WorkbenchHelper.getPage().bringToTop(r.getPart(true));
			}
		});
	}

	@Override
	public void prepareForExperiment(final IScope scope, final IExperimentPlan exp) {
		if (exp.isGui()) {
			// hideScreen();
			final IOpenGLInitializer initializer = getUIService(IOpenGLInitializer.class);
			if (initializer != null && !initializer.isDone()) {
				initializer.run();
			}
			WorkbenchHelper.setWorkbenchWindowTitle(exp.getName() + " - " + exp.getModel().getFilePath());
			final ExperimentAgent agent = exp.getAgent();
			final IOutputManager.Experiment manager = agent.getOutputManager();
			ISymbol layout = manager.getLayout();
			if (layout == null) {
				layout = manager;
			}
			final Boolean keepTabs = layout.getFacetValue(scope, "tabs", true);
			final Boolean keepToolbars = layout.getFacetValue(scope, "toolbars", null);
			final Boolean showParameters = layout.getFacetValue(scope, "parameters", null);
			final Boolean showConsoles = layout.getFacetValue(scope, "consoles", null);
			final Boolean showNavigator = layout.getFacetValue(scope, "navigator", null);
			final Boolean showControls = layout.getFacetValue(scope, "controls", null);
			final Boolean keepTray = layout.getFacetValue(scope, "tray", null);
			boolean showEditors;
			if (layout.hasFacet("editors")) {
				showEditors = layout.getFacetValue(scope, "editors", false);
			} else {
				showEditors = !GamaPreferences.Modeling.EDITOR_PERSPECTIVE_HIDE.getValue();
			}
			WorkbenchHelper.runInUI("Arranging views", 0, (m) -> {
				WorkbenchHelper.getPage().setEditorAreaVisible(showEditors);
				if (showConsoles != null && !showConsoles) {
					WorkbenchHelper.hideView(IGui.CONSOLE_VIEW_ID);
					WorkbenchHelper.hideView(IGui.INTERACTIVE_CONSOLE_VIEW_ID);
				} else {
					getConsole().showConsoleView(exp.getAgent());
				}
				if (showParameters != null && !showParameters) {
					WorkbenchHelper.hideView(IGui.PARAMETER_VIEW_ID);
				} else {
					updateParameterView(scope, exp);
				}
				if (showNavigator != null && !showNavigator) {
					WorkbenchHelper.hideView(IGui.NAVIGATOR_VIEW_ID);
				}
				if (showControls != null) {
					WorkbenchHelper.getWindow().setCoolBarVisible(showControls);
				}
				if (keepTray != null) {
					PerspectiveHelper.getInstance().showBottomTray(keepTray);
				}

				PerspectiveHelper.getInstance().initCurrentSimulationPerspective(keepTabs, keepToolbars, showControls,
						keepTray);

			});

		}

	}

	@Override
	public IPath openSelectContainerDialog(final String title, final String msg) {
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(WorkbenchHelper.getShell(), null, false,
				"Select a parent project or cancel to create a new project:");
		dialog.setTitle(title);
		dialog.showClosedProjects(false);
		final int result = dialog.open();
		if (result == MessageDialog.CANCEL)
			return null;
		return (IPath) dialog.getResult()[0];
	}

	/**
	 * Method cleanAfterExperiment()
	 *
	 * @see msi.gama.common.interfaces.gui.IGui#cleanAfterExperiment(msi.gama.common.interfaces.experiment.IExperimentPlan)
	 */
	@Override
	public void cleanAfterExperiment() {
		WorkbenchHelper.hideView(PARAMETER_VIEW_ID);
		hideMonitorView();
		getConsole().eraseConsole(true);
		final IGamaView icv = (IGamaView) WorkbenchHelper.findView(INTERACTIVE_CONSOLE_VIEW_ID, null, false);
		if (icv != null) {
			icv.reset();
		}
		final IRuntimeExceptionHandler handler = getRuntimeExceptionHandler();
		handler.stop();
	}

	private IRuntimeExceptionHandler getRuntimeExceptionHandler() {
		return getUIService(IRuntimeExceptionHandler.class);
	}

	public static List<IDisplaySurface> allDisplaySurfaces() {
		final List<IDisplaySurface> result = new ArrayList<>();
		final IViewReference[] viewRefs = WorkbenchHelper.getPage().getViewReferences();
		for (final IViewReference ref : viewRefs) {
			final IWorkbenchPart part = ref.getPart(false);
			if (part instanceof IGamaView.Display) {
				result.add(((IGamaView.Display) part).getDisplaySurface());
			}
		}
		return result;
	}

	/**
	 * Method updateSpeedDisplay()
	 *
	 * @see msi.gama.common.interfaces.gui.IGui#updateSpeedDisplay(java.lang.Double)
	 */
	@Override
	public void updateSpeedDisplay(final IScope scope, final Double d, final boolean notify) {
		final ISpeedDisplayer speedStatus = getUIService(ISpeedDisplayer.class);
		if (speedStatus != null) {
			WorkbenchHelper.asyncRun(() -> speedStatus.setInit(d, notify));

		}
	}

	@Override
	public void closeSimulationViews(final IScope scope, final boolean openModelingPerspective,
			final boolean immediately) {
		WorkbenchHelper.run(() -> {
			final IWorkbenchPage page = WorkbenchHelper.getPage();
			final IViewReference[] views = page.getViewReferences();

			for (final IViewReference view : views) {
				final IViewPart part = view.getView(false);
				if (part instanceof IGamaView) {
					DEBUG.OUT("Closing " + view.getId());
					((IGamaView) part).close(scope);
				}
			}
			if (openModelingPerspective) {
				DEBUG.OUT("Deleting simulation perspective and opening immediately the modeling perspective = "
						+ immediately);
				PerspectiveHelper.getInstance().deleteCurrentSimulationPerspective();
				PerspectiveHelper.getInstance().openModelingPerspective(immediately, false);
			}

			getStatus(scope).neutralStatus("No simulation running");
		});

	}

	@Override
	public String getExperimentState(final String uid) {
		final IExperimentController controller = GAMA.getFrontmostController();
		if (controller == null)
			return NONE;
		else if (controller.getScheduler().paused)
			return PAUSED;
		return RUNNING;
	}

	@Override
	public void updateExperimentState(final IScope scope, final String forcedState) {
		// DEBUG.OUT("STATE: " + forcedState);
		final ISourceProviderService service = getUIService(ISourceProviderService.class);
		final ISimulationStateProvider stateProvider =
				(ISimulationStateProvider) service.getSourceProvider("gama.ui.base.experiment.SimulationRunningState");
		if (stateProvider != null) {
			WorkbenchHelper.run(() -> stateProvider.updateStateTo(forcedState));
		}
	}

	@Override
	public void updateExperimentState(final IScope scope) {
		updateExperimentState(scope, getExperimentState(""));
	}

	@Override
	public void updateViewTitle(final IDisplayOutput out, final SimulationAgent agent) {
		WorkbenchHelper.run(() -> {
			final IViewPart part =
					WorkbenchHelper.findView(out.getViewId(), out.isUnique() ? null : out.getName(), true);
			if (part != null && part instanceof IGamaView) {
				((IGamaView) part).changePartNameWithSimulation(agent);
			}
		});

	}

	@Override
	public void updateDecorator(final String id) {
		WorkbenchHelper.asyncRun(() -> WorkbenchHelper.getWorkbench().getDecoratorManager().update(id));

	}

	@Override
	public IStatusDisplayer getStatus(final IScope scope) {
		return getUIService(IStatusDisplayer.class);
	}

	@Override
	public IConsoleDisplayer getConsole() {
		return getUIService(IConsoleDisplayer.class);
	}

	@Override
	public void run(final String taskName, final Runnable r, final boolean asynchronous) {

		if (asynchronous) {
			WorkbenchHelper.runInUI(taskName, 0, (m) -> r.run());
		} else {
			WorkbenchHelper.run(r);
		}
	}

	@Override
	public void runInWorkspace(final Consumer<IProgressMonitor> r) {
		WorkbenchHelper.runInWorkspace(r);
	}

	@Override
	public void setFocusOn(final IShape shape) {
		for (final IDisplaySurface surface : this.allDisplaySurfaces()) {
			surface.focusOn(shape);
		}
		GAMA.getExperiment().refreshAllOutputs();
	}

	@Override
	public void applyLayout(final IScope scope, final Object layout) {
		final IDisplayLayoutManager manager = getUIService(IDisplayLayoutManager.class);
		if (manager != null) {
			manager.applyLayout(layout);
		}
	}

	@Override
	public GamaPoint getMouseLocationInModel() {
		return mouseLocationInModel;
	}

	@Override
	public void setMouseLocationInModel(final GamaPoint location) {
		mouseLocationInModel = location;
	}

	@Override
	public void exit() {
		WorkbenchHelper.asyncRun(() -> {
			if (PlatformUI.isWorkbenchRunning()) {
				PlatformUI.getWorkbench().close();
			}
		});

	}

	@Override
	public void openInteractiveConsole(final IScope scope) {
		this.showView(scope, INTERACTIVE_CONSOLE_VIEW_ID, null, IWorkbenchPage.VIEW_VISIBLE);

	}

	@Override
	public boolean toggleFullScreenMode() {
		final IViewPart part = WorkbenchHelper.findFrontmostGamaViewUnderMouse();
		if (part instanceof IGamaView.Display) {
			((IGamaView.Display) part).toggleFullScreen();
			return true;
		}
		return false;
	}

	@Override
	public void refreshNavigator() {
		final IRefreshHandler refresh = getUIService(IRefreshHandler.class);
		if (refresh != null) {
			refresh.completeRefresh(null);
		}

	}

	@Override
	public boolean isInDisplayThread() {
		return EventQueue.isDispatchThread() || Display.getCurrent() != null;
	}

	@Override
	public <T> T getUIService(final Class<T> clazz) {
		return WorkbenchHelper.getService(clazz);
	}

	@Override
	public int runUI() {

		final boolean removeWorkbenchXMI = PrefUtil.getInternalPreferenceStore().getBoolean(CLEAR_WORKSPACE);
		if (removeWorkbenchXMI) {
			setProperty(org.eclipse.e4.ui.workbench.IWorkbench.CLEAR_PERSISTED_STATE, "true");
			clearInitialLayout(false);
		}

		final Display display = Display.getDefault();
		splash.open();
		final int result = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
		if (display != null) {
			display.dispose();
		}
		return result;
	}

	@Override
	public void failureExit(final String msg) {
		/* If we dont or cant remember and the location is set, we cant do anything as we need a workspace */
		error(msg);
		exit();
		System.exit(0);
	}

	@Override
	public void clearInitialLayout(final boolean clear) {
		PrefUtil.getInternalPreferenceStore().setValue(CLEAR_WORKSPACE, Boolean.valueOf(clear).toString());
		PrefUtil.saveInternalPrefs();
	}

	@Override
	public int openPickWorkspaceDialog() {
		final int[] result = new int[1];
		Display.getDefault().syncExec(() -> result[0] = new PickWorkspaceDialog().open());
		return result[0];
	}

}
