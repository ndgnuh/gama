
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

import java.io.Serializable;
import java.util.Collection;

import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory;
import org.locationtech.jts.geom.util.GeometryEditor;
import org.locationtech.jts.util.Assert;

/**
 * Supplies a set of utility methods for building Geometry objects from lists of Coordinates.
 * <p>
 * Note that the factory constructor methods do <b>not</b> change the input coordinates in any way. In particular, they
 * are not rounded to the supplied <tt>PrecisionModel</tt>. It is assumed that input Coordinates meet the given
 * precision.
 *
 *
 * @version 1.7
 */
public class GeometryFactory implements Serializable {
	private static final long serialVersionUID = -6820524753094095635L;
	private final PrecisionModel precisionModel;

	private final CoordinateSequenceFactory coordinateSequenceFactory;

	public static Point createPointFromInternalCoord(final Coordinate coord, final Geometry exemplar) {
		exemplar.getPrecisionModel().makePrecise(coord);
		return exemplar.getFactory().createPoint(coord);
	}

	/**
	 * Constructs a GeometryFactory that generates Geometries having the given PrecisionModel, spatial-reference ID, and
	 * CoordinateSequence implementation.
	 */
	public GeometryFactory(final PrecisionModel precisionModel, final int SRID,
			final CoordinateSequenceFactory coordinateSequenceFactory) {
		this.precisionModel = precisionModel;
		this.coordinateSequenceFactory = coordinateSequenceFactory;
		this.SRID = SRID;
	}

	/**
	 * Constructs a GeometryFactory that generates Geometries having the given CoordinateSequence implementation, a
	 * double-precision floating PrecisionModel and a spatial-reference ID of 0.
	 */
	public GeometryFactory(final CoordinateSequenceFactory coordinateSequenceFactory) {
		this(new PrecisionModel(), 0, coordinateSequenceFactory);
	}

	/**
	 * Constructs a GeometryFactory that generates Geometries having the given {@link PrecisionModel} and the default
	 * CoordinateSequence implementation.
	 *
	 * @param precisionModel
	 *            the PrecisionModel to use
	 */
	public GeometryFactory(final PrecisionModel precisionModel) {
		this(precisionModel, 0, getDefaultCoordinateSequenceFactory());
	}

	/**
	 * Constructs a GeometryFactory that generates Geometries having the given {@link PrecisionModel} and
	 * spatial-reference ID, and the default CoordinateSequence implementation.
	 *
	 * @param precisionModel
	 *            the PrecisionModel to use
	 * @param SRID
	 *            the SRID to use
	 */
	public GeometryFactory(final PrecisionModel precisionModel, final int SRID) {
		this(precisionModel, SRID, getDefaultCoordinateSequenceFactory());
	}

	/**
	 * Constructs a GeometryFactory that generates Geometries having a floating PrecisionModel and a spatial-reference
	 * ID of 0.
	 */
	public GeometryFactory() {
		this(new PrecisionModel(), 0);
	}

	private static CoordinateSequenceFactory getDefaultCoordinateSequenceFactory() {
		return CoordinateArraySequenceFactory.instance();
	}

	/**
	 * Converts the <code>List</code> to an array.
	 *
	 * @param points
	 *            the <code>List</code> of Points to convert
	 * @return the <code>List</code> in array format
	 */
	public static Point[] toPointArray(final Collection<?> points) {
		final Point[] pointArray = new Point[points.size()];
		return points.toArray(pointArray);
	}

	/**
	 * Converts the <code>List</code> to an array.
	 *
	 * @param geometries
	 *            the list of <code>Geometry's</code> to convert
	 * @return the <code>List</code> in array format
	 */
	public static Geometry[] toGeometryArray(final Collection<?> geometries) {
		if (geometries == null) { return null; }
		final Geometry[] geometryArray = new Geometry[geometries.size()];
		return geometries.toArray(geometryArray);
	}

	/**
	 * Converts the <code>List</code> to an array.
	 *
	 * @param linearRings
	 *            the <code>List</code> of LinearRings to convert
	 * @return the <code>List</code> in array format
	 */
	public static LinearRing[] toLinearRingArray(final Collection<?> linearRings) {
		final LinearRing[] linearRingArray = new LinearRing[linearRings.size()];
		return linearRings.toArray(linearRingArray);
	}

