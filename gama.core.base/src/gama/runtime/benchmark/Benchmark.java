/*******************************************************************************************************
 *
 * gama.runtime.benchmark.Benchmark.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.runtime.benchmark;

import java.util.concurrent.ConcurrentHashMap;

import gama.common.interfaces.IBenchmarkable;
import gama.common.interfaces.experiment.IExperimentPlan;
import gama.runtime.benchmark.Benchmark.ScopeRecord;
import gama.runtime.scope.IScope;

public class Benchmark extends ConcurrentHashMap<IScope, ScopeRecord> {

	class ScopeRecord extends ConcurrentHashMap<IBenchmarkable, BenchmarkRecord> {

		final BenchmarkRecord ownRecord;

		public ScopeRecord(final IScope scope) {
			ownRecord = new BenchmarkRecord(scope);
		}

		public BenchmarkRecord find(final IBenchmarkable object) {
			return computeIfAbsent(object, (o) -> new BenchmarkRecord(o));
		}

		public StopWatch getStopWatchFor(final IBenchmarkable desc) {
			return new StopWatch(ownRecord, find(desc));
		}

	}

	public final BenchmarkTree tree;

	public Benchmark(final IExperimentPlan experiment) {
		tree = new BenchmarkTree(experiment.getModel().getDescription(), experiment.getDescription());
	}

	public StopWatch record(final IScope scope, final IBenchmarkable symbol) {
		return computeIfAbsent(scope, (s) -> new ScopeRecord(s)).getStopWatchFor(symbol).start();
	}

	public void saveAndDispose(final IExperimentPlan experiment) {
		new BenchmarkConsolePrinter().print(this);
		new BenchmarkCSVExporter().save(experiment, this);
		tree.dispose();
		clear();
	}

}
