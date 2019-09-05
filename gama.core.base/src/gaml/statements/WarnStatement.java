/*******************************************************************************************************
 *
 * gaml.statements.WarnStatement.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.statements;

import gama.GAMA;
import gama.common.interfaces.IAgent;
import gama.common.interfaces.IKeyword;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.*;
import gama.runtime.*;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.descriptions.IDescription;
import gaml.expressions.IExpression;
import gaml.operators.Cast;
import gaml.types.IType;

/**
 * Written by drogoul Modified on 6 févr. 2010
 *
 * @todo Description
 *
 */

@symbol(name = IKeyword.WARNING, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false,
concept = { IConcept.SYSTEM })
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets(
	value = { @facet(name = IKeyword.MESSAGE,
		type = IType.STRING,
		optional = false,
		doc = @doc("the message to display as a warning.") ) },
	omissible = IKeyword.MESSAGE)
@doc(value = "The statement makes the agent output an arbitrary message in the error view as a warning.",
	usages = {
		@usage(value = "Emmitting a warning", examples = { @example("warn 'This is a warning from ' + self;") }) })
public class WarnStatement extends AbstractStatement {

	final IExpression message;

	public WarnStatement(final IDescription desc) {
		super(desc);
		message = getFacet(IKeyword.MESSAGE);
	}

	@Override
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		IAgent agent = scope.getAgent();
		String mes = null;
		if ( agent != null && !agent.dead() ) {
			mes = Cast.asString(scope, message.value(scope));
			GAMA.reportError(scope, GamaRuntimeException.warning(mes, scope), false);
		}
		return mes;
	}

	// @Override
	// public IType getType() {
	// return Types.STRING;
	// }

}
