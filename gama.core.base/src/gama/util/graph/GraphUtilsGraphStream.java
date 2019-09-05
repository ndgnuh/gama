/*******************************************************************************************************
 *
 * gama.util.graph.GraphUtilsGraphStream.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.util.graph;

import static gama.GAMA.reportError;
import static gama.runtime.exceptions.GamaRuntimeException.warning;

import java.util.HashMap;
import java.util.Map;

import gama.common.interfaces.IAgent;
import gama.core.ext.graphstream.Edge;
import gama.core.ext.graphstream.EdgeRejectedException;
import gama.core.ext.graphstream.Graph;
import gama.core.ext.graphstream.IdAlreadyInUseException;
import gama.core.ext.graphstream.MultiGraph;
import gama.core.ext.graphstream.Node;
import gama.metamodel.shape.IShape;
import gama.runtime.scope.IScope;
import gama.util.GamaColor;

/**
 * Graph utilities for the use of the graphstream library.
 *
 * @author Samuel Thiriot
 *
 */
@SuppressWarnings ({ "rawtypes" })
public class GraphUtilsGraphStream {

	/**
	 * Preprocess a gama object before exportation. Filters gama objects that have no meaning out of gama; notably GAMA
	 * colors are translated to RGB values.
	 *
	 * @param gamaValue
	 * @return
	 */
	public static Object preprocessGamaValue(final Object gamaValue) {

		if (gamaValue instanceof GamaColor) {
			// colors can't remain as GAMA colors; let's encode them as RGB java
			final GamaColor gamaColor = (GamaColor) gamaValue;
			return gamaColor.getRGB();
		}

		return gamaValue;
	}

	/**
	 * Takes a gama graph as an input, returns a graphstream graph as close as possible. Preserves double links (multi
	 * graph).
	 *
	 * @param gamaGraph
	 * @return
	 */
	public static Graph getGraphstreamGraphFromGamaGraph(final IScope scope, final IGraph gamaGraph) {

		final Graph g = new MultiGraph("tmpGraph", true, false);

		final Map<Object, Node> gamaNode2graphStreamNode = new HashMap<>(gamaGraph._internalNodesSet().size());

		// add nodes
		for (final Object v : gamaGraph._internalVertexMap().keySet()) {
			// final _Vertex vertex = (_Vertex) gamaGraph._internalVertexMap().get(v);

			final Node n = g.addNode(v.toString());

			gamaNode2graphStreamNode.put(v, n);

			if (v instanceof IAgent) {
				final IAgent a = (IAgent) v;
				a.forEachAttribute((key, value) -> {
					n.setAttribute(key.toString(), preprocessGamaValue(value).toString());
					return true;
				});
			}

			if (v instanceof IShape) {
				final IShape sh = (IShape) v;

				n.setAttribute("x", sh.getLocation().getX());
				n.setAttribute("y", sh.getLocation().getY());
				n.setAttribute("z", sh.getLocation().getZ());

			}

		}

		// add edges
		for (final Object edgeObj : gamaGraph._internalEdgeMap().keySet()) {
			final _Edge edge = (_Edge) gamaGraph._internalEdgeMap().get(edgeObj);

			try {
				final Edge e = g.addEdgeToNodes(edgeObj.toString(), gamaNode2graphStreamNode.get(edge.getSource()),
						gamaNode2graphStreamNode.get(edge.getTarget()), gamaGraph.isDirected());
				if (edgeObj instanceof IAgent) {
					final IAgent a = (IAgent) edgeObj;
					a.forEachAttribute((key, value) -> {
						e.setAttribute(key.toString(), preprocessGamaValue(value).toString());
						return true;
					});
					// for (final Object key : a.getAttributes().keySet()) {
					// final Object value = preprocessGamaValue(a.getAttributes().get(key));
					// e.setAttribute(key.toString(), value.toString());
					// }
				}
			} catch (final EdgeRejectedException e) {
				reportError(scope,
						warning("an edge was rejected during the transformation, probably because it was a double one",
								scope),
						true);
			} catch (final IdAlreadyInUseException e) {
				reportError(scope,
						warning("an edge was rejected during the transformation, probably because it was a double one",
								scope),
						true);
			}

		}

		// some basic tests for integrity
		if (gamaGraph.getVertices().size() != g.getNodeCount()) {
			reportError(scope,
					warning("The exportation ran without error, but an integrity test failed: "
							+ "the number of vertices is not correct(" + g.getNodeCount() + " instead of "
							+ gamaGraph.getVertices().size() + ")", scope),
					true);
		}
		if (gamaGraph.getEdges().size() != g.getEdgeCount()) {
			reportError(scope,
					warning("The exportation ran without error, but an integrity test failed: "
							+ "the number of edges is not correct(" + g.getEdgeCount() + " instead of "
							+ gamaGraph.getEdges().size() + ")", scope),
					true);
		}

		return g;
	}
}
