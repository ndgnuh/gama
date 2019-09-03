/*******************************************************************************************************
 *
 * msi.gaml.expressions.ZoomUnitExpression.java, in plugin msi.gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gaml.expressions;

import msi.gama.common.interfaces.outputs.IGraphics;
import msi.gama.runtime.scope.IScope;
import msi.gaml.types.Types;

public class ZoomUnitExpression extends UnitConstantExpression<Double> {

	public ZoomUnitExpression(final String name, final String doc) {
		super(1.0, Types.FLOAT, name, doc, null);
	}

	@Override
	public Double _value(final IScope scope) {
		final IGraphics g = scope.getGraphics();
		if (g == null)
			return 1d;
		return g.getZoomLevel();
	}

	@Override
	public boolean isConst() {
		return false;
	}

	@Override
	public boolean isContextIndependant() {
		return false;
	}

}
