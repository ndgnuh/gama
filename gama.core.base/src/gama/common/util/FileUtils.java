/*******************************************************************************************************
 *
 * gama.common.util.FileUtils.java, in plugin gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.common.util;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;

import gama.GAMA;
import gama.common.interfaces.IModel;
import gama.common.interfaces.experiment.IExperimentAgent;
import gama.common.preferences.GamaPreferences;
import gama.core.ext.webb.Webb;
import gama.core.ext.webb.WebbException;
import gama.dev.utils.DEBUG;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.file.CacheLocationProvider;

/**
 * The class FileUtils.
 *
 * @author drogoul
 * @since 20 dec. 2011
 *
 */
@SuppressWarnings ("deprecation")
public class FileUtils {

	public final static String GAMA_NATURE = "gama.core.application.gamaNature";
	public final static String XTEXT_NATURE = "org.eclipse.xtext.ui.shared.xtextNature";
	public final static String PLUGIN_NATURE = "gama.core.application.pluginNature";
	public final static String TEST_NATURE = "gama.core.application.testNature";
	public final static String BUILTIN_NATURE = "gama.core.application.builtinNature";

	public static final QualifiedName BUILTIN_PROPERTY = new QualifiedName("gama.builtin", "models");
	public static ThreadLocal<Webb> WEB = ThreadLocal.withInitial(() -> Webb.create());
	public static final String URL_SEPARATOR_REPLACEMENT = "+_+";
	public static final String COPY_OF = "copy of ";
	public static final String HOME = "~";
	public static final String SEPARATOR = "/";
	public static final IPath CACHE_FOLDER_PATH = new Path(".cache");
	public static final IPath EXTERNAL_FOLDER_PATH = new Path("external");
	private static IWorkspaceRoot ROOT;
	static IFileSystem FILE_SYSTEM = EFS.getLocalFileSystem();
	static String USER_HOME = System.getProperty("user.home");
	private static URI WORKSPACE_URI;
	private static File CACHE;
	private static final FilenameFilter isDotFile = (dir, name) -> name.equals(".project");

	/**
	 * Checks if is absolute path.
	 *
	 * @param filePath
	 *            the file path
	 *
	 * @return true, if is absolute path
	 */
	static boolean isAbsolutePath(final String filePath) {
		// Fixes #2456
		return Paths.get(filePath).isAbsolute();
	}

	// Add a thin layer of workspace-based searching in order to resolve linked
	// resources.
	// Should be able to catch most of the calls to relative resources as well
	static public String constructAbsoluteFilePath(final IScope scope, final String filePath, final boolean mustExist) {
		String fp;
		if (filePath.startsWith(HOME)) {
			fp = filePath.replaceFirst(HOME, USER_HOME);
		} else {
			fp = filePath;
		}
		if (isAbsolutePath(fp)) {
			URI modelBase = null;
			if (scope != null) {
				final IModel m = scope.getModel();
				if (m != null) {
					modelBase = m.getURI();
				}
			}
			final String file = findOutsideWorkspace(fp, modelBase, mustExist);
			if (file != null)
				// DEBUG.OUT("Hit with EFS-based search: " + file);
				return file;
		}
		if (scope != null) {
			final IExperimentAgent a = scope.getExperiment();
			// No need to search more if the experiment is null
			if (a == null)
				return fp;
			if (!a.isHeadless()) {
				// Necessary to ask the workspace for the containers as projects might be linked
				final List<IContainer> paths = a.getWorkingPaths().stream()
						.map(s -> ROOT().findContainersForLocation(new Path(s))[0]).collect(toList());
				for (final IContainer folder : paths) {
					final String file = findInWorkspace(fp, folder, mustExist);
					if (file != null) {
						DEBUG.OUT("Hit with workspace-based search: " + file);
						return file;
					}
				}
			}
		}

		DEBUG.OUT("Falling back to the old JavaIO based search");
		return OldFileUtils.constructAbsoluteFilePathAlternate(scope, fp, mustExist);
	}

