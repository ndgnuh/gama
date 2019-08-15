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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.util.Assert;

/**
 * A list of the {@link SegmentNode}s present along a noded {@link SegmentString}.
 *
 * @version 1.7
 */
public class SegmentNodeList {
	private final Map<SegmentNode, SegmentNode> nodeMap = new TreeMap<>();
	private final NodedSegmentString edge; // the parent edge

	public SegmentNodeList(final NodedSegmentString edge) {
		this.edge = edge;
	}

	public NodedSegmentString getEdge() {
		return edge;
	}

	/**
	 * Adds an intersection into the list, if it isn't already there. The input segmentIndex and dist are expected to be
	 * normalized.
	 *
	 * @return the SegmentIntersection found or added
	 */
	public SegmentNode add(final Coordinate intPt, final int segmentIndex) {
		final SegmentNode eiNew = new SegmentNode(edge, intPt, segmentIndex, edge.getSegmentOctant(segmentIndex));
		final SegmentNode ei = nodeMap.get(eiNew);
		if (ei != null) {
			// debugging sanity check
			Assert.isTrue(ei.coord.equals2D(intPt), "Found equal nodes with different coordinates");
			// if (! ei.coord.equals2D(intPt))
			// Debug.println("Found equal nodes with different coordinates");

			return ei;
		}
		// node does not exist, so create it
		nodeMap.put(eiNew, eiNew);
		return eiNew;
	}

	/**
	 * returns an iterator of SegmentNodes
	 */
	public Iterator<SegmentNode> iterator() {
		return nodeMap.values().iterator();
	}

	/**
	 * Adds nodes for the first and last points of the edge
	 */
	private void addEndpoints() {
		final int maxSegIndex = edge.size() - 1;
		add(edge.getCoordinate(0), 0);
		add(edge.getCoordinate(maxSegIndex), maxSegIndex);
	}

	/**
	 * Adds nodes for any collapsed edge pairs. Collapsed edge pairs can be caused by inserted nodes, or they can be
	 * pre-existing in the edge vertex list. In order to provide the correct fully noded semantics, the vertex at the
	 * base of a collapsed pair must also be added as a node.
	 */
	private void addCollapsedNodes() {
		final List<Integer> collapsedVertexIndexes = new ArrayList<>();

		findCollapsesFromInsertedNodes(collapsedVertexIndexes);
		findCollapsesFromExistingVertices(collapsedVertexIndexes);

		// node the collapses
		for (final Integer integer : collapsedVertexIndexes) {
			final int vertexIndex = integer.intValue();
			add(edge.getCoordinate(vertexIndex), vertexIndex);
		}
	}

	/**
	 * Adds nodes for any collapsed edge pairs which are pre-existing in the vertex list.
	 */
	private void findCollapsesFromExistingVertices(final List<Integer> collapsedVertexIndexes) {
		for (int i = 0; i < edge.size() - 2; i++) {
			final Coordinate p0 = edge.getCoordinate(i);
			final Coordinate p1 = edge.getCoordinate(i + 1);
			final Coordinate p2 = edge.getCoordinate(i + 2);
			if (p0.equals2D(p2)) {
				// add base of collapse as node
				collapsedVertexIndexes.add(i + 1);
			}
		}
	}

	/**
	 * Adds nodes for any collapsed edge pairs caused by inserted nodes Collapsed edge pairs occur when the same
	 * coordinate is inserted as a node both before and after an existing edge vertex. To provide the correct fully
	 * noded semantics, the vertex must be added as a node as well.
	 */
	private void findCollapsesFromInsertedNodes(final List<Integer> collapsedVertexIndexes) {
		final int[] collapsedVertexIndex = new int[1];
		final Iterator<SegmentNode> it = iterator();
		// there should always be at least two entries in the list, since the endpoints are nodes
		SegmentNode eiPrev = it.next();
		while (it.hasNext()) {
			final SegmentNode ei = it.next();
			final boolean isCollapsed = findCollapseIndex(eiPrev, ei, collapsedVertexIndex);
			if (isCollapsed) {
				collapsedVertexIndexes.add((collapsedVertexIndex[0]));
			}

			eiPrev = ei;
		}
	}

