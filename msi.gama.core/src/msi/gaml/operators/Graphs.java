/*******************************************************************************************************
 *
 * msi.gaml.operators.Graphs.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8.1)
 *
 * (c) 2007-2020 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gaml.operators;

import static one.util.streamex.StreamEx.of;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.ArrayUtils;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.flow.EdmondsKarpMFImpl;
import org.jgrapht.alg.interfaces.MaximumFlowAlgorithm.MaximumFlow;

import org.locationtech.jts.geom.Coordinate;

import msi.gama.common.geometry.GeometryUtils;
import msi.gama.common.interfaces.IKeyword;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.metamodel.shape.ILocation;
import msi.gama.metamodel.shape.IShape;
import msi.gama.metamodel.topology.graph.GamaSpatialGraph;
import msi.gama.metamodel.topology.graph.GamaSpatialGraph.VertexRelationship;
import msi.gama.precompiler.GamlAnnotations.doc;
import msi.gama.precompiler.GamlAnnotations.example;
import msi.gama.precompiler.GamlAnnotations.no_test;
import msi.gama.precompiler.GamlAnnotations.operator;
import msi.gama.precompiler.GamlAnnotations.test;
import msi.gama.precompiler.GamlAnnotations.usage;
import msi.gama.precompiler.IConcept;
import msi.gama.precompiler.IOperatorCategory;
import msi.gama.precompiler.ITypeProvider;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.util.Collector;
import msi.gama.util.GamaList;
import msi.gama.util.GamaListFactory;
import msi.gama.util.GamaMapFactory;
import msi.gama.util.GamaPair;
import msi.gama.util.ICollector;
import msi.gama.util.IContainer;
import msi.gama.util.IList;
import msi.gama.util.IMap;
import msi.gama.util.file.GamaFile;
import msi.gama.util.graph.GamaGraph;
import msi.gama.util.graph.GraphAlgorithmsHandmade;
import msi.gama.util.graph.GraphFromAgentContainerSynchronizer;
import msi.gama.util.graph.IGraph;
import msi.gama.util.graph.layout.LayoutCircle;
import msi.gama.util.graph.layout.LayoutForceDirected;
import msi.gama.util.graph.layout.LayoutGrid;
import msi.gama.util.graph.loader.GraphLoader;
import msi.gama.util.matrix.GamaFloatMatrix;
import msi.gama.util.matrix.GamaIntMatrix;
import msi.gama.util.matrix.GamaMatrix;
import msi.gama.util.path.GamaSpatialPath;
import msi.gama.util.path.IPath;
import msi.gaml.skills.GridSkill.IGridAgent;
import msi.gaml.species.ISpecies;
import msi.gaml.types.GamaGraphType;
import msi.gaml.types.GamaPathType;
import msi.gaml.types.IType;
import msi.gaml.types.Types;

/**
 * Written by drogoul Modified on 13 avr. 2011
 *
 * @todo Description
 *
 */
@SuppressWarnings ({ "unchecked", "rawtypes" })
public class Graphs {

	private static class IntersectionRelation implements VertexRelationship<IShape> {

		double tolerance;

		IntersectionRelation(final double t) {
			tolerance = t;
		}

		@Override
		public boolean related(final IScope scope, final IShape p1, final IShape p2) {
			return Spatial.Properties.intersects(
					Spatial.Transformations.enlarged_by(scope, p1.getGeometry(), tolerance),
					Spatial.Transformations.enlarged_by(scope, p2.getGeometry(), tolerance));
		}

		@Override
		public boolean equivalent(final IScope scope, final IShape p1, final IShape p2) {
			return p1 == null ? p2 == null : p1.getGeometry().equals(p2.getGeometry());
		}
	};

	private static class GridNeighborsRelation implements VertexRelationship<IShape> {

		GridNeighborsRelation() {}

		@Override
		public boolean related(final IScope scope, final IShape p1, final IShape p2) {
			if (!(p1 instanceof IGridAgent)) { return false; }
			return ((IGridAgent) p1).getNeighbors(scope).contains(p2);
		}

		@Override
		public boolean equivalent(final IScope scope, final IShape p1, final IShape p2) {
			return p1 == p2;
		}
	};

	// private static class IntersectionRelationLine implements
	// VertexRelationship<IShape> {
	//
	// IntersectionRelationLine() {}
	//
	// @Override
	// public boolean related(final IScope scope, final IShape p1, final IShape
	// p2) {
	// return p1.getInnerGeometry().relate(p2.getInnerGeometry(), "****1****");
	// }
	//
	// @Override
	// public boolean equivalent(final IScope scope, final IShape p1, final
	// IShape p2) {
	// return p1 == null ? p2 == null :
	// p1.getGeometry().equals(p2.getGeometry());
	// }
	//
	// };

	private static class IntersectionRelationLineTriangle implements VertexRelationship<IShape> {

		final boolean optimizedForTriangulation;

		IntersectionRelationLineTriangle(final boolean optimizedForTriangulation) {
			this.optimizedForTriangulation = optimizedForTriangulation;
		}

		@Override
		public boolean related(final IScope scope, final IShape p1, final IShape p2) {
			if (optimizedForTriangulation) {
				int nb = 0;
				final Coordinate[] coord1 = p1.getInnerGeometry().getCoordinates();
				final Coordinate[] coord2 = p2.getInnerGeometry().getCoordinates();

				for (int i = 0; i < 3; i++) {
					if (ArrayUtils.contains(coord2, coord1[i])) {
						nb++;
					}
				}

				return nb == 2;
			}
			try (ICollector<ILocation> cp = Collector.getSet()) {
				final GamaPoint[] lp1 = GeometryUtils.getPointsOf(p1);
				for (final GamaPoint pt : GeometryUtils.getPointsOf(p2)) {
					if (ArrayUtils.contains(lp1, pt)) {
						cp.add(pt);
					}
				}

				return cp.size() == 2;
			}
		}

		@Override
		public boolean equivalent(final IScope scope, final IShape p1, final IShape p2) {
			if (optimizedForTriangulation) { return p1 == p2; }
			return p1 == null ? p2 == null : p1.getGeometry().equals(p2.getGeometry());
		}

	};

	private static class DistanceRelation implements VertexRelationship<IShape> {

		double distance;

		DistanceRelation(final double d) {
			distance = d;
		}

		/**
		 * @throws GamaRuntimeException
		 * @see msi.gama.util.graph.GamaSpatialGraph.VertexRelationship#related(msi.gama.interfaces.IScope,
		 *      msi.gama.interfaces.IGeometry, msi.gama.interfaces.IGeometry)
		 */
		@Override
		public boolean related(final IScope scope, final IShape g1, final IShape g2) {
			return Spatial.Relations.distance_to(scope, g1.getGeometry(), g2.getGeometry()) <= distance;
		}

		/**
		 * @throws GamaRuntimeException
		 * @see msi.gama.util.graph.GamaSpatialGraph.VertexRelationship#equivalent(msi.gama.interfaces.IGeometry,
		 *      msi.gama.interfaces.IGeometry)
		 */
		@Override
		public boolean equivalent(final IScope scope, final IShape p1, final IShape p2) {
			return p1 == null ? p2 == null : p1.getGeometry().equals(p2.getGeometry());
		}

	}

	/**
	 * Placeholders for fake expressions used to build complex items (like edges and nodes). These expressions are never
	 * evaluated, and return special graph objects (node, edge, nodes and edges)
	 */

	public interface GraphObjectToAdd {

		Object getObject();
	}

	public static class EdgeToAdd implements GraphObjectToAdd {

		public Object source, target;
		public Object object;
		public Double weight;

		public EdgeToAdd(final Object source, final Object target, final Object object, final Double weight) {
			this.object = object;
			this.weight = weight;
			this.source = source;
			this.target = target;
		}

		public EdgeToAdd(final Object source, final Object target, final Object object, final Integer weight) {
			this.object = object;
			this.weight = weight == null ? null : weight.doubleValue();
			this.source = source;
			this.target = target;
		}

		@Override
		public Object getObject() {
			return object;
		}

		/**
		 * @param cast
		 */
		public EdgeToAdd(final Object o) {
			this.object = o;
		}
	}

	public static class NodeToAdd implements GraphObjectToAdd {

		public Object object;
		public Double weight;

		public NodeToAdd(final Object object, final Double weight) {
			this.object = object;
			this.weight = weight;
		}

		/**
		 * @param cast
		 */
		public NodeToAdd(final Object o) {
			object = o;
		}

		@Override
		public Object getObject() {
			return object;
		}

	}

	public static class NodesToAdd extends GamaList<GraphObjectToAdd> implements GraphObjectToAdd {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public NodesToAdd() {
			super(0, Types.NO_TYPE);
		}

		public static NodesToAdd from(final IScope scope, final IContainer object) {
			final NodesToAdd n = new NodesToAdd();
			for (final Object o : object.iterable(scope)) {
				n.add((GraphObjectToAdd) o);
			}
			return n;
		}

		@Override
		public Object getObject() {
			return this;
		}

	}

	public static class EdgesToAdd extends GamaList<GraphObjectToAdd> implements GraphObjectToAdd {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public EdgesToAdd() {
			super(0, Types.NO_TYPE);
		}

		public static EdgesToAdd from(final IScope scope, final IContainer object) {
			final EdgesToAdd n = new EdgesToAdd();
			for (final Object o : object.iterable(scope)) {
				n.add((GraphObjectToAdd) o);
			}
			return n;
		}

		@Override
		public Object getObject() {
			return this;
		}

	}

	@operator (
			value = "agent_from_geometry",
			type = IType.AGENT,
			category = { IOperatorCategory.GRAPH, IOperatorCategory.PATH },
			concept = { IConcept.GRAPH, IConcept.GEOMETRY })
	@doc (
			value = "returns the agent corresponding to given geometry (right-hand operand) in the given path (left-hand operand).",
			usages = @usage ("if the left-hand operand is nil, returns nil"),
			examples = { @example (
					value = "geometry line <- one_of(path_followed.segments);",
					isExecutable = false),
					@example (
							value = "road ag <- road(path_followed agent_from_geometry line);",
							isExecutable = false) },
			see = "path")
	@no_test
	public static IAgent getAgentFromGeom(final IPath path, final IShape geom) {
		if (path == null) { return null; }
		return (IAgent) path.getRealObject(geom);
	}

	/*
	 * TO DO : CHECK THE VALIDITY OF THESE OPERATORS FOR ALL KINDS OF PATH
	 *
	 * @operator(value = "vertices") public static IList nodesOfPath(final GamaPath path) { if ( path == null ) { return
	 * new IList(); } return path.getVertexList(); }
	 *
	 * @operator(value = "edges") public static IList edgesOfPath(final GamaPath path) { if ( path == null ) { return
	 * new IList(); } return path.getEdgeList(); }
	 */

