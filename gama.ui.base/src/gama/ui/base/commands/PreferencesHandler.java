/*********************************************************************************************
 *
 * 'PreferencesHandler.java, in plugin gama.ui.base.shared, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package gama.ui.base.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import gama.ui.base.views.GamaPreferencesView;

public class PreferencesHandler extends AbstractHandler {

	// GamaPreferencesView view;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		GamaPreferencesView.show();
		return null;
	}

}
