/*********************************************************************************************
 *
 *
 * GAMA modeling and simulation platform. 'Application.java', in plugin 'msi.gama.headless', is part of the source code
 * of the (v. 1.8.1)
 *
 * (c) 2007-2020 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package msi.gama.headless.runtime;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.w3c.dom.Document;

import msi.gama.headless.batch.documentation.ModelLibraryGenerator;
import msi.gama.headless.batch.test.ModelLibraryTester;
import msi.gama.headless.batch.validation.ModelLibraryRunner;
import msi.gama.headless.batch.validation.ModelLibraryValidator;
import msi.gama.headless.common.Globals;
import msi.gama.headless.common.HeadLessErrors;
import msi.gama.headless.core.GamaHeadlessException;
import msi.gama.headless.core.HeadlessSimulationLoader;
import msi.gama.headless.daemon.GamaDaemon;
import msi.gama.headless.job.ExperimentJob;
import msi.gama.headless.job.IExperimentJob;
import msi.gama.headless.script.ExperimentationPlanFactory;
import msi.gama.headless.xml.ConsoleReader;
import msi.gama.headless.xml.Reader;
import msi.gama.headless.xml.XMLWriter;
import msi.gama.runtime.GAMA;
import ummisco.gama.dev.utils.DEBUG;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application implements IApplication {
	class GamaDaemon implements Daemon {
		private ExecutorService executorService = Executors.newSingleThreadExecutor();

		@Override
		public void destroy() {
			// TODO Auto-generated method stub
			System.out.println("Destroy daemon");
		}

		@Override
		public void init(DaemonContext context) throws DaemonInitException, Exception {
			// TODO Auto-generated method stub
			System.out.println("Daemon initialization: " + context.getArguments());
		}

		@Override
		public void start() throws Exception {
			// TODO Auto-generated method stub
			this.executorService.execute(new Runnable() {

				CountDownLatch latch = new CountDownLatch(1);

				public void run() {
					try {
						latch.await();
					} catch (InterruptedException e) {
						System.out.println("Thread interrupted, probably means we're shutting down now.");
					}
				}
			});
		}

		@Override
		public void stop() throws Exception {
			// TODO Auto-generated method stub
			System.out.println("Shutting down");
			this.executorService.shutdown();
		}

	}

	final public static String CONSOLE_PARAMETER = "-c";
	final public static String GAMA_VERSION = "-version";
	final public static String TUNNELING_PARAMETER = "-p";
	final public static String THREAD_PARAMETER = "-hpc";
	final public static String SOCKET_PARAMETER = "-socket";
	final public static String VERBOSE_PARAMETER = "-v";
	final public static String HELP_PARAMETER = "-help";
	final public static String BUILD_XML_PARAMETER = "-xml";
	final public static String CHECK_MODEL_PARAMETER = "-check";
	final public static String VALIDATE_LIBRARY_PARAMETER = "-validate";
	final public static String RUN_LIBRARY_PARAMETER = "-runLibrary";
	final public static String TEST_LIBRARY_PARAMETER = "-test";
	final public static String RUN_GAMA_DAEMON = "-daemon";

	public static boolean headLessSimulation = false;
	public int numberOfThread = -1;
	public int socket = -1;
	public boolean consoleMode = false;
	public boolean tunnelingMode = false;
	public boolean verbose = false;
	public SimulationRuntime processorQueue;

	private static String showHelp() {
		final String res = " Welcome to Gama-platform.org version " + GAMA.VERSION + "\n"
				+ "sh ./gama-headless.sh [Options] [XML Input] [output directory]\n" + "\nList of available options:"
				+ "\n      -help     				 	-- get the help of the command line"
				+ "\n      -version     				-- get the the version of gama"
				+ "\n      -m [mem]    					-- allocate memory (ex 2048m)"
				+ "\n      -c        					-- start the console to write xml parameter file"
				+ "\n      -v 							-- verbose mode"
				+ "\n      -hpc [core] 					-- set the number of core available for experimentation"
				+ "\n      -socket [socketPort] 		-- start socket pipeline to interact with another framework"
				+ "\n" + "\n      -p        					-- start pipeline to interact with another framework"
				+ "\n"
				+ "\n      -validate [directory]    	-- invokes GAMA to validate the models present in the directory passed as argument"
				+ "\n      -test [directory]		   	-- invokes GAMA to execute the tests present in the directory and display their results"
				+ "\n      -failed		   				-- only display the failed and aborted test results"
				+ "\n" + " -daemon 						-- run Gama Daemon"
				+ "\n      -xml	[experimentName] [modelFile.gaml] [xmlOutputFile.xml]	-- only display the failed and aborted test results"
				+ "\n" + " sh ./gama-headless.sh -xml experimentName gamlFile xmlOutputFile\n"
				+ "\n      build an xml parameter file from a model" + "\n" + "\n";

		return res;
	}

	// private static boolean containParameter(final String[] args, final String
	// param) {
	// for (final String p : args) {
	// if (p.equals(param))
	// return true;
	// }
	// return false;
	// }

	private boolean checkParameters(final List<String> args) {

		int size = args.size();
		boolean mustContainInFile = true;
		boolean mustContainOutFile = true;
		if (args.contains(CONSOLE_PARAMETER)) {
			size = size - 1;
			mustContainInFile = false;
		}
		if (args.contains(TUNNELING_PARAMETER)) {
			size = size - 1;
			mustContainOutFile = false;
		}
		if (args.contains(SOCKET_PARAMETER)) {
			size = size - 2;
			mustContainOutFile = false;
		}
		if (args.contains(GAMA_VERSION)) {
			size = size - 1;
			mustContainOutFile = false;
		}
		if (args.contains(THREAD_PARAMETER)) {
			size = size - 2;
		}
		if (args.contains(VERBOSE_PARAMETER)) {
			size = size - 1;
		}
		if (args.contains(RUN_GAMA_DAEMON)) {
			mustContainInFile = false;
			mustContainOutFile = false;
		}
		if (mustContainInFile && mustContainOutFile && size < 2) {
			showError(HeadLessErrors.INPUT_NOT_DEFINED, null);
			return false;
		}
		if (!mustContainInFile && mustContainOutFile && size < 1) {
			showError(HeadLessErrors.OUTPUT_NOT_DEFINED, null);
			return false;
		}

		if (mustContainOutFile) {
			final int outIndex = args.size() - 1;
			Globals.OUTPUT_PATH = args.get(outIndex);
			Globals.IMAGES_PATH = Globals.OUTPUT_PATH + "/snapshot";
			final File output = new File(Globals.OUTPUT_PATH);
			if (!output.exists()) {
				output.mkdir();
			}
			final File images = new File(Globals.IMAGES_PATH);
			if (!images.exists()) {
				images.mkdir();
			}
		}

		if (mustContainInFile) {
			final int inIndex = mustContainOutFile ? args.size() - 2 : args.size() - 1;
			final File input = new File(args.get(inIndex));
			if (!input.exists()) {
				showError(HeadLessErrors.NOT_EXIST_FILE_ERROR, args.get(inIndex));
				return false;
			}
		}
		return true;
	}

	private static boolean showError(final int errorCode, final String path) {
		DEBUG.ON();
		DEBUG.ERR(HeadLessErrors.getError(errorCode, path));
		DEBUG.OFF();

		return false;
	}

	@Override
	public Object start(final IApplicationContext context) throws Exception {

		DEBUG.OFF();

		final Map<String, String[]> mm = context.getArguments();
		final List<String> args = Arrays.asList(mm.get("application.args"));
		if (args.contains(GAMA_VERSION)) {

		} else if (args.contains(HELP_PARAMETER)) {
			DEBUG.ON();
			DEBUG.LOG(showHelp());
			DEBUG.OFF();

		} else if (args.contains(RUN_GAMA_DAEMON)) {
			return GamaDaemon.run();
		} else if (args.contains(RUN_LIBRARY_PARAMETER)) {
			return ModelLibraryRunner.getInstance().start(args);
		} else if (args.contains(VALIDATE_LIBRARY_PARAMETER)) {
			return ModelLibraryValidator.getInstance().start(args);
		} else if (args.contains(TEST_LIBRARY_PARAMETER)) {
			return ModelLibraryTester.getInstance().start(args);
		} else if (args.contains(CHECK_MODEL_PARAMETER)) {
			ModelLibraryGenerator.start(this, args);
		} else if (args.contains(BUILD_XML_PARAMETER)) {
			buildXML(args);
		} else {
			runSimulation(args);
		}
		return null;
	}

	public String after(final List<String> args, final String arg) {
		if (args == null || args.size() < 2) {
			return null;
		}
		for (int i = 0; i < args.size() - 1; i++) {
			if (args.get(i).equals(arg)) {
				return args.get(i + 1);
			}
		}
		return null;
	}

	public void buildXML(final List<String> arg)
			throws ParserConfigurationException, TransformerException, IOException, GamaHeadlessException {
		verbose = arg.contains(VERBOSE_PARAMETER);
		if (this.verbose) {
			DEBUG.ON();
			DEBUG.LOG("Log active", true);
		}

		if (arg.size() < 3) {
			DEBUG.ON();
			DEBUG.ERR("Check your parameters!");
			DEBUG.ERR(showHelp());
			return;
		}
		HeadlessSimulationLoader.preloadGAMA();
		final List<IExperimentJob> jb = ExperimentationPlanFactory.buildExperiment(arg.get(arg.size() - 2));
		final ArrayList<IExperimentJob> selectedJob = new ArrayList<>();
		for (final IExperimentJob j : jb) {
			if (j.getExperimentName().equals(arg.get(arg.size() - 3))) {
				selectedJob.add(j);
				break;
			}
		}

		final Document dd = ExperimentationPlanFactory.buildXmlDocument(selectedJob);
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		final Transformer transformer = transformerFactory.newTransformer();
		final DOMSource source = new DOMSource(dd);
		final File output = new File(arg.get(arg.size() - 1));
		final StreamResult result = new StreamResult(output);
		transformer.transform(source, result);
		DEBUG.ON();
		DEBUG.LOG("Parameter file saved at: " + output.getAbsolutePath());
	}

	public void buildXMLForModelLibrary(final ArrayList<File> modelPaths, final String outputPath)
			throws ParserConfigurationException, TransformerException, IOException, GamaHeadlessException {
		// "arg[]" are the paths to the different models
		HeadlessSimulationLoader.preloadGAMA();
		final ArrayList<IExperimentJob> selectedJob = new ArrayList<>();
		for (final File modelFile : modelPaths) {
			final List<IExperimentJob> jb = ExperimentationPlanFactory.buildExperiment(modelFile.getAbsolutePath());
			for (final IExperimentJob j : jb) {
				selectedJob.add(j);
			}
		}

		final Document dd = ExperimentationPlanFactory.buildXmlDocumentForModelLibrary(selectedJob);
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		final Transformer transformer = transformerFactory.newTransformer();
		final DOMSource source = new DOMSource(dd);
		final File output = new File(outputPath);
		output.createNewFile();
		final StreamResult result = new StreamResult(output);
		transformer.transform(source, result);
		DEBUG.ON();
		DEBUG.LOG("Parameter file saved at: " + output.getAbsolutePath());
	}

	public void runXMLForModelLibrary(final String xmlPath) throws FileNotFoundException {

		processorQueue = new LocalSimulationRuntime();
		final Reader in = new Reader(xmlPath);
		in.parseXmlFile();
		this.buildAndRunSimulation(in.getSimulation());
		in.dispose();
		while (processorQueue.isPerformingSimulation()) {
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void runSimulation(final List<String> args) throws FileNotFoundException, InterruptedException {
		if (!checkParameters(args)) {
			System.exit(-1);
		}

		verbose = args.contains(VERBOSE_PARAMETER);
		if (verbose) {
			DEBUG.ON();

		}
		HeadlessSimulationLoader.preloadGAMA();
		this.tunnelingMode = args.contains(TUNNELING_PARAMETER);
		this.consoleMode = args.contains(CONSOLE_PARAMETER);
		if (args.contains(SOCKET_PARAMETER)) {
			this.socket = Integer.valueOf(after(args, SOCKET_PARAMETER));
		} else {
			this.socket = -1;
		}

		if (args.contains(THREAD_PARAMETER)) {
			this.numberOfThread = Integer.valueOf(after(args, THREAD_PARAMETER));
		} else {
			numberOfThread = SimulationRuntime.UNDEFINED_QUEUE_SIZE;
		}
		processorQueue = new LocalSimulationRuntime(this.numberOfThread);

		Reader in = null;
		if (this.verbose && !this.tunnelingMode) {
			DEBUG.ON();
		}

		if (this.consoleMode) {
			in = new Reader(ConsoleReader.readOnConsole());
		} else {
			in = new Reader(args.get(args.size() - 2));
		}
		in.parseXmlFile();
		this.buildAndRunSimulation(in.getSimulation());
		in.dispose();
		while (processorQueue.isPerformingSimulation()) {
			Thread.sleep(1000);
		}

		System.exit(0);
	}

	public void buildAndRunSimulation(final Collection<ExperimentJob> sims) {
		final Iterator<ExperimentJob> it = sims.iterator();
		while (it.hasNext()) {
			final ExperimentJob sim = it.next();
			try {
				XMLWriter ou = null;
				if (tunnelingMode) {
					ou = new XMLWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
				} else {
					ou = new XMLWriter(
							Globals.OUTPUT_PATH + "/" + Globals.OUTPUT_FILENAME + sim.getExperimentID() + ".xml");
				}
				sim.setBufferedWriter(ou);

				processorQueue.pushSimulation(sim);
			} catch (final Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	@Override
	public void stop() {
	}

}
