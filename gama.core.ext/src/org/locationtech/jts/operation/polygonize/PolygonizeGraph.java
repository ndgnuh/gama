
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

package org.locationtech.jts.operation.polygonize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.planargraph.DirectedEdge;
import org.locationtech.jts.planargraph.DirectedEdgeStar;
import org.locationtech.jts.planargraph.Edge;
import org.locationtech.jts.planargraph.Node;
import org.locationtech.jts.planargraph.PlanarGraph;
import org.locationtech.jts.util.Assert;

/**
 * Represents a planar graph of edges that can be used to compute a polygonization, and implements the algorithms to
 * compute the {@link EdgeRings} formed by the graph.
 * <p>
 * The marked flag on {@link DirectedEdge}s is used to indicate that a directed edge has be logically deleted from the
 * graph.
 *
 * @version 1.7
 */
class PolygonizeGraph extends PlanarGraph {

	private static int getDegreeNonDeleted(final Node node) {
		final List edges = node.getOutEdges().getEdges();
		int degree = 0;
		for (final Iterator i = edges.iterator(); i.hasNext();) {
			final PolygonizeDirectedEdge de = (PolygonizeDirectedEdge) i.next();
			if (!de.isMarked()) {
				degree++;
			}
		}
		return degree;
	}

	private static int getDegree(final Node node, final long label) {
		final List edges = node.getOutEdges().getEdges();
		int degree = 0;
		for (final Iterator i = edges.iterator(); i.hasNext();) {
			final PolygonizeDirectedEdge de = (PolygonizeDirectedEdge) i.next();
			if (de.getLabel() == label) {
				degree++;
			}
		}
		return degree;
	}

	/**
	 * Deletes all edges at a node
	 */
	public static void deleteAllEdges(final Node node) {
		final List edges = node.getOutEdges().getEdges();
		for (final Iterator i = edges.iterator(); i.hasNext();) {
			final PolygonizeDirectedEdge de = (PolygonizeDirectedEdge) i.next();
			de.setMarked(true);
			final PolygonizeDirectedEdge sym = (PolygonizeDirectedEdge) de.getSym();
			if (sym != null) {
				sym.setMarked(true);
			}
		}
	}

	private final GeometryFactory factory;

	// private List labelledRings;

	/**
	 * Create a new polygonization graph.
	 */
	public PolygonizeGraph(final GeometryFactory factory) {
		this.factory = factory;
	}

	/**
	 * Add a {@link LineString} forming an edge of the polygon graph.
	 *
	 * @param line
	 *            the line to add
	 */
	public void addEdge(final LineString line) {
		if (line.isEmpty()) { return; }
		final Coordinate[] linePts = CoordinateArrays.removeRepeatedPoints(line.getCoordinates());

		if (linePts.length < 2) { return; }

		final Coordinate startPt = linePts[0];
		final Coordinate endPt = linePts[linePts.length - 1];

		final Node nStart = getNode(startPt);
		final Node nEnd = getNode(endPt);

		final DirectedEdge de0 = new PolygonizeDirectedEdge(nStart, nEnd, linePts[1], true);
		final DirectedEdge de1 = new PolygonizeDirectedEdge(nEnd, nStart, linePts[linePts.length - 2], false);
		final Edge edge = new PolygonizeEdge(line);
		edge.setDirectedEdges(de0, de1);
		add(edge);
	}

	private Node getNode(final Coordinate pt) {
		Node node = findNode(pt);
		if (node == null) {
			node = new Node(pt);
			// ensure node is only added once to graph
			add(node);
		}
		return node;
	}

	private void computeNextCWEdges() {
		// set the next pointers for the edges around each node
		for (final Iterator iNode = nodeIterator(); iNode.hasNext();) {
			final Node node = (Node) iNode.next();
			computeNextCWEdges(node);
		}
	}

	/**
	 * Convert the maximal edge rings found by the initial graph traversal into the minimal edge rings required by JTS
	 * polygon topology rules.
	 *
	 * @param ringEdges
	 *            the list of start edges for the edgeRings to convert.
	 */
	private void convertMaximalToMinimalEdgeRings(final List ringEdges) {
		for (final Iterator i = ringEdges.iterator(); i.hasNext();) {
			final PolygonizeDirectedEdge de = (PolygonizeDirectedEdge) i.next();
			final long label = de.getLabel();
			final List intNodes = findIntersectionNodes(de, label);

			if (intNodes == null) {
				continue;
			}
			// flip the next pointers on the intersection nodes to create minimal edge rings
			for (final Iterator iNode = intNodes.iterator(); iNode.hasNext();) {
				final Node node = (Node) iNode.next();
				computeNextCCWEdges(node, label);
			}
		}
	}

