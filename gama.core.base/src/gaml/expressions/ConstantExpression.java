/*******************************************************************************************************
 *
 * gaml.expressions.ConstantExpression.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.expressions;

import java.util.Collection;

import gama.common.util.StringUtils;
import gama.runtime.scope.IScope;
import gaml.descriptions.SpeciesDescription;
import gaml.descriptions.VariableDescription;
import gaml.types.GamaType;
import gaml.types.IType;

/**
 * ConstantValueExpr.
 *
 * @author drogoul 22 août 07
 */

public class ConstantExpression<T> extends AbstractExpression {

	T value;

	public ConstantExpression(final T val, final IType<T> t, final String name) {
		value = val;
		type = t;
		setName(name);
	}

	public ConstantExpression(final T val, final IType<T> t) {
		this(val, t, val == null ? "nil" : val.toString());
	}

	public ConstantExpression(final T val) {
		this(val, GamaType.of(val));
	}

	@Override
	public T _value(final IScope scope) {
		return value;
	}

	@Override
	public boolean isConst() {
		return true;
	}

	@Override
	public String toString() {
		return value == null ? "nil" : value.toString();
	}

	@Override
	public String serialize(final boolean includingBuiltIn) {
		return StringUtils.toGaml(value, includingBuiltIn);
	}

	/**
	 * @see gaml.expressions.IExpression#getDocumentation()
	 */
	@Override
	public String getDocumentation() {
		return "Literal expression of type " + getGamlType().getTitle();
	}

	@Override
	public String getTitle() {
		return literalValue();
	}

	@Override
	public boolean shouldBeParenthesized() {
		return false;
	}

	@Override
	public void collectUsedVarsOf(final SpeciesDescription species, final Collection<VariableDescription> result) {}

}
