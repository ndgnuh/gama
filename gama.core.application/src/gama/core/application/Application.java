/*********************************************************************************************
 *
 * 'Application.java, in plugin gama.core.application, is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.core.application;

import static gama.runtime.GAMA.getGui;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.service.datalocation.Location;
import gama.common.util.MemoryUtils;
import gama.core.application.bundles.GamaBundleLoader;
import gama.core.application.workspace.WorkspaceManager;
import gama.runtime.GAMA;
import gama.runtime.concurrent.GamaExecutorService;
import msi.gaml.operators.Dates;

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
