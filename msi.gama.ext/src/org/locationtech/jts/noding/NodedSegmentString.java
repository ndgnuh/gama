
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
package org.locationtech.jts.noding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.io.WKTWriter;

/**
 * Represents a list of contiguous line segments, and supports noding the segments. The line segments are represented by
 * an array of {@link Coordinate}s. Intended to optimize the noding of contiguous segments by reducing the number of
 * allocated objects. SegmentStrings can carry a context object, which is useful for preserving topological or parentage
 * information. All noded substrings are initialized with the same context object.
 *
 * @version 1.7
 */
public class NodedSegmentString implements NodableSegmentString {
	/**
	 * Gets the {@link SegmentString}s which result from splitting this string at node points.
	 *
	 * @param segStrings
	 *            a Collection of NodedSegmentStrings
	 * @return a Collection of NodedSegmentStrings representing the substrings
	 */
	public static List getNodedSubstrings(final Collection<NodedSegmentString> segStrings) {
		final List<SegmentString> resultEdgelist = new ArrayList<>();
		getNodedSubstrings(segStrings, resultEdgelist);
		return resultEdgelist;
	}

	/**
	 * Adds the noded {@link SegmentString}s which result from splitting this string at node points.
	 *
	 * @param segStrings
	 *            a Collection of NodedSegmentStrings
	 * @param resultEdgelist
	 *            a List which will collect the NodedSegmentStrings representing the substrings
	 */
	public static void getNodedSubstrings(final Collection<NodedSegmentString> segStrings,
			final Collection<SegmentString> resultEdgelist) {
		for (final NodedSegmentString ss : segStrings) {
			ss.getNodeList().addSplitEdges(resultEdgelist);
		}
	}

	private final SegmentNodeList nodeList = new SegmentNodeList(this);
	private final Coordinate[] pts;
	private Object data;

	/**
	 * Creates a new segment string from a list of vertices.
	 *
	 * @param pts
	 *            the vertices of the segment string
	 * @param data
	 *            the user-defined data of this segment string (may be null)
	 */
	public NodedSegmentString(final Coordinate[] pts, final Object data) {
		this.pts = pts;
		this.data = data;
	}

	/**
	 * Gets the user-defined data for this segment string.
	 *
	 * @return the user-defined data
	 */
	@Override
	public Object getData() {
		return data;
	}

	/**
	 * Sets the user-defined data for this segment string.
	 *
	 * @param data
	 *            an Object containing user-defined data
	 */
	@Override
	public void setData(final Object data) {
		this.data = data;
	}

	public SegmentNodeList getNodeList() {
		return nodeList;
	}

	@Override
	public int size() {
		return pts.length;
	}

	@Override
	public Coordinate getCoordinate(final int i) {
		return pts[i];
	}

	@Override
	public Coordinate[] getCoordinates() {
		return pts;
	}

	@Override
	public boolean isClosed() {
		return pts[0].equals(pts[pts.length - 1]);
	}

	/**
	 * Gets the octant of the segment starting at vertex <code>index</code>.
	 *
	 * @param index
	 *            the index of the vertex starting the segment. Must not be the last index in the vertex list
	 * @return the octant of the segment at the vertex
	 */
	public int getSegmentOctant(final int index) {
		if (index == pts.length - 1) { return -1; }
		return safeOctant(getCoordinate(index), getCoordinate(index + 1));
		// return Octant.octant(getCoordinate(index), getCoordinate(index + 1));
	}

	private int safeOctant(final Coordinate p0, final Coordinate p1) {
		if (p0.equals2D(p1)) { return 0; }
		return Octant.octant(p0, p1);
	}

	/**
	 * Adds EdgeIntersections for one or both intersections found for a segment of an edge to the edge intersection
	 * list.
	 */
	public void addIntersections(final LineIntersector li, final int segmentIndex, final int geomIndex) {
		for (int i = 0; i < li.getIntersectionNum(); i++) {
			addIntersection(li, segmentIndex, geomIndex, i);
		}
	}

	/**
	 * Add an SegmentNode for intersection intIndex. An intersection that falls exactly on a vertex of the SegmentString
	 * is normalized to use the higher of the two possible segmentIndexes
	 */
	public void addIntersection(final LineIntersector li, final int segmentIndex, final int geomIndex,
			final int intIndex) {
		final Coordinate intPt = new Coordinate(li.getIntersection(intIndex));
		addIntersection(intPt, segmentIndex);
	}

	/**
	 * Adds an intersection node for a given point and segment to this segment string.
	 *
	 * @param intPt
	 *            the location of the intersection
	 * @param segmentIndex
	 *            the index of the segment containing the intersection
	 */
	@Override
	public void addIntersection(final Coordinate intPt, final int segmentIndex) {
		addIntersectionNode(intPt, segmentIndex);
	}

	/**
	 * Adds an intersection node for a given point and segment to this segment string. If an intersection already exists
	 * for this exact location, the existing node will be returned.
	 *
	 * @param intPt
	 *            the location of the intersection
	 * @param segmentIndex
	 *            the index of the segment containing the intersection
	 * @return the intersection node for the point
	 */
	public SegmentNode addIntersectionNode(final Coordinate intPt, final int segmentIndex) {
		int normalizedSegmentIndex = segmentIndex;
		// Debug.println("edge intpt: " + intPt + " dist: " + dist);
		// normalize the intersection point location
		final int nextSegIndex = normalizedSegmentIndex + 1;
		if (nextSegIndex < pts.length) {
			final Coordinate nextPt = pts[nextSegIndex];
			// Debug.println("next pt: " + nextPt);

			// Normalize segment index if intPt falls on vertex
			// The check for point equality is 2D only - Z values are ignored
			if (intPt.equals2D(nextPt)) {
				// Debug.println("normalized distance");
				normalizedSegmentIndex = nextSegIndex;
			}
		}
		/**
		 * Add the intersection point to edge intersection list.
		 */
		final SegmentNode ei = nodeList.add(intPt, normalizedSegmentIndex);
		return ei;
	}

	@Override
	public String toString() {
		return WKTWriter.toLineString(new CoordinateArraySequence(pts));
	}
}
