/*********************************************************************************************
 *
 * 'Activator.java, in plugin ummisco.gama.ui.shared, is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package ummisco.gama.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import msi.gama.runtime.GAMA;
import ummisco.gama.ui.utils.PreferencesHelper;
import ummisco.gama.ui.utils.SwtGui;

public class Activator extends AbstractUIPlugin {

	public Splash splash = new Splash();

	public static Activator instance;

	public Activator() {

		PreferencesHelper.initialize();
		instance = this;
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		Display.setAppName("Gama");
		Display.setAppVersion("1.8.0");
		splash.open();
		if (GAMA.getRegularGui() == null) {
			GAMA.setRegularGui(new SwtGui());
		}

	}

}
