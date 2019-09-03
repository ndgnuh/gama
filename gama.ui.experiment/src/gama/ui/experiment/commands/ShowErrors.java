/*********************************************************************************************
 *
 * 'ShowErrors.java, in plugin gama.ui.experiment.experiment, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.experiment.commands;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

import gama.ui.base.utils.WorkbenchHelper;
import msi.gama.common.interfaces.gui.IGui;
import msi.gama.common.preferences.GamaPreferences;
import msi.gama.runtime.GAMA;

public class ShowErrors extends AbstractHandler implements IElementUpdater {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		GamaPreferences.Runtime.CORE_SHOW_ERRORS.set(!GamaPreferences.Runtime.CORE_SHOW_ERRORS.getValue());
		final ICommandService service =
				HandlerUtil.getActiveWorkbenchWindowChecked(event).getService(ICommandService.class);
		service.refreshElements(event.getCommand().getId(), null);
		if (GamaPreferences.Runtime.CORE_SHOW_ERRORS.getValue()) {
			GAMA.getGui().showView(null, IGui.ERROR_VIEW_ID, null, IWorkbenchPage.VIEW_VISIBLE);
		} else {
			WorkbenchHelper.hideView(IGui.ERROR_VIEW_ID);
		}
		return null;
	}

	@Override
	public void updateElement(final UIElement element, final Map parameters) {
		element.setChecked(GamaPreferences.Runtime.CORE_SHOW_ERRORS.getValue());
	}

}
