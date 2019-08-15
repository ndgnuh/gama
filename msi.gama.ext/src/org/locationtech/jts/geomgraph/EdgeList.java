
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
package org.locationtech.jts.geomgraph;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.noding.OrientedCoordinateArray;

/**
 * A EdgeList is a list of Edges. It supports locating edges that are pointwise equals to a target edge.
 * 
 * @version 1.7
 */
public class EdgeList {
	private final List<Edge> edges = new ArrayList<>();
	/**
	 * An index of the edges, for fast lookup.
	 *
	 */
	private final Map<OrientedCoordinateArray, Edge> ocaMap = new TreeMap<>();

	public EdgeList() {}

	/**
	 * Insert an edge unless it is already in the list
	 */
	public void add(final Edge e) {
		edges.add(e);
		final OrientedCoordinateArray oca = new OrientedCoordinateArray(e.getCoordinates());
		ocaMap.put(oca, e);
	}

	public void addAll(final Collection<?> edgeColl) {
		for (final Object name : edgeColl) {
			add((Edge) name);
		}
	}

	public List<Edge> getEdges() {
		return edges;
	}

	/**
	 * If there is an edge equal to e already in the list, return it. Otherwise return null.
	 * 
	 * @return equal edge, if there is one already in the list null otherwise
	 */
	public Edge findEqualEdge(final Edge e) {
		final OrientedCoordinateArray oca = new OrientedCoordinateArray(e.getCoordinates());
		// will return null if no edge matches
		final Edge matchEdge = ocaMap.get(oca);
		return matchEdge;
	}

	public Iterator<Edge> iterator() {
		return edges.iterator();
	}

	public Edge get(final int i) {
		return edges.get(i);
	}

	/**
	 * If the edge e is already in the list, return its index.
	 * 
	 * @return index, if e is already in the list -1 otherwise
	 */
	public int findEdgeIndex(final Edge e) {
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).equals(e)) { return i; }
		}
		return -1;
	}

	public void print(final PrintStream out) {
		out.print("MULTILINESTRING ( ");
		for (int j = 0; j < edges.size(); j++) {
			final Edge e = edges.get(j);
			if (j > 0) {
				out.print(",");
			}
			out.print("(");
			final Coordinate[] pts = e.getCoordinates();
			for (int i = 0; i < pts.length; i++) {
				if (i > 0) {
					out.print(",");
				}
				out.print(pts[i].x + " " + pts[i].y);
			}
			out.println(")");
		}
		out.print(")  ");
	}

}
