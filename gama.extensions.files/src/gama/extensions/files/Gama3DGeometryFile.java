/*******************************************************************************************************
 *
 * msi.gama.util.file.Gama3DGeometryFile.java, in plugin msi.gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.extensions.files;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Geometry;

import gama.common.geometry.AxisAngle;
import gama.common.geometry.Envelope3D;
import gama.common.geometry.GeometryUtils;
import gama.metamodel.shape.GamaPoint;
import gama.metamodel.shape.GamaShape;
import gama.metamodel.shape.IShape;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.GamaPair;
import gaml.operators.Cast;

public abstract class Gama3DGeometryFile extends GamaGeometryFile {

	protected AxisAngle initRotation;
	protected Envelope3D envelope;

	public Gama3DGeometryFile(final IScope scope, final String pathName) throws GamaRuntimeException {
		super(scope, pathName);
	}

	public Gama3DGeometryFile(final IScope scope, final String pathName, final GamaPair<Double, GamaPoint> initRotation)
			throws GamaRuntimeException {
		super(scope, pathName);
		if (initRotation != null) {
			final Double angle = Cast.asFloat(null, initRotation.key);
			final GamaPoint axis = initRotation.value;
			this.initRotation = new AxisAngle(axis, angle);
		} else {
			this.initRotation = null;
		}
	}

	@Override
	protected IShape buildGeometry(final IScope scope) {
		final List<Geometry> faces = new ArrayList<>();
		for (final IShape shape : getBuffer().iterable(scope)) {
			faces.add(shape.getInnerGeometry());
		}
		return new GamaShape(GeometryUtils.GEOMETRY_FACTORY.buildGeometry(faces));
	}

	@Override
	public AxisAngle getInitRotation() {
		return initRotation;
	}

	public void setInitRotation(final AxisAngle initRotation) {
		this.initRotation = initRotation;
	}

	@Override
	public Envelope3D computeEnvelope(final IScope scope) {
		if (envelope == null) {
			fillBuffer(scope);
			if (initRotation != null && initRotation.angle != 0.0)
				envelope = envelope.rotate(initRotation);
		}
		return envelope;
	}

	@Override
	public boolean is2D() {
		return false;
	}

}