	private boolean findCollapseIndex(final SegmentNode ei0, final SegmentNode ei1, final int[] collapsedVertexIndex) {
		// only looking for equal nodes
		if (!ei0.coord.equals2D(ei1.coord)) { return false; }

		int numVerticesBetween = ei1.segmentIndex - ei0.segmentIndex;
		if (!ei1.isInterior()) {
			numVerticesBetween--;
		}

		// if there is a single vertex between the two equal nodes, this is a collapse
		if (numVerticesBetween == 1) {
			collapsedVertexIndex[0] = ei0.segmentIndex + 1;
			return true;
		}
		return false;
	}

	/**
	 * Creates new edges for all the edges that the intersections in this list split the parent edge into. Adds the
	 * edges to the provided argument list (this is so a single list can be used to accumulate all split edges for a set
	 * of {@link SegmentString}s).
	 */
	public void addSplitEdges(final Collection<SegmentString> edgeList) {
		// ensure that the list has entries for the first and last point of the edge
		addEndpoints();
		addCollapsedNodes();

		final Iterator<SegmentNode> it = iterator();
		// there should always be at least two entries in the list, since the endpoints are nodes
		SegmentNode eiPrev = it.next();
		while (it.hasNext()) {
			final SegmentNode ei = it.next();
			final SegmentString newEdge = createSplitEdge(eiPrev, ei);
			/*
			 * if (newEdge.size() < 2) throw new RuntimeException("created single point edge: " + newEdge.toString());
			 */
			edgeList.add(newEdge);
			eiPrev = ei;
		}
		// checkSplitEdgesCorrectness(testingSplitEdges);
	}

	/**
	 * Checks the correctness of the set of split edges corresponding to this edge.
	 *
	 * @param splitEdges
	 *            the split edges for this edge (in order)
	 */
	private void checkSplitEdgesCorrectness(final List<?> splitEdges) {
		final Coordinate[] edgePts = edge.getCoordinates();

		// check that first and last points of split edges are same as endpoints of edge
		final SegmentString split0 = (SegmentString) splitEdges.get(0);
		final Coordinate pt0 = split0.getCoordinate(0);
		if (!pt0.equals2D(edgePts[0])) { throw new RuntimeException("bad split edge start point at " + pt0); }

		final SegmentString splitn = (SegmentString) splitEdges.get(splitEdges.size() - 1);
		final Coordinate[] splitnPts = splitn.getCoordinates();
		final Coordinate ptn = splitnPts[splitnPts.length - 1];
		if (!ptn.equals2D(edgePts[edgePts.length - 1])) {
			throw new RuntimeException("bad split edge end point at " + ptn);
		}

	}

	/**
	 * Create a new "split edge" with the section of points between (and including) the two intersections. The label for
	 * the new edge is the same as the label for the parent edge.
	 */
	SegmentString createSplitEdge(final SegmentNode ei0, final SegmentNode ei1) {
		// Debug.println("\ncreateSplitEdge"); Debug.print(ei0); Debug.print(ei1);
		int npts = ei1.segmentIndex - ei0.segmentIndex + 2;

		final Coordinate lastSegStartPt = edge.getCoordinate(ei1.segmentIndex);
		// if the last intersection point is not equal to the its segment start pt,
		// add it to the points list as well.
		// (This check is needed because the distance metric is not totally reliable!)
		// The check for point equality is 2D only - Z values are ignored
		final boolean useIntPt1 = ei1.isInterior() || !ei1.coord.equals2D(lastSegStartPt);
		if (!useIntPt1) {
			npts--;
		}

		final Coordinate[] pts = new Coordinate[npts];
		int ipt = 0;
		pts[ipt++] = new Coordinate(ei0.coord);
		for (int i = ei0.segmentIndex + 1; i <= ei1.segmentIndex; i++) {
			pts[ipt++] = edge.getCoordinate(i);
		}
		if (useIntPt1) {
			pts[ipt] = new Coordinate(ei1.coord);
		}

		return new NodedSegmentString(pts, edge.getData());
	}

