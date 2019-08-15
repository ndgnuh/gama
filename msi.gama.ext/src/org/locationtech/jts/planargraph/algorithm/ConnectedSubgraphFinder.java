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

package org.locationtech.jts.planargraph.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.locationtech.jts.planargraph.DirectedEdge;
import org.locationtech.jts.planargraph.Edge;
import org.locationtech.jts.planargraph.GraphComponent;
import org.locationtech.jts.planargraph.Node;
import org.locationtech.jts.planargraph.PlanarGraph;
import org.locationtech.jts.planargraph.Subgraph;

/**
 * Finds all connected {@link Subgraph}s of a {@link PlanarGraph}.
 * <p>
 * <b>Note:</b> uses the <code>isVisited</code> flag on the nodes.
 */
public class ConnectedSubgraphFinder {

	private final PlanarGraph graph;

	public ConnectedSubgraphFinder(final PlanarGraph graph) {
		this.graph = graph;
	}

	public List<Subgraph> getConnectedSubgraphs() {
		final List<Subgraph> subgraphs = new ArrayList<>();

		GraphComponent.setVisited(graph.nodeIterator(), false);
		for (final Iterator<?> i = graph.edgeIterator(); i.hasNext();) {
			final Edge e = (Edge) i.next();
			final Node node = e.getDirEdge(0).getFromNode();
			if (!node.isVisited()) {
				subgraphs.add(findSubgraph(node));
			}
		}
		return subgraphs;
	}

	private Subgraph findSubgraph(final Node node) {
		final Subgraph subgraph = new Subgraph(graph);
		addReachable(node, subgraph);
		return subgraph;
	}

	/**
	 * Adds all nodes and edges reachable from this node to the subgraph. Uses an explicit stack to avoid a large depth
	 * of recursion.
	 *
	 * @param node
	 *            a node known to be in the subgraph
	 */
	private void addReachable(final Node startNode, final Subgraph subgraph) {
		final Stack<Node> nodeStack = new Stack<>();
		nodeStack.add(startNode);
		while (!nodeStack.empty()) {
			final Node node = nodeStack.pop();
			addEdges(node, nodeStack, subgraph);
		}
	}

	/**
	 * Adds the argument node and all its out edges to the subgraph.
	 *
	 * @param node
	 *            the node to add
	 * @param nodeStack
	 *            the current set of nodes being traversed
	 */
	private void addEdges(final Node node, final Stack<Node> nodeStack, final Subgraph subgraph) {
		node.setVisited(true);
		for (final Iterator<?> i = node.getOutEdges().iterator(); i.hasNext();) {
			final DirectedEdge de = (DirectedEdge) i.next();
			subgraph.add(de.getEdge());
			final Node toNode = de.getToNode();
			if (!toNode.isVisited()) {
				nodeStack.push(toNode);
			}
		}
	}

}