	private static String findInWorkspace(final String fp, final IContainer container, final boolean mustExist) {
		final IPath full = container.getFullPath().append(fp);
		IResource file = ROOT().getFile(full);
		if (!file.exists()) {
			// Might be a folder we're looking for
			file = ROOT().getFolder(full);
		}
		if (!file.exists()) {
			if (mustExist)
				return null;
		}
		final IPath loc = file.getLocation();
		// cf. #2997
		if (loc == null)
			return fp;
		return loc.toString(); // getLocation() works for regular and linked files
	}

	private static String findOutsideWorkspace(final String fp, final URI modelBase, final boolean mustExist) {
		if (!mustExist)
			return fp;
		final IFileStore file = FILE_SYSTEM.getStore(new Path(fp));
		final IFileInfo info = file.fetchInfo();
		if (info.exists()) {
			final IFile linkedFile = createLinkToExternalFile(fp, modelBase);
			if (linkedFile == null)
				return fp;
			return linkedFile.getLocation().toFile().getAbsolutePath();
		}
		return null;
	}

	public static IFile createLinkToExternalFile(final String path, final URI workspaceResource) {
		// Always try to return the full file, without creating a link, if the file
		// happens to be in the workspace
		// (manageable by it)
		final IPath filePath = new Path(path);
		final IResource[] resources = ROOT().findFilesForLocation(filePath);
		if (resources.length > 0) {
			final IResource r = resources[0];
			if (r instanceof IFile)
				return (IFile) r;
		}

		final IFolder folder = createExternalFolder(workspaceResource);
		if (folder == null)
			return null;
		// We try to find an existing file linking to this uri (in case it has been
		// renamed, for instance)
		IFile file = findExistingLinkedFile(folder, path);
		if (file != null)
			return file;
		// We get the file with the same last name
		// If it already exists, we need to find it a new name as it doesnt point to the
		// same absolute file
		String fileName = new Path(path).lastSegment();
		final int i = fileName.lastIndexOf(URL_SEPARATOR_REPLACEMENT);
		if (i > -1) {
			fileName = fileName.substring(i + URL_SEPARATOR_REPLACEMENT.length());
		}
		file = correctlyNamedFile(folder, fileName);
		return createLinkedFile(path, file);
	}

	/**
	 * Returns a best guess URI based on the target string and an optional URI specifying from where the relative URI
	 * should be run. If existingResource is null, then the root of the workspace is used as the relative URI
	 *
	 * @param target
	 *            a String giving the path
	 * @param existingResource
	 *            the URI of the resource from which relative URIs should be interpreted
	 * @author Alexis Drogoul, July 2018
	 * @return an URI or null if it cannot be determined.
	 */
	public static URI getURI(final String target, final URI existingResource) {
		if (target == null)
			return null;
		try {
			final IPath path = Path.fromOSString(target);
			final IFileStore file = EFS.getLocalFileSystem().getStore(path);
			final IFileInfo info = file.fetchInfo();
			if (info.exists()) {
				// We have an absolute file
				final URI fileURI = URI.createFileURI(target);
				return fileURI;
			} else {
				final URI first = URI.createURI(target, false);
				URI root;
				if (!existingResource.isPlatformResource()) {
					root = URI.createPlatformResourceURI(existingResource.toString(), false);
				} else {
					root = existingResource;
				}
				if (root == null) {
					root = WORKSPACE_URI();
				}
				final URI iu = first.resolve(root);
				if (isFileExistingInWorkspace(iu))
					return iu;
				return null;
			}
		} catch (final Exception e) {
			return null;
		}
	}

	public static boolean isFileExistingInWorkspace(final URI uri) {
		if (uri == null)
			return false;
		final IFile file = getWorkspaceFile(uri);
		if (file != null)
			return file.exists();
		return false;
	}