	/**
	 * Converts the <code>List</code> to an array.
	 *
	 * @param lineStrings
	 *            the <code>List</code> of LineStrings to convert
	 * @return the <code>List</code> in array format
	 */
	public static LineString[] toLineStringArray(final Collection<?> lineStrings) {
		final LineString[] lineStringArray = new LineString[lineStrings.size()];
		return lineStrings.toArray(lineStringArray);
	}

	/**
	 * Converts the <code>List</code> to an array.
	 *
	 * @param polygons
	 *            the <code>List</code> of Polygons to convert
	 * @return the <code>List</code> in array format
	 */
	public static Polygon[] toPolygonArray(final Collection<?> polygons) {
		final Polygon[] polygonArray = new Polygon[polygons.size()];
		return polygons.toArray(polygonArray);
	}

	/**
	 * Converts the <code>List</code> to an array.
	 *
	 * @param multiPolygons
	 *            the <code>List</code> of MultiPolygons to convert
	 * @return the <code>List</code> in array format
	 */
	public static MultiPolygon[] toMultiPolygonArray(final Collection<?> multiPolygons) {
		final MultiPolygon[] multiPolygonArray = new MultiPolygon[multiPolygons.size()];
		return multiPolygons.toArray(multiPolygonArray);
	}

	/**
	 * Converts the <code>List</code> to an array.
	 *
	 * @param multiLineStrings
	 *            the <code>List</code> of MultiLineStrings to convert
	 * @return the <code>List</code> in array format
	 */
	public static MultiLineString[] toMultiLineStringArray(final Collection<?> multiLineStrings) {
		final MultiLineString[] multiLineStringArray = new MultiLineString[multiLineStrings.size()];
		return multiLineStrings.toArray(multiLineStringArray);
	}

	/**
	 * Converts the <code>List</code> to an array.
	 *
	 * @param multiPoints
	 *            the <code>List</code> of MultiPoints to convert
	 * @return the <code>List</code> in array format
	 */
	public static MultiPoint[] toMultiPointArray(final Collection<?> multiPoints) {
		final MultiPoint[] multiPointArray = new MultiPoint[multiPoints.size()];
		return multiPoints.toArray(multiPointArray);
	}

	/**
	 * Creates a {@link Geometry} with the same extent as the given envelope. The Geometry returned is guaranteed to be
	 * valid. To provide this behaviour, the following cases occur:
	 * <p>
	 * If the <code>Envelope</code> is:
	 * <ul>
	 * <li>null : returns an empty {@link Point}
	 * <li>a point : returns a non-empty {@link Point}
	 * <li>a line : returns a two-point {@link LineString}
	 * <li>a rectangle : returns a {@link Polygon} whose points are (minx, miny), (minx, maxy), (maxx, maxy), (maxx,
	 * miny), (minx, miny).
	 * </ul>
	 *
	 * @param envelope
	 *            the <code>Envelope</code> to convert
	 * @return an empty <code>Point</code> (for null <code>Envelope</code>s), a <code>Point</code> (when min x = max x
	 *         and min y = max y) or a <code>Polygon</code> (in all other cases)
	 */
	public Geometry toGeometry(final Envelope envelope) {
		// null envelope - return empty point geometry
		if (envelope.isNull()) { return createPoint(); }

		// point?
		if (envelope.getMinX() == envelope.getMaxX() && envelope.getMinY() == envelope.getMaxY()) {
			return createPoint(new Coordinate(envelope.getMinX(), envelope.getMinY()));
		}

		// vertical or horizontal line?
		if (envelope.getMinX() == envelope.getMaxX() || envelope.getMinY() == envelope.getMaxY()) {
			return createLineString(new Coordinate[] { new Coordinate(envelope.getMinX(), envelope.getMinY()),
					new Coordinate(envelope.getMaxX(), envelope.getMaxY()) });
		}

		// create a CW ring for the polygon
		return createPolygon(createLinearRing(new Coordinate[] { new Coordinate(envelope.getMinX(), envelope.getMinY()),
				new Coordinate(envelope.getMinX(), envelope.getMaxY()),
				new Coordinate(envelope.getMaxX(), envelope.getMaxY()),
				new Coordinate(envelope.getMaxX(), envelope.getMinY()),
				new Coordinate(envelope.getMinX(), envelope.getMinY()) }), null);
	}

	/**
	 * Returns the PrecisionModel that Geometries created by this factory will be associated with.
	 *
	 * @return the PrecisionModel for this factory
	 */
	public PrecisionModel getPrecisionModel() {
		return precisionModel;
	}

	public Point createPoint() {
		return createPoint(getCoordinateSequenceFactory().create(new Coordinate[] {}));
	}

