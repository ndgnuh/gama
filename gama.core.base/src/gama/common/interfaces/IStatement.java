/*******************************************************************************************************
 *
 * gaml.statements.IStatement.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.common.interfaces;

import gama.runtime.scope.IScope;
import gaml.compilation.interfaces.ISymbol;
import gaml.statements.Arguments;

/**
 * Written by drogoul Feb. 2009
 * 
 * @todo Description
 * 
 */
public interface IStatement extends ISymbol, IExecutable {

	public interface WithArgs extends IStatement {

		public abstract void setFormalArgs(Arguments args);

		public abstract void setRuntimeArgs(IScope scope, Arguments args);

	}

	public interface Breakable extends IStatement {
		// Unused tagging interface (for the moment)
	}

}
