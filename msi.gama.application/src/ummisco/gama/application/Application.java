/*********************************************************************************************
 *
 * 'Application.java, in plugin ummisco.gama.application, is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package ummisco.gama.application;

import static msi.gama.runtime.GAMA.getGui;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.service.datalocation.Location;
import msi.gama.common.util.MemoryUtils;
import msi.gama.runtime.GAMA;
import msi.gama.runtime.concurrent.GamaExecutorService;
import msi.gaml.operators.Dates;
import ummisco.gama.application.bundles.GamaBundleLoader;
import ummisco.gama.application.workspace.WorkspaceManager;

/** This class controls all aspects of the application's execution */
public class Application implements IApplication {

	final Object[] result = { 0 };

	@Override
	public Object start(final IApplicationContext context) throws Exception {
		/*
		 * Early build of various GAML/GAMA contributions
		 */
		GamaBundleLoader.preBuildContributions();

		GAMA.initializeAtStartup("Initializing execution services", () -> {
			GamaExecutorService.startUp();
		});
		GAMA.initializeAtStartup("Initializing memory management", () -> {
			MemoryUtils.initialize();
		});
		GAMA.initializeAtStartup("Initializing date management", () -> {
			Dates.initialize();
		});
		GAMA.initializeAtStartup("Checking workspace", () -> {
			result[0] = WorkspaceManager.checkWorkspace();
		});

		if ( EXIT_OK.equals(result[0]) )
			return EXIT_OK;

		// This is where a branch to headless should (or could) be made
		GamaBundleLoader.loadUI();

		try {
			if ( GAMA.getGui().runUI() == 1 /* PlatformUI.RETURN_RESTART */ )
				return IApplication.EXIT_RESTART;
			return IApplication.EXIT_OK;
		} finally {
			final Location instanceLoc = Platform.getInstanceLocation();
			if ( instanceLoc != null ) {
				instanceLoc.release();
			}
		}

	}

	@Override
	public void stop() {
		getGui().exit();
	}

}
