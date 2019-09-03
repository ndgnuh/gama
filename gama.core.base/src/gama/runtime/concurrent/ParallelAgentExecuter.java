/*******************************************************************************************************
 *
 * gama.runtime.concurrent.ParallelAgentExecuter.java, in plugin gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.runtime.concurrent;

import java.util.Spliterator;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IExecutable;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;

public class ParallelAgentExecuter extends ParallelAgentRunner<Object> {

	final IExecutable executable;

	public ParallelAgentExecuter(final IScope scope, final IExecutable executable, final Spliterator<IAgent> agents) {
		super(scope, agents);
		this.executable = executable;
	}

	@Override
	public Object executeOn(final IScope scope) throws GamaRuntimeException {
		final Boolean[] mutableBoolean = { Boolean.TRUE };
		// final AccumulatingExecutionResult result = new AccumulatingExecutionResult();
		agents.forEachRemaining(each -> {
			if (mutableBoolean[0].booleanValue()) {
				// if (result.passed()) {
				mutableBoolean[0] = scope.execute(executable, each, null).passed();
				// result.accept(scope.execute(executable, each, null));
			}
		});
		return mutableBoolean[0];
		// return result.passed() ? result.getValue() : null;
	}

	@Override
	ParallelAgentExecuter subTask(final Spliterator<IAgent> sub) {
		return new ParallelAgentExecuter(originalScope, executable, sub);
	}

}