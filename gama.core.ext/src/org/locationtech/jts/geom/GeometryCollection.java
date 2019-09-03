
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

import java.util.Arrays;
import java.util.TreeSet;

import org.locationtech.jts.util.Assert;

/**
 * Models a collection of {@link Geometry}s of arbitrary type and dimension.
 *
 *
 * @version 1.7
 */
public class GeometryCollection extends Geometry {
	// With contributions from Markus Schaber [schabios@logi-track.com] 2004-03-26
	private static final long serialVersionUID = -5694727726395021467L;
	/**
	 * Internal representation of this <code>GeometryCollection</code>.
	 */
	protected Geometry[] geometries;

	/** @deprecated Use GeometryFactory instead */
	@Deprecated
	public GeometryCollection(final Geometry[] geometries, final PrecisionModel precisionModel, final int SRID) {
		this(geometries, new GeometryFactory(precisionModel, SRID));
	}

	/**
	 * @param geometries
	 *            the <code>Geometry</code>s for this <code>GeometryCollection</code>, or <code>null</code> or an empty
	 *            array to create the empty geometry. Elements may be empty <code>Geometry</code>s, but not
	 *            <code>null</code>s.
	 */
	public GeometryCollection(Geometry[] geometries, final GeometryFactory factory) {
		super(factory);
		if (geometries == null) {
			geometries = new Geometry[] {};
		}
		if (hasNullElements(geometries)) {
			throw new IllegalArgumentException("geometries must not contain null elements");
		}
		this.geometries = geometries;
	}

	@Override
	public Coordinate getCoordinate() {
		if (isEmpty()) { return null; }
		return geometries[0].getCoordinate();
	}

	/**
	 * Collects all coordinates of all subgeometries into an Array.
	 *
	 * Note that while changes to the coordinate objects themselves may modify the Geometries in place, the returned
	 * Array as such is only a temporary container which is not synchronized back.
	 *
	 * @return the collected coordinates
	 */
	@Override
	public Coordinate[] getCoordinates() {
		final Coordinate[] coordinates = new Coordinate[getNumPoints()];
		int k = -1;
		for (final Geometry geometrie : geometries) {
			final Coordinate[] childCoordinates = geometrie.getCoordinates();
			for (final Coordinate childCoordinate : childCoordinates) {
				k++;
				coordinates[k] = childCoordinate;
			}
		}
		return coordinates;
	}

	@Override
	public boolean isEmpty() {
		for (final Geometry geometrie : geometries) {
			if (!geometrie.isEmpty()) { return false; }
		}
		return true;
	}

	@Override
	public int getDimension() {
		int dimension = Dimension.FALSE;
		for (final Geometry geometrie : geometries) {
			dimension = Math.max(dimension, geometrie.getDimension());
		}
		return dimension;
	}

	@Override
	public int getBoundaryDimension() {
		int dimension = Dimension.FALSE;
		for (final Geometry geometrie : geometries) {
			dimension = Math.max(dimension, geometrie.getBoundaryDimension());
		}
		return dimension;
	}

	@Override
	public int getNumGeometries() {
		return geometries.length;
	}

	@Override
	public Geometry getGeometryN(final int n) {
		return geometries[n];
	}

	@Override
	public int getNumPoints() {
		int numPoints = 0;
		for (final Geometry geometrie : geometries) {
			numPoints += geometrie.getNumPoints();
		}
		return numPoints;
	}

	@Override
	public String getGeometryType() {
		return "GeometryCollection";
	}

	@Override
	public Geometry getBoundary() {
		checkNotGeometryCollection(this);
		Assert.shouldNeverReachHere();
		return null;
	}

	/**
	 * Returns the area of this <code>GeometryCollection</code>
	 *
	 * @return the area of the polygon
	 */
	@Override
	public double getArea() {
		double area = 0.0;
		for (final Geometry geometrie : geometries) {
			area += geometrie.getArea();
		}
		return area;
	}

	@Override
	public double getLength() {
		double sum = 0.0;
		for (final Geometry geometrie : geometries) {
			sum += geometrie.getLength();
		}
		return sum;
	}

