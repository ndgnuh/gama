package ummisco.gama.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import msi.gama.runtime.GAMA;
import ummisco.gama.ui.utils.IPerspectiveHelper;

public class ToggleDisplayTabs extends AbstractHandler {

	// NOT YET READY FOR PRIME TIME
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		GAMA.getGui().getUIService(IPerspectiveHelper.class).toggleTabsOfCurrentSimulationPerspective();
		ArrangeDisplayViews
				.execute(new LayoutTreeConverter().convertCurrentLayout(ArrangeDisplayViews.listDisplayViews()));
		return this;
	}

}
