/*******************************************************************************************************
 *
 * gaml.expressions.CameraOrientationUnitExpression.java, in plugin gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.constants;

import gama.common.interfaces.outputs.IGraphics;
import gama.metamodel.shape.GamaPoint;
import gama.runtime.scope.IScope;
import gaml.types.Types;

public class CameraOrientationUnitExpression extends UnitConstantExpression<GamaPoint> {

	public CameraOrientationUnitExpression(final String doc) {
		super(new GamaPoint(), Types.POINT, "camera_orientation", doc, null);
	}

	@Override
	public GamaPoint _value(final IScope scope) {
		final IGraphics g = scope.getGraphics();
		if (g == null || g.is2D())
			return (GamaPoint) getConstValue();
		return ((IGraphics.ThreeD) g).getCameraOrientation();
	}

	@Override
	public boolean isConst() {
		return false;
	}

}
