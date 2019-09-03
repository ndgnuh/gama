/*******************************************************************************************************
 *
 * gaml.statements.test.AssertStatement.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.statements.test;

import java.util.Collection;

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
import gama.runtime.exceptions.GamaAssertException;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.descriptions.IDescription;
import gaml.expressions.IExpression;
import gaml.operators.Cast;
import gaml.statements.AbstractStatement;
import gaml.types.IType;

@symbol (
		name = { "assert" },
		kind = ISymbolKind.SINGLE_STATEMENT,
		with_sequence = false,
		concept = { IConcept.TEST })
@facets (
		value = { @facet (
				name = IKeyword.VALUE,
				type = IType.BOOL,
				optional = false,
				doc = @doc ("a boolean expression. If its evaluation is true, the assertion is successful. Otherwise, an error (or a warning) is raised.")),
				@facet (
						name = "warning",
						type = IType.BOOL,
						optional = true,
						doc = @doc ("if set to true, makes the assertion emit a warning instead of an error")) },
		omissible = IKeyword.VALUE)
@inside (
		symbols = { "test", "action" },
		kinds = { ISymbolKind.ACTION, ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT })
@doc (
		value = "Allows to check if the evaluation of a given expression returns true. If not, an error (or a warning) is raised. If the statement is used inside a test, the error is not propagagated but invalidates the test (in case of a warning, it partially invalidates it). Otherwise, it is normally propagated",
		usages = { @usage (
				value = "Any boolean expression can be used",
				examples = { @example ("assert (2+2) = 4;"), @example ("assert self != nil;"),
						@example ("int t <- 0; assert is_error(3/t);"), @example ("(1 / 2) is float") }),

				@usage (
						value = "if the 'warn:' facet is set to true, the statement emits a warning (instead of an error) in case the expression is false",
						examples = { @example ("assert 'abc' is string warning: true") }) },
		see = { "test", "setup", "is_error", "is_warning" })
public class AssertStatement extends AbstractStatement implements WithTestSummary<AssertionSummary> {

	final IExpression value, warn;
	final AssertionSummary summary;

	public AssertStatement(final IDescription desc) {
		super(desc);
		value = getFacet(IKeyword.VALUE);
		warn = getFacet("warning");
		summary = new AssertionSummary(this);
	}

	@Override
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		summary.reset();
		boolean result;
		try {
			result = Cast.asBool(scope, value.value(scope));
		} catch (final GamaRuntimeException e) {
			summary.setError(e.getMessage());
			summary.setState(TestState.ABORTED);
			throw e;
		}
		if (result) {
			summary.setState(TestState.PASSED);
		} else {
			final TestState s = isWarning(scope) ? TestState.WARNING : TestState.FAILED;
			summary.setState(s);
			throw new GamaAssertException(scope, "Assert failed: " + getTitleForSummary(), isWarning(scope));
		}
		return result;
	}

	public boolean isWarning(final IScope scope) {
		return warn != null && Cast.asBool(scope, warn.value(scope));
	}

	@Override
	public AssertionSummary getSummary() {
		return summary;
	}

	@Override
	public String getTitleForSummary() {
		return value.serialize(true);
	}

	@Override
	public Collection<? extends WithTestSummary<?>> getSubElements() {
		return null;
	}

}