	/**
	 * Creates a Point using the given Coordinate. A null Coordinate creates an empty Geometry.
	 *
	 * @param coordinate
	 *            a Coordinate, or null
	 * @return the created Point
	 */
	public Point createPoint(final Coordinate coordinate) {
		return createPoint(
				coordinate != null ? getCoordinateSequenceFactory().create(new Coordinate[] { coordinate }) : null);
	}

	/**
	 * Creates a Point using the given CoordinateSequence; a null or empty CoordinateSequence will create an empty
	 * Point.
	 *
	 * @param coordinates
	 *            a CoordinateSequence (possibly empty), or null
	 * @return the created Point
	 */
	public Point createPoint(final CoordinateSequence coordinates) {
		return new Point(coordinates, this);
	}

	public MultiLineString createMultiLineString() {
		return new MultiLineString(null, this);
	}

	/**
	 * Creates a MultiLineString using the given LineStrings; a null or empty array will create an empty
	 * MultiLineString.
	 *
	 * @param lineStrings
	 *            LineStrings, each of which may be empty but not null
	 * @return the created MultiLineString
	 */
	public MultiLineString createMultiLineString(final LineString[] lineStrings) {
		return new MultiLineString(lineStrings, this);
	}

	public GeometryCollection createGeometryCollection() {
		return new GeometryCollection(null, this);
	}

	/**
	 * Creates a GeometryCollection using the given Geometries; a null or empty array will create an empty
	 * GeometryCollection.
	 *
	 * @param geometries
	 *            an array of Geometries, each of which may be empty but not null, or null
	 * @return the created GeometryCollection
	 */
	public GeometryCollection createGeometryCollection(final Geometry[] geometries) {
		return new GeometryCollection(geometries, this);
	}

	public MultiPolygon createMultiPolygon() {
		return new MultiPolygon(null, this);
	}

	/**
	 * Creates a MultiPolygon using the given Polygons; a null or empty array will create an empty Polygon. The polygons
	 * must conform to the assertions specified in the <A HREF="http://www.opengis.org/techno/specs.htm">OpenGIS Simple
	 * Features Specification for SQL</A>.
	 *
	 * @param polygons
	 *            Polygons, each of which may be empty but not null
	 * @return the created MultiPolygon
	 */
	public MultiPolygon createMultiPolygon(final Polygon[] polygons) {
		return new MultiPolygon(polygons, this);
	}

	public LinearRing createLinearRing() {
		return createLinearRing(getCoordinateSequenceFactory().create(new Coordinate[] {}));
	}

	/**
	 * Creates a {@link LinearRing} using the given {@link Coordinate}s. A null or empty array creates an empty
	 * LinearRing. The points must form a closed and simple linestring.
	 *
	 * @param coordinates
	 *            an array without null elements, or an empty array, or null
	 * @return the created LinearRing
	 * @throws IllegalArgumentException
	 *             if the ring is not closed, or has too few points
	 */
	public LinearRing createLinearRing(final Coordinate[] coordinates) {
		return createLinearRing(coordinates != null ? getCoordinateSequenceFactory().create(coordinates) : null);
	}

	/**
	 * Creates a {@link LinearRing} using the given {@link CoordinateSequence}. A null or empty array creates an empty
	 * LinearRing. The points must form a closed and simple linestring.
	 *
	 * @param coordinates
	 *            a CoordinateSequence (possibly empty), or null
	 * @return the created LinearRing
	 * @throws IllegalArgumentException
	 *             if the ring is not closed, or has too few points
	 */
	public LinearRing createLinearRing(final CoordinateSequence coordinates) {
		return new LinearRing(coordinates, this);
	}

	public MultiPoint createMultiPoint() {
		return new MultiPoint(null, this);
	}

	/**
	 * Creates a {@link MultiPoint} using the given {@link Point}s. A null or empty array will create an empty
	 * MultiPoint.
	 *
	 * @param point
	 *            an array of Points (without null elements), or an empty array, or <code>null</code>
	 * @return a MultiPoint object
	 */
	public MultiPoint createMultiPoint(final Point[] point) {
		return new MultiPoint(point, this);
	}

	/**
	 * Creates a {@link MultiPoint} using the given {@link Coordinate}s. A null or empty array will create an empty
	 * MultiPoint.
	 *
	 * @param coordinates
	 *            an array (without null elements), or an empty array, or <code>null</code>
	 * @return a MultiPoint object
	 * @deprecated Use {@link GeometryFactory#createMultiPointFromCoords} instead
	 */
	@Deprecated
	public MultiPoint createMultiPoint(final Coordinate[] coordinates) {
		return createMultiPoint(coordinates != null ? getCoordinateSequenceFactory().create(coordinates) : null);
	}

