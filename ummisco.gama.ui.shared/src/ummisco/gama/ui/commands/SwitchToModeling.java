/*********************************************************************************************
 *
 * 'SwitchToModeling.java, in plugin ummisco.gama.ui.shared, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package ummisco.gama.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import msi.gama.runtime.GAMA;
import ummisco.gama.ui.utils.IPerspectiveHelper;

public class SwitchToModeling extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		GAMA.getGui().getUIService(IPerspectiveHelper.class).openModelingPerspective(true, true);
		return null;
	}
}