	@operator (
			value = "contains_vertex",
			type = IType.BOOL,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE })
	@doc (
			value = "returns true if the graph(left-hand operand) contains the given vertex (righ-hand operand), false otherwise",
			usages = @usage ("if the left-hand operand is nil, returns false"),
			examples = { @example ("graph graphFromMap<-  as_edge_graph([{1,5}::{12,45},{12,45}::{34,56}]);"),
					@example (
							value = "graphFromMap contains_vertex {1,5}",
							equals = "true") },
			see = { "contains_edge" })
	public static Boolean containsVertex(final IScope scope, final IGraph graph, final Object vertex) {
		if (graph == null) {
			throw GamaRuntimeException.error("In the contains_vertex operator, the graph should not be null!", scope);
		}
		if (vertex instanceof Graphs.NodeToAdd) { return graph.containsVertex(((Graphs.NodeToAdd) vertex).object); }
		return graph.containsVertex(vertex);
	}

	@operator (
			value = "contains_edge",
			type = IType.BOOL,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.EDGE })
	@doc (
			value = "returns true if the graph(left-hand operand) contains the given edge (righ-hand operand), false otherwise",
			masterDoc = true,
			usages = @usage ("if the left-hand operand is nil, returns false"),
			examples = { @example ("graph graphFromMap <-  as_edge_graph([{1,5}::{12,45},{12,45}::{34,56}]);"),
					@example (
							value = "graphFromMap contains_edge link({1,5},{12,45})",
							equals = "true") },
			see = { "contains_vertex" })
	public static Boolean containsEdge(final IScope scope, final IGraph graph, final Object edge) {
		if (graph == null) { throw GamaRuntimeException.error("graph is nil", scope); }
		if (edge instanceof Graphs.EdgeToAdd) {
			final Graphs.EdgeToAdd edge2 = (Graphs.EdgeToAdd) edge;
			if (edge2.object != null) {
				return graph.containsEdge(edge2.object);
			} else if (edge2.source != null && edge2.target != null) {
				return graph.containsEdge(edge2.source, edge2.target);
			}

		}
		return graph.containsEdge(edge);
	}

	@operator (
			value = "contains_edge",
			type = IType.BOOL,
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			value = "returns true if the graph(left-hand operand) contains the given edge (righ-hand operand), false otherwise",
			usages = @usage (
					value = "if the right-hand operand is a pair, returns true if it exists an edge between the two elements of the pair in the graph",
					examples = { @example (
							value = "graphEpidemio contains_edge (node(0)::node(3))",
							equals = "true",
							isExecutable = false) }))
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ "(g contains_edge ({10,5}::{20,3})) = true")
	public static Boolean containsEdge(final IScope scope, final IGraph graph, final GamaPair edge) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		return graph.containsEdge(edge.first(), edge.last());
	}

	@operator (
			value = "source_of",
			type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.EDGE, IConcept.NODE })
	@doc (
			value = "returns the source of the edge (right-hand operand) contained in the graph given in left-hand operand.",
			usages = @usage ("if the lef-hand operand (the graph) is nil, throws an Exception"),
			examples = { @example (
					value = "graph graphEpidemio <- generate_barabasi_albert( [\"edges_species\"::edge,\"vertices_specy\"::node,\"size\"::3,\"m\"::5] );",
					isExecutable = false),
					@example (
							value = "graphEpidemio source_of(edge(3))",
							equals = "node1",
							isExecutable = false),
					@example (
							value = "graph graphFromMap <-  as_edge_graph([{1,5}::{12,45},{12,45}::{34,56}]);"),
					@example (
							value = "graphFromMap source_of(link({1,5},{12,45}))",
							returnType = IKeyword.POINT,
							equals = "{1,5}") },
			see = { "target_of" })
	public static Object sourceOf(final IScope scope, final IGraph graph, final Object edge) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		if (graph.containsEdge(edge)) { return graph.getEdgeSource(edge); }
		return null;
	}

	@operator (
			value = "target_of",
			type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.EDGE, IConcept.NODE })
	@doc (
			value = "returns the target of the edge (right-hand operand) contained in the graph given in left-hand operand.",
			usages = @usage ("if the lef-hand operand (the graph) is nil, returns nil"),
			examples = { @example (
					value = "graph graphEpidemio <- generate_barabasi_albert( [\"edges_species\"::edge,\"vertices_specy\"::node,\"size\"::3,\"m\"::5] );",
					isExecutable = false),
					@example (
							value = "graphEpidemio source_of(edge(3))",
							equals = "node1",
							isExecutable = false),
					@example ("graph graphFromMap <-  as_edge_graph([{1,5}::{12,45},{12,45}::{34,56}]);"), @example (
							value = "graphFromMap target_of(link({1,5},{12,45}))",
							equals = "{12,45}") },
			see = "source_of")
	public static Object targetOf(final IScope scope, final IGraph graph, final Object edge) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		if (graph.containsEdge(edge)) { return graph.getEdgeTarget(edge); }
		return null;
	}

	@operator (
			value = "weight_of",
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.GRAPH_WEIGHT })
	@doc (
			value = "returns the weight of the given edge (right-hand operand) contained in the graph given in right-hand operand.",
			comment = "In a localized graph, an edge has a weight by default (the distance between both vertices).",
			usages = { @usage ("if the left-operand (the graph) is nil, returns nil"),
					@usage ("if the right-hand operand is not an edge of the given graph, weight_of checks whether it is a node of the graph and tries to return its weight"),
					@usage ("if the right-hand operand is neither a node, nor an edge, returns 1.") },
			examples = { @example ("graph graphFromMap <-  as_edge_graph([{1,5}::{12,45},{12,45}::{34,56}]);"),
					@example (
							value = "graphFromMap weight_of(link({1,5},{12,45}))",
							equals = "1.0") })
	public static Double weightOf(final IScope scope, final IGraph graph, final Object edge) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		if (edge instanceof Graphs.GraphObjectToAdd) {
			if (edge instanceof Graphs.EdgeToAdd) {
				final Graphs.EdgeToAdd edge2 = (Graphs.EdgeToAdd) edge;
				if (edge2.object != null) {
					return graph.getEdgeWeight(edge2.object);
				} else if (edge2.source != null && edge2.target != null) {
					final Object edge3 = graph.getEdge(edge2.source, edge2.target);
					return graph.getEdgeWeight(edge3);
				}
			} else if (edge instanceof Graphs.NodeToAdd) {
				return graph.getVertexWeight(((Graphs.NodeToAdd) edge).object);
			}
		}
		if (graph.containsEdge(edge)) {
			return graph.getEdgeWeight(edge);
		} else if (graph.containsVertex(edge)) { return graph.getVertexWeight(edge); }
		return 1d;
	}

	@operator (
			value = "in_edges_of",
			type = IType.LIST,
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.EDGE })
	@doc (
			value = "returns the list of the in-edges of a vertex (right-hand operand) in the graph given as left-hand operand.",
			examples = { @example (
					value = "graph graphFromMap <- graph([]);",
					isTestOnly = true),
					@example (
							value = "graphFromMap in_edges_of node({12,45})",
							equals = "[LineString]",
							test = false) },
			see = "out_edges_of")
	@test ("graph<geometry, geometry> g2 <- directed(as_edge_graph([ edge({10,5}, {30,30}), edge({30,30}, {80,35}), node ({30,30})]));\r\n"
			+ "first(link({10,5},{30,30})) = first(g2 in_edges_of {30,30})")
	public static IList inEdgesOf(final IScope scope, final IGraph graph, final Object vertex) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		if (graph.containsVertex(vertex)) {
			return GamaListFactory.create(scope, graph.getGamlType().getContentType(), graph.incomingEdgesOf(vertex));
		}
		return GamaListFactory.create(graph.getGamlType().getContentType());
	}

	@operator (
			value = "edge_between",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH, IConcept.EDGE })
	@doc (
			value = "returns the edge linking two nodes",
			examples = { @example (
					value = "graphFromMap edge_between node1::node2",
					equals = "edge1",
					isExecutable = false) },
			see = { "out_edges_of", "in_edges_of" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),"
			+ "edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ "(g edge_between ({10,5}::{20,3})) = g.edges[0]")
	public static Object EdgeBetween(final IScope scope, final IGraph graph, final GamaPair verticePair) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		if (graph.containsVertex(verticePair.key) && graph.containsVertex(verticePair.value)) {
			return graph.getEdge(verticePair.key, verticePair.value);
		}
		return null;
	}

	@operator (
			value = "in_degree_of",
			type = IType.INT,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE })
	@doc (
			value = "returns the in degree of a vertex (right-hand operand) in the graph given as left-hand operand.",
			examples = { @example (
					value = "graph graphFromMap <- graph([]);",
					isTestOnly = true),
					@example (
							value = "graphFromMap in_degree_of (node(3))",
							equals = "2",
							test = false) },
			see = { "out_degree_of", "degree_of" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ "(g in_degree_of ({20,3})) = 1")
	public static int inDregreeOf(final IScope scope, final IGraph graph, final Object vertex) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		if (graph.containsVertex(vertex)) { return graph.inDegreeOf(vertex); }
		return 0;
	}

	@operator (
			value = "out_edges_of",
			type = IType.LIST,
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE, IConcept.EDGE })
	@doc (
			value = "returns the list of the out-edges of a vertex (right-hand operand) in the graph given as left-hand operand.",
			masterDoc = true,
			examples = { @example (
					value = "graph graphFromMap <- graph([]);",
					isTestOnly = true),
					@example (
							value = "graphFromMap out_edges_of (node(3))",
							equals = "3",
							test = false) },
			see = "in_edges_of")
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " list li <- g out_edges_of {10,5};  length(li) = 2")
	public static IList outEdgesOf(final IScope scope, final IGraph graph, final Object vertex) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		if (graph.containsVertex(vertex)) {
			return GamaListFactory.create(scope, graph.getGamlType().getContentType(), graph.outgoingEdgesOf(vertex));
		}
		return GamaListFactory.create(graph.getGamlType().getContentType());
	}

	@operator (
			value = "out_degree_of",
			type = IType.INT,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE, IConcept.EDGE })
	@doc (
			value = "returns the out degree of a vertex (right-hand operand) in the graph given as left-hand operand.",
			examples = { @example (
					value = "graph graphFromMap <- graph([]);",
					isTestOnly = true),
					@example (
							value = "graphFromMap out_degree_of (node(3))",
							equals = "4",
							test = false) },
			see = { "in_degree_of", "degree_of" })
	@test ("graph<geometry, geometry> g1 <- directed(as_edge_graph([ edge({10,5}, {30,30}), edge({30,30}, {80,35}), node ({30,30})]));\r\n"
			+ "g1 out_degree_of {30,30} = 1")
	@test ("graph<geometry, geometry> g2 <- directed(as_edge_graph([ edge({30,30}, {10,5}), edge({30,30}, {80,35}), node ({30,30})]));\r\n"
			+ "g2 out_degree_of {30,30} = 2")
	public static int outDregreeOf(final IScope scope, final IGraph graph, final Object vertex) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		if (graph.containsVertex(vertex)) { return graph.outDegreeOf(vertex); }
		return 0;
	}

	@operator (
			value = "degree_of",
			type = IType.INT,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE })
	@doc (
			value = "returns the degree (in+out) of a vertex (right-hand operand) in the graph given as left-hand operand.",
			examples = { @example (
					value = "graph graphFromMap <- graph([]);",
					isTestOnly = true),
					@example (
							value = "graphFromMap degree_of (node(3))",
							equals = "3",
							test = false) },
			see = { "in_degree_of", "out_degree_of" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " (g degree_of ({10,5})) = 3")
	public static int degreeOf(final IScope scope, final IGraph graph, final Object vertex) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		if (graph.containsVertex(vertex)) { return graph.degreeOf(vertex); }
		return 0;
	}

	@operator (
			value = "connected_components_of",
			type = IType.LIST,
			content_type = IType.LIST,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE, IConcept.EDGE })
	@doc (
			value = "returns the connected components of a graph, i.e. the list of all vertices that are in the maximally connected component together with the specified vertex. ",
			examples = { @example (
					value = "graph my_graph <- graph([]);"),
					@example (
							value = "connected_components_of (my_graph)",
							equals = "the list of all the components as list",
							test = false) },
			see = { "alpha_index", "connectivity_index", "nb_cycles" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " list comp <- connected_components_of(g); " + " length(comp) = 2")
	public static IList<IList> connectedComponentOf(final IScope scope, final IGraph graph) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }

		ConnectivityInspector ci;
		// there is an error with connectivity inspector of JGrapht....
		ci = new ConnectivityInspector((DirectedGraph) graph);
		final IList<IList> results = GamaListFactory.create(Types.LIST);
		for (final Object obj : ci.connectedSets()) {
			results.add(GamaListFactory.create(scope, graph.getGamlType().getKeyType(), (Set) obj));
		}
		return results;
	}

	@operator (
			value = "connected_components_of",
			type = IType.LIST,
			content_type = IType.LIST,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE, IConcept.EDGE })
	@doc (
			value = "returns the connected components of a graph, i.e. the list of all edges (if the boolean is true) or vertices (if the boolean is false) that are in the connected components. ",
			examples = { @example (
					value = "graph my_graph2 <- graph([]);"),
					@example (
							value = "connected_components_of (my_graph2, true)",
							equals = "the list of all the components as list",
							test = false) },
			see = { "alpha_index", "connectivity_index", "nb_cycles" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " list comp <- connected_components_of(g, true); " + " length(comp) = 2")
	public static IList<IList> connectedComponentOf(final IScope scope, final IGraph graph, final boolean edge) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }

		ConnectivityInspector ci;
		// there is an error with connectivity inspector of JGrapht....
		ci = new ConnectivityInspector((DirectedGraph) graph);
		final IList<IList> results = GamaListFactory.create(Types.LIST);
		for (final Object obj : ci.connectedSets()) {
			if (edge) {
				final IList edges = GamaListFactory.create(scope, graph.getGamlType().getContentType());
				for (final Object v : (Set) obj) {
					edges.addAll(graph.edgesOf(v));
				}

				results.add(Containers.remove_duplicates(scope, edges));

			} else {
				results.add(GamaListFactory.create(scope, graph.getGamlType().getKeyType(), (Set) obj));
			}
		}
		return results;
	}

	@operator (
			value = "main_connected_component",
			type = IType.GRAPH,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE, IConcept.EDGE })
	@doc (
			value = "returns the sub-graph corresponding to the main connected components of the graph",
			examples = { @example (
					value = "main_connected_component(my_graph)",
					isExecutable = false,
					equals = "the sub-graph corresponding to the main connected components of the graph",
					test = false) },
			see = { "connected_components_of" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " length(main_connected_component(g)) = 5")
	public static IGraph ReduceToMainconnectedComponentOf(final IScope scope, final IGraph graph) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }

		final IList<IList> cc = connectedComponentOf(scope, graph);
		final IGraph newGraph = (IGraph) graph.copy(scope);
		IList mainCC = null;
		int size = 0;
		for (final IList c : cc) {
			if (c.size() > size) {
				size = c.size();
				mainCC = c;
			}
		}
		if (mainCC != null) {
			final Set vs = graph.vertexSet();
			vs.removeAll(mainCC);
			for (final Object v : vs) {
				newGraph.removeVertex(v);
			}
		}

		return newGraph;
	}

	@operator (
			value = "maximal_cliques_of",
			type = IType.LIST,
			content_type = IType.LIST,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE, IConcept.EDGE })
	@doc (
			value = "returns the maximal cliques of a graph using the Bron-Kerbosch clique detection algorithm: A clique is maximal if it is impossible to enlarge it by adding another vertex from the graph. Note that a maximal clique is not necessarily the biggest clique in the graph. ",
			examples = { @example (
					value = "graph my_graph <- graph([]);"),
					@example (
							value = "maximal_cliques_of (my_graph)",
							equals = "the list of all the maximal cliques as list",
							test = false) },
			see = { "biggest_cliques_of" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " maximal_cliques_of(g) = [[{10.0,5.0,0.0},{20.0,3.0,0.0}],[{30.0,30.0,0.0},{10.0,5.0,0.0}],[{20.0,3.0,0.0}],[{30.0,30.0,0.0},{80.0,35.0,0.0}],[{40.0,60.0,0.0},{80.0,35.0,0.0}],[{40.0,60.0,0.0}],[{50.0,50.0,0.0}]]  ")
	public static IList<IList> getMaximalCliques(final IScope scope, final IGraph graph) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		final BronKerboschCliqueFinder cls = new BronKerboschCliqueFinder(graph);
		final IList<IList> results = GamaListFactory.create(Types.LIST);
		final Collection cliques = cls.getAllMaximalCliques();
		for (final Object obj : cliques) {
			results.add(GamaListFactory.create(scope, graph.getGamlType().getKeyType(), (Set) obj));
		}
		return results;
	}

	@operator (
			value = "biggest_cliques_of",
			type = IType.LIST,
			content_type = IType.LIST,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE, IConcept.EDGE })
	@doc (
			value = "returns the biggest cliques of a graph using the Bron-Kerbosch clique detection algorithm",
			examples = { @example (
					value = "graph my_graph <- graph([]);"),
					@example (
							value = "biggest_cliques_of (my_graph)",
							equals = "the list of the biggest cliques as list",
							test = false) },
			see = { "maximal_cliques_of" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " biggest_cliques_of(g) = [[{10.0,5.0,0.0},{20.0,3.0,0.0}],[{30.0,30.0,0.0},{10.0,5.0,0.0}],[{30.0,30.0,0.0},{80.0,35.0,0.0}],[{40.0,60.0,0.0},{80.0,35.0,0.0}]]  ")
	public static IList<IList> getBiggestCliques(final IScope scope, final IGraph graph) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		final BronKerboschCliqueFinder cls = new BronKerboschCliqueFinder(graph);

		final IList<IList> results = GamaListFactory.create(Types.LIST);
		final Collection cliques = cls.getBiggestMaximalCliques();
		for (final Object obj : cliques) {
			results.add(GamaListFactory.create(scope, graph.getGamlType().getKeyType(), (Set) obj));
		}
		return results;
	}

	@operator (
			value = "nb_cycles",
			type = IType.INT,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "returns the maximum number of independent cycles in a graph. This number (u) is estimated through the number of nodes (v), links (e) and of sub-graphs (p): u = e - v + p.",
			examples = { @example (
					value = "graph graphEpidemio <- graph([]);"),
					@example (
							value = "nb_cycles(graphEpidemio)",
							equals = "the number of cycles in the graph",
							test = false) },
			see = { "alpha_index", "beta_index", "gamma_index", "connectivity_index" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " nb_cycles(g) = 1 ")
	public static int nbCycles(final IScope scope, final IGraph graph) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		final int S = graph.vertexSet().size();
		final int C = connectedComponentOf(scope, graph).size();
		final int L = graph.edgeSet().size();
		return L - S + C;
	}

	@operator (
			value = "alpha_index",
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "returns the alpha index of the graph (measure of connectivity which evaluates the number of cycles in a graph in comparison with the maximum number of cycles. The higher the alpha index, the more a network is connected: alpha = nb_cycles / (2`*`S-5) - planar graph)",
			examples = { @example (
					value = "graph graphEpidemio <- graph([]);",
					isTestOnly = true),
					@example (
							value = "alpha_index(graphEpidemio)",
							equals = "the alpha index of the graph",
							test = false) },
			see = { "beta_index", "gamma_index", "nb_cycles", "connectivity_index" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " alpha_index(g) = 0.14285714285714285 ")
	public static double alphaIndex(final IScope scope, final IGraph graph) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		final int S = graph.vertexSet().size();
		return nbCycles(scope, graph) / (2.0 * S - 5);
	}

	@operator (
			value = "beta_index",
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "returns the beta index of the graph (Measures the level of connectivity in a graph and is expressed by the relationship between the number of links (e) over the number of nodes (v) : beta = e/v.",
			examples = { @example (
					value = "graph graphEpidemio <- graph([]);"),
					@example (
							value = "beta_index(graphEpidemio)",
							equals = "the beta index of the graph",
							test = false) },
			see = { "alpha_index", "gamma_index", "nb_cycles", "connectivity_index" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " beta_index(g) = 0.8333333333333334 ")
	public static double betaIndex(final IScope scope, final IGraph graph) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		return (graph.edgeSet().size() + 0.0) / graph.vertexSet().size();
	}

	@operator (
			value = "gamma_index",
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "returns the gamma index of the graph (A measure of connectivity that considers the relationship between the number of observed links and the number of possible links: gamma = e/(3 `*` (v - 2)) - for planar graph.",
			examples = { @example (
					value = "graph graphEpidemio <- graph([]);"),
					@example (
							value = "gamma_index(graphEpidemio)",
							equals = "the gamma index of the graph",
							test = false) },
			see = { "alpha_index", "beta_index", "nb_cycles", "connectivity_index" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " gamma_index(g) = 0.7142857142857143 ")
	public static double gammaIndex(final IScope scope, final IGraph graph) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		return graph.edgeSet().size() / (2.0 * graph.vertexSet().size() - 5);
	}

	@operator (
			value = "connectivity_index",
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "returns a simple connectivity index. This number is estimated through the number of nodes (v) and of sub-graphs (p) : IC = (v - p) /(v - 1).",
			examples = { @example (
					value = "graph graphEpidemio <- graph([]);"),
					@example (
							value = "connectivity_index(graphEpidemio)",
							equals = "the connectivity index of the graph",
							test = false) },
			see = { "alpha_index", "beta_index", "gamma_index", "nb_cycles" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " connectivity_index(g) = 0.8 ")
	public static double connectivityIndex(final IScope scope, final IGraph graph) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		final int S = graph.vertexSet().size();
		final int C = connectedComponentOf(scope, graph).size();
		return (S - C) / (S - 1.0);
	}

	@operator (
			value = "betweenness_centrality",
			type = IType.MAP,
			content_type = IType.INT,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "returns a map containing for each vertex (key), its betweenness centrality (value): number of shortest paths passing through each vertex ",
			examples = { @example (
					value = "graph graphEpidemio <- graph([]);"),
					@example (
							value = "betweenness_centrality(graphEpidemio)",
							equals = "the betweenness centrality index of the graph",
							test = false) },
			see = {})
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " betweenness_centrality(g) = [{10.0,5.0,0.0}::5,{20.0,3.0,0.0}::0,{30.0,30.0,0.0}::2,{80.0,35.0,0.0}::4,{40.0,60.0,0.0}::0,{50.0,50.0,0.0}::0] ")
	public static IMap betweennessCentrality(final IScope scope, final IGraph graph) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }

		final IMap mapResult = GamaMapFactory.create(graph.getGamlType().getKeyType(), Types.INT);
		final IList vertices = Cast.asList(scope, graph.vertexSet());
		for (final Object v : vertices) {
			mapResult.put(v, 0);
		}
		final boolean directed = graph.isDirected();
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = directed ? 0 : i + 1; j < vertices.size(); j++) {
				final Object v1 = vertices.get(i);
				final Object v2 = vertices.get(j);
				if (v1 == v2) {
					continue;
				}
				final List edges = graph.computeBestRouteBetween(scope, v1, v2);
				if (edges == null) {
					continue;
				}
				Object vc = v1;
				for (final Object edge : edges) {
					Object node = graph.getEdgeTarget(edge);
					if (node == vc) {
						node = graph.getEdgeSource(edge);
					}
					if (node != v2 && node != v1) {
						mapResult.put(node, (Integer) mapResult.get(node) + 1);
					}
					vc = node;
				}
			}
		}
		return mapResult;
	}

	@operator (
			value = "edge_betweenness",
			type = IType.MAP,
			content_type = IType.INT,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "returns a map containing for each edge (key), its betweenness centrality (value): number of shortest paths passing through each edge ",
			examples = { @example (
					value = "graph graphEpidemio <- graph([]);"),
					@example (
							value = "edge_betweenness(graphEpidemio)",
							equals = "the edge betweenness index of the graph",
							test = false) },
			see = {})
	@no_test
	public static IMap edgeBetweenness(final IScope scope, final IGraph graph) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		// DEBUG.OUT("result.getRaw() : " + result.getRaw());

		final IMap mapResult = GamaMapFactory.create(graph.getGamlType().getContentType(), Types.INT);
		for (final Object v : graph.edgeSet()) {
			mapResult.put(v, 0);
		}
		final IList vertices = Cast.asList(scope, graph.vertexSet());
		final boolean directed = graph.isDirected();
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = directed ? 0 : i + 1; j < vertices.size(); j++) {
				final Object v1 = vertices.get(i);
				final Object v2 = vertices.get(j);
				if (v1 == v2) {
					continue;
				}
				final List edges = graph.computeBestRouteBetween(scope, v1, v2);
				if (edges == null) {
					continue;
				}
				for (final Object edge : edges) {
					mapResult.put(edge, (Integer) mapResult.get(edge) + 1);
				}
			}
		}
		return mapResult;
	}

	@operator (
			value = { "neighbors_of" },
			type = IType.LIST,
			content_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE, IConcept.NEIGHBORS })
	@doc (
			value = "returns the list of neighbors of the given vertex (right-hand operand) in the given graph (left-hand operand)",
			examples = { @example (
					value = "graphEpidemio neighbors_of (node(3))",
					equals = "[node0,node2]",
					isExecutable = false),
					@example (
							value = "graphFromMap neighbors_of node({12,45})",
							equals = "[{1.0,5.0},{34.0,56.0}]",
							isExecutable = false) },
			see = { "predecessors_of", "successors_of" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ "(g neighbors_of ({10,5}) sort_by point(each)) = [{20.0,3.0,0.0},{30.0,30.0,0.0},{80.0,35.0,0.0}]")
	public static IList neighborsOf(final IScope scope, final IGraph graph, final Object vertex) {
		if (graph == null) { throw GamaRuntimeException.error("The graph is nil", scope); }
		if (graph.containsVertex(vertex)) {
			return GamaListFactory.create(scope, graph.getGamlType().getKeyType(),
					org.jgrapht.Graphs.neighborListOf(graph, vertex));
		}
		return GamaListFactory.create(graph.getGamlType().getKeyType());
	}

	@operator (
			value = "predecessors_of",
			type = IType.LIST,
			content_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE, IConcept.NEIGHBORS })
	@doc (
			value = "returns the list of predecessors (i.e. sources of in edges) of the given vertex (right-hand operand) in the given graph (left-hand operand)",
			examples = { @example (
					value = "graph graphEpidemio <- as_edge_graph([{1,5}::{12,45},{12,45}::{34,56}]);",
					isTestOnly = true),
					@example (
							value = "graphEpidemio predecessors_of ({1,5})",
							equals = "[]",
							test = false),
					@example (
							value = "graphEpidemio predecessors_of node({34,56})",
							equals = "[{12;45}]",
							test = false) },
			see = { "neighbors_of", "successors_of" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ "g predecessors_of ({10,5}) = [{80.0,35.0,0.0}]")
	public static IList predecessorsOf(final IScope scope, final IGraph graph, final Object vertex) {
		if (graph.containsVertex(vertex)) {
			return GamaListFactory.create(scope, graph.getGamlType().getKeyType(),
					org.jgrapht.Graphs.predecessorListOf(graph, vertex));
		}
		return GamaListFactory.create(graph.getGamlType().getKeyType());
	}

	@operator (
			value = "successors_of",
			content_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE, IConcept.NEIGHBORS })
	@doc (
			value = "returns the list of successors (i.e. targets of out edges) of the given vertex (right-hand operand) in the given graph (left-hand operand)",
			examples = { @example (
					value = "graph graphEpidemio <- as_edge_graph([{1,5}::{12,45},{12,45}::{34,56}]);",
					isTestOnly = true),
					@example (
							value = "graphEpidemio successors_of ({1,5})",
							equals = "[{12,45}]"),
					@example (
							value = "graphEpidemio successors_of node({34,56})",
							equals = "[]") },
			see = { "predecessors_of", "neighbors_of" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ "g successors_of ({10,5}) = [{20.0,3.0,0.0},{30.0,30.0,0.0}]")
	public static IList successorsOf(final IScope scope, final IGraph graph, final Object vertex) {
		if (graph.containsVertex(vertex)) {
			return GamaListFactory.create(scope, graph.getGamlType().getKeyType(),
					org.jgrapht.Graphs.successorListOf(graph, vertex));
		}
		return GamaListFactory.create(graph.getGamlType().getKeyType());
	}

	@operator (
			value = "as_edge_graph",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = IType.GEOMETRY,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.CAST, IConcept.MAP, IConcept.LIST, IConcept.EDGE })
	@doc (
			value = "creates a graph from the list/map of edges given as operand",
			masterDoc = true,
			usages = @usage (
					value = "if the operand is a list, the graph will be built with elements of the list as edges",
					examples = { @example (
							value = "as_edge_graph([line([{1,5},{12,45}]),line([{12,45},{34,56}])])",
							equals = "a graph with two edges and three vertices",
							test = false) }),
			see = { "as_intersection_graph", "as_distance_graph" })
	@test (" graph<geometry,geometry> comp <- as_edge_graph([line([{1,5},{12,45}]),line([{12,45},{34,56}])]); "
			+ " ( ({1,5} in comp.vertices) and  ({12,45} in comp.vertices) and  ({34,56} in comp.vertices) ) ")
	public static IGraph spatialFromEdges(final IScope scope, final IContainer edges) {

		final IGraph createdGraph = new GamaSpatialGraph(edges, true, false, null, null, scope, Types.GEOMETRY,
				edges.getGamlType().getContentType());
		if (Types.AGENT.equals(edges.getGamlType().getContentType())) {
			GraphFromAgentContainerSynchronizer.synchronize(scope, null, edges, createdGraph);
		}

		return createdGraph;
	}
	
	@operator (
			value = "as_intersection_graph",
			content_type = IType.GEOMETRY,
			index_type = IType.GEOMETRY,
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			value = "creates a graph from a list of vertices (left-hand operand). An edge is created between each pair of vertices with an intersection (with a given tolerance).",
			see = { "as_distance_graph", "as_edge_graph" })
	@no_test
	public static IGraph spatialFromVertices(final IScope scope, final IContainer vertices, final Double tolerance,
			final ISpecies edgeSpecies) {
		final IType edgeType = scope.getType(edgeSpecies.getName());
		final IGraph createdGraph = new GamaSpatialGraph(vertices, false, false, new IntersectionRelation(tolerance),
				edgeSpecies, scope, vertices.getGamlType().getContentType(), edgeType);

		if (Types.AGENT.equals(vertices.getGamlType().getContentType())) {
			GraphFromAgentContainerSynchronizer.synchronize(scope, vertices, edgeSpecies, createdGraph);
		}

		return createdGraph;
	}

	@operator (
			value = "as_edge_graph",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = IType.GEOMETRY,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.CAST, IConcept.MAP, IConcept.LIST, IConcept.EDGE })
	@doc (
			usages = @usage (
					value = "if the operand is a list and a tolerance (max distance in meters to consider that 2 points are the same node) is given, "
							+ "the graph will be built with elements of the list as edges and two edges will be connected by a node if the distance between their "
							+ "extremity (first or last points) are at distance lower or equal to the tolerance",
					examples = { @example (
							value = "as_edge_graph([line([{1,5},{12,45}]),line([{13,45},{34,56}])],1)",
							equals = "a graph with two edges and three vertices",
							test = false) }),
			see = { "as_intersection_graph", "as_distance_graph" })
	@test (" graph<geometry,geometry> g <- as_edge_graph([line([{1,5},{12,45}]),line([{13,45},{34,56}])],1); "
			+ " [{1.0,5.0,0.0},{12.0,45.0,0.0},{34.0,56.0,0.0}] = g.vertices  ")
	public static IGraph spatialFromEdges(final IScope scope, final IContainer edges, final Double tolerance) {

		final GamaSpatialGraph createdGraph = new GamaSpatialGraph(edges, true, false, null, null, scope,
				Types.GEOMETRY, edges.getGamlType().getContentType(), tolerance);

		if (Types.AGENT.equals(edges.getGamlType().getContentType())) {
			GraphFromAgentContainerSynchronizer.synchronize(scope, null, edges, createdGraph);
		}

		return createdGraph;
	}

	@operator (
			value = "as_edge_graph",
			index_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			usages = @usage (
					value = "if the operand is a map, the graph will be built by creating edges from pairs of the map",
					examples = @example (
							value = "as_edge_graph([{1,5}::{12,45},{12,45}::{34,56}])",
							equals = "a graph with these three vertices and two edges",
							test = false)))
	@test (" graph<geometry,geometry> g <- as_edge_graph([{1,5}::{12,45},{12,45}::{34,56}]); "
			+ " length(g.vertices) = 3 and length(g.edges) = 2")
	public static IGraph spatialFromEdges(final IScope scope, final IMap edges) {
		// Edges are represented by pairs of vertex::vertex

		return GamaGraphType.from(scope, edges, true);
	}@operator (
			value = "as_edge_graph",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = IType.GEOMETRY,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.CAST, IConcept.MAP, IConcept.LIST, IConcept.EDGE })
	@doc (
			value = "creates a graph from the first list of edges and the list nodes",
			masterDoc = false,
			see = { "as_intersection_graph", "as_distance_graph" })
	@test ("graph<geometry,geometry> comp <- as_edge_graph([line([{1,5},{12,45}]),line([{12,45},{34,56}])], [{1,5},{12,45},{34,56}]);"
			+ " ( ({1,5} in comp.vertices) and  ({12,45} in comp.vertices) and  ({34,56} in comp.vertices) ) ")
	public static IGraph spatialFromEdges(final IScope scope, final IContainer edges, final IContainer nodes) {
			return new GamaSpatialGraph(edges, nodes, scope);
	}

	@operator (
			value = "as_intersection_graph",
			content_type = IType.GEOMETRY,
			index_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE, IConcept.CAST })
	@doc (
			value = "creates a graph from a list of vertices (left-hand operand). An edge is created between each pair of vertices with an intersection (with a given tolerance).",
			comment = "as_intersection_graph is more efficient for a list of geometries (but less accurate) than as_distance_graph.",
			examples = @example (
					value = "list(ant) as_intersection_graph 0.5",
					isExecutable = false),
			see = { "as_distance_graph", "as_edge_graph" })
	@no_test
	public static IGraph spatialFromVertices(final IScope scope, final IContainer vertices, final Double tolerance) {
		final IGraph createdGraph = new GamaSpatialGraph(vertices, false, false, new IntersectionRelation(tolerance),
				null, scope, vertices.getGamlType().getContentType(), Types.GEOMETRY);
		if (Types.AGENT.equals(vertices.getGamlType().getContentType())) {
			GraphFromAgentContainerSynchronizer.synchronize(scope, vertices, null, createdGraph);
		}
		return createdGraph;
	}

	public static IGraph spatialLineIntersection(final IScope scope, final IContainer vertices) {
		return new GamaSpatialGraph(vertices, false, false, new IntersectionRelationLineTriangle(true), null, scope,
				vertices.getGamlType().getContentType(), Types.GEOMETRY);
	}

	public static IGraph spatialLineIntersectionTriangle(final IScope scope, final IContainer vertices) {
		final IGraph g = new GamaSpatialGraph(scope, vertices.getGamlType().getContentType(), Types.GEOMETRY);
		for (final Object o : vertices.iterable(scope)) {
			g.addVertex(o);
		}
		for (final Object o1 : vertices.iterable(scope)) {
			final Coordinate[] coord1 = ((IShape) o1).getInnerGeometry().getCoordinates();
			for (final Object o2 : vertices.iterable(scope)) {
				final Coordinate[] coord2 = ((IShape) o2).getInnerGeometry().getCoordinates();
				if (o1 != o2 && lineInter(coord1, coord2)) {
					g.addEdge(o1, o2);
				}
			}
		}
		return g;
	}

	static boolean lineInter(final Coordinate[] coord1, final Coordinate[] coord2) {
		int nb = 0;
		for (int i = 0; i < 3; i++) {
			final Coordinate c1 = coord1[i];
			for (int j = 0; j < 3; j++) {
				final Coordinate c2 = coord2[j];
				if (c1.x == c2.x && c1.y == c2.y) {
					nb++;
				}
			}
		}
		return nb == 2;
	}

	@operator (
			value = "as_distance_graph",
			content_type = IType.GEOMETRY,
			index_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE, IConcept.EDGE, IConcept.CAST })
	@doc (
			value = "creates a graph from a list of vertices (left-hand operand). An edge is created between each pair of vertices close enough (less than a distance, right-hand operand).",
			masterDoc = true,
			comment = "as_distance_graph is more efficient for a list of points than as_intersection_graph.",
			examples = @example (
					value = "list(ant) as_distance_graph 3.0",
					isExecutable = false),
			see = { "as_intersection_graph", "as_edge_graph" })
	@no_test
	public static IGraph spatialDistanceGraph(final IScope scope, final IContainer vertices, final Double distance) {
		final IGraph createdGraph = new GamaSpatialGraph(vertices, false, false, new DistanceRelation(distance), null,
				scope, vertices.getGamlType().getContentType(), Types.GEOMETRY);
		if (Types.AGENT.equals(vertices.getGamlType().getContentType())) {
			GraphFromAgentContainerSynchronizer.synchronize(scope, vertices, null, createdGraph);
		}
		return createdGraph;
	}

	@operator (
			value = "grid_cells_to_graph",
			content_type = IType.GEOMETRY,
			index_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.GRID, IConcept.CAST, IConcept.NEIGHBORS })
	@doc (
			value = "creates a graph from a list of cells (operand). An edge is created between neighbors.",
			masterDoc = true,
			comment = "",
			examples = @example (
					value = "my_cell_graph<-grid_cells_to_graph(cells_list)",
					isExecutable = false),
			see = {})
	@no_test
	public static IGraph gridCellsToGraph(final IScope scope, final IContainer vertices) {
		final IGraph graph = new GamaSpatialGraph(vertices, false, false, new GridNeighborsRelation(), null, scope,
				vertices.getGamlType().getContentType(), Types.GEOMETRY);
		for (final Object e : graph.edgeSet()) {
			graph.setEdgeWeight(e, ((IShape) e).getPerimeter());
		}
		return graph;
	}
	

	@operator (
			value = "grid_cells_to_graph",
			content_type = IType.GEOMETRY,
			index_type = IType.GEOMETRY,
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			value = "creates a graph from a list of cells (operand). An edge is created between neighbors.",
			see = { "as_intersection_graph", "as_edge_graph" })
	@no_test
	public static IGraph gridCellsToGraph(final IScope scope, final IContainer vertices,final ISpecies edgeSpecies) {
		final IType edgeType = scope.getType(edgeSpecies.getName());
		final IGraph createdGraph = new GamaSpatialGraph(vertices, false, false, new GridNeighborsRelation(),
				edgeSpecies, scope, vertices.getGamlType().getContentType(), edgeType);

		for (final Object e : createdGraph.edgeSet()) {
			createdGraph.setEdgeWeight(e, ((IShape) e).getPerimeter());
		}

		return createdGraph;
	}


	@operator (
			value = "as_distance_graph",
			content_type = IType.GEOMETRY,
			index_type = IType.GEOMETRY,
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			value = "creates a graph from a list of vertices (left-hand operand). An edge is created between each pair of vertices close enough (less than a distance, right-hand operand).",
			see = { "as_intersection_graph", "as_edge_graph" })
	@no_test
	public static IGraph spatialDistanceGraph(final IScope scope, final IContainer vertices, final Double distance,
			final ISpecies edgeSpecies) {
		final IType edgeType = scope.getType(edgeSpecies.getName());
		final IGraph createdGraph = new GamaSpatialGraph(vertices, false, false, new DistanceRelation(distance),
				edgeSpecies, scope, vertices.getGamlType().getContentType(), edgeType);

		GraphFromAgentContainerSynchronizer.synchronize(scope, vertices, edgeSpecies, createdGraph);

		return createdGraph;
	}

	@operator (
			value = "as_distance_graph",
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			value = "creates a graph from a list of vertices (left-hand operand). An edge is created between each pair of vertices close enough (less than a distance, right-hand operand).",
			see = { "as_intersection_graph", "as_edge_graph" })
	@no_test
	public static IGraph spatialDistanceGraph(final IScope scope, final IContainer vertices, final IMap params) {
		final Double distance = (Double) params.get("distance");
		final ISpecies edgeSpecies = (ISpecies) params.get("species");
		final IType edgeType = edgeSpecies == null ? Types.GEOMETRY : scope.getType(edgeSpecies.getName());
		final IGraph createdGraph = new GamaSpatialGraph(vertices, false, false, new DistanceRelation(distance),
				edgeSpecies, scope, vertices.getGamlType().getContentType(), edgeType);

		if (Types.AGENT.equals(vertices.getGamlType().getContentType())) {
			GraphFromAgentContainerSynchronizer.synchronize(scope, vertices, edgeSpecies, createdGraph);
		}
		return createdGraph;
	}

	@operator (
			value = "spatial_graph",
			index_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.GEOMETRY, IConcept.POINT })
	@doc (
			value = "allows to create a spatial graph from a container of vertices, without trying to wire them. "
					+ "The container can be empty. Emits an error if the contents of the container are not geometries, points or agents",
			see = { "graph" })
	@no_test
	public static IGraph spatial_graph(final IScope scope, final IContainer vertices) {
		return new GamaSpatialGraph(vertices, false, false, null, null, scope, vertices.getGamlType().getContentType(),
				Types.GEOMETRY);
	}

	@operator (
			value = "use_cache",
			category = { IOperatorCategory.GRAPH, IOperatorCategory.PATH },
			concept = { IConcept.GRAPH, IConcept.SHORTEST_PATH })
	@doc (
			value = "if the second operand is true, the operand graph will store in a cache all the previously computed shortest path (the cache be cleared if the graph is modified).",
			comment = "WARNING / side effect: this operator modifies the operand and does not create a new graph.",
			see = { "path_between" })
	@no_test
	public static IGraph useCacheForShortestPaths(final IGraph g, final boolean useCache) {
		return GamaGraphType.useChacheForShortestPath(g, useCache);
	}

	@operator (
			value = "directed",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "the operand graph becomes a directed graph.",
			comment = "WARNING / side effect: this operator modifies the operand and does not create a new graph.",
			see = { "undirected" })
	@no_test
	public static IGraph asDirectedGraph(final IGraph g) {
		g.incVersion();
		return GamaGraphType.asDirectedGraph(g);
	}

	@operator (
			value = "undirected",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.SHORTEST_PATH })
	@doc (
			value = "the operand graph becomes an undirected graph.",
			comment = "WARNING / side effect: this operator modifies the operand and does not create a new graph.",
			see = { "directed" })
	@no_test
	public static IGraph asUndirectedGraph(final IGraph g) {
		g.incVersion();
		return GamaGraphType.asUndirectedGraph(g);
	}

	@operator (
			value = "with_weights",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.GRAPH_WEIGHT })
	@doc (
			value = "returns the graph (left-hand operand) with weight given in the map (right-hand operand).",
			masterDoc = true,
			comment = "WARNING / side effect: this operator modifies the operand and does not create a new graph. It also re-initializes the path finder",
			usages = @usage (
					value = "if the left-hand operand is a map, the map should contains pairs such as: vertex/edge::double",
					examples = @example (
							value = "graph_from_edges (list(ant) as_map each::one_of (list(ant))) with_weights (list(ant) as_map each::each.food)",
							isExecutable = false)))
	@no_test
	public static IGraph withWeights(final IScope scope, final IGraph graph, final IMap weights) {
		graph.setWeights(weights);
		graph.incVersion();
		if (graph instanceof GamaSpatialGraph) {
			((GamaSpatialGraph) graph).reInitPathFinder();
		}
		return graph;
	}

	@operator (
			value = "with_weights",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			usages = @usage ("if the right-hand operand is a list, assigns the n elements of the list to the n first edges. "
					+ "Note that the ordering of edges may change overtime, which can create some problems..."))
	@no_test
	public static IGraph withWeights(final IScope scope, final IGraph graph, final IList weights) {
		// Simply a list of double... and, by default, for edges.However, the
		// ordering of edges may
		// change overtime, which can create a problem somewhere...
		final IList edges = graph.getEdges();
		final int n = edges.size();
		if (n != weights.size()) { return graph; }
		for (int i = 0; i < n; i++) {
			graph.setEdgeWeight(edges.get(i), Cast.asFloat(scope, weights.get(i)));
		}
		graph.incVersion();
		if (graph instanceof GamaSpatialGraph) {
			((GamaSpatialGraph) graph).reInitPathFinder();
		}
		return graph;
	}

	@operator (
			value = "with_optimizer_type",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.SHORTEST_PATH, IConcept.GRAPH_WEIGHT, IConcept.OPTIMIZATION,
					IConcept.ALGORITHM })
	@doc (
			value = "changes the shortest path computation method of the given graph",
			comment = "the right-hand operand can be \"Djikstra\", \"Bellmann\", \"Astar\", \"NBAStar\", \"NBAStarApprox\" to use the associated algorithm. "
					+ "Note that these methods are dynamic: the path is computed when needed. In contrarily, if the operand is another string, "
					+ "a static method will be used, i.e. all the shortest are previously computed.",
			examples = @example (
					value = "graphEpidemio <- graphEpidemio with_optimizer_type \"static\";",
					isExecutable = false),
			see = "set_verbose")
	@no_test
	public static IGraph setOptimizeType(final IScope scope, final IGraph graph, final String optimizerType) {
		final List<String> existingOptimizer = Arrays.asList(GamaGraph.shortestPathAlgorithm.values()).stream()
				.map(a -> a.toString()).collect(Collectors.toList());
		if (existingOptimizer.contains(optimizerType)) {
			graph.setOptimizerType(optimizerType);
		} else {
			throw GamaRuntimeException.error("The Optimizer type " + optimizerType
					+ " does not exist. Possible optimizer types: " + existingOptimizer, scope);
		}
		return graph;
	}

	@operator (
			value = "add_node",
			type = IType.GRAPH,
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE })
	@doc (
			comment = "WARNING / side effect: this operator modifies the operand and does not create a new graph",
			value = "adds a node in a graph.",
			examples = @example (
					value = "graph add_node node(0) ",
					equals = "the graph, to which node(0) has been added",

					isExecutable = false),
			see = { "add_edge", "graph" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ "g <- g add_node {10,40} ;" + " length(g.vertices) = 7")
	public static IGraph addNode(final IGraph g, final IShape node) {
		g.addVertex(node);
		return g;
	}

	@operator (
			value = "remove_node_from",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.NODE })
	@doc (
			comment = "WARNING / side effect: this operator modifies the operand and does not create a new graph. All the edges containing this node are also removed.",
			value = "removes a node from a graph.",
			examples = @example (
					value = "node(0) remove_node_from graphEpidemio",
					equals = "the graph without node(0)",
					isExecutable = false))
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ "g <- geometry({10,5}) remove_node_from g; " + " length(g.vertices) = 5 and length(g.edges) = 2")
	public static IGraph removeNodeFrom(final IShape node, final IGraph g) {
		g.removeVertex(node);

		return g;
	}

	@operator (
			value = "rewire_n",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.EDGE })
	@doc (
			comment = "WARNING / side effect: this operator modifies the operand and does not create a new graph. If there are too many edges, all the edges will be rewired.",
			value = "rewires the given count of edges.",
			examples = { @example (
					value = "graph graphEpidemio <- as_edge_graph([{1,5}::{12,45},{12,45}::{34,56}]);",
					isTestOnly = true),
					@example (
							value = "graphEpidemio rewire_n 10",
							equals = "the graph with 3 edges rewired",
							test = false) })
	public static IGraph rewireGraph(final IScope scope, final IGraph g, final Integer count) {
		GraphAlgorithmsHandmade.rewireGraphCount(scope, g, count);
		g.incVersion();
		return g;
	}

	@operator (
			value = "add_edge",
			type = IType.GRAPH,
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.EDGE })
	@doc (
			comment = "WARNING / side effect: this operator modifies the operand and does not create a new graph. If the edge already exists, the graph is unchanged",
			value = "add an edge between a source vertex and a target vertex (resp. the left and the right element of the pair operand)",
			examples = @example (
					value = "graph <- graph add_edge (source::target);",
					isExecutable = false),
			see = { "add_node", "graph" })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ "g <- g add_edge ({40,60}::{50,50}); " + " length(g.edges) = 6")
	public static IGraph addEdge(final IGraph g, final GamaPair nodes) {
		g.addEdge(nodes.first(), nodes.last());
		g.incVersion();
		return g;
	}

	@operator (
			value = "path_between",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH, IOperatorCategory.PATH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "The shortest path between a list of two objects in a graph",
			masterDoc = true,
			examples = { @example (
					value = "path_between (my_graph, ag1, ag2)",
					equals = "A path between ag1 and ag2",
					isExecutable = false) })
	@test ("graph<geometry, geometry> g <- directed(as_edge_graph([edge({10,5}, {20,3}), edge({10,5}, {30,30}),edge({30,30}, {80,35}),edge({80,35}, {40,60}),edge({80,35}, {10,5}), node ({50,50})]));\r\n"
			+ " length((path_between (g, {10,5}, {50,50}))) = 1 ")
	public static IPath path_between(final IScope scope, final IGraph graph, final IShape source, final IShape target)
			throws GamaRuntimeException {
		if (graph instanceof GamaSpatialGraph) {
			return Cast.asTopology(scope, graph).pathBetween(scope, source, target);
		}
		return graph.computeShortestPathBetween(scope, source, target);
	}

	@operator (
			value = "paths_between",
			type = IType.LIST,
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH, IOperatorCategory.PATH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "The K shortest paths between a list of two objects in a graph",
			examples = { @example (
					value = "paths_between(my_graph, ag1:: ag2, 2)",
					equals = "the 2 shortest paths (ordered by length) between ag1 and ag2",
					isExecutable = false) })
	@test (" graph<geometry, geometry> g <- directed(as_edge_graph([\r\n"
			+ "								edge({10,5}, {20,3}), \r\n"
			+ "								edge({10,5}, {30,30}),\r\n"
			+ "								edge({30,30}, {80,35}),\r\n"
			+ "								edge({80,35}, {40,60}),\r\n"
			+ "								edge({80,35}, {10,5}), \r\n"
			+ "								edge({10,5}, {80,35}),\r\n"
			+ "								edge({30,30}, {85,25}),\r\n"
			+ "								edge({85,35}, {80,35}),\r\n"
			+ "								node ({50,50})\r\n" + "	])); "
			+ " length((paths_between(g, {10,5}:: {80,35}, 2))) = 2")
	public static IList<GamaSpatialPath> Kpaths_between(final IScope scope, final GamaGraph graph,
			final GamaPair sourTarg, final int k) throws GamaRuntimeException {
		return Cast.asTopology(scope, graph).KpathsBetween(scope, (IShape) sourTarg.key, (IShape) sourTarg.value, k);
	}

	@operator (
			value = "max_flow_between",
			type = IType.LIST,
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH, IOperatorCategory.PATH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "The max flow (map<edge,flow> in a graph between the source and the sink using Edmonds-Karp algorithm",
			examples = { @example (
					value = "max_flow_between(my_graph, vertice1, vertice2)",
					isExecutable = false) })
	@no_test
	public static IMap<Object, Double> maxFlowBetween(final IScope scope, final GamaGraph graph, final Object source,
			final Object sink) throws GamaRuntimeException {
		final EdmondsKarpMFImpl ek = new EdmondsKarpMFImpl(graph);
		final MaximumFlow<IShape> mf = ek.getMaximumFlow(source, sink);
		final IMap<Object, Double> result = GamaMapFactory.create();
		result.putAll(mf.getFlow());
		return result;
	}

	@operator (
			value = "as_path",
			type = IType.PATH,
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH, IOperatorCategory.PATH },
			concept = { IConcept.GRAPH, IConcept.CAST })
	@doc (
			value = "create a graph path from the list of shape",
			examples = { @example (
					value = "[road1,road2,road3] as_path my_graph",
					equals = "a path road1->road2->road3 of my_graph",
					isExecutable = false) })
	@no_test
	public static IPath as_path(final IScope scope, final IList<IShape> edgesNodes, final GamaGraph graph)
			throws GamaRuntimeException {
		final IPath path = GamaPathType.staticCast(scope, edgesNodes, null, false);
		path.setGraph(graph);
		return path;
	}

	/**
	 * the comment for all the operators
	 */
	static private final String comment = "Available formats: "
			+ "\"pajek\": Pajek (Slovene word for Spider) is a program, for Windows, for analysis and visualization of large networks. See: http://pajek.imfm.si/doku.php?id=pajek for more details."
			+
			// "\"dgs_old\", \"dgs\": DGS is a file format allowing to store
			// graphs and dynamic graphs in a textual human readable way, yet
			// with a small size allowing to store large graphs. Graph dynamics
			// is defined using events like adding, deleting or changing a node
			// or edge. With DGS, graphs will therefore be seen as stream of
			// such events. [From GraphStream related page:
			// http://graphstream-project.org/]"+
			"\"lgl\": LGL is a compendium of applications for making the visualization of large networks and trees tractable. See: http://lgl.sourceforge.net/ for more details."
			+ "\"dot\": DOT is a plain text graph description language. It is a simple way of describing graphs that both humans and computer programs can use. See: http://en.wikipedia.org/wiki/DOT_language for more details."
			+ "\"edge\": This format is a simple text file with numeric vertex ids defining the edges."
			+ "\"gexf\": GEXF (Graph Exchange XML Format) is a language for describing complex networks structures, their associated data and dynamics. Started in 2007 at Gephi project by different actors, deeply involved in graph exchange issues, the gexf specifications are mature enough to claim being both extensible and open, and suitable for real specific applications. See: http://gexf.net/format/ for more details."
			+ "\"graphml\": GraphML is a comprehensive and easy-to-use file format for graphs based on XML. See: http://graphml.graphdrawing.org/ for more details."
			+ "\"tlp\" or \"tulip\": TLP is the Tulip software graph format. See: http://tulip.labri.fr/TulipDrupal/?q=tlp-file-format for more details. "
			+ "\"ncol\": This format is used by the Large Graph Layout progra. It is simply a symbolic weighted edge list. It is a simple text file with one edge per line. An edge is defined by two symbolic vertex names separated by whitespace. (The symbolic vertex names themselves cannot contain whitespace.) They might followed by an optional number, this will be the weight of the edge. See: http://bioinformatics.icmb.utexas.edu/lgl for more details."
			+ "The map operand should includes following elements:";

	// version depuis un filename avec edge et specy et indication si spatial ou
	// pas

	@operator (
			value = "load_graph_from_file",
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH, IConcept.FILE })
	@doc (
			value = "returns a graph loaded from a given file encoded into a given format. The last boolean parameter indicates whether the resulting graph will be considered as spatial or not by GAMA",
			masterDoc = true,
			comment = comment,
			usages = { @usage (
					value = "\"format\": the format of the file"),
					@usage (
							value = "\"filename\": the filename of the file containing the network"),
					@usage (
							value = "\"edges_species\": the species of edges"),
					@usage (
							value = "\"vertices_specy\": the species of vertices") },
			examples = { @example (
					value = "graph<myVertexSpecy,myEdgeSpecy> myGraph <- load_graph_from_file(",
					isExecutable = false),
					@example (
							value = "			\"pajek\",",
							isExecutable = false),
					@example (
							value = "			\"./example_of_Pajek_file\",",
							isExecutable = false),
					@example (
							value = "			myVertexSpecy,",
							isExecutable = false),
					@example (
							value = "			myEdgeSpecy , true);",
							isExecutable = false) })
	@no_test
	public static IGraph primLoadGraphFromFile(final IScope scope, final String format, final String filename,
			final ISpecies vertex_specy, final ISpecies edge_specy, final Boolean spatial) throws GamaRuntimeException {

		return GraphLoader.loadGraph(scope, filename, vertex_specy, edge_specy, null, null, format, spatial);

	}

	@operator (
			value = "load_graph_from_file",
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			value = "returns a graph loaded from a given file encoded into a given format. This graph will not be spatial. Nodes and edges will be of the given sprecies.",
			usages = { @usage (
					value = "\"format\": the format of the file"),
					@usage (
							value = "\"filename\": the filename of the file containing the network"),
					@usage (
							value = "\"edges_species\": the species of edges"),
					@usage (
							value = "\"vertices_specy\": the species of vertices") },
			examples = { @example (
					value = "graph<myVertexSpecy,myEdgeSpecy> myGraph <- load_graph_from_file(",
					isExecutable = false),
					@example (
							value = "			\"pajek\",",
							isExecutable = false),
					@example (
							value = "			\"./example_of_Pajek_file\",",
							isExecutable = false),
					@example (
							value = "			myVertexSpecy,",
							isExecutable = false),
					@example (
							value = "			myEdgeSpecy);",
							isExecutable = false) })
	@no_test
	public static IGraph primLoadGraphFromFile(final IScope scope, final String format, final String filename,
			final ISpecies vertex_specy, final ISpecies edge_specy) throws GamaRuntimeException {

		return primLoadGraphFromFile(scope, format, filename, vertex_specy, edge_specy, false);

	}

	@operator (
			value = "load_graph_from_file",
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			value = "loads a graph from a file (with given  node and edge species).",
			usages = @usage (
					value = "\"filename\": the filename of the file containing the network, \"edges_species\": the species of edges, \"vertices_specy\": the species of vertices",
					examples = { @example (
							value = "graph<myVertexSpecy,myEdgeSpecy> myGraph <- load_graph_from_file(",
							isExecutable = false),
							@example (
									value = "			\"pajek\",",
									isExecutable = false),
							@example (
									value = "			\"./example_of_Pajek_file\",",
									isExecutable = false),
							@example (
									value = "			myVertexSpecy,",
									isExecutable = false),
							@example (
									value = "			myEdgeSpecy );",
									isExecutable = false) }))
	@no_test
	public static IGraph primLoadGraphFromFile(final IScope scope, final String filename, final ISpecies vertex_specy,
			final ISpecies edge_specy) throws GamaRuntimeException {

		return primLoadGraphFromFile(scope, null, filename, vertex_specy, edge_specy);

	}

	// version depuis un file avec edge et specy

	@operator (
			value = "load_graph_from_file",
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			usages = @usage (
					value = "\"format\": the format of the file, \"file\": the file containing the network, \"edges_species\": the species of edges, \"vertices_specy\": the species of vertices",
					examples = { @example (
							value = "graph<myVertexSpecy,myEdgeSpecy> myGraph <- load_graph_from_file(",
							isExecutable = false),
							@example (
									value = "			\"pajek\",",
									isExecutable = false),
							@example (
									value = "			\"example_of_Pajek_file\",",
									isExecutable = false),
							@example (
									value = "			myVertexSpecy,",
									isExecutable = false),
							@example (
									value = "			myEdgeSpecy );",
									isExecutable = false) }))
	@no_test
	public static IGraph primLoadGraphFromFile(final IScope scope, final String format, final GamaFile gamaFile,
			final ISpecies vertex_specy, final ISpecies edge_specy) throws GamaRuntimeException {
		return primLoadGraphFromFile(scope, gamaFile.getPath(scope), vertex_specy, edge_specy);

	}

	// version depuis un filename sans edge et sans specy

	@operator (
			value = "load_graph_from_file",
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			usages = { @usage (
					value = "\"format\": the format of the file, \"filename\": the filename of the file containing the network",
					examples = { @example (
							value = "graph<myVertexSpecy,myEdgeSpecy> myGraph <- load_graph_from_file(",
							isExecutable = false),
							@example (
									value = "			\"pajek\",",
									isExecutable = false),
							@example (
									value = "			\"example_of_Pajek_file\");",
									isExecutable = false) }) })
	@no_test
	public static IGraph primLoadGraphFromFile(final IScope scope, final String format, final String filename)
			throws GamaRuntimeException {
		// AD 29/09/13: Changed the previous code that was triggering an
		// overflow.
		return primLoadGraphFromFile(scope, format, filename, null, null);
	}

	// version depuis un file avec edge et specy

	@operator (
			value = "load_graph_from_file",
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			usages = { @usage (
					value = "\"format\": the format of the file, \"file\": the file containing the network",
					examples = { @example (
							value = "graph<myVertexSpecy,myEdgeSpecy> myGraph <- load_graph_from_file(",
							isExecutable = false),
							@example (
									value = "			\"pajek\",",
									isExecutable = false),
							@example (
									value = "			\"example_of_Pajek_file\");",
									isExecutable = false) }) })
	@no_test
	public static IGraph primLoadGraphFromFile(final IScope scope, final String format, final GamaFile gamaFile)
			throws GamaRuntimeException {
		// AD 29/09/13 : Simply called the previous method with the path of the
		// file. Not efficient, but should work.
		return primLoadGraphFromFile(scope, format, gamaFile.getPath(scope));
		// throw GamaRuntimeException.error("not implemented: loading from gama
		// file");

	}

	@operator (
			value = "load_graph_from_file",
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			usages = { @usage (
					value = "\"file\": the file containing the network",
					examples = { @example (
							value = "graph<myVertexSpecy,myEdgeSpecy> myGraph <- load_graph_from_file(",
							isExecutable = false),
							@example (
									value = "			\"pajek\",",
									isExecutable = false),
							@example (
									value = "			\"example_of_Pajek_file\");",
									isExecutable = false) }) })
	@no_test
	public static IGraph primLoadGraphFromFile(final IScope scope, final String filename) throws GamaRuntimeException {
		return primLoadGraphFromFile(scope, null, filename);
	}

	@operator (
			value = "load_shortest_paths",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH, IOperatorCategory.PATH },
			concept = { IConcept.GRAPH, IConcept.SHORTEST_PATH })
	@doc (
			value = "put in the graph cache the computed shortest paths contained in the matrix (rows: source, columns: target)",
			examples = { @example (
					value = "load_shortest_paths(shortest_paths_matrix)",
					equals = "return my_graph with all the shortest paths computed",
					isExecutable = false) })
	@no_test
	public static IGraph primLoadGraphFromFile(final IScope scope, final GamaGraph graph, final GamaMatrix matrix)
			throws GamaRuntimeException {
		if (graph == null) {
			throw GamaRuntimeException.error("In the load_shortest_paths operator, the graph should not be null!",
					scope);
		}
		// final int n = graph.vertexSet().size();
		graph.loadShortestPaths(scope, matrix);
		return graph;
		// throw GamaRuntimeException.error("not implemented: loading from gama
		// file");

	}

	@operator (
			value = "all_pairs_shortest_path",
			type = IType.MATRIX,
			content_type = IType.INT,
			category = { IOperatorCategory.GRAPH, IOperatorCategory.PATH },
			concept = { IConcept.GRAPH, IConcept.SHORTEST_PATH })
	@doc (
			value = "returns the successor matrix of shortest paths between all node pairs (rows: source, columns: target): a cell (i,j) will thus contains the next node in the shortest path between i and j.",
			examples = { @example (
					value = "all_pairs_shortest_paths(my_graph)",
					equals = "shortest_paths_matrix will contain all pairs of shortest paths",
					isExecutable = false) })
	@no_test
	public static GamaIntMatrix primAllPairShortestPaths(final IScope scope, final GamaGraph graph)
			throws GamaRuntimeException {
		if (graph == null) {
			throw GamaRuntimeException.error("In the all_pairs_shortest_paths operator, the graph should not be null!",
					scope);
		}
		return graph.saveShortestPaths(scope);
	}

	@operator (
			value = "layout_force",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,

			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "layouts a GAMA graph using Force model (in a given spatial  bound and given coeff_force, cooling_rate, max_iteration, and equilibirum criterion parameters). ",
			masterDoc = true,
			special_cases = "usage: layoutForce(graph, bounds, coeff_force, cooling_rate, max_iteration, equilibirum criterion). graph is the graph to which "
					+ "applied the layout;  bounds is the shape (geometry) in which the graph should be located; coeff_force is the coefficien use to compute the force, typical value is 0.4; "
					+ "cooling rate is the decreasing coefficient of the temperature, typical value is 0.01;  max_iteration is the maximal number of iterations; equilibirum criterion is the maximal"
					+ "distance of displacement for a vertice to be considered as in equilibrium")
	@no_test
	public static IGraph layoutForce(final IScope scope, final GamaGraph graph, final IShape bounds,
			final double coeffForce, final double coolingRate, final int maxIteration, final double criterion) {
		final LayoutForceDirected sim =
				new LayoutForceDirected(graph, bounds, coeffForce, coolingRate, maxIteration, true, criterion);
		sim.startSimulation(scope);
		return graph;
	}

	@operator (
			value = "layout_force",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,

			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "layouts a GAMA graph using Force model (in a given spatial  bound and given coeff_force, cooling_rate, and max_iteration parameters).", 
			special_cases = "usage: layoutForce(graph, bounds, coeff_force, cooling_rate, max_iteration). graph is the graph to which "
					+ "applied the layout;  bounds is the shape (geometry) in which the graph should be located; coeff_force is the coefficient used to compute the force, typical value is 0.4; "
					+ "cooling rate is the decreasing coefficient of the temperature, typical value is 0.01;  max_iteration is the maximal number of iterations"
					+ "distance of displacement for a vertice to be considered as in equilibrium")
	@no_test
	public static IGraph layoutForce(final IScope scope, final GamaGraph graph, final IShape bounds,
			final double coeffForce, final double coolingRate, final int maxIteration) {
		final LayoutForceDirected sim =
				new LayoutForceDirected(graph, bounds, coeffForce, coolingRate, maxIteration, false, 0);
		sim.startSimulation(scope);
		return graph;
	}

	@operator (
			value = "layout_circle",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "layouts a Gama graph on a circle with equidistance between nodes. For now there is no optimization on node ordering.",
			special_cases = "Usage: layoutCircle(graph, bound, shuffle) => graph : the graph to layout, bound : the geometry to display the graph within, "
					+ "shuffle : if true shuffle the nodes, then render same ordering",
			examples = { @example (
					value = "layout_circle(graph, world.shape, false);",
					isExecutable = false) })
	@no_test
	public static IGraph layoutCircle(final IScope scope, final GamaGraph graph, final IShape bounds,
			final boolean shuffle) {
		final LayoutCircle layouter = new LayoutCircle(graph, bounds);
		layouter.applyLayout(scope, shuffle);
		return graph;
	}

	@operator (
			value = "layout_grid",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH },
			concept = { IConcept.GRAPH })
	@doc (
			value = "layouts a Gama graph based on a grid latice. usage: layoutForce(graph, bounds, coeff_nb_cells). graph is the graph to which \"\r\n"
					+ "					+ \"applied the layout;  bounds is the shape (geometry) in which the graph should be located; coeff_nb_cells"
					+ "the coefficient for the number of cells to locate the vertices (nb of places = coeff_nb_cells * nb of vertices). ",
			examples = { @example (
					value = "layout_grid(graph, world.shape);",
					isExecutable = false) })
	@no_test
	public static IGraph layoutGrid(final IScope scope, final GamaGraph graph, final IShape bounds,
			final double coeffSq) {
		final LayoutGrid layouter = new LayoutGrid(graph, bounds, Math.max(1.0, coeffSq));
		layouter.applyLayout(scope);
		return graph;
	}

	@operator (
			value = "adjacency",
			category = { IOperatorCategory.GRAPH },
			concept = {})
	@doc (
			value = "adjacency matrix of the given graph.")
	@no_test
	public static GamaFloatMatrix adjacencyMatrix(final IScope scope, final GamaGraph graph) {
		return graph.toMatrix(scope);
	}

	@operator (
			value = "strahler",
			content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH, IConcept.EDGE })
	@doc (
			value = "retur for each edge, its strahler number")
	@no_test
	public static IMap strahlerNumber(final IScope scope, final GamaGraph graph) {
		final IMap<Object, Integer> results = GamaMapFactory.create(Types.NO_TYPE, Types.INT);
		if (graph == null || graph.isEmpty(scope)) { return results; }
		if (!graph.getConnected() || graph.hasCycle()) {
			throw GamaRuntimeException
					.error("Strahler number can only be computed for Tree (connected graph with no cycle)!", scope);
		}

		List currentEdges = of(graph.getEdges()).filter(a -> graph.outDegreeOf(graph.getEdgeTarget(a)) == 0).toList();
		while (!currentEdges.isEmpty()) {
			final List newList = new ArrayList<>();
			for (final Object e : currentEdges) {
				final List previousEdges = inEdgesOf(scope, graph, graph.getEdgeSource(e));
				final List nextEdges = outEdgesOf(scope, graph, graph.getEdgeTarget(e));
				if (nextEdges.isEmpty()) {
					results.put(e, 1);
					newList.addAll(previousEdges);
				} else {
					final boolean notCompleted = nextEdges.stream().anyMatch(a -> !results.containsKey(a));
					if (notCompleted) {
						newList.add(e);
					} else {
						final List<Integer> vals = of(nextEdges).map(a -> results.get(a)).toList();
						final Integer maxVal = Collections.max(vals);
						final int nbIt = Collections.frequency(vals, maxVal);
						if (nbIt > 1) {
							results.put(e, maxVal + 1);
						} else {
							results.put(e, maxVal);
						}
						newList.addAll(previousEdges);
					}
				}
			}
			currentEdges = newList;
		}
		return results;
	}

	@operator (
		value = "edge",
		type = IType.NONE, // ITypeProvider.TYPE_AT_INDEX + 1, // FIXME This is false
		category = { IOperatorCategory.GRAPH })
	 @doc (
		value = "Allows to create a wrapper (of type unknown) that wraps two objects and indicates they should be considered as the source and the target of a new edge of a graph. The third (omissible) parameter indicates which weight this edge should have in the graph",
		masterDoc = true,
		comment = "Useful only in graph-related operations (addition, removal of edges, creation of graphs)")
	@no_test
	public static Object edge(final Object source, final Object target, final Double weight) {
		return edge(source, target, null, weight);
	}

	@operator (
			value = "edge",
			type = IType.NONE, // ITypeProvider.TYPE_AT_INDEX + 1, // FIXME This is
			// false
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps two objects and indicates they should be considered as the source and the target of a new edge of a graph. The third parameter indicates which weight this edge should have in the graph")
	@no_test
	public static Object edge(final Object source, final Object target, final Integer weight) {
		return edge(source, target, null, weight);
	}

	@operator (
			value = "edge",
			type = ITypeProvider.TYPE_AT_INDEX + 2,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps a pair of objects and a third and indicates  they should respectively be considered as the source (key of the pair), the target (value of the pair) and the actual object representing an edge of a graph. The third parameter indicates which weight this edge should have in the graph")
	@no_test
	public static Object edge(final GamaPair pair, final Object object, final Double weight) {
		return edge(pair.key, pair.value, object, weight);
	}

	@operator (
			value = "edge",
			type = ITypeProvider.TYPE_AT_INDEX + 2,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps a pair of objects and a third and indicates  they should respectively be considered as the source (key of the pair), the target (value of the pair) and the actual object representing an edge of a graph. The third parameter indicates which weight this edge should have in the graph")
	@no_test
	public static Object edge(final GamaPair pair, final Object object, final Integer weight) {
		return edge(pair.key, pair.value, object, weight);
	}

	@operator (
			value = "edge",
			type = IType.NONE, // ITypeProvider.TYPE_AT_INDEX + 1, // FIXME this is
			// false
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps two objects and indicates they should be considered as the source and the target of a new edge of a graph ")
	@no_test
	public static Object edge(final Object source, final Object target) {
		return edge(source, target, null, (Double) null);
	}

	@operator (
			value = "edge",
			type = ITypeProvider.TYPE_AT_INDEX + 3,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps three objects and indicates they should respectively be considered as the source, the target and the actual object representing an edge of a graph")
	@no_test
	public static Object edge(final Object source, final Object target, final Object object) {
		return edge(source, target, object, (Double) null);
	}

	@operator (
			value = "edge",
			type = ITypeProvider.TYPE_AT_INDEX + 3,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps two objects and indicates they should be considered as the source and the target of a new edge of a graph. The fourth parameter indicates which weight this edge should have in the graph")
	@no_test
	public static Object edge(final Object source, final Object target, final Object object, final Double weight) {
		return new EdgeToAdd(source, target, object, weight);
	}

	@operator (
			value = "edge",
			type = ITypeProvider.TYPE_AT_INDEX + 3,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps two objects and indicates they should be considered as the source and the target of a new edge of a graph. The fourth parameter indicates which weight this edge should have in the graph")
	@no_test
	public static Object edge(final Object source, final Object target, final Object object, final Integer weight) {
		return new EdgeToAdd(source, target, object, weight);
	}

	@operator (
			value = "edge",
			type = ITypeProvider.TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps an actual object and indicates it should be considered as an edge of a graph. The second parameter indicates which weight this edge should have in the graph")
	@no_test
	public static Object edge(final Object edgeObject, final Double weight) {
		return edge(null, null, edgeObject, weight);
	}

	@operator (
			value = "edge",
			type = ITypeProvider.TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps an actual object and indicates it should be considered as an edge of a graph. The second parameter indicates which weight this edge should have in the graph")
	@no_test
	public static Object edge(final Object edgeObject, final Integer weight) {
		return edge(null, null, edgeObject, weight);
	}

	@operator (
			value = "edge",
			type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps a pair of objects and indicates they should be considered as the source and target of an edge. The second parameter indicates which weight this edge should have in the graph")
	@no_test
	public static Object edge(final GamaPair pair, final Double weight) {
		return edge(pair.key, pair.value, null, weight);
	}

	@operator (
			value = "edge",
			type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps a pair of objects and indicates they should be considered as the source and target of an edge. The second parameter indicates which weight this edge should have in the graph")
	@no_test
	public static Object edge(final GamaPair pair, final Integer weight) {
		return edge(pair.key, pair.value, null, weight);
	}

	@operator (
			value = "edge",
			type = ITypeProvider.TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps an actual object and indicates it should be considered as an edge of a graph")
	@no_test
	public static Object edge(final Object object) {
		return edge(null, null, object, (Double) null);
	}

	@operator (
			value = "edge",
			type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps a pair of objects and indicates they should be considered as the source and target of an edge of a graph")
	@no_test
	public static Object edge(final GamaPair pair) {
		return edge(pair.key, pair.value, null, (Double) null);
	}

	@operator (
			value = "node",
			type = ITypeProvider.TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps an actual object and indicates it should be considered as a node of a graph. The second (optional) parameter indicates which weight the node should have in the graph",
			masterDoc = true,
			comment = "Useful only in graph-related operations (addition, removal of nodes, creation of graphs)")
	@no_test
	public static Object node(final Object object, final Double weight) {
		return new NodeToAdd(object, weight);
	}

	@operator (
			value = "node",
			type = ITypeProvider.TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type unknown) that wraps an actual object and indicates it should be considered as a node of a graph")
	@no_test
	public static Object node(final Object nodeObject) {
		return node(nodeObject, null);
	}

	@operator (
			value = "nodes",
			type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type list) that wraps a list of objects and indicates they should be considered as nodes of a graph")
	@no_test
	public static IContainer nodes(final IScope scope, final IContainer nodes) {
		return NodesToAdd.from(scope, nodes);
	}

	@operator (
			value = "edges",
			type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.GRAPH })
	@doc (
			value = "Allows to create a wrapper (of type list) that wraps a list of objects and indicates they should be considered as edges of a graph")
	@no_test
	public static IContainer edges(final IScope scope, final IContainer nodes) {
		return EdgesToAdd.from(scope, nodes);
	}

	// TODO "complete" (pour créer un graphe complet)

	// vertices_to_graph [vertices] with_weights (vertices collect: each.val) ->
	// renvoie un graphe
	// construit à partir des vertex (edges g�n�r�s soit sous la forme d'une
	// paire vertex::vertex,
	// soit sous la forme d'un lien géométrique)
	// vertices_to_graph [a1, a2, a3] with_weights ([1, 4, 8]) -> même chose
	// edges_to_graph [edges] with_weights (edges collect: each.length) ->
	// renvoie un graphe
	// construit à partir des edges (vertex g�n�r�s soit sous la forme d'une
	// paire edge::edge, soit
	// sous la forme d'un point pour les géométries)
	// edges_to_graph [a1::a2, a2::a3] with_weights ([3.0, 1.3]) -> même chose
	// add item: v1 to:g weight: 1 -> ajout d'un vertex
	// add item: v1::v2 to:g weight:1 -> ajout d'un edge g�n�r� (et des vertex
	// correspondants si
	// nécessaire)
	// add item: (v1::v2)::e to: g weight: 1 -> edge (ajout d'un edge explicite
	// et des vertex
	// correspondants si nécessaire)
	// remove item: v1::v2 from: g -> remove edge
	// remove item: o from: g -> remove edge / vertex
	// put item: e2 at: v1::v2 in: g -> replace/add an edge (on peut aussi faire
	// la même chose pour
	// remplacer un vertex)

	// TODO Transformer peu à peu toutes les primitives (GeometricFunctions,
	// GeometricSkill, etc.)
	// en opérateurs (as_graph, as_network, as_triangle_graph, as_complete_graph
	// -- En créant les
	// liens dynamiques correspondants --, as_weighted_graph ...).

	// TODO Ajouter les opérateurs d'union, d'intersection, d'égalité, de
	// différence

	// TODO Ajouter des générateurs spécifiques a partir de GraphGenerator (pb:
	// quelles classes pour
	// les vertices/edges ??

}
