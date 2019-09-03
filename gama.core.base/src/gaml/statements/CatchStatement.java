/*******************************************************************************************************
 *
 * gaml.statements.CatchStatement.java, in plugin gama.core,
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
import gaml.descriptions.IDescription;

/**
 * Written by drogoul Modified on 8 févr. 2010
 * 
 * @todo Description
 * 
 */
@symbol (
		name = IKeyword.CATCH,
		kind = ISymbolKind.SEQUENCE_STATEMENT,
		with_sequence = true,
		concept = { IConcept.ACTION })
@inside (
		symbols = IKeyword.TRY)
@doc (
		value = "This statement cannot be used alone",
		see = { IKeyword.TRY })
public class CatchStatement extends AbstractStatementSequence {

	public CatchStatement(final IDescription desc) {
		super(desc);
		setName(IKeyword.CATCH);
	}

}
