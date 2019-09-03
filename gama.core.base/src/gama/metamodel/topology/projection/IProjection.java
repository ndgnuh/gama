/*******************************************************************************************************
 *
 * gama.metamodel.topology.projection.IProjection.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.metamodel.topology.projection;

import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import gama.common.geometry.Envelope3D;
import gama.runtime.scope.IScope;

import org.locationtech.jts.geom.Geometry;

/**
 * Class IProjection.
 * 
 * @author drogoul
 * @since 17 déc. 2013
 * 
 */
public interface IProjection {

	public abstract void createTransformation(final MathTransform t);

	public abstract Geometry transform(final Geometry g);

	public abstract Geometry inverseTransform(final Geometry g);

	public abstract CoordinateReferenceSystem getInitialCRS(IScope scope);

	public abstract CoordinateReferenceSystem getTargetCRS(IScope scope);

	public abstract Envelope3D getProjectedEnvelope();

	/**
	 * @param geom
	 */
	public abstract void translate(Geometry geom);

	public abstract void inverseTranslate(Geometry geom);
	

	public abstract void convertUnit(Geometry geom);

	public abstract void inverseConvertUnit(Geometry geom);

}