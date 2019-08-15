/*********************************************************************************************
 *
 * 'WorkspaceModelsManager.java, in plugin msi.gama.application, is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package msi.gama.application.workspace;

import static msi.gama.common.util.FileUtils.BUILTIN_PROPERTY;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import com.google.common.collect.Multimap;
import msi.gama.common.util.FileUtils;
import msi.gama.runtime.GAMA;
import msi.gaml.compilation.kernel.GamaBundleLoader;
import ummisco.gama.dev.utils.DEBUG;

/**
 * Class InitialModelOpener.
 *
 * @author drogoul
 * @since 16 nov. 2013
 *
 */
public class WorkspaceModelsManager {

	public final static WorkspaceModelsManager instance = new WorkspaceModelsManager();

	public void openModelPassedAsArgument(final String modelPath) {
		String filePath = modelPath;
		String expName = null;
		if ( filePath.contains("#") ) {
			final String[] segments = filePath.split("#");
			if ( segments.length != 2 ) {
				DEBUG.OUT("Wrong definition of model and experiment in argument '" + filePath + "'");
				return;
			}
			filePath = segments[0];
			expName = segments[1];
		}
		if ( filePath.endsWith(".experiment") && expName == null ) {
			expName = "0";
			// Verify that it works even if the included model defines experiments itself...

		}
		IFile file = null;
		try {
			file = FileUtils.findAndLoadIFile(filePath);
		} catch (final CoreException e) {
			e.printStackTrace();
		}
		if ( file != null ) {
			final String en = expName;
			try {
				file.touch(null);
				file.getProject().build(IncrementalProjectBuilder.FULL_BUILD, null);
			} catch (final CoreException e1) {
				DEBUG.OUT(Thread.currentThread().getName() + ": File " + file.getFullPath() + " cannot be built");
				return;
			}

			if ( en == null ) {
				GAMA.getGui().editModel(file);
			} else {
				GAMA.runModel(file, en, false);
			}

		}
	}

	public static void linkSampleModelsToWorkspace() {

		final WorkspaceJob job = new WorkspaceJob("Updating the Built-in Models Library") {

			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				// DEBUG.OUT("Asynchronous link of models library...");
				GAMA.getGui().refreshNavigator();
				return GamaBundleLoader.ERRORED ? Status.CANCEL_STATUS : Status.OK_STATUS;
			}

		};
		job.setUser(true);
		job.schedule();

	}

	public static void loadModelsLibrary() {
		while (!GamaBundleLoader.LOADED && !GamaBundleLoader.ERRORED) {
			try {
				Thread.sleep(100);
				// DEBUG.OUT("Waiting for GAML subsystem to load...");
			} catch (final InterruptedException e) {}
		}
		if ( GamaBundleLoader.ERRORED ) {
			GAMA.getGui().tell("Error", "Error in loading GAML language subsystem. Please consult the logs");
			return;
		}
		// DEBUG.OUT("Synchronous link of models library...");
		final Multimap<Bundle, String> pluginsWithModels = GamaBundleLoader.getPluginsWithModels();
		for ( final Bundle plugin : pluginsWithModels.keySet() ) {
			for ( final String entry : pluginsWithModels.get(plugin) ) {
				linkModelsToWorkspace(plugin, entry, false);
			}
		}
		final Multimap<Bundle, String> pluginsWithTests = GamaBundleLoader.getPluginsWithTests();
		for ( final Bundle plugin : pluginsWithTests.keySet() ) {
			for ( final String entry : pluginsWithTests.get(plugin) ) {
				linkModelsToWorkspace(plugin, entry, true);
			}
		}
	}

	/**
	 * @param plugin
	 */

	private static void linkModelsToWorkspace(final Bundle bundle, final String path, final boolean tests) {
		// DEBUG.OUT("Linking library from bundle " + bundle.getSymbolicName() + " at path " + path);
		final boolean core = bundle.equals(GamaBundleLoader.CORE_MODELS);
		final URL fileURL = bundle.getEntry(path);
		File modelsRep = null;
		try {
			final URL resolvedFileURL = FileLocator.toFileURL(fileURL);
			// We need to use the 3-arg constructor of URI in order to properly escape file system chars
			final URI resolvedURI = new URI(resolvedFileURL.getProtocol(), resolvedFileURL.getPath(), null).normalize();
			modelsRep = new File(resolvedURI);

		} catch (final URISyntaxException e1) {
			e1.printStackTrace();
		} catch (final IOException e1) {
			e1.printStackTrace();
		}

		final Map<File, IPath> foundProjects = new HashMap<>();
		FileUtils.findProjects(modelsRep, foundProjects);
		importBuiltInProjects(bundle, core, tests, foundProjects);

		if ( core ) {

			final IWorkspace workspace = ResourcesPlugin.getWorkspace();
			try {
				final String stamp = WorkspaceManager.getCurrentGamaStampString();
				final IWorkspaceRoot root = workspace.getRoot();
				final String oldStamp = root.getPersistentProperty(BUILTIN_PROPERTY);
				if ( oldStamp != null ) {
					final File stampFile =
						new File(new Path(root.getLocation().toOSString() + File.separator + oldStamp).toOSString());
					if ( stampFile.exists() ) {
						stampFile.delete();
					}
				}
				root.setPersistentProperty(BUILTIN_PROPERTY, stamp);
				final File stampFile =
					new File(new Path(root.getLocation().toOSString() + File.separator + stamp).toOSString());
				if ( !stampFile.exists() ) {
					stampFile.createNewFile();
				}
			} catch (final CoreException | IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @param plugin
	 * @param core
	 * @param workspace
	 * @param project
	 */
	private static void importBuiltInProjects(final Bundle plugin, final boolean core, final boolean tests,
		final Map<File, IPath> projects) {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		for ( final Map.Entry<File, IPath> entry : projects.entrySet() ) {
			final File project = entry.getKey();
			final IPath location = entry.getValue();
			final Consumer<IProgressMonitor> operation = (monitor) -> {
				IProject proj = workspace.getRoot().getProject(project.getName());
				try {
					if ( !proj.exists() ) {
						proj.create(workspace.loadProjectDescription(location), monitor);
					} else {
						// project exists but is not accessible
						if ( !proj.isAccessible() ) {
							proj.delete(true, null);
							proj = workspace.getRoot().getProject(project.getName());
							proj.create(workspace.loadProjectDescription(location), monitor);
						}
					}
					proj.open(IResource.NONE, monitor);
					FileUtils.setValuesProjectDescription(proj, true, !core, tests, plugin);
				} catch (final CoreException e) {
					GAMA.getGui().error("Error wien importing projects: " + e.getMessage());
				}
			};
			GAMA.getGui().runInWorkspace(operation);
		}

	}

	public static void stampWorkspaceFromModels() {}

}
