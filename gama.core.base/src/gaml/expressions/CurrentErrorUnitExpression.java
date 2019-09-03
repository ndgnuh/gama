/*******************************************************************************************************
 *
 * gaml.expressions.CurrentErrorUnitExpression.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.expressions;

import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.types.Types;

public class CurrentErrorUnitExpression extends UnitConstantExpression<String> {

	public CurrentErrorUnitExpression(final String doc) {
		super("", Types.STRING, "current_error", doc, null);
	}

	@Override
	public String _value(final IScope scope) {
		final GamaRuntimeException e = scope.getCurrentError();
		if (e == null)
			return "nil";
		return e.getMessage();
	}

	@Override
	public boolean isConst() {
		return false;
	}

}
