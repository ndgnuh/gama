/*********************************************************************************************
 *
 * 'WorkspacePreferences.java, in plugin gama.core.application, is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.core.application.workspace;

import static gama.GAMA.getGui;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IExportedPreferences;
import org.eclipse.core.runtime.preferences.IPreferenceFilter;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.core.runtime.preferences.PreferenceFilterEntry;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.osgi.service.datalocation.Location;
import gama.GAMA;
import gama.common.preferences.GamaPreferences;
import gama.dev.utils.DEBUG;

public class WorkspaceManager {

	/*
	 * The name of the file that tells us that the workspace directory belongs
	 * to our application
	 */

	static {
		GamaPreferences.Interface.CORE_ASK_REBUILD.onChange(v -> askBeforeRebuildingWorkspace(v));
		GamaPreferences.Interface.CORE_ASK_OUTDATED.onChange(v -> askBeforeUsingOutdatedWorkspace(v));
	}

	private static final String keyWorkspaceRootDir = "wsRootDir";
	private static final String keyRememberWorkspace = "wsRemember";
	private static final String keyLastUsedWorkspaces = "wsLastUsedWorkspaces";
	private static final String keyAskForRebuilding = "wsAskRebuildingWorkspace";
	private static final String keyAskForOutdated = "wsAskOutdatedWorkspace";

	static String selectedWorkspaceRootLocation;
	static boolean applyPrefs;
	public static final String WS_IDENTIFIER = ".gama_application_workspace";
	private static String MODEL_IDENTIFIER = null;

	public static String getSelectedWorkspaceRootLocation() {
		return selectedWorkspaceRootLocation;
	}

	public static void setSelectedWorkspaceRootLocation(final String s) {
		selectedWorkspaceRootLocation = s;
	}

	public static String getCurrentGamaStampString() {
		String gamaStamp = null;
		try {
			final URL tmpURL = new URL("platform:/plugin/gama.core.models/models/");
			final URL resolvedFileURL = FileLocator.toFileURL(tmpURL);
			// We need to use the 3-arg constructor of URI in order to properly escape file system chars
			final URI resolvedURI = new URI(resolvedFileURL.getProtocol(), resolvedFileURL.getPath(), null).normalize();
			final File modelsRep = new File(resolvedURI);

			// loading file from URL Path is not a good idea. There are some bugs
			// File modelsRep = new File(urlRep.getPath());

			final long time = modelsRep.lastModified();
			gamaStamp = ".built_in_models_" + time;
			DEBUG.OUT(
				">GAMA version " + Platform.getProduct().getDefiningBundle().getVersion().toString() + " loading...");
			DEBUG.OUT(">GAMA models library version: " + gamaStamp);
		} catch (final IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return gamaStamp;
	}

	public static IPreferenceFilter[] getPreferenceFilters() {
		final IPreferenceFilter[] transfers = new IPreferenceFilter[1];

		// For export all create a preference filter that can export
		// all nodes of the Instance and Configuration scopes
		transfers[0] = new IPreferenceFilter() {

			@Override
			public String[] getScopes() {
				return new String[] { InstanceScope.SCOPE };
			}

			@Override
			public Map<String, PreferenceFilterEntry[]> getMapping(final String scope) {
				return null;
			}
		};

		return transfers;
	}

	public static boolean applyPrefs() {
		return applyPrefs;
	}

	public static void setApplyPrefs(final boolean b) {
		applyPrefs = b;
	}

	public static void applyEclipsePreferences(final String targetDirectory) {
		final IPreferencesService service = Platform.getPreferencesService();
		IExportedPreferences prefs;

		try (FileInputStream input = new FileInputStream(new File(targetDirectory + "/.gama.epf"))) {
			prefs = service.readPreferences(input);
			service.applyPreferences(prefs, getPreferenceFilters());
		} catch (final IOException e) {} catch (final CoreException e) {}
		setApplyPrefs(false);

	}

	/**
	 * Ensures a workspace directory is OK in regards of reading/writing, etc.
	 * This method will get called externally as well.
	 *
	 * @param parentShell
	 *            Shell parent shell
	 * @param workspaceLocation
	 *            Directory the user wants to use
	 * @param askCreate
	 *            Whether to ask if to create the workspace or not in this
	 *            location if it does not exist already
	 * @param fromDialog
	 *            Whether this method was called from our dialog or from
	 *            somewhere else just to check a location
	 * @return null if everything is ok, or an error message if not
	 */
	public static String checkWorkspaceDirectory(final String workspaceLocation, final boolean askCreate,
		final boolean fromDialog, final boolean cloning) {
		final File f = new File(workspaceLocation);
		if ( !f.exists() ) {
			if ( askCreate ) {
				final boolean create = GAMA.getGui().confirm("New Directory",
					workspaceLocation + " does not exist. Would you like to create a new workspace here" +
						(cloning ? ", copy the projects and preferences of your current workspace into it, " : "") +
						" and proceeed ?");
				if ( create ) {
					try {
						f.mkdirs();
						final File wsDot = new File(workspaceLocation + File.separator + WS_IDENTIFIER);
						wsDot.createNewFile();
						final File dotFile = new File(workspaceLocation + File.separator + getModelIdentifier());
						dotFile.createNewFile();
					} catch (final RuntimeException err) {
						err.printStackTrace();
						return "Error creating directories, please check folder permissions";
					} catch (final IOException er) {
						er.printStackTrace();
						return "Error creating directories, please check folder permissions";
					}
				}

				if ( !f.exists() )
					return "The selected directory does not exist";
				else return null;
			}
		}

		if ( !f.canRead() )
			// scope.getGui().debug("The selected directory is not readable");
			return "The selected directory is not readable";

		if ( !f.isDirectory() )
			// scope.getGui().debug("The selected path is not a directory");
			return "The selected path is not a directory";

		testWorkspaceSanity(f);

		final File wsTest = new File(workspaceLocation + File.separator + WS_IDENTIFIER);
		if ( fromDialog ) {
			if ( !wsTest.exists() ) {
				final boolean create =
					GAMA.getGui().confirm("New Workspace", "The directory '" + wsTest.getAbsolutePath() +
						"' exists but is not identified as a GAMA workspace. \n\nWould you like to use it anyway ?");
				if ( create ) {
					try {
						f.mkdirs();
						final File wsDot = new File(workspaceLocation + File.separator + WS_IDENTIFIER);
						wsDot.createNewFile();
					} catch (final Exception err) {
						return "Error creating directories, please check folder permissions";
					}
				} else return "Please select a directory for your workspace";

				if ( !wsTest.exists() )
					return "The selected directory does not exist";

				return null;
			}
		} else {
			if ( !wsTest.exists() )
				return "The selected directory is not a workspace directory";
		}
		final File dotFile = new File(workspaceLocation + File.separator + getModelIdentifier());
		if ( !dotFile.exists() ) {
			if ( fromDialog ) {
				boolean create = true;
				if ( askBeforeUsingOutdatedWorkspace() ) {
					create = getGui().confirm("Different version of the models library",
						"The workspace contains a different version of the models library. Do you want to proceed anyway ?");
				}
				if ( create ) {
					try {
						dotFile.createNewFile();
					} catch (final IOException e) {
						return "Error updating the models library";
					}
					return null;
				}
			}

			return "models";
		} else if ( cloning ) {
			final boolean b = GAMA.getGui().confirm("Existing workspace",
				"The path entered is a path to an existing workspace. All its contents will be erased and replaced by the current workspace contents. Proceed anyway ?");
			if ( !b )
				return "";
		}
		return null;
	}

	public static boolean testWorkspaceSanity(final File workspace) {
		DEBUG.OUT("[GAMA] Checking for workspace sanity");
		File[] files = workspace.listFiles((FileFilter) file -> file.getName().equals(".metadata"));
		if ( files == null || files.length == 0 )
			return true;
		final File[] logs = files[0].listFiles((FileFilter) file -> file.getName().contains(".log"));
		if ( logs != null ) {
			for ( final File log : logs ) {
				log.delete();
			}
		}
		files = files[0].listFiles((FileFilter) file -> file.getName().equals(".plugins"));
		if ( files == null )
			return false;
		if ( files.length == 0 )
			return true;
		files = files[0].listFiles((FileFilter) file -> file.getName().equals("org.eclipse.core.resources"));
		if ( files == null )
			return false;
		if ( files.length == 0 )
			return true;
		files = files[0].listFiles((FileFilter) file -> file.getName().contains("snap"));
		if ( files == null )
			return false;
		DEBUG.OUT("[GAMA] Workspace appears to be " + (files.length == 0 ? "clean" : "corrupted"));
		if ( files.length == 0 )
			return true;
		boolean rebuild = true;
		if ( askBeforeRebuildingWorkspace() ) {
			rebuild = getGui().confirm("Corrupted workspace",
				"The workspace appears to be corrupted (due to a previous crash) or it is currently used by another instance of the platform. Would you like GAMA to clean it ?");
		}
		if ( rebuild ) {
			for ( final File file : files ) {
				if ( file.exists() ) {
					file.delete();
				}
			}
			GAMA.getGui().clearInitialLayout(true);
			return false;
		}
		return true;
	}

	public static String getModelIdentifier() {
		if ( MODEL_IDENTIFIER == null ) {
			MODEL_IDENTIFIER = getCurrentGamaStampString();
		}
		return MODEL_IDENTIFIER;
	}

	public static Object checkWorkspace() throws IOException, MalformedURLException {
		final Location instanceLoc = Platform.getInstanceLocation();
		if ( instanceLoc == null ) {
			// -data @none was specified but GAMA requires a workspace
			getGui().failureExit("A workspace is required to run GAMA");
			return IApplication.EXIT_OK;
		}
		boolean remember = false;
		String lastUsedWs = null;
		if ( instanceLoc.isSet() ) {
			lastUsedWs = instanceLoc.getURL().getFile();
			final String ret = WorkspaceManager.checkWorkspaceDirectory(lastUsedWs, false, false, false);
			if ( ret != null ) {
				getGui().failureExit("The workspace provided cannot be used. Please change it");
				return IApplication.EXIT_OK;
			}
		} else {

			/* Get what the user last said about remembering the workspace location */
			remember = remembersWorkspace();
			/* Get the last used workspace location */
			lastUsedWs = getLastSetWorkspaceDirectory();
			/* If we have a "remember" but no last used workspace, it's not much to remember */
			if ( remember && (lastUsedWs == null || lastUsedWs.length() == 0) ) {
				remember = false;
			}
			if ( remember ) {
				/*
				 * If there's any problem with the workspace, force a dialog
				 */
				final String ret = WorkspaceManager.checkWorkspaceDirectory(lastUsedWs, false, false, false);
				if ( ret != null ) {
					if ( ret.equals("models") ) {
						remember = !getGui().confirm("Different version of the models library",
							"The workspace contains a different version of the models library. Do you want to use another workspace ?");

					} else {
						remember = false;
					}
				}
			}
		}

		/* If we don't remember the workspace, show the dialog */
		if ( !remember ) {
			final int pick = getGui().openPickWorkspaceDialog();
			/* If the user cancelled, we can't do anything as we need a workspace */
			if ( pick == 1 /* Window.CANCEL */ && WorkspaceManager.getSelectedWorkspaceRootLocation() == null ) {
				getGui().failureExit("The application can not start without a workspace and will exit.");
				return IApplication.EXIT_OK;
			}
			/* Tell Eclipse what the selected location was and continue */
			instanceLoc.set(new URL("file", null, WorkspaceManager.getSelectedWorkspaceRootLocation()), false);
			if ( WorkspaceManager.applyPrefs() ) {
				WorkspaceManager.applyEclipsePreferences(WorkspaceManager.getSelectedWorkspaceRootLocation());
			}
		} else {
			if ( !instanceLoc.isSet() ) {
				/* Set the last used location and continue */
				instanceLoc.set(new URL("file", null, lastUsedWs), false);
			}

		}

		return null;
	}

	/**
	 * Returns whether the user selected "remember workspace" in the preferences
	 */
	public static boolean remembersWorkspace() {
		return getSystemWidePreferences().getBoolean(keyRememberWorkspace, false);
	}

	/**
	 * Returns the last set workspace directory from the preferences
	 *
	 * @return null if none
	 */
	public static String getLastSetWorkspaceDirectory() {
		return getSystemWidePreferences().get(keyWorkspaceRootDir, null);
	}

	public static Preferences getSystemWidePreferences() {
		try {
			if ( Preferences.userRoot().nodeExists("gama") )
				return Preferences.userRoot().node("gama");
		} catch (final BackingStoreException e1) {
			e1.printStackTrace();
		}
		final Preferences p = Preferences.userRoot().node("gama");
		try {
			p.flush();
		} catch (final BackingStoreException e) {
			e.printStackTrace();
		}
		return p;
	}

	public static void remembersWorkspace(final boolean value) {
		getSystemWidePreferences().putBoolean(keyRememberWorkspace, value);
		try {
			getSystemWidePreferences().flush();
		} catch (final BackingStoreException e) {
			e.printStackTrace();
		}
	}

	public static void lastSetWorkspaceDirectory(final String value) {
		getSystemWidePreferences().put(keyWorkspaceRootDir, value);
		flush();

	}

	private static void flush() {
		try {
			getSystemWidePreferences().flush();
		} catch (final BackingStoreException e) {
			e.printStackTrace();
		}
	}

	public static String lastUsedWorkspaces() {
		return getSystemWidePreferences().get(keyLastUsedWorkspaces, "");
	}

	public static boolean askBeforeRebuildingWorkspace() {
		// true by default
		return getSystemWidePreferences().getBoolean(keyAskForRebuilding, true);
	}

	public static void askBeforeRebuildingWorkspace(final boolean ask) {
		// true by default
		getSystemWidePreferences().putBoolean(keyAskForRebuilding, ask);
		flush();
	}

	public static boolean askBeforeUsingOutdatedWorkspace() {
		// true by default
		return getSystemWidePreferences().getBoolean(keyAskForOutdated, true);
	}

	public static void askBeforeUsingOutdatedWorkspace(final boolean ask) {
		// true by default
		getSystemWidePreferences().putBoolean(keyAskForOutdated, ask);
		flush();
	}

}
