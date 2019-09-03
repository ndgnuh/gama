/*******************************************************************************************************
 *
 * gama.core.application.workbench.PerspectiveHelper.java, in plugin gama.core.application, is part of the source code of
 * the GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.ui.base.utils;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.impl.PerspectiveImpl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.registry.PerspectiveDescriptor;
import org.eclipse.ui.internal.registry.PerspectiveRegistry;

import msi.gama.common.interfaces.IModel;
import msi.gama.common.interfaces.gui.IGui;
import msi.gama.common.preferences.GamaPreferences;

public class PerspectiveHelper {

	public static String PERSPECTIVE_MODELING_ID = IGui.PERSPECTIVE_MODELING_ID;
	static String PERSPECTIVE_SIMULATION_ID = "gama.core.application.perspectives.SimulationPerspective";
	static String PERSPECTIVE_SIMULATION_FRAGMENT = "Simulation";

	private static final String BOTTOM_TRIM_ID = "org.eclipse.ui.trim.status"; //$NON-NLS-1$

	public static String currentPerspectiveId = PERSPECTIVE_MODELING_ID;
	public static IPerspectiveDescriptor currentSimulationPerspective = null;
	public static IEditorInput activeEditor;
	private static PerspectiveHelper instance = new PerspectiveHelper();

	public static PerspectiveHelper getInstance() {
		return instance;
	}

	private PerspectiveHelper() {}

	static boolean matches(final String id) {
		return !id.equals(PERSPECTIVE_SIMULATION_ID) && id.contains(PERSPECTIVE_SIMULATION_FRAGMENT);
	}

	public void cleanPerspectives() {
		final EModelService e = PlatformUI.getWorkbench().getService(EModelService.class);
		final MApplication a = PlatformUI.getWorkbench().getService(MApplication.class);

		final List<PerspectiveImpl> perspectives = e.findElements(a, PerspectiveImpl.class, EModelService.ANYWHERE,
				element -> matches(element.getElementId()));
		for (final PerspectiveImpl p : perspectives) {
			// DEBUG.OUT("Dirty perspective implementation found and removed: " + p.getElementId());
			p.getParent().getChildren().remove(p);
		}

		final IPerspectiveRegistry reg = PlatformUI.getWorkbench().getPerspectiveRegistry();
		for (final IPerspectiveDescriptor desc : reg.getPerspectives()) {
			if (matches(desc.getId())) {
				// DEBUG.OUT("Dirty perspective descriptor found and removed: " + desc.getId());
				reg.deletePerspective(desc);
			}
		}

		// DEBUG.OUT("Current perspectives: " + listCurrentPerspectives());
	}

	private void deletePerspectiveFromApplication(final IPerspectiveDescriptor d) {
		final MApplication a = PlatformUI.getWorkbench().getService(MApplication.class);
		final EModelService e = PlatformUI.getWorkbench().getService(EModelService.class);
		final List<PerspectiveImpl> perspectives = e.findElements(a, PerspectiveImpl.class, EModelService.ANYWHERE,
				element -> element.getElementId().contains(d.getId()));
		for (final PerspectiveImpl p : perspectives) {
			// DEBUG.OUT("Dirty perspective implementation found and removed: " + p.getElementId());
			p.getParent().getChildren().remove(p);
		}
	}

	private IPerspectiveRegistry getPerspectiveRegistry() {
		return PlatformUI.getWorkbench().getPerspectiveRegistry();
	}

	public boolean isModelingPerspective() {
		return currentPerspectiveId.equals(PERSPECTIVE_MODELING_ID);
	}

	public boolean isSimulationPerspective() {
		return isSimulationPerspective(currentPerspectiveId);
	}

	private boolean isSimulationPerspective(final String perspectiveId) {
		return perspectiveId.contains(PERSPECTIVE_SIMULATION_FRAGMENT);
	}

	public final boolean openModelingPerspective(final boolean immediately, final boolean memorizeEditors) {
		// AD 08/18: turn off autosave to prevent workspace corruption
		return openPerspective(PERSPECTIVE_MODELING_ID, immediately, false, memorizeEditors);
	}

	/* Get the MUIElement representing the status bar for the given window */
	private MUIElement getTrimStatus(final WorkbenchWindow window) {
		final EModelService modelService = window.getService(EModelService.class);
		final MUIElement searchRoot = window.getModel();
		return modelService.find(BOTTOM_TRIM_ID, searchRoot);
	}

	public void showBottomTray(final Boolean show) {
		final WorkbenchWindow window = WorkbenchHelper.getWindow();
		final MUIElement trimStatus = getTrimStatus(window);
		if (trimStatus != null) {
			// toggle statusbar visibility
			trimStatus.setVisible(show);
		}

	}

	public final boolean switchToSimulationPerspective() {
		if (currentSimulationPerspective == null)
			return false;
		IWorkbenchPage activePage = null;
		try {
			activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		if (activePage == null)
			return false;
		final IWorkbenchPage page = activePage;
		final WorkbenchWindow window = (WorkbenchWindow) page.getWorkbenchWindow();
		if (page.getPerspective().equals(currentSimulationPerspective))
			return true;
		Display.getDefault().syncExec(() -> {
			memorizeActiveEditor(page);
			try {
				page.setPerspective(currentSimulationPerspective);
			} catch (final NullPointerException e) {
				// DEBUG.ERR(
				// "NPE in WorkbenchPage.setPerspective(). See Issue #1602.
				// Working around the bug in e4...");
				page.setPerspective(currentSimulationPerspective);
			}
			final Boolean showControls = keepControls();
			if (showControls != null) {
				window.setCoolBarVisible(showControls);
			}
			final Boolean keepTray = keepTray();
			if (keepTray != null) {
				showBottomTray(keepTray);
			}
			applyActiveEditor(page);
		});
		currentPerspectiveId = currentSimulationPerspective.getId();
		return true;
	}

	public final boolean openSimulationPerspective(final IModel model, final String experimentName) {
		if (model == null)
			return false;
		final String name = getNewPerspectiveName(model.getName(), experimentName);
		return openPerspective(name, true, false, true);
	}

	private PerspectiveDescriptor getSimulationDescriptor() {
		return (PerspectiveDescriptor) getPerspectiveRegistry().findPerspectiveWithId(PERSPECTIVE_SIMULATION_ID);
	}

	private IPerspectiveDescriptor findOrBuildPerspectiveWithId(final String id) {
		if (currentSimulationPerspective != null && currentSimulationPerspective.getId().equals(id))
			return currentSimulationPerspective;
		final PerspectiveRegistry pr = (PerspectiveRegistry) getPerspectiveRegistry();
		IPerspectiveDescriptor tempDescriptor = pr.findPerspectiveWithId(id);
		if (tempDescriptor == null) {
			getPerspectiveRegistry()
					.revertPerspective(getPerspectiveRegistry().findPerspectiveWithId(PERSPECTIVE_SIMULATION_ID));
			tempDescriptor = new SimulationPerspectiveDescriptor(id);
		}
		return tempDescriptor;
	}

	@SuppressWarnings ({ "rawtypes", "unchecked" })
	private void dirtySavePerspective(final SimulationPerspectiveDescriptor sp) {
		try {
			final java.lang.reflect.Field descField = PerspectiveRegistry.class.getDeclaredField("descriptors");
			descField.setAccessible(true);
			final Map m = (Map) descField.get(getPerspectiveRegistry());
			m.put(sp.getId(), sp);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private boolean openPerspective(final String perspectiveId, final boolean immediately, final boolean withAutoSave,
			final boolean memorizeEditors) {
		if (perspectiveId == null)
			return false;
		if (perspectiveId.equals(currentPerspectiveId))
			return true;

		IWorkbenchPage activePage = null;
		try {
			activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		} catch (final Exception e) {
			try {
				activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().openPage(perspectiveId, null);
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		if (activePage == null)
			return false;

		if (GamaPreferences.Modeling.EDITOR_PERSPECTIVE_SAVE.getValue()) {
			activePage.saveAllEditors(false);
		}

		if (memorizeEditors) {
			memorizeActiveEditor(activePage);
		}

		final IPerspectiveDescriptor oldDescriptor = activePage.getPerspective();
		final IPerspectiveDescriptor descriptor = findOrBuildPerspectiveWithId(perspectiveId);
		final IWorkbenchPage page = activePage;
		final WorkbenchWindow window = (WorkbenchWindow) page.getWorkbenchWindow();
		final Runnable r = () -> {
			try {
				page.setPerspective(descriptor);
			} catch (final NullPointerException e) {
				// DEBUG.ERR(
				// "NPE in WorkbenchPage.setPerspective(). See Issue #1602.
				// Working around the bug in e4...");
				page.setPerspective(descriptor);
			}
			activateAutoSave(withAutoSave);
			if (isSimulationPerspective(currentPerspectiveId) && isSimulationPerspective(perspectiveId)) {
				// DEBUG.OUT("Destroying perspective " + oldDescriptor.getId());
				page.closePerspective(oldDescriptor, false, false);
				getPerspectiveRegistry().deletePerspective(oldDescriptor);
			}

			currentPerspectiveId = perspectiveId;
			if (isSimulationPerspective(perspectiveId) && !descriptor.equals(currentSimulationPerspective)) {
				// Early activation or deactivation of editors based on the global preference
				page.setEditorAreaVisible(!GamaPreferences.Modeling.EDITOR_PERSPECTIVE_HIDE.getValue());
				deleteCurrentSimulationPerspective();
				currentSimulationPerspective = descriptor;
			}
			applyActiveEditor(page);
			final Boolean showControls = keepControls();
			if (showControls != null) {
				window.setCoolBarVisible(showControls);
			}
			final Boolean keepTray = keepTray();
			if (keepTray != null) {
				showBottomTray(keepTray);
			}
			// DEBUG.OUT("Perspective " + perspectiveId + " opened ");
		};
		if (immediately) {
			Display.getDefault().syncExec(r);
		} else {
			Display.getDefault().asyncExec(r);
		}
		return true;
	}

	private void applyActiveEditor(final IWorkbenchPage page) {
		if (activeEditor == null)
			return;
		final IEditorPart part = page.findEditor(activeEditor);
		if (part != null) {
			page.activate(part);
			// DEBUG.OUT("Applying memorized editor to " + page.getPerspective().getId() + " = " +
			// activeEditor.getName());
			// page.bringToTop(part);
		}

	}

	private void memorizeActiveEditor(final IWorkbenchPage page) {
		// DEBUG.OUT("Trying to memorize editor in " + page.getPerspective().getId());
		final IEditorPart part = page.isEditorAreaVisible() ? page.getActiveEditor() : null;
		if (part == null)
			return;
		activeEditor = part.getEditorInput();
		// DEBUG.OUT("Memorized editor in " + page.getPerspective().getId() + " = " + activeEditor.getName());

	}

	private void activateAutoSave(final boolean activate) {
		// DEBUG.OUT("auto-save activated: " + activate);
		Workbench.getInstance().setEnableAutoSave(activate);
		// ApplicationWorkbenchAdvisor.CONFIGURER.setSaveAndRestore(activate);
	}

	private IPerspectiveDescriptor getActivePerspective() {
		final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		final IPerspectiveDescriptor currentDescriptor = activePage.getPerspective();
		return currentDescriptor;

	}

	public Boolean keepTabs() {
		final IPerspectiveDescriptor d = getActivePerspective();
		if (d instanceof SimulationPerspectiveDescriptor)
			return ((SimulationPerspectiveDescriptor) d).keepTabs;
		else
			return true;
	}

	public Boolean keepToolbars() {
		final IPerspectiveDescriptor d = getActivePerspective();
		if (d instanceof SimulationPerspectiveDescriptor)
			return ((SimulationPerspectiveDescriptor) d).keepToolbars();
		else
			return null;
	}

	public Boolean keepControls() {
		final IPerspectiveDescriptor d = getActivePerspective();
		if (d instanceof SimulationPerspectiveDescriptor)
			return ((SimulationPerspectiveDescriptor) d).keepControls();
		else
			return true;
	}

	public Boolean keepTray() {
		final IPerspectiveDescriptor d = getActivePerspective();
		if (d instanceof SimulationPerspectiveDescriptor)
			return ((SimulationPerspectiveDescriptor) d).keepTray();
		else
			return true;
	}

	public boolean showOverlays() {
		return GamaPreferences.Displays.CORE_OVERLAY.getValue();
	}

	public class SimulationPerspectiveFactory implements IPerspectiveFactory {

		final IPerspectiveFactory original;

		SimulationPerspectiveFactory(final IPerspectiveFactory original) {
			this.original = original;
		}

		@Override
		public void createInitialLayout(final IPageLayout layout) {
			original.createInitialLayout(layout);
			// TODO do the rest... See SimulationPerspective
		}

	}

	public class SimulationPerspectiveDescriptor extends PerspectiveDescriptor {

		Boolean keepTabs = true;
		Boolean keepToolbars = null;
		Boolean keepControls = true;
		Boolean keepTray = true;

		SimulationPerspectiveDescriptor(final String id) {
			super(id, id, getSimulationDescriptor());
			dirtySavePerspective(this);
		}

		@Override
		public IPerspectiveFactory createFactory() {

			try {
				return new SimulationPerspectiveFactory(
						(IPerspectiveFactory) getConfigElement().createExecutableExtension("class"));
			} catch (final CoreException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		@Override
		public boolean hasCustomDefinition() {
			return true;
		}

		@Override
		public boolean isPredefined() {
			return false;
		}

		@Override
		public IConfigurationElement getConfigElement() {
			return getSimulationDescriptor().getConfigElement();
		}

		@Override
		public String getDescription() {
			return "Perspective for " + getId();
		}

		@Override
		public String getOriginalId() {
			return getId();
		}

		@Override
		public String getPluginId() {
			return getSimulationDescriptor().getPluginId();
		}

		public Boolean keepTabs() {
			return keepTabs;
		}

		public void keepTabs(final Boolean b) {
			keepTabs = b;
		}

		public Boolean keepToolbars() {
			return keepToolbars;
		}

		public void keepToolbars(final Boolean b) {
			keepToolbars = b;
		}

		public void keepControls(final Boolean b) {
			keepControls = b;
		}

		public Boolean keepControls() {
			return keepControls;
		}

		public void keepTray(final Boolean b) {
			keepTray = b;
		}

		public Boolean keepTray() {
			return keepTray;
		}

	}

	private String getNewPerspectiveName(final String model, final String experiment) {
		return PERSPECTIVE_SIMULATION_FRAGMENT + ":" + model + ":" + experiment;
	}

	public void deleteCurrentSimulationPerspective() {
		if (currentSimulationPerspective != null) {
			final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if (page != null) {
				page.closePerspective(currentSimulationPerspective, false, false);
				getPerspectiveRegistry().deletePerspective(currentSimulationPerspective);
				deletePerspectiveFromApplication(currentSimulationPerspective);
				// DEBUG.OUT("Perspective destroyed: " + currentSimulationPerspective.getId());
			}
			currentSimulationPerspective = null;
		}

	}

	private SimulationPerspectiveDescriptor getActiveSimulationPerspective() {
		final IPerspectiveDescriptor d = getActivePerspective();
		if (d instanceof SimulationPerspectiveDescriptor)
			return (SimulationPerspectiveDescriptor) d;
		else
			return null;
	}

	public void initCurrentSimulationPerspective(final Boolean keepTabs, final Boolean keepToolbars,
			final Boolean showControls, final Boolean keepTray) {
		final SimulationPerspectiveDescriptor sd = getActiveSimulationPerspective();
		if (sd != null) {
			sd.keepTabs(keepTabs);
			sd.keepToolbars(keepToolbars);
			sd.keepControls(showControls);
			sd.keepTray(keepTray);
		}

	}

	public void toggleTabsOfCurrentSimulationPerspective() {
		final SimulationPerspectiveDescriptor sd = getActiveSimulationPerspective();
		if (sd != null) {
			sd.keepTabs(!sd.keepTabs());
		}

	}

}