	public static IFile getFile(final String path, final URI root, final boolean mustExist) {
		final URI uri = getURI(path, root);
		if (uri != null) {
			if (uri.isPlatformResource())
				return getWorkspaceFile(uri);
			return createLinkToExternalFile(path, root);
		}
		return null;
	}

	// public static IFile linkAndGetExternalFile(final URI uri, final URI
	// workspaceResource) {
	// final String path = URI.decode(uri.isFile() ? uri.toFileString() :
	// uri.toString());
	// return linkAndGetExternalFile(URI.decode(path), workspaceResource);
	// }

	private static IFile createLinkedFile(final String path, final IFile file) {
		java.net.URI resolvedURI = null;
		final java.net.URI javaURI = URIUtil.toURI(path);// new java.io.File(path).toURI();

		try {
			resolvedURI = ROOT().getPathVariableManager().convertToRelative(javaURI, true, null);
		} catch (final CoreException e1) {
			resolvedURI = javaURI;
		}
		try {
			file.createLink(resolvedURI, IResource.NONE, null);
		} catch (final CoreException e) {
			e.printStackTrace();
			return null;
		}
		return file;
	}

	private static IFile correctlyNamedFile(final IFolder folder, final String fileName) {
		IFile file;
		String fn = fileName;
		do {
			file = folder.getFile(fn);
			fn = COPY_OF + fn;
		} while (file.exists());
		return file;
	}

	private static IFile findExistingLinkedFile(final IFolder folder, final String name) {
		final IFile[] result = new IFile[1];
		try {
			folder.accept((IResourceVisitor) resource -> {
				if (resource.isLinked()) {
					final String p = resource.getLocation().toString();
					if (p.equals(name)) {
						result[0] = (IFile) resource;
						return false;
					}
				}
				return true;

			}, IResource.DEPTH_INFINITE, IResource.FILE);
		} catch (final CoreException e1) {
			e1.printStackTrace();
		}
		final IFile file = result[0];
		return file;
	}

	private static IFolder createExternalFolder(final URI workspaceResource) {
		if (workspaceResource == null || !isFileExistingInWorkspace(workspaceResource))
			return null;
		final IFile root = getWorkspaceFile(workspaceResource);
		final IProject project = root.getProject();
		if (!project.exists())
			return null;
		final IFolder folder = project.getFolder(EXTERNAL_FOLDER_PATH);
		if (!folder.exists()) {
			try {
				folder.create(true, true, null);
			} catch (final CoreException e) {
				e.printStackTrace();
				return null;
			}
		}
		return folder;
	}

	public static IFile getWorkspaceFile(final URI uri) {
		final IPath uriAsPath = new Path(URI.decode(uri.toString()));
		IFile file;
		try {
			file = ROOT().getFile(uriAsPath);
		} catch (final Exception e1) {
			return null;
		}
		if (file != null && file.exists())
			return file;
		final String uriAsText = uri.toPlatformString(true);
		final IPath path = uriAsText != null ? new Path(uriAsText) : null;
		if (path == null)
			return null;
		try {
			file = ROOT().getFile(path);
		} catch (final Exception e) {
			return null;
		}
		if (file != null && file.exists())
			return file;
		return null;
	}

	public static String constructAbsoluteTempFilePath(final IScope scope, final URL url) {
		return CACHE().getAbsolutePath() + SEPARATOR + url.getHost() + URL_SEPARATOR_REPLACEMENT
				+ url.getPath().replace(SEPARATOR, URL_SEPARATOR_REPLACEMENT);

	}

	private static String constructRelativeTempFilePath(final IScope scope, final URL url) {
		return "" + CacheLocationProvider.NAME + "" + SEPARATOR + url.getHost() + URL_SEPARATOR_REPLACEMENT
				+ url.getPath().replace(SEPARATOR, URL_SEPARATOR_REPLACEMENT);

	}

