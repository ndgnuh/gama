/*********************************************************************************************
 *
 *
 * 'HeadlessSimulationLoader.java', in plugin 'gama.core.headless', is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.core.headless.core;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;

import com.google.inject.Injector;

import gama.common.interfaces.IModel;
import gama.common.preferences.GamaPreferences;
import gama.core.lang.GamlStandaloneSetup;
import gama.dev.utils.DEBUG;
import gama.runtime.GAMA;
import gama.runtime.exceptions.GamaRuntimeException;
import gaml.compilation.GAML;
import gaml.compilation.GamlCompilationError;
import one.util.streamex.StreamEx;

public class HeadlessSimulationLoader {

	public static Injector preloadGAMA() {
		DEBUG.LOG("GAMA configuring and loading...");
		System.setProperty("java.awt.headless", "true");
		GAMA.setHeadLessMode();
		Injector injector;
		try {
			// We initialize XText and Gaml.
			injector = GamlStandaloneSetup.doSetup();
		} catch (final Exception e1) {
			throw GamaRuntimeException.create(e1, GAMA.getRuntimeScope());
		}
		// SEED HACK // WARNING AD : Why ?
		GamaPreferences.External.CORE_SEED_DEFINED.set(true);
		GamaPreferences.External.CORE_SEED.set(1.0);
		// SEED HACK
		return injector;
	}

	/**
	 * Compiles a file to a GAMA model ready to be experimented
	 *
	 * @param myFile
	 *            the main model file
	 * @return a compiled model
	 * @throws IOException
	 *             in case the file is null or not found
	 * @throws GamaHeadlessException
	 *             in case the compilation ends in error
	 * @deprecated use loadModel(File, List<GamlCompilationError>) instead
	 */
	@Deprecated
	public static synchronized IModel loadModel(final File myFile) throws IOException, GamaHeadlessException {
		return loadModel(myFile, null);
	}

	/**
	 * Compiles a file to a GAMA model ready to be experimented
	 *
	 * @param myFile
	 *            the main model file
	 * @param errors
	 *            a list that will be filled with compilation errors / warnings (can be null)
	 * @return a compiled model
	 * @throws IOException
	 *             in case the file is null or not found
	 * @throws GamaHeadlessException
	 *             in case the compilation ends in error
	 */
	public static synchronized IModel loadModel(final File myFile, final List<GamlCompilationError> errors)
			throws IOException, GamaHeadlessException {
		return loadModel(myFile, errors, null);
	}

	/**
	 * Compiles a file to a GAMA model ready to be experimented
	 *
	 * @param myFile
	 *            the main model file
	 * @param errors
	 *            a list that will be filled with compilation errors / warnings (can be null)
	 * @param metaProperties
	 *            an instance of GamlProperties that will be filled with the sylmbolic names of bundles required to run
	 *            the model (can be null) and other informations (skills, operators, statements, ...).
	 * @return a compiled model
	 * @throws IOException
	 *             in case the file is null or not found
	 * @throws GamaHeadlessException
	 *             in case the compilation ends in error
	 */
	public static synchronized IModel loadModel(final File myFile, final List<GamlCompilationError> errors,
			final GamlProperties metaProperties) throws IOException, GamaHeadlessException {
		if (myFile == null)
			throw new IOException("Model file is null");
		final String fileName = myFile.getAbsolutePath();
		if (!myFile.exists())
			throw new IOException("Model file does not exist: " + fileName);
		DEBUG.LOG(fileName + " model is being compiled...");

		final IModel model = GAML.compile(URI.createFileURI(fileName), errors);
		if (model == null) {
			DEBUG.LOG("Model compiled with following indications: \n"
					+ (errors == null ? "" : StreamEx.of(errors).joining("\n")));
			throw new GamaHeadlessException(
					"Model cannot be compiled. See list of attached errors \n" + StreamEx.of(errors).joining("\n"));
		}
		// if (metaProperties != null)
		// model.getDescription().collectMetaInformation(metaProperties);
		return model;
	}

}