	/**
	 * Finds all nodes in a maximal edgering which are self-intersection nodes
	 *
	 * @param startDE
	 * @param label
	 * @return the list of intersection nodes found, or <code>null</code> if no intersection nodes were found
	 */
	private static List findIntersectionNodes(final PolygonizeDirectedEdge startDE, final long label) {
		PolygonizeDirectedEdge de = startDE;
		List<Node> intNodes = null;
		do {
			final Node node = de.getFromNode();
			if (getDegree(node, label) > 1) {
				if (intNodes == null) {
					intNodes = new ArrayList<Node>();
				}
				intNodes.add(node);
			}

			de = de.getNext();
			Assert.isTrue(de != null, "found null DE in ring");
			Assert.isTrue(de == startDE || !de.isInRing(), "found DE already in ring");
		} while (de != startDE);

		return intNodes;
	}

	/**
	 * Computes the minimal EdgeRings formed by the edges in this graph.
	 *
	 * @return a list of the {@link EdgeRing}s found by the polygonization process.
	 */
	public List getEdgeRings() {
		// maybe could optimize this, since most of these pointers should be set correctly already
		// by deleteCutEdges()
		computeNextCWEdges();
		// clear labels of all edges in graph
		label(dirEdges, -1);
		final List maximalRings = findLabeledEdgeRings(dirEdges);
		convertMaximalToMinimalEdgeRings(maximalRings);

		// find all edgerings (which will now be minimal ones, as required)
		final List<EdgeRing> edgeRingList = new ArrayList<EdgeRing>();
		for (final Object element : dirEdges) {
			final PolygonizeDirectedEdge de = (PolygonizeDirectedEdge) element;
			if (de.isMarked()) {
				continue;
			}
			if (de.isInRing()) {
				continue;
			}

			final EdgeRing er = findEdgeRing(de);
			edgeRingList.add(er);
		}
		return edgeRingList;
	}

	/**
	 * Finds and labels all edgerings in the graph. The edge rings are labeling with unique integers. The labeling
	 * allows detecting cut edges.
	 *
	 * @param dirEdges
	 *            a List of the DirectedEdges in the graph
	 * @return a List of DirectedEdges, one for each edge ring found
	 */
	private static List findLabeledEdgeRings(final Collection dirEdges) {
		final List<PolygonizeDirectedEdge> edgeRingStarts = new ArrayList<PolygonizeDirectedEdge>();
		// label the edge rings formed
		long currLabel = 1;
		for (final Iterator i = dirEdges.iterator(); i.hasNext();) {
			final PolygonizeDirectedEdge de = (PolygonizeDirectedEdge) i.next();
			if (de.isMarked()) {
				continue;
			}
			if (de.getLabel() >= 0) {
				continue;
			}

			edgeRingStarts.add(de);
			final List edges = EdgeRing.findDirEdgesInRing(de);

			label(edges, currLabel);
			currLabel++;
		}
		return edgeRingStarts;
	}

	/**
	 * Finds and removes all cut edges from the graph.
	 *
	 * @return a list of the {@link LineString}s forming the removed cut edges
	 */
	public List deleteCutEdges() {
		computeNextCWEdges();
		// label the current set of edgerings
		findLabeledEdgeRings(dirEdges);

		/**
		 * Cut Edges are edges where both dirEdges have the same label. Delete them, and record them
		 */
		final List<LineString> cutLines = new ArrayList<LineString>();
		for (final Object element : dirEdges) {
			final PolygonizeDirectedEdge de = (PolygonizeDirectedEdge) element;
			if (de.isMarked()) {
				continue;
			}

			final PolygonizeDirectedEdge sym = (PolygonizeDirectedEdge) de.getSym();

			if (de.getLabel() == sym.getLabel()) {
				de.setMarked(true);
				sym.setMarked(true);

				// save the line as a cut edge
				final PolygonizeEdge e = (PolygonizeEdge) de.getEdge();
				cutLines.add(e.getLine());
			}
		}
		return cutLines;
	}

	private static void label(final Collection dirEdges, final long label) {
		for (final Iterator i = dirEdges.iterator(); i.hasNext();) {
			final PolygonizeDirectedEdge de = (PolygonizeDirectedEdge) i.next();
			de.setLabel(label);
		}
	}

