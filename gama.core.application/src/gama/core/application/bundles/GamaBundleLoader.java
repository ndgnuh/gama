/*******************************************************************************************************
 *
 * msi.gaml.compilation.kernel.GamaBundleLoader.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.application.bundles;

import static gama.dev.utils.DEBUG.ERR;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import gama.common.interfaces.ICreateDelegate;
import gama.common.interfaces.gui.IGui;
import gama.common.interfaces.outputs.IDisplaySurface;
import gama.common.interfaces.outputs.IEventLayerDelegate;
import gama.common.interfaces.outputs.IEventLayerStatement;
import gama.common.interfaces.outputs.IDisplayCreator.DisplayDescription;
import gama.dev.utils.DEBUG;
import gama.runtime.GAMA;
import msi.gaml.compilation.AbstractGamlAdditions;
import msi.gaml.compilation.interfaces.IGamlAdditions;
import msi.gaml.compilation.kernel.GamaClassLoader;
import msi.gaml.compilation.kernel.GamaMetaModel;
import msi.gaml.statements.CreateStatement;
import msi.gaml.types.Types;

/**
 * The class GamaBundleLoader.
 *
 * @author drogoul
 * @since 24 janv. 2012
 *
 */
public class GamaBundleLoader {

	static {
		DEBUG.ON();
	}

	public static void ERROR(final Exception e) {
		ERRORED = true;
		LAST_EXCEPTION = e;
		e.printStackTrace();
	}

	public static final String LINE =
		"\n\n****************************************************************************************************\n\n";
	public static final String ERROR_MESSAGE = LINE
		+ "The initialization of GAML artefacts went wrong. If you use the developer version, please clean and recompile all plugins. \nOtherwise post an issue at https://github.com/gama-platform/gama/issues"
		+ LINE;
	public volatile static boolean LOADED = false;
	public volatile static boolean ERRORED = false;
	public volatile static Exception LAST_EXCEPTION = null;
	public static final Bundle CORE_PLUGIN = Platform.getBundle("gama.core");
	public static final Bundle CORE_MODELS = Platform.getBundle("gama.models");
	public static final String CORE_TESTS = "tests";
	public static final String ADDITIONS = "gaml.additions.GamlAdditions";
	public static final String GRAMMAR_EXTENSION_DEPRECATED = "gaml.grammar.addition";
	public static final String GRAMMAR_EXTENSION = "gaml.extension";
	public static final String CREATE_EXTENSION = "gama.create";
	public static final String EVENT_LAYER_EXTENSION = "gama.event_layer";
	public static final String MODELS_EXTENSION = "gama.models";
	public static final String DISPLAY_EXTENSION = "gama.display";
	public static boolean DISPLAY_INITIALIZED;
	public static final String REGULAR_MODELS_LAYOUT = "models";
	public static final String REGULAR_TESTS_LAYOUT = "tests";
	public static final String GENERATED_TESTS_LAYOUT = "gaml/tests";
	public static final String CONTENT_EXTENSION = "org.eclipse.core.contenttype.contentTypes";
	private static final Set<Bundle> GAMA_PLUGINS = new HashSet<>();
	private static final Multimap<Bundle, String> MODEL_PLUGINS = ArrayListMultimap.create();
	private static final Multimap<Bundle, String> TEST_PLUGINS = ArrayListMultimap.create();
	public static final Set<String> HANDLED_FILE_EXTENSIONS = new HashSet<>();

	static final IExtensionRegistry registry = Platform.getExtensionRegistry();

