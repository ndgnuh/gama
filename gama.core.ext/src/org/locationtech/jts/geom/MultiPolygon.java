
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
package org.locationtech.jts.geom;

import java.util.ArrayList;

/**
 * Models a collection of {@link Polygon}s.
 * <p>
 * As per the OGC SFS specification, the Polygons in a MultiPolygon may not overlap, and may only touch at single
 * points. This allows the topological point-set semantics to be well-defined.
 *
 *
 * @version 1.7
 */
public class MultiPolygon extends GeometryCollection implements Polygonal {
	private static final long serialVersionUID = -551033529766975875L;

	/**
	 * Constructs a <code>MultiPolygon</code>.
	 *
	 * @param polygons
	 *            the <code>Polygon</code>s for this <code>MultiPolygon</code> , or <code>null</code> or an empty array
	 *            to create the empty geometry. Elements may be empty <code>Polygon</code>s, but not <code>null</code>
	 *            s. The polygons must conform to the assertions specified in the
	 *            <A HREF="http://www.opengis.org/techno/specs.htm">OpenGIS Simple Features Specification for SQL</A> .
	 * @param precisionModel
	 *            the specification of the grid of allowable points for this <code>MultiPolygon</code>
	 * @param SRID
	 *            the ID of the Spatial Reference System used by this <code>MultiPolygon</code>
	 * @deprecated Use GeometryFactory instead
	 */
	@Deprecated
	public MultiPolygon(final Polygon[] polygons, final PrecisionModel precisionModel, final int SRID) {
		this(polygons, new GeometryFactory(precisionModel, SRID));
	}

	/**
	 * @param polygons
	 *            the <code>Polygon</code>s for this <code>MultiPolygon</code>, or <code>null</code> or an empty array
	 *            to create the empty geometry. Elements may be empty <code>Polygon</code>s, but not <code>null</code>s.
	 *            The polygons must conform to the assertions specified in the
	 *            <A HREF="http://www.opengis.org/techno/specs.htm">OpenGIS Simple Features Specification for SQL</A>.
	 */
	public MultiPolygon(final Polygon[] polygons, final GeometryFactory factory) {
		super(polygons, factory);
	}

	@Override
	public int getDimension() {
		return 2;
	}

	@Override
	public int getBoundaryDimension() {
		return 1;
	}

	@Override
	public String getGeometryType() {
		return "MultiPolygon";
	}

	/*
	 * public boolean isSimple() { return true; }
	 */

	/**
	 * Computes the boundary of this geometry
	 *
	 * @return a lineal geometry (which may be empty)
	 * @see Geometry#getBoundary
	 */
	@Override
	public Geometry getBoundary() {
		if (isEmpty()) { return getFactory().createMultiLineString(); }
		final ArrayList<Geometry> allRings = new ArrayList<>();
		for (final Geometry geometrie : geometries) {
			final Polygon polygon = (Polygon) geometrie;
			final Geometry rings = polygon.getBoundary();
			for (int j = 0; j < rings.getNumGeometries(); j++) {
				allRings.add(rings.getGeometryN(j));
			}
		}
		final LineString[] allRingsArray = new LineString[allRings.size()];
		return getFactory().createMultiLineString(allRings.toArray(allRingsArray));
	}

	@Override
	public boolean equalsExact(final Geometry other, final double tolerance) {
		if (!isEquivalentClass(other)) { return false; }
		return super.equalsExact(other, tolerance);
	}

	/**
	 * Creates a {@link MultiPolygon} with every component reversed. The order of the components in the collection are
	 * not reversed.
	 *
	 * @return a MultiPolygon in the reverse order
	 */
	@Override
	public Geometry reverse() {
		final int n = geometries.length;
		final Polygon[] revGeoms = new Polygon[n];
		for (int i = 0; i < geometries.length; i++) {
			revGeoms[i] = (Polygon) geometries[i].reverse();
		}
		return getFactory().createMultiPolygon(revGeoms);
	}

	@Override
	protected MultiPolygon copyInternal() {
		final Polygon[] polygons = new Polygon[this.geometries.length];
		for (int i = 0; i < polygons.length; i++) {
			polygons[i] = (Polygon) this.geometries[i].copy();
		}
		return new MultiPolygon(polygons, factory);
	}

	@Override
	protected int getSortIndex() {
		return Geometry.SORTINDEX_MULTIPOLYGON;
	}
}