	private static void computeNextCWEdges(final Node node) {
		final DirectedEdgeStar deStar = node.getOutEdges();
		PolygonizeDirectedEdge startDE = null;
		PolygonizeDirectedEdge prevDE = null;

		// the edges are stored in CCW order around the star
		for (final Iterator i = deStar.getEdges().iterator(); i.hasNext();) {
			final PolygonizeDirectedEdge outDE = (PolygonizeDirectedEdge) i.next();
			if (outDE.isMarked()) {
				continue;
			}

			if (startDE == null) {
				startDE = outDE;
			}
			if (prevDE != null) {
				final PolygonizeDirectedEdge sym = (PolygonizeDirectedEdge) prevDE.getSym();
				sym.setNext(outDE);
			}
			prevDE = outDE;
		}
		if (prevDE != null) {
			final PolygonizeDirectedEdge sym = (PolygonizeDirectedEdge) prevDE.getSym();
			sym.setNext(startDE);
		}
	}

	/**
	 * Computes the next edge pointers going CCW around the given node, for the given edgering label. This algorithm has
	 * the effect of converting maximal edgerings into minimal edgerings
	 */
	private static void computeNextCCWEdges(final Node node, final long label) {
		final DirectedEdgeStar deStar = node.getOutEdges();
		// PolyDirectedEdge lastInDE = null;
		PolygonizeDirectedEdge firstOutDE = null;
		PolygonizeDirectedEdge prevInDE = null;

		// the edges are stored in CCW order around the star
		final List edges = deStar.getEdges();
		// for (Iterator i = deStar.getEdges().iterator(); i.hasNext(); ) {
		for (int i = edges.size() - 1; i >= 0; i--) {
			final PolygonizeDirectedEdge de = (PolygonizeDirectedEdge) edges.get(i);
			final PolygonizeDirectedEdge sym = (PolygonizeDirectedEdge) de.getSym();

			PolygonizeDirectedEdge outDE = null;
			if (de.getLabel() == label) {
				outDE = de;
			}
			PolygonizeDirectedEdge inDE = null;
			if (sym.getLabel() == label) {
				inDE = sym;
			}

			if (outDE == null && inDE == null) {
				continue; // this edge is not in edgering
			}

			if (inDE != null) {
				prevInDE = inDE;
			}

			if (outDE != null) {
				if (prevInDE != null) {
					prevInDE.setNext(outDE);
					prevInDE = null;
				}
				if (firstOutDE == null) {
					firstOutDE = outDE;
				}
			}
		}
		if (prevInDE != null) {
			Assert.isTrue(firstOutDE != null);
			prevInDE.setNext(firstOutDE);
		}
	}

	private EdgeRing findEdgeRing(final PolygonizeDirectedEdge startDE) {
		final EdgeRing er = new EdgeRing(factory);
		er.build(startDE);
		return er;
	}

	/**
	 * Marks all edges from the graph which are "dangles". Dangles are which are incident on a node with degree 1. This
	 * process is recursive, since removing a dangling edge may result in another edge becoming a dangle. In order to
	 * handle large recursion depths efficiently, an explicit recursion stack is used
	 *
	 * @return a List containing the {@link LineString}s that formed dangles
	 */
	public Collection deleteDangles() {
		final List nodesToRemove = findNodesOfDegree(1);
		final Set<LineString> dangleLines = new HashSet<>();

		final Stack<Node> nodeStack = new Stack<>();
		for (final Iterator<Node> i = nodesToRemove.iterator(); i.hasNext();) {
			nodeStack.push(i.next());
		}

		while (!nodeStack.isEmpty()) {
			final Node node = nodeStack.pop();

			deleteAllEdges(node);
			final List nodeOutEdges = node.getOutEdges().getEdges();
			for (final Iterator i = nodeOutEdges.iterator(); i.hasNext();) {
				final PolygonizeDirectedEdge de = (PolygonizeDirectedEdge) i.next();
				// delete this edge and its sym
				de.setMarked(true);
				final PolygonizeDirectedEdge sym = (PolygonizeDirectedEdge) de.getSym();
				if (sym != null) {
					sym.setMarked(true);
				}

				// save the line as a dangle
				final PolygonizeEdge e = (PolygonizeEdge) de.getEdge();
				dangleLines.add(e.getLine());

				final Node toNode = de.getToNode();
				// add the toNode to the list to be processed, if it is now a dangle
				if (getDegreeNonDeleted(toNode) == 1) {
					nodeStack.push(toNode);
				}
			}
		}
		return dangleLines;
	}

	/**
	 * Traverses the polygonized edge rings in the graph and computes the depth parity (odd or even) relative to the
	 * exterior of the graph. If the client has requested that the output be polygonally valid, only odd polygons will
	 * be constructed.
	 *
	 */
	public void computeDepthParity() {
		while (true) {
			final PolygonizeDirectedEdge de = null; // findLowestDirEdge();
			if (de == null) { return; }
			computeDepthParity(de);
		}
	}

	/**
	 * Traverses all connected edges, computing the depth parity of the associated polygons.
	 *
	 * @param de
	 */
	private void computeDepthParity(final PolygonizeDirectedEdge de) {

	}

}
