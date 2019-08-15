/*********************************************************************************************
 *
 * 'Application.java, in plugin msi.gama.application, is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package msi.gama.application;

import static msi.gama.runtime.GAMA.getGui;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.service.datalocation.Location;
import msi.gama.application.workspace.WorkspaceManager;
import msi.gama.runtime.GAMA;
import msi.gama.runtime.concurrent.GamaExecutorService;
import msi.gaml.compilation.kernel.GamaBundleLoader;
import msi.gaml.operators.Dates;

/** This class controls all aspects of the application's execution */
public class Application implements IApplication {

	@Override
	public Object start(final IApplicationContext context) throws Exception {
		GamaBundleLoader.loadUI();

		// while (GAMA.getRegularGui() == null) {
		// try {
		// Thread.sleep(100);
		// } catch (final InterruptedException e2) {}
		// }
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			if ( e instanceof OutOfMemoryError &&
				GAMA.getGui().confirm("Out of memory", "GAMA is out of memory. Do you want to close now ?") ) {
				this.stop();
			}
			e.printStackTrace();
		});

		final Object check = WorkspaceManager.checkWorkspace();
		if ( EXIT_OK.equals(check) ) { return EXIT_OK; }

		/*
		 * Early build of various GAML/GAMA contributions
		 */
		GamaBundleLoader.preBuildContributions();

		GAMA.initializeAtStartup("Initializing execution services", () -> {
			GamaExecutorService.startUp();
		});
		GAMA.initializeAtStartup("Initializing date management", () -> {
			Dates.initialize();
		});
		try {
			if ( GAMA.getGui().runUI() == 1 /* PlatformUI.RETURN_RESTART */ ) { return IApplication.EXIT_RESTART; }
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
