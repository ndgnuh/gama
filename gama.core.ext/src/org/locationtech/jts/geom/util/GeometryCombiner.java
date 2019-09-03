/*
 * Copyright (c) 2016 Vivid Solutions.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 and Eclipse Distribution License v. 1.0 which accompanies this distribution. The Eclipse Public
 * License is available at http://www.eclipse.org/legal/epl-v10.html and the Eclipse Distribution License is available
 * at
 *
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.locationtech.jts.geom.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygonal;

/**
 * Combines {@link Geometry}s to produce a {@link GeometryCollection} of the most appropriate type. Input geometries
 * which are already collections will have their elements extracted first. No validation of the result geometry is
 * performed. (The only case where invalidity is possible is where {@link Polygonal} geometries are combined and result
 * in a self-intersection).
 *
 * @author mbdavis
 * @see GeometryFactory#buildGeometry
 */
public class GeometryCombiner {
	/**
	 * Combines a collection of geometries.
	 *
	 * @param geoms
	 *            the geometries to combine
	 * @return the combined geometry
	 */
	public static Geometry combine(final Collection<Object> geoms) {
		final GeometryCombiner combiner = new GeometryCombiner(geoms);
		return combiner.combine();
	}

	/**
	 * Combines two geometries.
	 *
	 * @param g0
	 *            a geometry to combine
	 * @param g1
	 *            a geometry to combine
	 * @return the combined geometry
	 */
	public static Geometry combine(final Geometry g0, final Geometry g1) {
		final GeometryCombiner combiner = new GeometryCombiner(createList(g0, g1));
		return combiner.combine();
	}

	/**
	 * Combines three geometries.
	 *
	 * @param g0
	 *            a geometry to combine
	 * @param g1
	 *            a geometry to combine
	 * @param g2
	 *            a geometry to combine
	 * @return the combined geometry
	 */
	public static Geometry combine(final Geometry g0, final Geometry g1, final Geometry g2) {
		final GeometryCombiner combiner = new GeometryCombiner(createList(g0, g1, g2));
		return combiner.combine();
	}

	/**
	 * Creates a list from two items
	 *
	 * @param obj0
	 * @param obj1
	 * @return a List containing the two items
	 */
	private static List<Object> createList(final Object obj0, final Object obj1) {
		final List<Object> list = new ArrayList<>();
		list.add(obj0);
		list.add(obj1);
		return list;
	}

	/**
	 * Creates a list from two items
	 *
	 * @param obj0
	 * @param obj1
	 * @return a List containing the two items
	 */
	private static List<Object> createList(final Object obj0, final Object obj1, final Object obj2) {
		final List<Object> list = new ArrayList<>();
		list.add(obj0);
		list.add(obj1);
		list.add(obj2);
		return list;
	}

	private final GeometryFactory geomFactory;
	private final boolean skipEmpty = false;
	private final Collection<Object> inputGeoms;

	/**
	 * Creates a new combiner for a collection of geometries
	 *
	 * @param geoms
	 *            the geometries to combine
	 */
	public GeometryCombiner(final Collection<Object> geoms) {
		geomFactory = extractFactory(geoms);
		this.inputGeoms = geoms;
	}

	/**
	 * Extracts the GeometryFactory used by the geometries in a collection
	 *
	 * @param geoms
	 * @return a GeometryFactory
	 */
	public static GeometryFactory extractFactory(final Collection<Object> geoms) {
		if (geoms.isEmpty()) { return null; }
		return ((Geometry) geoms.iterator().next()).getFactory();
	}

	/**
	 * Computes the combination of the input geometries to produce the most appropriate {@link Geometry} or
	 * {@link GeometryCollection}
	 *
	 * @return a Geometry which is the combination of the inputs
	 */
	public Geometry combine() {
		final List<Geometry> elems = new ArrayList<>();
		for (final Object object : inputGeoms) {
			final Geometry g = (Geometry) object;
			extractElements(g, elems);
		}

		if (elems.size() == 0) {
			if (geomFactory != null) {
				// return an empty GC
				return geomFactory.createGeometryCollection();
			}
			return null;
		}
		// return the "simplest possible" geometry
		return geomFactory.buildGeometry(elems);
	}

	private void extractElements(final Geometry geom, final List<Geometry> elems) {
		if (geom == null) { return; }

		for (int i = 0; i < geom.getNumGeometries(); i++) {
			final Geometry elemGeom = geom.getGeometryN(i);
			if (skipEmpty && elemGeom.isEmpty()) {
				continue;
			}
			elems.add(elemGeom);
		}
	}

}
