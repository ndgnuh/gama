/*********************************************************************************************
 *
 * 'Activator.java, in plugin gama.ui.base.shared, is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.base;

import org.eclipse.ui.plugin.AbstractUIPlugin;

import gama.core.GamlRuntimeModule;
import gama.ui.base.utils.SwtGui;
import msi.gama.runtime.GAMA;

public class Activator extends AbstractUIPlugin {

	public Activator() {
		GAMA.setRegularGui(new SwtGui());
		// Early activation in case of a UI, in order to be ready for the navigator
		GamlRuntimeModule.staticInitialize();
	}

}
