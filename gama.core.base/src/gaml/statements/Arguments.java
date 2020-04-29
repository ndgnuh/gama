/*******************************************************************************************************
 *
 * gaml.statements.Arguments.java, in plugin gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.statements;

import gama.common.interfaces.IAgent;
import gama.runtime.scope.IScope;
import gaml.expressions.IExpression;

/**
 * @author drogoul
 */
public class Arguments extends Facets {

	/*
	 * The caller represents the agent in the context of which the arguments need to be evaluated.
	 */
	ThreadLocal<IAgent> caller = new ThreadLocal<>();

	public Arguments() {}

	public Arguments(final Arguments args) {
		super(args);
		if (args != null) {
			setCaller(args.caller.get());
		}
	}

	public Arguments resolveAgainst(final IScope scope) {
		final Arguments result = new Arguments();
		result.setCaller(caller.get());
		for (final Facet f : facets) {
			final IExpression exp = getExpr(f.key);
			if (exp != null) {
				result.put(f.key, exp.resolveAgainst(scope));
			}
		}
		return result;
	}

	@Override
	public Arguments cleanCopy() {
		final Arguments result = new Arguments();
		result.setCaller(caller.get());
		for (final Facet f : facets) {
			result.facets.add(f.cleanCopy());
		}
		return result;
	}

	public void setCaller(final IAgent caller) {
		this.caller.set(caller);
	}

	public IAgent getCaller() {
		return caller.get();
	}

	@Override
	public void dispose() {
		super.dispose();
		caller.set(null);
	}

}