	/**
	 * Creates a {@link MultiPoint} using the given {@link Coordinate}s. A null or empty array will create an empty
	 * MultiPoint.
	 *
	 * @param coordinates
	 *            an array (without null elements), or an empty array, or <code>null</code>
	 * @return a MultiPoint object
	 */
	public MultiPoint createMultiPointFromCoords(final Coordinate[] coordinates) {
		return createMultiPoint(coordinates != null ? getCoordinateSequenceFactory().create(coordinates) : null);
	}

	/**
	 * Creates a {@link MultiPoint} using the points in the given {@link CoordinateSequence}. A <code>null</code> or
	 * empty CoordinateSequence creates an empty MultiPoint.
	 *
	 * @param coordinates
	 *            a CoordinateSequence (possibly empty), or <code>null</code>
	 * @return a MultiPoint geometry
	 */
	public MultiPoint createMultiPoint(final CoordinateSequence coordinates) {
		if (coordinates == null) { return createMultiPoint(new Point[0]); }
		final Point[] points = new Point[coordinates.size()];
		for (int i = 0; i < coordinates.size(); i++) {
			final CoordinateSequence ptSeq =
					getCoordinateSequenceFactory().create(1, coordinates.getDimension(), coordinates.getMeasures());
			CoordinateSequences.copy(coordinates, i, ptSeq, 0, 1);
			points[i] = createPoint(ptSeq);
		}
		return createMultiPoint(points);
	}

	/**
	 * Constructs a <code>Polygon</code> with the given exterior boundary and interior boundaries.
	 *
	 * @param shell
	 *            the outer boundary of the new <code>Polygon</code>, or <code>null</code> or an empty
	 *            <code>LinearRing</code> if the empty geometry is to be created.
	 * @param holes
	 *            the inner boundaries of the new <code>Polygon</code>, or <code>null</code> or empty
	 *            <code>LinearRing</code> s if the empty geometry is to be created.
	 * @throws IllegalArgumentException
	 *             if a ring is invalid
	 */
	public Polygon createPolygon(final LinearRing shell, final LinearRing[] holes) {
		return new Polygon(shell, holes, this);
	}

	/**
	 * Constructs a <code>Polygon</code> with the given exterior boundary.
	 *
	 * @param shell
	 *            the outer boundary of the new <code>Polygon</code>, or <code>null</code> or an empty
	 *            <code>LinearRing</code> if the empty geometry is to be created.
	 * @throws IllegalArgumentException
	 *             if the boundary ring is invalid
	 */
	public Polygon createPolygon(final CoordinateSequence shell) {
		return createPolygon(createLinearRing(shell));
	}

	/**
	 * Constructs a <code>Polygon</code> with the given exterior boundary.
	 *
	 * @param shell
	 *            the outer boundary of the new <code>Polygon</code>, or <code>null</code> or an empty
	 *            <code>LinearRing</code> if the empty geometry is to be created.
	 * @throws IllegalArgumentException
	 *             if the boundary ring is invalid
	 */
	public Polygon createPolygon(final Coordinate[] shell) {
		return createPolygon(createLinearRing(shell));
	}

	/**
	 * Constructs a <code>Polygon</code> with the given exterior boundary.
	 *
	 * @param shell
	 *            the outer boundary of the new <code>Polygon</code>, or <code>null</code> or an empty
	 *            <code>LinearRing</code> if the empty geometry is to be created.
	 * @throws IllegalArgumentException
	 *             if the boundary ring is invalid
	 */
	public Polygon createPolygon(final LinearRing shell) {
		return createPolygon(shell, null);
	}

	public Polygon createPolygon() {
		return createPolygon(null, null);
	}

