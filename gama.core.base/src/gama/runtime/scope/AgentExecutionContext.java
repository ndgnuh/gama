package gama.runtime.scope;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IDisposable;
import gama.common.util.PoolUtils;

class AgentExecutionContext implements IDisposable {

	private static final PoolUtils.ObjectPool<AgentExecutionContext> POOL =
			PoolUtils.create("Agent Execution Context", true, () -> new AgentExecutionContext(), null);

	public static AgentExecutionContext create(final IAgent agent, final AgentExecutionContext outer) {
		final AgentExecutionContext result = POOL.get();
		result.agent = agent;
		result.outer = outer;
		return result;
	}

	IAgent agent;
	AgentExecutionContext outer;

	private AgentExecutionContext() {}

	public IAgent getAgent() {
		return agent;
	}

	@Override
	public String toString() {
		return "context of " + agent;
	}

	public AgentExecutionContext getOuterContext() {
		return outer;
	}

	@Override
	public void dispose() {
		agent = null;
		outer = null;
		POOL.release(this);
	}

	public AgentExecutionContext createCopy() {
		return create(agent, outer);
	}

}