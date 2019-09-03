/*******************************************************************************************************
 *
 * gama.runtime.benchmark.BenchmarkRecord.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.runtime.benchmark;

import java.util.concurrent.atomic.LongAdder;

import gama.common.interfaces.IBenchmarkable;

public class BenchmarkRecord {

	public static BenchmarkRecord NULL = new BenchmarkRecord(() -> "unknown");
	public final LongAdder milliseconds = new LongAdder(), times = new LongAdder();
	public final IBenchmarkable object;

	public BenchmarkRecord(final IBenchmarkable object) {
		this.object = object;
	}

	public boolean isUnrecorded() {
		return times.longValue() == 0l;
	}

}
