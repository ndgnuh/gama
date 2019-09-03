/*******************************************************************************************************
 *
 * gaml.statements.IExecutable.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.common.interfaces;

import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.statements.Arguments;

/**
 * Class IExecutable.
 *
 * @author drogoul
 * @since 20 août 2013
 *
 */
public interface IExecutable {

	Object executeOn(final IScope scope) throws GamaRuntimeException;

	default void setRuntimeArgs(final IScope executionScope, final Arguments args) {
		// Do nothing
	}

	default void setMyself(final IAgent caller) {
		// Do nothing
	}

}
