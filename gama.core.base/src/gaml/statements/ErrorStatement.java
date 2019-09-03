/*******************************************************************************************************
 *
 * gaml.statements.ErrorStatement.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.statements;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IKeyword;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.example;
import gama.processor.annotations.GamlAnnotations.facet;
import gama.processor.annotations.GamlAnnotations.facets;
import gama.processor.annotations.GamlAnnotations.inside;
import gama.processor.annotations.GamlAnnotations.symbol;
import gama.processor.annotations.GamlAnnotations.usage;
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

@symbol (
		name = IKeyword.ERROR,
		kind = ISymbolKind.SINGLE_STATEMENT,
		with_sequence = false,
		concept = { IConcept.SYSTEM })
@inside (
		kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets (
		value = { @facet (
				name = IKeyword.MESSAGE,
				type = IType.STRING,
				optional = false,
				doc = @doc ("the message to display in the error.")) },
		omissible = IKeyword.MESSAGE)
@doc (
		value = "The statement makes the agent output an error dialog (if the simulation contains a user interface). Otherwise displays the error in the console.",
		usages = { @usage (
				value = "Throwing an error",
				examples = { @example ("error 'This is an error raised by ' + self;") }) })
public class ErrorStatement extends AbstractStatement {

	final IExpression message;

	public ErrorStatement(final IDescription desc) {
		super(desc);
		message = getFacet(IKeyword.MESSAGE);
	}

	@Override
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		final IAgent agent = scope.getAgent();
		String mes = null;
		if (agent != null && !agent.dead()) {
			mes = Cast.asString(scope, message.value(scope));
			throw GamaRuntimeException.error(mes, scope);
		}
		return mes;
	}

}
