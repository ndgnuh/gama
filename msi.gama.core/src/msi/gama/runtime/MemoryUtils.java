/*******************************************************************************************************
 *
 * msi.gama.runtime.MemoryUtils.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gama.runtime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import msi.gama.common.preferences.GamaPreferences;
import msi.gama.common.preferences.Pref;
import msi.gama.common.util.FileUtils;

/**
 * All-purpose static-method container class.
 *
 * @author Sebastiano Vigna & Alexis Drogoul
 * @since 0.1
 */

public final class MemoryUtils {
	private MemoryUtils() {}

	public static void initialize() {
		final File ini = FileUtils.findIniFile();
		final int memory = readMaxMemoryInMegabytes(ini);
		final String text = ini == null || memory == 0
				? "The max. memory allocated needs to be set in Eclipse (developer version) or in Gama.ini file"
				: "Maximum memory allocated in Mb (requires to restart GAMA)";
		final Pref<Integer> p = GamaPreferences
				.create("pref_memory_max", text, memory == 0 ? (int) availableMemory() : memory, 1, false)
				.in(GamaPreferences.Runtime.NAME, GamaPreferences.Runtime.MEMORY);
		if (memory == 0) {
			p.disabled();
		}
		p.onChange(newValue -> {
			MemoryUtils.changeMaxMemory(ini, newValue);
			p.restartRequired();
		});

	}

	/** A static reference to {@link Runtime#getRuntime()}. */
	public final static Runtime RUNTIME = Runtime.getRuntime();

	/**
	 * Returns true if less then a percentage of the available memory is free.
	 *
	 */
	public static boolean memoryIsLow() {
		return availableMemory() * 100 < RUNTIME.totalMemory()
				* GamaPreferences.Runtime.CORE_MEMORY_PERCENTAGE.getValue();
	}

	/**
	 * Returns the amount of available memory (free memory plus never allocated memory).
	 *
	 * @return the amount of available memory, in bytes.
	 */
	public static long availableMemory() {
		return RUNTIME.freeMemory() + RUNTIME.maxMemory() - RUNTIME.totalMemory();
	}

	public static int readMaxMemoryInMegabytes(final File ini) {
		try {
			if (ini != null) {
				try (final FileInputStream stream = new FileInputStream(ini);
						final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));) {
					String s = reader.readLine();
					while (s != null) {
						if (s.startsWith("-Xmx")) {
							final char last = s.charAt(s.length() - 1);
							double divider = 1000000;
							boolean unit = false;
							switch (last) {
								case 'k':
								case 'K':
									unit = true;
									divider = 1000;
									break;
								case 'm':
								case 'M':
									unit = true;
									divider = 1;
									break;
								case 'g':
								case 'G':
									unit = true;
									divider = 0.001;
									break;
							}
							String trim = s;
							trim = trim.replace("-Xmx", "");
							if (unit) {
								trim = trim.substring(0, trim.length() - 1);
							}
							final int result = Integer.parseInt(trim);
							return (int) (result / divider);

						}
						s = reader.readLine();
					}
				}
			}
		} catch (final IOException e) {}
		return 0;

	}

	public static void changeMaxMemory(final File ini, final int memory) {
		final int mem = memory < 128 ? 128 : memory;
		try {
			final List<String> contents = new ArrayList<>();
			if (ini != null) {
				try (final FileInputStream stream = new FileInputStream(ini);
						final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));) {
					String s = reader.readLine();
					while (s != null) {
						if (s.startsWith("-Xmx")) {
							s = "-Xmx" + mem + "m";
						}
						contents.add(s);
						s = reader.readLine();
					}
				}
				try (final FileOutputStream os = new FileOutputStream(ini);
						final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));) {
					for (final String line : contents) {
						writer.write(line);
						writer.newLine();
					}
					writer.flush();
				}
			}
		} catch (final IOException e) {}

	}

}
