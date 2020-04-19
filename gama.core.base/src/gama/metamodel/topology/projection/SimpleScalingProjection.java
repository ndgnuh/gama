package gama.metamodel.topology.projection;

import org.locationtech.jts.geom.CoordinateFilter;
import org.locationtech.jts.geom.Geometry;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import gama.common.geometry.Envelope3D;
import gama.runtime.scope.IScope;

public class SimpleScalingProjection implements IProjection {

	public CoordinateFilter scaling, inverseScaling;

	@Override
	public void createTransformation(final MathTransform t) {

	}

	public SimpleScalingProjection(final Double scale) {
		if (scale != null) {
			createScalingTransformations(scale);
		}

	}

	@Override
	public Geometry transform(final Geometry geom) {
		if (scaling != null) {
			geom.apply(scaling);
			geom.geometryChanged();
		}
		return geom;
	}

	@Override
	public Geometry inverseTransform(final Geometry geom) {
		if (inverseScaling != null) {
			geom.apply(inverseScaling);
			geom.geometryChanged();
		}
		return geom;
	}

	public void createScalingTransformations(final Double scale) {
		scaling = coord -> {
			coord.x *= scale;
			coord.y *= scale;
			coord.z *= scale;
		};
		inverseScaling = coord -> {
			coord.x /= scale;
			coord.y /= scale;
			coord.z /= scale;
		};
	}

	@Override
	public CoordinateReferenceSystem getInitialCRS(final IScope scope) {
		return null;
	}

	@Override
	public CoordinateReferenceSystem getTargetCRS(final IScope scope) {
		return null;
	}

	@Override
	public Envelope3D getProjectedEnvelope() {
		return null;
	}

	@Override
	public void translate(final Geometry geom) {

	}

	@Override
	public void inverseTranslate(final Geometry geom) {

	}

	@Override
	public void convertUnit(final Geometry geom) {}

	@Override
	public void inverseConvertUnit(final Geometry geom) {

	}

}
