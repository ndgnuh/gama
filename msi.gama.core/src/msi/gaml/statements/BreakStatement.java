/*******************************************************************************************************
 *
 * msi.gaml.statements.BreakStatement.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gaml.statements;

import msi.gama.common.interfaces.IGamlIssue;
import msi.gama.common.interfaces.IKeyword;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.compilation.annotations.serializer;
import msi.gaml.compilation.annotations.validator;
import msi.gaml.compilation.interfaces.IDescriptionValidator;
import msi.gaml.descriptions.IDescription;
import msi.gaml.descriptions.StatementDescription;
import msi.gaml.descriptions.StatementWithChildrenDescription;
import msi.gaml.descriptions.SymbolDescription;
import msi.gaml.descriptions.SymbolSerializer;
import msi.gaml.statements.BreakStatement.BreakSerializer;
import msi.gaml.statements.BreakStatement.BreakValidator;
import ummisco.gama.processor.IConcept;
import ummisco.gama.processor.ISymbolKind;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.inside;
import ummisco.gama.processor.GamlAnnotations.symbol;

/**
 * The class BreakCommand.
 *
 * @author drogoul
 * @since 22 avr. 2012
 *
 */
@symbol (
		name = IKeyword.BREAK,
		kind = ISymbolKind.SINGLE_STATEMENT,
		with_sequence = false,
		concept = { IConcept.LOOP })
@inside (
		kinds = ISymbolKind.SEQUENCE_STATEMENT)
@doc (
		value = "`" + IKeyword.BREAK + "` allows to interrupt the current sequence of statements.")
@validator (BreakValidator.class)
@serializer (BreakSerializer.class)
public class BreakStatement extends AbstractStatement {

	public static class BreakSerializer extends SymbolSerializer<StatementDescription> {

		@Override
		protected void serialize(final SymbolDescription desc, final StringBuilder sb, final boolean includingBuiltIn) {
			sb.append(BREAK).append(";");
		}
	}

	public static class BreakValidator implements IDescriptionValidator<StatementDescription> {

		/**
		 * Method validate()
		 *
		 * @see msi.gaml.compilation.interfaces.IDescriptionValidator#validate(msi.gaml.descriptions.IDescription)
		 */
		@Override
		public void validate(final StatementDescription description) {
			IDescription superDesc = description.getEnclosingDescription();
			while (superDesc instanceof StatementWithChildrenDescription) {
				if (((StatementWithChildrenDescription) superDesc).isBreakable()) { return; }
				superDesc = superDesc.getEnclosingDescription();
			}
			description.error("'break' must be used in the context of a loop, a switch or an ask statement",
					IGamlIssue.WRONG_CONTEXT);
		}
	}

	/**
	 * @param desc
	 */
	public BreakStatement(final IDescription desc) {
		super(desc);
	}

	/**
	 * @see msi.gaml.commands.AbstractCommand#privateExecuteIn(msi.gama.runtime.scope.IScope)
	 */
	@Override
	protected Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		scope.interruptLoop();
		return null; // How to return the last object ??
	}

}
