/*******************************************************************************************************
 *
 * gaml.architecture.user.UserInitPanelStatement.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.architecture.user;

import gama.common.interfaces.IKeyword;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.inside;
import gama.processor.annotations.GamlAnnotations.symbol;
import gaml.descriptions.IDescription;

@symbol(name = IKeyword.USER_INIT, kind = ISymbolKind.BEHAVIOR, with_sequence = true, concept = { IConcept.GUI })
@inside(kinds = { ISymbolKind.SPECIES, ISymbolKind.EXPERIMENT, ISymbolKind.MODEL })
@doc(value = "Used in the user control architecture, user_init is executed only once when the agent is created. It opens a special panel (if it contains user_commands statements). It is the equivalent to the init block in the basic agent architecture.", see = {
		IKeyword.USER_COMMAND, IKeyword.USER_INIT, IKeyword.USER_INPUT })
public class UserInitPanelStatement extends UserPanelStatement {

	public UserInitPanelStatement(final IDescription desc) {
		super(desc);
	}

}
