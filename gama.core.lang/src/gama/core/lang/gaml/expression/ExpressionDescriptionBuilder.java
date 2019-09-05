/*********************************************************************************************
 *
 * 'ExpressionDescriptionBuilder.java, in plugin gama.core.lang, is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package gama.core.lang.gaml.expression;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.diagnostics.Diagnostic;

import gama.core.lang.gaml.EGaml;
import gama.core.lang.gaml.resource.GamlResourceServices;
import gama.common.interfaces.IKeyword;
import gaml.compilation.interfaces.ISyntacticElement;
import gaml.descriptions.ConstantExpressionDescription;
import gaml.descriptions.IExpressionDescription;
import gaml.operators.IUnits;
import gama.core.lang.gaml.BooleanLiteral;
import gama.core.lang.gaml.DoubleLiteral;
import gama.core.lang.gaml.IntLiteral;
import gama.core.lang.gaml.StringLiteral;
import gama.core.lang.gaml.Unary;
import gama.core.lang.gaml.UnitName;
import gama.core.lang.gaml.util.GamlSwitch;

public class ExpressionDescriptionBuilder extends GamlSwitch<IExpressionDescription> {

	// private Set<Diagnostic> currentErrors;

	void setErrors(final Set<Diagnostic> errors) {
		// currentErrors = errors;
	}

	@Override
	public IExpressionDescription caseIntLiteral(final IntLiteral object) {
		IExpressionDescription ed = null;
		try {
			ed = ConstantExpressionDescription.create(Integer.parseInt(object.getOp()));
		} catch (final NumberFormatException e) {
			// final Diagnostic d = new EObjectDiagnosticImpl(Severity.WARNING,
			// "",
			// "Impossible to parse this int value, automatically set to 0",
			// object, null, 0, null);
			// if (currentErrors != null)
			// currentErrors.add(d);
			ed = ConstantExpressionDescription.create(0);
		}
		GamlResourceServices.getResourceDocumenter().setGamlDocumentation(object, ed.getExpression(), true);
		return ed;
	}

	@Override
	public IExpressionDescription caseDoubleLiteral(final DoubleLiteral object) {
		IExpressionDescription ed = null;
		try {
			ed = ConstantExpressionDescription.create(Double.parseDouble(object.getOp()));
		} catch (final NumberFormatException e) {
			// final Diagnostic d = new EObjectDiagnosticImpl(Severity.WARNING,
			// "",
			// "Impossible to parse this float value, automatically set to 0.0",
			// object, null, 0, null);
			// if (currentErrors != null)
			// currentErrors.add(d);
			ed = ConstantExpressionDescription.create(0d);
		}
		GamlResourceServices.getResourceDocumenter().setGamlDocumentation(object, ed.getExpression(), true);
		return ed;
	}

	@Override
	public IExpressionDescription caseStringLiteral(final StringLiteral object) {
		final IExpressionDescription ed = ConstantExpressionDescription.create(object.getOp());

		// AD: Change 14/11/14
		// IExpressionDescription ed =
		// LabelExpressionDescription.create(object.getOp());
		GamlResourceServices.getResourceDocumenter().setGamlDocumentation(object, ed.getExpression(), true);
		return ed;
	}

	@Override
	public IExpressionDescription caseBooleanLiteral(final BooleanLiteral object) {
		final IExpressionDescription ed = ConstantExpressionDescription.create(object.getOp().equals(IKeyword.TRUE));
		GamlResourceServices.getResourceDocumenter().setGamlDocumentation(object, ed.getExpression(), true);
		return ed;
	}

	static int count;

	@Override
	public IExpressionDescription caseUnitName(final UnitName object) {
		final String s = EGaml.getInstance().getKeyOf(object);
		if (IUnits.UNITS_EXPR.containsKey(s)) {
			return IUnits.UNITS_EXPR.get(s);
		}
		return null;
	}

	@Override
	public IExpressionDescription caseUnary(final Unary object) {
		final String op = EGaml.getInstance().getKeyOf(object);
		if (op.equals("°") || op.equals("#")) {
			return doSwitch(object.getRight());
		}
		return null;
	}

	@Override
	public IExpressionDescription defaultCase(final EObject object) {
		return new EcoreBasedExpressionDescription(object);
	}

	public static IExpressionDescription create(final ISyntacticElement e, final Set<Diagnostic> errors) {
		final IExpressionDescription ed = new BlockExpressionDescription(e);
		return ed;
	}

	public IExpressionDescription create(
			final EObject expr/* , final Set<Diagnostic> errors */) {
		try {
			// setErrors(errors);
			final IExpressionDescription result = doSwitch(expr);
			result.setTarget(expr);
			return result;
		} finally {
			// setErrors(null);
		}
	}

}