package ummisco.gama.ui.utils;

import msi.gama.common.interfaces.IGui;
import msi.gama.kernel.model.IModel;

public interface IPerspectiveHelper {

	String PERSPECTIVE_MODELING_ID = IGui.PERSPECTIVE_MODELING_ID;
	String PERSPECTIVE_SIMULATION_ID = "msi.gama.application.perspectives.SimulationPerspective";
	String PERSPECTIVE_SIMULATION_FRAGMENT = "Simulation";

	void cleanPerspectives();

	boolean isModelingPerspective();

	boolean isSimulationPerspective();

	boolean openModelingPerspective(boolean immediately, boolean memorizeEditors);

	void showBottomTray(Boolean show);

	boolean switchToSimulationPerspective();

	boolean openSimulationPerspective(IModel model, String experimentName);

	Boolean keepTabs();

	Boolean keepToolbars();

	Boolean keepControls();

	Boolean keepTray();

	boolean showOverlays();

	void deleteCurrentSimulationPerspective();

	void initCurrentSimulationPerspective(Boolean keepTabs, Boolean keepToolbars, Boolean showControls,
		Boolean keepTray);

	void toggleTabsOfCurrentSimulationPerspective();

}