	public static void preBuildContributions() throws Exception {

		GAMA.initializeAtStartup("Initializing plugins", () -> {

			// We retrieve the elements declared as extensions to the GAML language,
			// either with the new or the deprecated extension
			final Set<IExtension> extensions = new HashSet<>();
			try {
				IExtensionPoint p = registry.getExtensionPoint(GRAMMAR_EXTENSION);
				extensions.addAll(Arrays.asList(p.getExtensions()));
				p = registry.getExtensionPoint(GRAMMAR_EXTENSION_DEPRECATED);
				extensions.addAll(Arrays.asList(p.getExtensions()));
			} catch (final InvalidRegistryObjectException e) {
				ERROR(e);
			}

			// We retrieve their contributor plugin and add them to the
			// GAMA_PLUGINS. In addition, we verify if they declare a folder called
			// `models` or `tests` or if they have generated tests
			// TEST_PLUGINS.put(CORE_MODELS, REGULAR_TESTS_LAYOUT);
			MODEL_PLUGINS.put(CORE_MODELS, REGULAR_MODELS_LAYOUT);
			for ( final IExtension e : extensions ) {
				final IContributor plugin = e.getContributor();
				final Bundle bundle = Platform.getBundle(plugin.getName());

				GAMA_PLUGINS.add(bundle);
				if ( bundle.getEntry(REGULAR_MODELS_LAYOUT) != null ) {
					MODEL_PLUGINS.put(bundle, REGULAR_MODELS_LAYOUT);
				}
				if ( bundle.getEntry(REGULAR_TESTS_LAYOUT) != null ) {
					TEST_PLUGINS.put(bundle, REGULAR_TESTS_LAYOUT);
				}
				if ( bundle.getEntry(GENERATED_TESTS_LAYOUT) != null ) {
					TEST_PLUGINS.put(bundle, GENERATED_TESTS_LAYOUT);
				}
			}

			// We remove the core plugin, in order to build it first (important)
			GAMA_PLUGINS.remove(CORE_PLUGIN);
			try {
				preBuild(CORE_PLUGIN);
			} catch (final Exception e2) {
				ERR(ERROR_MESSAGE);
				ERR("Error in loading plugin " + CORE_PLUGIN.getSymbolicName() + ": " + e2.getMessage());
				System.exit(0);
				return;
			}
			// We then build the other extensions to the language
			for ( final Bundle addition : GAMA_PLUGINS ) {
				AbstractGamlAdditions.CURRENT_PLUGIN_NAME = addition.getSymbolicName();
				try {
					preBuild(addition);
				} catch (final Exception e1) {
					ERR(ERROR_MESSAGE);
					ERR("Error in loading plugin " + CORE_PLUGIN.getSymbolicName() + ": " + e1.getMessage());
					System.exit(0);
					return;
				}
			}
			AbstractGamlAdditions.CURRENT_PLUGIN_NAME = null;
			// We gather all the extensions to the `create` statement and add them
			// as delegates to CreateStatement. If an exception occurs, we discard it
			for ( final IConfigurationElement e : registry.getConfigurationElementsFor(CREATE_EXTENSION) ) {
				ICreateDelegate cd = null;
				try {
					// TODO Add the defining plug-in
					cd = (ICreateDelegate) e.createExecutableExtension("class");
					if ( cd != null ) {
						CreateStatement.addDelegate(cd);
					}
				} catch (final Exception e1) {
					ERR(ERROR_MESSAGE);
					ERR("Error in loading CreateStatement delegate : " + e1.getMessage());
					System.exit(0);
					return;

				}
			}

			// We gather all the display names (not the classes yet in order to not load graphical extensions )
			for ( final IConfigurationElement e : registry.getConfigurationElementsFor(DISPLAY_EXTENSION) ) {
				try {
					String kw = e.getAttribute("keyword");
					AbstractGamlAdditions.CONSTANTS.add(kw);
					IGui.DISPLAYS.put(kw, null);
				} catch (final Exception e1) {
					ERR(ERROR_MESSAGE);
					ERR("Error in loading display : " + e1.getMessage());
				}
			}

			// We gather all the extensions to the `event` statement and add them
			// as delegates to EventLayerStatement
			for ( final IConfigurationElement e : registry.getConfigurationElementsFor(EVENT_LAYER_EXTENSION) ) {
				try {
					// TODO Add the defining plug-in
					IEventLayerStatement.DELEGATES.add((IEventLayerDelegate) e.createExecutableExtension("class"));
				} catch (final CoreException e1) {

					ERR(ERROR_MESSAGE);
					ERR("Error in loading EventLayerStatement delegate : " + e1.getMessage());
					System.exit(0);
					return;

				}
			}

			// We gather all the GAMA_PLUGINS that explicitly declare models using
			// the non-default scheme (plugin > models ...).
			for ( final IConfigurationElement e : registry.getConfigurationElementsFor(MODELS_EXTENSION) ) {
				MODEL_PLUGINS.put(Platform.getBundle(e.getContributor().getName()), e.getAttribute("name"));
			}
			// CRUCIAL INITIALIZATIONS
			LOADED = true;
		});
		GAMA.initializeAtStartup("Initializing metamodel", () -> {
			GamaMetaModel.INSTANCE.build();
			// Types.init();

			// We gather all the content types extensions defined in GAMA plugins
			// (not in the other ones)
			final IExtensionPoint contentType = registry.getExtensionPoint(CONTENT_EXTENSION);
			final Set<IExtension> contentExtensions = new HashSet<>();
			contentExtensions.addAll(Arrays.asList(contentType.getExtensions()));
			for ( final IExtension ext : contentExtensions ) {
				final IConfigurationElement[] configs = ext.getConfigurationElements();
				for ( final IConfigurationElement config : configs ) {
					final String s = config.getAttribute("file-extensions");
					if ( s != null ) {
						HANDLED_FILE_EXTENSIONS.addAll(Arrays.asList(s.split(",")));
					}
				}
			}

			// We init the type hierarchy to gather additional types
			Types.init();
		});

	}