	/**
	 * Build an appropriate <code>Geometry</code>, <code>MultiGeometry</code>, or <code>GeometryCollection</code> to
	 * contain the <code>Geometry</code>s in it. For example:<br>
	 *
	 * <ul>
	 * <li>If <code>geomList</code> contains a single <code>Polygon</code>, the <code>Polygon</code> is returned.
	 * <li>If <code>geomList</code> contains several <code>Polygon</code>s, a <code>MultiPolygon</code> is returned.
	 * <li>If <code>geomList</code> contains some <code>Polygon</code>s and some <code>LineString</code>s, a
	 * <code>GeometryCollection</code> is returned.
	 * <li>If <code>geomList</code> is empty, an empty <code>GeometryCollection</code> is returned
	 * </ul>
	 *
	 * Note that this method does not "flatten" Geometries in the input, and hence if any MultiGeometries are contained
	 * in the input a GeometryCollection containing them will be returned.
	 *
	 * @param geomList
	 *            the <code>Geometry</code>s to combine
	 * @return a <code>Geometry</code> of the "smallest", "most type-specific" class that can contain the elements of
	 *         <code>geomList</code> .
	 */
	public Geometry buildGeometry(final Collection<?> geomList) {

		/**
		 * Determine some facts about the geometries in the list
		 */
		Class<? extends Geometry> geomClass = null;
		boolean isHeterogeneous = false;
		boolean hasGeometryCollection = false;
		for (final Object name : geomList) {
			final Geometry geom = (Geometry) name;
			final Class<? extends Geometry> partClass = geom.getClass();
			if (geomClass == null) {
				geomClass = partClass;
			}
			if (partClass != geomClass) {
				isHeterogeneous = true;
			}
			if (geom instanceof GeometryCollection) {
				hasGeometryCollection = true;
			}
		}

		/**
		 * Now construct an appropriate geometry to return
		 */
		// for the empty geometry, return an empty GeometryCollection
		if (geomClass == null) { return createGeometryCollection(); }
		if (isHeterogeneous || hasGeometryCollection) { return createGeometryCollection(toGeometryArray(geomList)); }
		// at this point we know the collection is hetereogenous.
		// Determine the type of the result from the first Geometry in the list
		// this should always return a geometry, since otherwise an empty collection would have already been returned
		final Geometry geom0 = (Geometry) geomList.iterator().next();
		final boolean isCollection = geomList.size() > 1;
		if (isCollection) {
			if (geom0 instanceof Polygon) {
				return createMultiPolygon(toPolygonArray(geomList));
			} else if (geom0 instanceof LineString) {
				return createMultiLineString(toLineStringArray(geomList));
			} else if (geom0 instanceof Point) { return createMultiPoint(toPointArray(geomList)); }
			Assert.shouldNeverReachHere("Unhandled class: " + geom0.getClass().getName());
		}
		return geom0;
	}

	public LineString createLineString() {
		return createLineString(getCoordinateSequenceFactory().create(new Coordinate[] {}));
	}

	/**
	 * Creates a LineString using the given Coordinates. A null or empty array creates an empty LineString.
	 *
	 * @param coordinates
	 *            an array without null elements, or an empty array, or null
	 */
	public LineString createLineString(final Coordinate[] coordinates) {
		return createLineString(coordinates != null ? getCoordinateSequenceFactory().create(coordinates) : null);
	}

	/**
	 * Creates a LineString using the given CoordinateSequence. A null or empty CoordinateSequence creates an empty
	 * LineString.
	 *
	 * @param coordinates
	 *            a CoordinateSequence (possibly empty), or null
	 */
	public LineString createLineString(final CoordinateSequence coordinates) {
		return new LineString(coordinates, this);
	}

	/**
	 * Creates a deep copy of the input {@link Geometry}. The {@link CoordinateSequenceFactory} defined for this factory
	 * is used to copy the {@link CoordinateSequence}s of the input geometry.
	 * <p>
	 * This is a convenient way to change the <tt>CoordinateSequence</tt> used to represent a geometry, or to change the
	 * factory used for a geometry.
	 * <p>
	 * {@link Geometry#copy()} can also be used to make a deep copy, but it does not allow changing the
	 * CoordinateSequence type.
	 *
	 * @return a deep copy of the input geometry, using the CoordinateSequence type of this factory
	 *
	 * @see Geometry#copy()
	 */
	public Geometry createGeometry(final Geometry g) {
		final GeometryEditor editor = new GeometryEditor(this);
		return editor.edit(g, new GeometryEditor.CoordinateSequenceOperation() {
			@Override
			public CoordinateSequence edit(final CoordinateSequence coordSeq, final Geometry geometry) {
				return coordinateSequenceFactory.create(coordSeq);
			}
		});
	}

	/**
	 * Gets the SRID value defined for this factory.
	 *
	 * @return the factory SRID value
	 */
	public int getSRID() {
		return SRID;
	}

	private final int SRID;

	public CoordinateSequenceFactory getCoordinateSequenceFactory() {
		return coordinateSequenceFactory;
	}

}
