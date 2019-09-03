/*********************************************************************************************
 *
 * 'ClassicalSIEquations.java, in plugin gama.extensions.maths, is part of the source code of the GAMA modeling
 * and simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.extensions.maths.ode.utils.classicalEquations.epidemiology;

import java.util.ArrayList;
import java.util.List;

import gama.extensions.maths.ode.statements.SingleEquationStatement;
import gaml.compilation.GAML;
import gaml.descriptions.IDescription;
import gaml.descriptions.StatementDescription;
import gaml.expressions.IExpression;
import gaml.expressions.ListExpression;

// SI equation is defined by
// diff(S,t) = -beta * S * I / N ;
// diff(I,t) = beta * S * I / N ;
//
// It is called using
// equation eqSI type: SI vars: [S,I,t] params: [N,beta] {}

public class ClassicalSIEquations {
	private final IDescription parentDesc;

	public ClassicalSIEquations(final IDescription p) {
		parentDesc = p;
	}

	public IDescription getDescription() {
		return parentDesc;
	}

	public List<SingleEquationStatement> SI(final IExpression with_vars, final IExpression with_params) {
		if (with_vars == null || with_params == null) { return null; }
		final ArrayList<SingleEquationStatement> cmd = new ArrayList<>();
		final IExpression[] v = ((ListExpression) with_vars).getElements();
		final IExpression[] p = ((ListExpression) with_params).getElements();

		final StatementDescription stm = new StatementDescription("=", getDescription(), false, null, null, null);

		final SingleEquationStatement eq1 = new SingleEquationStatement(stm);
		eq1.setFunction(GAML.getExpressionFactory()
				.createExpr("diff(" + v[0].literalValue() + "," + v[2].literalValue() + ")", getDescription()));
		eq1.setExpression(
				GAML.getExpressionFactory().createExpr("(- " + p[1].literalValue() + " * " + v[0].literalValue() + " * "
						+ v[1].literalValue() + " / " + p[0].literalValue() + ")", getDescription()));
		// eq1.establishVar();
		cmd.add(eq1);

		final SingleEquationStatement eq2 = new SingleEquationStatement(stm);
		eq2.setFunction(GAML.getExpressionFactory()
				.createExpr("diff(" + v[1].literalValue() + "," + v[2].literalValue() + ")", getDescription()));
		eq2.setExpression(
				GAML.getExpressionFactory().createExpr("( " + p[1].literalValue() + " * " + v[0].literalValue() + " * "
						+ v[1].literalValue() + " / " + p[0].literalValue() + ")", getDescription()));
		// eq2.establishVar();
		cmd.add(eq2);

		return cmd;
	}

}
