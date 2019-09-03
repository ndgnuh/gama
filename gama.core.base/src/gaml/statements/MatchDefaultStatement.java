/*******************************************************************************************************
 *
 * gaml.statements.MatchDefaultStatement.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.statements;

import gama.common.interfaces.IKeyword;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.inside;
import gama.processor.annotations.GamlAnnotations.symbol;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.descriptions.IDescription;

@symbol (
		name = { IKeyword.DEFAULT },
		kind = ISymbolKind.SEQUENCE_STATEMENT,
		with_sequence = true,
		unique_in_context = true,
		concept = { IConcept.CONDITION })
@inside (
		symbols = IKeyword.SWITCH)
@doc (
		value = "Used in a switch match structure, the block prefixed by default is executed only if no other block has matched (otherwise it is not).",
		see = { "switch", "match" })
public class MatchDefaultStatement extends MatchStatement {

	public MatchDefaultStatement(final IDescription desc) {
		super(desc);
	}

	@Override
	public boolean matches(final IScope scope, final Object switchValue) throws GamaRuntimeException {
		return false;
	}

}