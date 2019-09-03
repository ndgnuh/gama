/*******************************************************************************************************
 *
 * msi.gaml.statements.ElseStatement.java, in plugin msi.gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package msi.gaml.statements;

import msi.gama.common.interfaces.IKeyword;
import msi.gaml.descriptions.IDescription;
import ummisco.gama.processor.*;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.inside;
import ummisco.gama.processor.GamlAnnotations.symbol;

/**
 * Written by drogoul Modified on 8 févr. 2010
 * 
 * @todo Description
 * 
 */
@symbol(name = IKeyword.ELSE, kind = ISymbolKind.SEQUENCE_STATEMENT, with_sequence = true, concept = { IConcept.CONDITION })
@inside(symbols = IKeyword.IF)
@doc(value="This statement cannot be used alone",see={IKeyword.IF})
public class ElseStatement extends AbstractStatementSequence {

	public ElseStatement(final IDescription desc) {
		super(desc);
		setName(IKeyword.ELSE);
	}

}
