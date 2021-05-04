/*
 * This file is basically a script
 * Do not split it up into multiple classes
 * There's absolutely no benefit doing so
 */
package msi.gama.headless.runtime;

import java.util.List;

import org.apache.commons.cli.*;

import ummisco.gama.dev.utils.DEBUG;

public class Application  {
	/* 3 phase:
	 * 1. Define options
	 * 2. Parsing
	 * 3. Process
	 */
	public static Options options = new Options();
	public static CommandLineParser parser = new DefaultParser();
	public static CommandLine commands = null;
	public static boolean needInput = true;
	public static boolean needOutput = true;

	/* 
	 * CLI STUFFS
	 */
	public static void showHelp () {
		/* Jesus, why do you need a class just for showing help? */
		HelpFormatter help = new HelpFormatter();
		help.printHelp("gama-headless <OPTIONS>", options);
	}

	public static void showHelp (Exception e) {
		showHelp();
		System.out.println(e.getMessage());
	}
	
	/* I'm too lazy to type out the whole System.exit */
	public static void exit(int code) { System.exit(code); }
	public static void exit() { exit(0); }


	public static void main(String args[]) {
		/*
		 * A bunch of options
		 * TODO: split into group for make help message easier to read
		 * */
		options.addOption("d", "daemonize", false, "Daemonize");
		options.addOption("k", "kill", false, "Kill the daemon");
		options.addOption("h", "help", false, "Show help");
		options.addOption("v", "version", false, "Show version and exit");
		options.addOption("x", "xml", false, "Generate xml experiment file");
		options.addOption("i", "input", true, "Input file/folder");
		options.addOption("o", "output", true, "Output file/folder");
		options.addOption("verbose", "verbose", false, "Verbose logging");
		
		/* Parsing */
		if (args.length == 0) { showHelp(); }
		try {
			commands = parser.parse(options, args);
		} catch (ParseException e) {
			showHelp(e);
			exit(2);
			return;
		}
		
		/* Process
		 * There's no need to check for the long options
		 */
		if (commands.hasOption("h")) {
			showHelp();
			exit();
		}
		if (commands.hasOption("d")) {
			needInput = false;
			needOutput = false;
		}
		if (commands.hasOption("verbose")) {
			DEBUG.ON();
		}
		
		exit();
	}
}