	/**
	 * Gets the list of coordinates for the fully noded segment string, including all original segment string vertices
	 * and vertices introduced by nodes in this list. Repeated coordinates are collapsed.
	 *
	 * @return an array of Coordinates
	 *
	 */
	public Coordinate[] getSplitCoordinates() {
		final CoordinateList coordList = new CoordinateList();
		// ensure that the list has entries for the first and last point of the edge
		addEndpoints();

		final Iterator<SegmentNode> it = iterator();
		// there should always be at least two entries in the list, since the endpoints are nodes
		SegmentNode eiPrev = it.next();
		while (it.hasNext()) {
			final SegmentNode ei = it.next();
			addEdgeCoordinates(eiPrev, ei, coordList);
			eiPrev = ei;
		}
		return coordList.toCoordinateArray();
	}

	private void addEdgeCoordinates(final SegmentNode ei0, final SegmentNode ei1, final CoordinateList coordList) {
		int npts = ei1.segmentIndex - ei0.segmentIndex + 2;

		final Coordinate lastSegStartPt = edge.getCoordinate(ei1.segmentIndex);
		// if the last intersection point is not equal to the its segment start pt,
		// add it to the points list as well.
		// (This check is needed because the distance metric is not totally reliable!)
		// The check for point equality is 2D only - Z values are ignored
		final boolean useIntPt1 = ei1.isInterior() || !ei1.coord.equals2D(lastSegStartPt);
		if (!useIntPt1) {
			npts--;
		}

		final int ipt = 0;
		coordList.add(new Coordinate(ei0.coord), false);
		for (int i = ei0.segmentIndex + 1; i <= ei1.segmentIndex; i++) {
			coordList.add(edge.getCoordinate(i));
		}
		if (useIntPt1) {
			coordList.add(new Coordinate(ei1.coord));
		}
	}

	public void print(final PrintStream out) {
		out.println("Intersections:");
		for (final Iterator<SegmentNode> it = iterator(); it.hasNext();) {
			final SegmentNode ei = it.next();
			ei.print(out);
		}
	}
}

// INCOMPLETE!
class NodeVertexIterator implements Iterator<Object> {
	private final SegmentNodeList nodeList;
	private final NodedSegmentString edge;
	private final Iterator<SegmentNode> nodeIt;
	private SegmentNode currNode = null;
	private SegmentNode nextNode = null;
	private int currSegIndex = 0;

	NodeVertexIterator(final SegmentNodeList nodeList) {
		this.nodeList = nodeList;
		edge = nodeList.getEdge();
		nodeIt = nodeList.iterator();
		readNextNode();
	}

	@Override
	public boolean hasNext() {
		if (nextNode == null) { return false; }
		return true;
	}

	@Override
	public Object next() {
		if (currNode == null) {
			currNode = nextNode;
			currSegIndex = currNode.segmentIndex;
			readNextNode();
			return currNode;
		}
		// check for trying to read too far
		if (nextNode == null) { return null; }

		if (nextNode.segmentIndex == currNode.segmentIndex) {
			currNode = nextNode;
			currSegIndex = currNode.segmentIndex;
			readNextNode();
			return currNode;
		}

		if (nextNode.segmentIndex > currNode.segmentIndex) {

		}
		return null;
	}

	private void readNextNode() {
		if (nodeIt.hasNext()) {
			nextNode = nodeIt.next();
		} else {
			nextNode = null;
		}
	}

	/**
	 * Not implemented.
	 *
	 * @throws UnsupportedOperationException
	 *             This method is not implemented.
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException(getClass().getName());
	}

}
