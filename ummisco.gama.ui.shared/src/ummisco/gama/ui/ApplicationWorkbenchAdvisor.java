/*********************************************************************************************
 *
 * 'ApplicationWorkbenchAdvisor.java, in plugin ummisco.gama.application, is part of the source code of the GAMA
 * modeling and simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package ummisco.gama.ui;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.PluginActionBuilder;
import org.eclipse.ui.internal.ide.application.DelayedEventsProcessor;
import org.eclipse.ui.internal.ide.application.IDEWorkbenchAdvisor;
import org.eclipse.ui.statushandlers.AbstractStatusHandler;
import org.eclipse.ui.statushandlers.StatusAdapter;

import msi.gama.common.interfaces.IEventLayerDelegate;
import msi.gama.common.interfaces.IGui;
import msi.gama.common.util.FileUtils;
import msi.gama.outputs.layers.EventLayerStatement;
import msi.gama.runtime.GAMA;
import msi.gama.runtime.concurrent.GamaExecutorService;
import msi.gaml.compilation.kernel.GamaBundleLoader;
import ummisco.gama.application.workspace.WorkspaceManager;
import ummisco.gama.application.workspace.WorkspaceModelsManager;
import ummisco.gama.dev.utils.DEBUG;
import ummisco.gama.ui.utils.PerspectiveHelper;
import ummisco.gama.ui.utils.SwtGui;

public class ApplicationWorkbenchAdvisor extends IDEWorkbenchAdvisor {

	public static class OpenDocumentEventProcessor extends DelayedEventsProcessor {

		OpenDocumentEventProcessor(final Display display) {
			super(display);
		}

		private final ArrayList<String> filesToOpen = new ArrayList<>(1);

		@Override
		public void handleEvent(final Event event) {
			if (event.text != null) {
				filesToOpen.add(event.text);
				DEBUG.OUT("RECEIVED FILE TO OPEN: " + event.text);
			}
		}

		@Override
		public void catchUp(final Display display) {
			if (filesToOpen.isEmpty())
				return;

			final String[] filePaths = filesToOpen.toArray(new String[filesToOpen.size()]);
			filesToOpen.clear();

			for (final String path : filePaths) {
				WorkspaceModelsManager.instance.openModelPassedAsArgument(path);
			}
		}
	}

	{
		DEBUG.OFF();
	}

	public ApplicationWorkbenchAdvisor() {
		super(new OpenDocumentEventProcessor(Display.getDefault()));
	}

	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(final IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(this, configurer);
	}

	@Override
	public void initialize(final IWorkbenchConfigurer configurer) {
		ResourcesPlugin.getPlugin().getStateLocation();
		try {
			super.initialize(configurer);
			IDE.registerAdapters();
			configurer.setSaveAndRestore(true);

			final IDecoratorManager dm = configurer.getWorkbench().getDecoratorManager();
			dm.setEnabled("org.eclipse.pde.ui.binaryProjectDecorator", false);
			dm.setEnabled("org.eclipse.team.svn.ui.decorator.SVNLightweightDecorator", false);
			dm.setEnabled("ummisco.gama.application.decorator", true);
			dm.setEnabled("org.eclipse.ui.LinkedResourceDecorator", false);
			dm.setEnabled("org.eclipse.ui.VirtualResourceDecorator", false);
			dm.setEnabled("org.eclipse.xtext.builder.nature.overlay", false);
			if (Display.getCurrent() != null) {
				Display.getCurrent().getThread().setUncaughtExceptionHandler(GamaExecutorService.EXCEPTION_HANDLER);
			}
		} catch (final CoreException e) {
			// e.printStackTrace();
		}
		PluginActionBuilder.setAllowIdeLogging(false);
	}

	@Override
	public void postStartup() {
		super.postStartup();

		FileUtils.cleanCache();
		final String[] args = Platform.getApplicationArgs();
		if (false) {
			DEBUG.LOG("Arguments received by GAMA : " + Arrays.toString(args));
		}
		if (args.length > 0 && args[0].contains("launcher.defaultAction")
				&& !args[0].contains("--launcher.defaultAction"))
			return;
		if (args.length >= 1) {

			if (args[args.length - 1].endsWith(".gamr")) {
				for (final IEventLayerDelegate delegate : EventLayerStatement.delegates) {
					if (delegate.acceptSource(null, "launcher")) {
						delegate.createFrom(null, args[args.length - 1], null);
					}
				}
			} else {
				WorkspaceModelsManager.instance.openModelPassedAsArgument(args[args.length - 1]);
			}
		}
		SwtGui.splash.close();
		//
		if (checkCopyOfBuiltInModels()) {
			linkSampleModelsToWorkspace();
		}
		//
		GAMA.getGui().updateExperimentState(null, SwtGui.NONE);
	}

	public static void linkSampleModelsToWorkspace() {

		final WorkspaceJob job = new WorkspaceJob("Updating the Built-in Models Library") {

			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				// DEBUG.OUT("Asynchronous link of models library...");
				GAMA.getGui().refreshNavigator();
				return GamaBundleLoader.ERRORED ? Status.CANCEL_STATUS : Status.OK_STATUS;
			}

		};
		job.setUser(true);
		job.schedule();

	}

	protected boolean checkCopyOfBuiltInModels() {

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject[] projects = workspace.getRoot().getProjects();
		// If no projects are registered at all, we are facing a fresh new workspace
		if (projects.length == 0)
			return true;
		return false;
	}

	@Override
	public String getInitialWindowPerspectiveId() {
		return IGui.PERSPECTIVE_MODELING_ID;
	}

	/**
	 * A workbench pre-shutdown method calls to prompt a confirmation of the shutdown and perform a saving of the
	 * workspace
	 */
	@Override
	public boolean preShutdown() {
		try {
			saveEclipsePreferences();
			GAMA.closeAllExperiments(true, true);
			PerspectiveHelper.getInstance().deleteCurrentSimulationPerspective();
			// So that they are not saved to the workbench.xmi file
			PerspectiveHelper.getInstance().cleanPerspectives();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return super.preShutdown();

	}

	@Override
	public void postShutdown() {
		try {
			super.postShutdown();
		} catch (final Exception e) {
			// Remove the trace of exceptions
			// e.printStackTrace();
		}
	}

	@Override
	public void preStartup() {
		// Suspend background jobs while we startup
		Job.getJobManager().suspend();
		// super.preStartup();
		/* Linking the stock models with the workspace if they are not already */

	}

	private void saveEclipsePreferences() {
		final IPreferencesService service = Platform.getPreferencesService();

		try (final FileOutputStream outputStream =
				new FileOutputStream(Platform.getInstanceLocation().getURL().getPath() + "/.gama.epf")) {
			service.exportPreferences(service.getRootNode(), WorkspaceManager.getPreferenceFilters(), outputStream);
		} catch (final CoreException | IOException e1) {}

	}

	/**
	 * Method getWorkbenchErrorHandler()
	 *
	 * @see org.eclipse.ui.internal.ide.application.IDEWorkbenchAdvisor#getWorkbenchErrorHandler()
	 */
	@Override
	public synchronized AbstractStatusHandler getWorkbenchErrorHandler() {
		return new AbstractStatusHandler() {

			@Override
			public void handle(final StatusAdapter statusAdapter, final int style) {
				final int severity = statusAdapter.getStatus().getSeverity();
				if (severity == IStatus.INFO || severity == IStatus.CANCEL)
					return;
				final Throwable e = statusAdapter.getStatus().getException();
				if (e instanceof OutOfMemoryError) {
					GamaExecutorService.EXCEPTION_HANDLER.uncaughtException(Thread.currentThread(), e);
				}
				final String message = statusAdapter.getStatus().getMessage();
				// Stupid Eclipse
				if (!message.contains("File toolbar contribution item") && !message.contains("Duplicate template id")) {
					DEBUG.OUT("GAMA Caught a workbench message : " + message);
				}
				if (e != null) {
					e.printStackTrace();
				}
			}
		};
	}

}
