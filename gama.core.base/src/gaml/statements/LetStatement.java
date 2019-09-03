/*******************************************************************************************************
 *
 * gaml.statements.LetStatement.java, in plugin gama.core,
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
import gama.processor.annotations.GamlAnnotations.facet;
import gama.processor.annotations.GamlAnnotations.facets;
import gama.processor.annotations.GamlAnnotations.inside;
import gama.processor.annotations.GamlAnnotations.symbol;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.compilation.annotations.serializer;
import gaml.compilation.annotations.validator;
import gaml.descriptions.IDescription;
import gaml.descriptions.SymbolDescription;
import gaml.statements.LetStatement.LetSerializer;
import gaml.statements.LetStatement.LetValidator;
import gaml.types.IType;

/**
 * Written by drogoul Modified on 6 févr. 2010
 * 
 * @todo Description
 * 
 */

@facets (
		value = { /* @facet(name = IKeyword.VAR, type = IType.NEW_TEMP_ID, optional = true), */
				@facet (
						name = IKeyword.NAME,
						type = IType.NEW_TEMP_ID,
						optional = false,
						doc = @doc ("The name of the variable declared ")),
				@facet (
						name = IKeyword.VALUE,
						type = { IType.NONE },
						optional = /* AD change false */true,
						doc = @doc ("The value assigned to this variable")),

				@facet (
						name = IKeyword.OF,
						type = { IType.TYPE_ID },
						optional = true,
						doc = @doc ("The type of the contents if this declaration concerns a container")),
				@facet (
						name = IKeyword.INDEX,
						type = IType.TYPE_ID,
						optional = true,
						doc = @doc ("The type of the index if this declaration concerns a container")),
				@facet (
						name = IKeyword.TYPE,
						type = { IType.TYPE_ID },
						optional = true,
						doc = @doc ("The type of the variable")) },
		omissible = IKeyword.NAME)
@symbol (
		name = { IKeyword.LET },
		kind = ISymbolKind.SINGLE_STATEMENT,
		concept = { IConcept.SYSTEM },
		with_sequence = false,
		doc = @doc ("Allows to declare a temporary variable of the specified type and to initialize it with a value"))
@inside (
		kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@validator (LetValidator.class)
@serializer (LetSerializer.class)
@doc ("Allows to declare a temporary variable of the specified type and to initialize it with a value")
public class LetStatement extends SetStatement {

	public static class LetSerializer extends AssignmentSerializer {

		@Override
		protected void serialize(final SymbolDescription desc, final StringBuilder sb, final boolean includingBuiltIn) {
			sb.append(desc.getGamlType().serialize(includingBuiltIn)).append(" ");
			super.serialize(desc, sb, includingBuiltIn);

		}

	}

	public static class LetValidator extends AssignmentValidator {

		/**
		 * Method validate()
		 * 
		 * @see gaml.compilation.interfaces.IDescriptionValidator#validate(gaml.descriptions.IDescription)
		 */
		@Override
		public void validate(final IDescription cd) {
			if (Assert.nameIsValid(cd)) {
				super.validate(cd);
			}
		}
	}

	public LetStatement(final IDescription desc) {
		super(desc);
		setName(IKeyword.LET + getVarName());
	}

	@Override
	protected Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		final Object val = value.value(scope);
		varExpr.setVal(scope, val, true);
		return val;
	}

}