	public static void cleanCache() {
		if (GamaPreferences.External.CORE_HTTP_EMPTY_CACHE.getValue()) {
			final File[] files = CACHE().listFiles();
			if (files != null) {
				for (final File f : files) {
					if (!f.isDirectory()) {
						try {
							f.delete();
						} catch (final Throwable e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public static boolean isDirectoryOrNullExternalFile(final String path) {
		final IFileStore external = FILE_SYSTEM.getStore(new Path(path));
		final IFileInfo info = external.fetchInfo();
		if (info.isDirectory() || !info.exists())
			return true;
		return false;
	}

	@SuppressWarnings ("deprecation")
	public static String fetchToTempFile(final IScope scope, final URL url) {
		String pathName = constructRelativeTempFilePath(scope, url);
		final String urlPath = url.toExternalForm();
		final String status = "Downloading file " + urlPath.substring(urlPath.lastIndexOf(SEPARATOR));
		scope.getGui().getStatus(scope).beginSubStatus(status);
		final Webb web = WEB.get();
		try {
			try (InputStream in = web.get(urlPath).ensureSuccess()
					.connectTimeout(GamaPreferences.External.CORE_HTTP_CONNECT_TIMEOUT.getValue())
					.readTimeout(GamaPreferences.External.CORE_HTTP_READ_TIMEOUT.getValue())
					.retry(GamaPreferences.External.CORE_HTTP_RETRY_NUMBER.getValue(), false).asStream().getBody();) {
				// final java.net.URI uri = URIUtil.toURI(pathName);
				pathName = ROOT().getPathVariableManager().resolvePath(new Path(pathName)).toOSString();
				// pathName = ROOT.getPathVariableManager().resolveURI(uri).getPath();
				final java.nio.file.Path p = new File(pathName).toPath();
				if (Files.exists(p)) {
					Files.delete(p);
				}
				Files.copy(in, p);
			}
		} catch (final IOException | WebbException e) {
			throw GamaRuntimeException.create(e, scope);
		} finally {
			scope.getGui().getStatus(scope).endSubStatus(status);
		}
		return pathName;
	}

	public static String UNCLASSIFIED_MODELS = "Unclassified Models";

	public static IFolder createUnclassifiedModelsProject(final IPath location) throws CoreException {
		// First allow to select a parent folder
		final IPath result = GAMA.getGui().openSelectContainerDialog("Project selection",
				"Select a parent project or cancel to create a new project:");

		IProject project;
		IFolder modelFolder;

		if (result == null) {
			project = createOrUpdateProject(UNCLASSIFIED_MODELS);
			modelFolder = project.getFolder(new Path("models"));
			if (!modelFolder.exists()) {
				modelFolder.create(true, true, null);
			}
		} else {
			final IContainer container = (IContainer) ResourcesPlugin.getWorkspace().getRoot().findMember(result);
			if (container instanceof IProject) {
				project = (IProject) container;
				modelFolder = project.getFolder(new Path("models"));
				if (!modelFolder.exists()) {
					modelFolder.create(true, true, null);
				}
			} else {
				modelFolder = (IFolder) container;
			}

		}

		return modelFolder;
	}

	public static boolean isGamaProject(final File f) throws CoreException {
		final String[] list = f.list();
		if (list != null) {
			for (final String s : list) {
				if (s.equals(".project")) {
					IPath p = new Path(f.getAbsolutePath());
					p = p.append(".project");
					final IProjectDescription pd = ResourcesPlugin.getWorkspace().loadProjectDescription(p);
					if (pd.hasNature(GAMA_NATURE))
						return true;
				}
			}
		}
		return false;
	}

	static public void setValuesProjectDescription(final IProject proj, final boolean builtin, final boolean inPlugin,
			final boolean inTests, final Bundle bundle) {
		/* Modify the project description */
		IProjectDescription desc = null;
		try {

			final List<String> ids = new ArrayList<>();
			ids.add(XTEXT_NATURE);
			ids.add(GAMA_NATURE);
			if (inTests) {
				ids.add(TEST_NATURE);
			} else if (inPlugin) {
				ids.add(PLUGIN_NATURE);
			} else if (builtin) {
				ids.add(BUILTIN_NATURE);
			}
			desc = proj.getDescription();
			desc.setNatureIds(ids.toArray(new String[ids.size()]));
			// Addition of a special nature to the project.
			if (inTests && bundle == null) {
				desc.setComment("user defined");
			} else if ((inPlugin || inTests) && bundle != null) {
				String name = bundle.getSymbolicName();
				final String[] ss = name.split("\\.");
				name = ss[ss.length - 1] + " plugin";
				desc.setComment(name);
			} else {
				desc.setComment("");
			}
			proj.setDescription(desc, IResource.FORCE, null);
			// Addition of a special persistent property to indicate that the project is built-in
			if (builtin) {
				proj.setPersistentProperty(BUILTIN_PROPERTY,
						Platform.getProduct().getDefiningBundle().getVersion().toString());
			}
		} catch (final CoreException e) {
			e.printStackTrace();
		}
	}

	public static void findProjects(final File folder, final Map<File, IPath> found) {
		if (folder == null)
			return;
		final File[] dotFile = folder.listFiles(isDotFile);
		if (dotFile == null)
			return;
		if (dotFile.length == 0) { // no .project file
			final File[] files = folder.listFiles();
			if (files != null) {
				for (final File f : files) {
					findProjects(f, found);
				}
			}
			return;
		}
		found.put(folder, new Path(dotFile[0].getAbsolutePath()));

	}

	/**
	 * @param lastSegment
	 * @param modelFolder
	 * @return
	 */
	public static IFile createUniqueFileFrom(final IFile originalFile, final IFolder modelFolder) {
		IFile file = originalFile;
		final Pattern p = Pattern.compile("(.*?)(\\d+)?(\\..*)?");
		while (file.exists()) {
			final IPath path = file.getLocation();
			String fName = path.lastSegment();
			final Matcher m = p.matcher(fName);
			if (m.matches()) {// group 1 is the prefix, group 2 is the number, group 3 is the suffix
				fName = m.group(1) + (m.group(2) == null ? 1 : Integer.parseInt(m.group(2)) + 1)
						+ (m.group(3) == null ? "" : m.group(3));
			}
			file = modelFolder.getFile(fName);
		}
		return file;

	}

	/**
	 * @param filePath
	 * @return
	 * @throws CoreException
	 */
	public static IFile findAndLoadIFile(final String filePath) throws CoreException {
		// GAMA.getGui().debug("WorkspaceModelsManager.findAndLoadIFile " + filePath);
		// No error in case of an empty argument
		if (isBlank(filePath))
			return null;
		final IPath path = new Path(filePath);

		// 1st case: the path can be identified as a file residing in the workspace
		IFile result = findInWorkspace(path);
		if (result != null)
			return result;
		// 2nd case: the path is outside the workspace
		result = findOutsideWorkspace(path);
		if (result != null)
			return result;
		// DEBUG.OUT(
		// "File " + filePath + " cannot be located. Please check its name and location. Arguments provided were : " +
		// Arrays.toString(CommandLineArgs.getApplicationArgs()));
		return null;
	}

	private static boolean isBlank(final String cs) {
		if (cs == null)
			return true;
		if (cs.isEmpty())
			return true;
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(cs.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * @param filePath
	 * @return
	 */
	private static IFile findInWorkspace(final IPath originalPath) {
		// GAMA.getGui().debug("WorkspaceModelsManager.findInWorkspace " + originalPath);
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IPath workspacePath = new Path(Platform.getInstanceLocation().getURL().getPath());
		final IPath filePath = originalPath.makeRelativeTo(workspacePath);
		IFile file = null;
		try {
			file = workspace.getRoot().getFile(filePath);
		} catch (final Exception e) {
			return null;
		}
		if (!file.exists())
			return null;
		return file;
	}

	private static IFile findOutsideWorkspace(final IPath originalPath) throws CoreException {
		// GAMA.getGui().debug("WorkspaceModelsManager.findOutsideWorkspace " + originalPath);
		final File modelFile = new File(originalPath.toOSString());
		// TODO If the file does not exist we return null (might be a good idea to check other locations)
		if (!modelFile.exists())
			return null;

		// We try to find a folder containing the model file which can be considered as a project
		File projectFileBean = new File(modelFile.getPath());
		File dotFile = null;
		while (projectFileBean != null && dotFile == null) {
			projectFileBean = projectFileBean.getParentFile();
			if (projectFileBean != null) {
				/* parcours des fils pour trouver le dot file et creer le lien vers le projet */
				final File[] children = projectFileBean.listFiles();
				if (children != null) {
					for (final File element : children) {
						if (element.getName().equals(".project")) {
							dotFile = element;
							break;
						}
					}
				}
			}
		}

		if (dotFile == null || projectFileBean == null) {
			GAMA.getGui().tell("No project", "The model '" + modelFile.getAbsolutePath()
					+ "' does not seem to belong to an existing GAML project. You can import it in an existing project or in the 'Unclassified models' project.");
			return createUnclassifiedModelsProjectAndAdd(originalPath);
		}

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IPath location = new Path(dotFile.getAbsolutePath());
		final String pathToProject = projectFileBean.getName();

		// We load the project description.
		final IProjectDescription description = workspace.loadProjectDescription(location);
		if (description != null) {
			final Consumer<IProgressMonitor> r = (monitor) -> {
				// We try to get the project in the workspace
				IProject proj = workspace.getRoot().getProject(pathToProject);
				// If it does not exist, we create it
				if (!proj.exists()) {
					// If a project with the same name exists
					final IProject[] projects = workspace.getRoot().getProjects();
					final String name = description.getName();
					for (final IProject p : projects) {
						if (p.getName().equals(name)) {
							GAMA.getGui().tell("Existing project",
									"A project with the same name already exists in the workspace. The model '"
											+ modelFile.getAbsolutePath()
											+ " will be imported as part of the 'Unclassified models' project.");
							createUnclassifiedModelsProjectAndAdd(originalPath);
							return;
						}
					}

					try {
						proj.create(description, monitor);
					} catch (final CoreException e) {
						GAMA.getGui().error("Error wien importing project: " + e.getMessage());
					}
				} else {
					// project exists but is not accessible, so we delete it and recreate it
					if (!proj.isAccessible()) {
						try {
							proj.delete(true, null);
						} catch (final CoreException e) {
							GAMA.getGui().error("Error wien importing project: " + e.getMessage());
						}
						proj = workspace.getRoot().getProject(pathToProject);
						try {
							proj.create(description, monitor);
						} catch (final CoreException e) {
							GAMA.getGui().error("Error wien importing project: " + e.getMessage());
						}
					}
				}
				// We open the project
				try {
					proj.open(IResource.NONE, monitor);
				} catch (final CoreException e) {
					GAMA.getGui().error("Error wien importing project: " + e.getMessage());
				}
				// And we set some properties to it
				setValuesProjectDescription(proj, false, false, false, null);
			};
			GAMA.getGui().runInWorkspace(r);
		}

		final IProject project = workspace.getRoot().getProject(pathToProject);
		final String relativePathToModel =
				project.getName() + modelFile.getAbsolutePath().replace(projectFileBean.getPath(), "");
		return findInWorkspace(new Path(relativePathToModel));
	}

	static public IProject createOrUpdateProject(final String name) {
		final IWorkspace ws = ResourcesPlugin.getWorkspace();
		final IProject[] projectHandle = new IProject[] { null };
		final Consumer<IProgressMonitor> r = (monitor) -> {
			final SubMonitor m = SubMonitor.convert(monitor, "Creating or updating " + name, 2000);
			final IProject project = ws.getRoot().getProject(name);
			if (!project.exists()) {
				final IProjectDescription desc = ws.newProjectDescription(name);
				try {
					project.create(desc, m.split(1000));
				} catch (final CoreException e) {
					GAMA.getGui().error("Error wien creating project: " + e.getMessage());
				}
			}
			if (monitor.isCanceled())
				throw new OperationCanceledException();
			try {
				project.open(IResource.BACKGROUND_REFRESH, m.split(1000));
			} catch (final CoreException e) {
				GAMA.getGui().error("Error wien creating project: " + e.getMessage());
			}
			projectHandle[0] = project;
			setValuesProjectDescription(project, false, false, false, null);
		};
		GAMA.getGui().runInWorkspace(r);
		return projectHandle[0];
	}

	public static IFile createUnclassifiedModelsProjectAndAdd(final IPath location) {
		IFile iFile = null;
		try {
			final IFolder modelFolder = createUnclassifiedModelsProject(location);
			iFile = modelFolder.getFile(location.lastSegment());
			if (iFile.exists()) {
				if (iFile.isLinked()) {
					final IPath path = iFile.getLocation();
					if (path.equals(location))
						// First case, this is a linked resource to the same location. In that case, we simply return
						// its name.
						return iFile;
					else {
						// Second case, this resource is a link to another location. We create a filename that is
						// guaranteed not to exist and change iFile accordingly.
						iFile = FileUtils.createUniqueFileFrom(iFile, modelFolder);
					}
				} else {
					// Third case, this resource is local and we do not want to overwrite it. We create a filename that
					// is guaranteed not to exist and change iFile accordingly.
					iFile = FileUtils.createUniqueFileFrom(iFile, modelFolder);
				}
			}
			iFile.createLink(location, IResource.NONE, null);
			// RefreshHandler.run();
			return iFile;
		} catch (final CoreException e) {
			e.printStackTrace();
			GAMA.getGui().tell("Error in creation",
					"The file " + (iFile == null ? location.lastSegment() : iFile.getFullPath().lastSegment())
							+ " cannot be created because of the following exception " + e.getMessage());
			return null;
		}
	}

	public static File findIniFile() {
		final String path = Platform.getConfigurationLocation().getURL().getPath();
		DEBUG.OUT("Install location of GAMA is " + path);
		File dir = new File(path);
		File result = findIn(dir);
		if (result == null) {
			if (Platform.getOS().equals(Platform.OS_MACOSX)) {
				dir = new File(path + "Gama.app/Contents/MacOS");
				result = findIn(dir);
				if (result == null) {
					dir = new File(path + "Gama.app/Eclipse");
					result = findIn(dir);
				}
			} else {
				dir = dir.getParentFile();
				result = findIn(dir);
			}
		}
		return result;
	}

	private static File findIn(final File path) {
		DEBUG.OUT("Looking for ini file in " + path);
		final File ini = new File(path.getAbsolutePath() + "/Gama.ini");
		return ini.exists() ? ini : null;
	}

	public static File CACHE() {
		if (CACHE == null) {
			CACHE = new File(
					ROOT().getLocation().toFile().getAbsolutePath() + SEPARATOR + CACHE_FOLDER_PATH.toString());
			if (!CACHE.exists()) {
				CACHE.mkdirs();
			}
			try {
				ROOT().getPathVariableManager().setValue("CACHE_LOC", ROOT().getLocation().append(CACHE_FOLDER_PATH));
			} catch (final CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return CACHE;
	}

	static IWorkspaceRoot ROOT() {
		if (ROOT == null) {
			ROOT = ResourcesPlugin.getWorkspace().getRoot();
		}
		return ROOT;
	}

	static URI WORKSPACE_URI() {
		if (WORKSPACE_URI == null) {
			WORKSPACE_URI = URI.createURI(ROOT().getLocationURI().toString(), false);
		}
		return WORKSPACE_URI;
	}

}
