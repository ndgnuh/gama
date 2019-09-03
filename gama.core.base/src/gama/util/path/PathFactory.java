/*******************************************************************************************************
 *
 * gama.util.path.PathFactory.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.util.path;

import static gama.common.geometry.GeometryUtils.getFirstPointOf;
import static gama.common.geometry.GeometryUtils.getLastPointOf;

import gama.metamodel.shape.GamaShape;
import gama.metamodel.shape.IShape;
import gama.metamodel.topology.ITopology;
import gama.metamodel.topology.graph.GamaSpatialGraph;
import gama.metamodel.topology.graph.GraphTopology;
import gama.runtime.scope.IScope;
import gama.util.graph.IGraph;
import gama.util.list.IList;

public class PathFactory {

	public static GamaSpatialPath create(final GamaSpatialGraph g, final IShape start, final IShape target,
			final IList<IShape> edges) {
		return new GamaSpatialPath(g, start, target, edges, true);
	}

	public static <V, E> GamaPath<V, E, IGraph<V, E>> create(final IGraph<V, E> g, final V start, final V target,
			final IList<E> edges) {
		return new GamaPath<>(g, start, target, edges, true);
	}

	public static GamaSpatialPath create(final IScope scope, final ITopology g, final IList<IShape> nodes) {
		return new GamaSpatialPath(g instanceof GraphTopology ? ((GraphTopology) g).getPlaces() : null, nodes);
	}

	public static GamaSpatialPath create(final IScope scope, final ITopology g, final IShape start, final IShape target,
			final IList<IShape> edges) {
		return create(scope, g, start, target, edges, g instanceof GraphTopology);
	}

	public static GamaSpatialPath create(final IScope scope, final ITopology g, final IShape start, final IShape target,
			final IList<IShape> edges, final boolean modify_edges) {
		return new GamaSpatialPath(g instanceof GraphTopology ? ((GraphTopology) g).getPlaces() : null, start, target,
				edges, modify_edges);
	}

	public static GamaSpatialPath create(final IScope scope, final IList<IShape> edgesNodes, final boolean isEdges) {
		if (isEdges) {
			final GamaShape shapeS = (GamaShape) edgesNodes.get(0).getGeometry();
			final GamaShape shapeT = (GamaShape) edgesNodes.get(edgesNodes.size() - 1).getGeometry();
			return new GamaSpatialPath(null, getFirstPointOf(shapeS), getLastPointOf(shapeT), edgesNodes, false);
		}
		return new GamaSpatialPath(edgesNodes);
	}

}
