/*******************************************************************************************************
 *
 * gama.runtime.benchmark.BenchmarkConsolePrinter.java, in plugin gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.runtime.benchmark;

import gama.common.util.TextBuilder;
import gama.dev.utils.DEBUG;
import gama.util.tree.GamaTree.Order;
import gaml.operators.Strings;

public class BenchmarkConsolePrinter {
	static final String S = "\n------------------------------------------------\n";

	public void print(final Benchmark scopes) {
		try (TextBuilder sb = TextBuilder.create()) {
			scopes.forEach((s, r) -> {
				final BenchmarkRecord sr = r.ownRecord;
				sb.append(S).append(sr.object.getNameForBenchmarks()).append(" (").append(sr.milliseconds)
						.append(" ms)").append(S);
				scopes.tree.visit(Order.PRE_ORDER, (n) -> {
					final BenchmarkRecord br = r.find(n.getData());
					if (br != BenchmarkRecord.NULL && !br.isUnrecorded()) {
						sb.append(Strings.LN)
								.append(String.format("%30s", "[" + br.milliseconds + " ms, " + br.times + " calls] "));
						for (int i = 0; i < n.getWeight(); i++) {
							sb.append("-" + Strings.TAB);
						}
						sb.append(' ').append(br.object.getNameForBenchmarks());

					}
				});
				sb.append(Strings.LN);
			});
			DEBUG.LOG(sb.toString());
		}
	}

}
