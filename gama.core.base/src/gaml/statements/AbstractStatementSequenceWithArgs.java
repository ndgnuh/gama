/*******************************************************************************************************
 *
 * gaml.statements.AbstractStatementSequenceWithArgs.java, in plugin gama.core, is part of the source code of
 * the GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.statements;

import gama.common.interfaces.IStatement.WithArgs;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.descriptions.IDescription;

/**
 * Class AbstractStatementSequenceWithArgs.
 *
 * @author drogoul
 * @since 11 mai 2014
 *
 */
public class AbstractStatementSequenceWithArgs extends AbstractStatementSequence implements WithArgs {

	final ThreadLocal<Arguments> actualArgs = new ThreadLocal<>();

	/**
	 * @param desc
	 */
	public AbstractStatementSequenceWithArgs(final IDescription desc) {
		super(desc);
	}

	/**
	 * Method setFormalArgs()
	 *
	 * @see gama.common.interfaces.IStatement.WithArgs#setFormalArgs(gaml.statements.Arguments)
	 */
	@Override
	public void setFormalArgs(final Arguments args) {}

	/**
	 * Method setRuntimeArgs()
	 *
	 * @see gama.common.interfaces.IStatement.WithArgs#setRuntimeArgs(gaml.statements.Arguments)
	 */
	@Override
	public void setRuntimeArgs(final IScope scope, final Arguments args) {
		// TODO Verify that this copy of the arguments is required or not.
		actualArgs.set(new Arguments(args));
	}

	@Override
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		scope.stackArguments(actualArgs.get());
		return super.privateExecuteIn(scope);
	}

}
