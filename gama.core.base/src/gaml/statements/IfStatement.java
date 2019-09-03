/*******************************************************************************************************
 *
 * gaml.statements.IfStatement.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.statements;

import com.google.common.collect.Iterables;

import gama.common.interfaces.IKeyword;
import gama.common.interfaces.IStatement;
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
import gaml.compilation.annotations.serializer;
import gaml.compilation.interfaces.ISymbol;
import gaml.descriptions.IDescription;
import gaml.descriptions.SymbolDescription;
import gaml.descriptions.SymbolSerializer.StatementSerializer;
import gaml.expressions.IExpression;
import gaml.operators.Strings;
import gaml.statements.IfStatement.IfSerializer;
import gaml.types.IType;

/**
 * IfPrototype.
 *
 * @author drogoul 14 nov. 07
 */
@symbol (
		name = IKeyword.IF,
		kind = ISymbolKind.SEQUENCE_STATEMENT,
		with_sequence = true,
		concept = { IConcept.CONDITION })
@inside (
		kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets (
		value = { @facet (
				name = IKeyword.CONDITION,
				type = IType.BOOL,
				optional = false,
				doc = @doc ("A boolean expression: the condition that is evaluated.")) },
		omissible = IKeyword.CONDITION)
@doc (
		value = "Allows the agent to execute a sequence of statements if and only if the condition evaluates to true.",
		usages = { @usage (
				value = "The generic syntax is:",
				examples = { @example (
						value = "if bool_expr {",
						isExecutable = false),
						@example (
								value = "    [statements]",
								isExecutable = false),
						@example (
								value = "}",
								isExecutable = false) }),
				@usage (
						value = "Optionally, the statements to execute when the condition evaluates to false can be defined in a following statement else. The syntax then becomes:",
						examples = { @example (
								value = "if bool_expr {",
								isExecutable = false),
								@example (
										value = "    [statements]",
										isExecutable = false),
								@example (
										value = "}",
										isExecutable = false),
								@example (
										value = "else {",
										isExecutable = false),
								@example (
										value = "    [statements]",
										isExecutable = false),
								@example (
										value = "}",
										isExecutable = false),
								@example (
										value = "string valTrue <- \"\";"),
								@example (
										value = "if true {"),
								@example (
										value = "	valTrue <- \"true\";"),
								@example (
										value = "}"),
								@example (
										value = "else {"),
								@example (
										value = "	valTrue <- \"false\";"),
								@example (
										value = "}"),
								@example (
										var = "valTrue",
										equals = "\"true\""),
								@example (
										value = "string valFalse <- \"\";"),
								@example (
										value = "if false {"),
								@example (
										value = "	valFalse <- \"true\";"),
								@example (
										value = "}"),
								@example (
										value = "else {"),
								@example (
										value = "	valFalse <- \"false\";"),
								@example (
										value = "}"),
								@example (
										var = "valFalse",
										equals = "\"false\"") }),
				@usage (
						value = "ifs and elses can be imbricated as needed. For instance:",
						examples = { @example (
								value = "if bool_expr {",
								isExecutable = false),
								@example (
										value = "    [statements]",
										isExecutable = false),
								@example (
										value = "}",
										isExecutable = false),
								@example (
										value = "else if bool_expr2 {",
										isExecutable = false),
								@example (
										value = "    [statements]",
										isExecutable = false),
								@example (
										value = "}",
										isExecutable = false),
								@example (
										value = "else {",
										isExecutable = false),
								@example (
										value = "    [statements]",
										isExecutable = false),
								@example (
										value = "}",
										isExecutable = false) }) })
@serializer (IfSerializer.class)
public class IfStatement extends AbstractStatementSequence {

	public static class IfSerializer extends StatementSerializer {

		@Override
		protected void serializeChildren(final SymbolDescription desc, final StringBuilder sb,
				final boolean includingBuiltIn) {
			sb.append(' ').append('{').append(Strings.LN);
			final String[] elseString = new String[] { null };
			desc.visitChildren(s -> {
				if (s.getKeyword().equals(IKeyword.ELSE)) {
					elseString[0] = s.serialize(false) + Strings.LN;
				} else {
					serializeChild(s, sb, includingBuiltIn);
				}
				return true;
			});

			sb.append('}');
			if (elseString[0] != null) {
				sb.append(elseString[0]);
			} else {
				sb.append(Strings.LN);
			}

		}

	}

	public IStatement alt;
	final IExpression cond;

	/**
	 * The Constructor.
	 *
	 * @param sim
	 *            the sim
	 */
	public IfStatement(final IDescription desc) {
		super(desc);
		cond = getFacet(IKeyword.CONDITION);
		if (cond != null) {
			setName("if " + cond.serialize(false));
		}

	}

	@Override
	public void setChildren(final Iterable<? extends ISymbol> commands) {
		for (final ISymbol c : commands) {
			if (c instanceof ElseStatement) {
				alt = (IStatement) c;
			}
		}
		super.setChildren(Iterables.filter(commands, each -> each != alt));
	}

	@Override
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		final Object condition = cond.value(scope);
		if (!(condition instanceof Boolean)) {
			throw GamaRuntimeException.error("Impossible to evaluate condition " + cond.serialize(true), scope);
		}
		return (Boolean) condition ? super.privateExecuteIn(scope) : alt != null ? scope.execute(alt).getValue() : null;
	}
}