	@SuppressWarnings("unchecked")
	public static void preBuild(final Bundle bundle) throws Exception {

		GAMA.initializeAtStartup("Loading " + bundle.getSymbolicName(), () -> {
			GamaClassLoader.getInstance().addBundle(bundle);
			Class<IGamlAdditions> gamlAdditions = null;
			try {
				gamlAdditions = (Class<IGamlAdditions>) bundle.loadClass(ADDITIONS);
			} catch (final ClassNotFoundException e1) {
				ERR(">> Impossible to load additions from " + bundle.toString() + " because of " + e1);
				throw e1;

			}

			IGamlAdditions add = null;
			try {
				add = gamlAdditions.getDeclaredConstructor().newInstance();
			} catch (final InstantiationException e) {
				ERR(">> Impossible to instantiate additions from " + bundle);
				throw e;
			} catch (final IllegalAccessException e) {
				ERR(">> Impossible to access additions from " + bundle);
				throw e;
			}
			try {
				add.initialize();
			} catch (final SecurityException | NoSuchMethodException e) {
				ERR(">> Impossible to instantiate additions from " + bundle);
				throw e;
			}
		});
	}

	/**
	 * The list of GAMA_PLUGINS declaring models, together with the inner path to the folder containing model projects
	 *
	 * @return
	 */
	public static Multimap<Bundle, String> getPluginsWithModels() {
		return MODEL_PLUGINS;
	}

	public static Multimap<Bundle, String> getPluginsWithTests() {
		return TEST_PLUGINS;
	}

	public static void loadUI() {
		final Bundle bundle = Platform.getBundle("ummisco.gama.ui.shared");
		GamaClassLoader.getInstance().addBundle(bundle);
		try {
			bundle.start();
		} catch (final BundleException e) {
			e.printStackTrace();
		}
	}

	public static void loadAllDisplays() {
		if ( DISPLAY_INITIALIZED )
			return;
		DISPLAY_INITIALIZED = true;
		GAMA.initializeAtStartup("Loading displays ", () -> {
			for ( final IConfigurationElement e : registry.getConfigurationElementsFor(DISPLAY_EXTENSION) ) {
				try {
					String bundleName = e.getContributor().getName();
					Bundle bundle = Platform.getBundle(bundleName);
					GamaClassLoader.getInstance().addBundle(bundle);
					// TODO Add the defining plug-in

					String cl = e.getAttribute("class");
					String kw = e.getAttribute("keyword");
					if ( cl != null ) {
						Class<IDisplaySurface> cd =
							(Class<IDisplaySurface>) GamaClassLoader.getInstance().loadClass(cl);
						if ( cd != null ) {
							IGui.DISPLAYS.put(kw, new DisplayDescription(cd, kw, bundleName));
						}

					}
				} catch (final Exception e1) {
					ERR(ERROR_MESSAGE);
					ERR("Error in loading display : " + e1.getMessage());
					return;

				}
			}
		});
	}

}