	@Override
	public boolean equalsExact(final Geometry other, final double tolerance) {
		if (!isEquivalentClass(other)) { return false; }
		final GeometryCollection otherCollection = (GeometryCollection) other;
		if (geometries.length != otherCollection.geometries.length) { return false; }
		for (int i = 0; i < geometries.length; i++) {
			if (!geometries[i].equalsExact(otherCollection.geometries[i], tolerance)) { return false; }
		}
		return true;
	}

	@Override
	public void apply(final CoordinateFilter filter) {
		for (final Geometry geometrie : geometries) {
			geometrie.apply(filter);
		}
	}

	@Override
	public void apply(final CoordinateSequenceFilter filter) {
		if (geometries.length == 0) { return; }
		for (final Geometry geometrie : geometries) {
			geometrie.apply(filter);
			if (filter.isDone()) {
				break;
			}
		}
		if (filter.isGeometryChanged()) {
			geometryChanged();
		}
	}

	@Override
	public void apply(final GeometryFilter filter) {
		filter.filter(this);
		for (final Geometry geometrie : geometries) {
			geometrie.apply(filter);
		}
	}

	@Override
	public void apply(final GeometryComponentFilter filter) {
		filter.filter(this);
		for (final Geometry geometrie : geometries) {
			geometrie.apply(filter);
		}
	}

	/**
	 * Creates and returns a full copy of this {@link GeometryCollection} object. (including all coordinates contained
	 * by it).
	 *
	 * @return a clone of this instance
	 * @deprecated
	 */
	@Deprecated
	@Override
	public Object clone() {
		return copy();
	}

	@Override
	protected GeometryCollection copyInternal() {
		final Geometry[] geometries = new Geometry[this.geometries.length];
		for (int i = 0; i < geometries.length; i++) {
			geometries[i] = this.geometries[i].copy();
		}
		return new GeometryCollection(geometries, factory);
	}

	@Override
	public void normalize() {
		for (final Geometry geometrie : geometries) {
			geometrie.normalize();
		}
		Arrays.sort(geometries);
	}

	@Override
	protected Envelope computeEnvelopeInternal() {
		final Envelope envelope = new Envelope();
		for (final Geometry geometrie : geometries) {
			envelope.expandToInclude(geometrie.getEnvelopeInternal());
		}
		return envelope;
	}

	@Override
	protected int compareToSameClass(final Object o) {
		final TreeSet<?> theseElements = new TreeSet<>(Arrays.asList(geometries));
		final TreeSet<?> otherElements = new TreeSet<>(Arrays.asList(((GeometryCollection) o).geometries));
		return compare(theseElements, otherElements);
	}

	@Override
	protected int compareToSameClass(final Object o, final CoordinateSequenceComparator comp) {
		final GeometryCollection gc = (GeometryCollection) o;

		final int n1 = getNumGeometries();
		final int n2 = gc.getNumGeometries();
		int i = 0;
		while (i < n1 && i < n2) {
			final Geometry thisGeom = getGeometryN(i);
			final Geometry otherGeom = gc.getGeometryN(i);
			final int holeComp = thisGeom.compareToSameClass(otherGeom, comp);
			if (holeComp != 0) { return holeComp; }
			i++;
		}
		if (i < n1) { return 1; }
		if (i < n2) { return -1; }
		return 0;

	}

	@Override
	protected int getSortIndex() {
		return Geometry.SORTINDEX_GEOMETRYCOLLECTION;
	}

	/**
	 * Creates a {@link GeometryCollection} with every component reversed. The order of the components in the collection
	 * are not reversed.
	 *
	 * @return a {@link GeometryCollection} in the reverse order
	 */
	@Override
	public Geometry reverse() {
		final int n = geometries.length;
		final Geometry[] revGeoms = new Geometry[n];
		for (int i = 0; i < geometries.length; i++) {
			revGeoms[i] = geometries[i].reverse();
		}
		return getFactory().createGeometryCollection(revGeoms);
	}
}
