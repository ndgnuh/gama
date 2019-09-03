/*******************************************************************************************************
 *
 * gaml.architecture.finite_state_machine.FsmArchitecture.java, in plugin gama.core, is part of the source code
 * of the GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.architecture.finite_state_machine;

import java.util.Map;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IKeyword;
import gama.common.interfaces.IStatement;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.getter;
import gama.processor.annotations.GamlAnnotations.setter;
import gama.processor.annotations.GamlAnnotations.skill;
import gama.processor.annotations.GamlAnnotations.variable;
import gama.processor.annotations.GamlAnnotations.vars;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.list.GamaListFactory;
import gama.util.list.IList;
import gama.util.map.GamaMapFactory;
import gaml.architecture.reflex.ReflexArchitecture;
import gaml.species.ISpecies;
import gaml.types.IType;
import gaml.types.Types;

/**
 * Written by drogoul Modified on 12 sept. 2010
 *
 * @todo Description
 *
 */
@vars ({ @variable (
		name = IKeyword.STATE,
		type = IType.STRING,
		doc = @doc ("Returns the current state in which the agent is")),
		@variable (
				name = IKeyword.STATES,
				type = IType.LIST,
				constant = true,
				doc = @doc ("Returns the list of all possible states the agents can be in")) })
@skill (
		name = IKeyword.FSM,
		concept = { IConcept.BEHAVIOR, IConcept.ARCHITECTURE },
		doc = @doc ("The Finite State Machine architecture, that allows to program agents using a finite set of states and conditional transitions between them"))
public class FsmArchitecture extends ReflexArchitecture {

	protected final Map<String, FsmStateStatement> states = GamaMapFactory.createUnordered();
	protected FsmStateStatement initialState;

	@Override
	protected void clearBehaviors() {
		super.clearBehaviors();
		states.clear();
	}

	@Override
	public void verifyBehaviors(final ISpecies context) {
		super.verifyBehaviors(context);
		for (final FsmStateStatement s : states.values()) {
			if (s.isInitial()) {
				initialState = s;
			}
		}
		if (initialState != null) {
			context.getVar(IKeyword.STATE).setValue(null, initialState.getName());
		}
	}

	@getter (
			value = IKeyword.STATES,
			initializer = true)
	public IList<String> getStateNames(final IAgent agent) {
		return GamaListFactory.wrap(Types.STRING, states.keySet());
	}

	@setter (IKeyword.STATES)
	public void setStateNames(final IAgent agent, final IList<String> list) {}

	@getter (IKeyword.STATE)
	public String getStateName(final IAgent agent) {
		final FsmStateStatement currentState = (FsmStateStatement) agent.getAttribute(IKeyword.CURRENT_STATE);
		if (currentState == null) { return null; }
		return currentState.getName();
	}

	public FsmStateStatement getState(final String stateName) {
		return states.get(stateName);
	}

	@setter (IKeyword.STATE)
	public void setStateName(final IAgent agent, final String stateName) {
		if (stateName != null && states.containsKey(stateName)) {
			setCurrentState(agent, states.get(stateName));
		}
	}

	@Override
	public void addBehavior(final IStatement c) {
		if (c instanceof FsmStateStatement) {
			final FsmStateStatement state = (FsmStateStatement) c;
			states.put(state.getName(), state);
		} else {
			super.addBehavior(c);
		}
	}

	@Override
	public Object executeOn(final IScope scope) throws GamaRuntimeException {
		super.executeOn(scope);
		return executeCurrentState(scope);
	}

	protected Object executeCurrentState(final IScope scope) throws GamaRuntimeException {
		final IAgent agent = getCurrentAgent(scope);
		if (scope.interrupted()) { return null; }
		final FsmStateStatement currentState = (FsmStateStatement) agent.getAttribute(IKeyword.CURRENT_STATE);
		if (currentState == null) { return null; }
		return scope.execute(currentState).getValue();
	}

	public void setCurrentState(final IAgent agent, final FsmStateStatement state) {
		final FsmStateStatement currentState = (FsmStateStatement) agent.getAttribute(IKeyword.CURRENT_STATE);
		if (currentState == state) { return; }
		// if ( currentState != null && currentState.hasExitActions() ) {
		// agent.setAttribute(IKeyword.STATE_TO_EXIT, currentState);
		// }
		agent.setAttribute(IKeyword.ENTER, true);
		agent.setAttribute(IKeyword.CURRENT_STATE, state);
	}

	/***
	 * What happens when the agent dies: calls the exit statement of the current state if it exists (see Issue #2865)
	 */
	@Override
	public boolean abort(final IScope scope) throws GamaRuntimeException {
		final IAgent agent = getCurrentAgent(scope);
		if (scope.interrupted() || agent == null) { return true; }
		final FsmStateStatement currentState = (FsmStateStatement) agent.getAttribute(IKeyword.CURRENT_STATE);
		if (currentState == null) { return true; }
		currentState.haltOn(scope);
		// and we return the regular abort behavior
		return super.abort(scope);
	}
}
