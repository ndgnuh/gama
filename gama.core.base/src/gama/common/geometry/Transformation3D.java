/*******************************************************************************************************
 *
 * gama.common.geometry.Transformation3D.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.common.geometry;

import org.locationtech.jts.geom.CoordinateFilter;

import gama.metamodel.shape.GamaPoint;

public interface Transformation3D extends CoordinateFilter {

	default void applyTo(final GamaPoint vertex) {
		filter(vertex);
	}
}
