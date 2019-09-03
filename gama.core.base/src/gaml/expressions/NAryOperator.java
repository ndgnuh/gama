/*******************************************************************************************************
 *
 * gaml.expressions.NAryOperator.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.expressions;

import gama.common.preferences.GamaPreferences;
import gaml.compilation.GAML;
import gaml.descriptions.OperatorProto;

public class NAryOperator extends AbstractNAryOperator {

	public static IExpression create(final OperatorProto proto, final IExpression... child) {
		final NAryOperator u = new NAryOperator(proto, child);
		if (u.isConst() && GamaPreferences.External.CONSTANT_OPTIMIZATION.getValue()) {
			return GAML.getExpressionFactory().createConst(u.getConstValue(), u.getGamlType(), u.serialize(false));
		}
		return u;
	}

	public NAryOperator(final OperatorProto proto, final IExpression... exprs) {
		super(proto, exprs);
	}

	@Override
	public NAryOperator copy() {
		return new NAryOperator(prototype, exprs);
	}

}
