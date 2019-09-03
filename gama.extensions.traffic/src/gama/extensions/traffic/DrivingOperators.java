/*********************************************************************************************
 * 
 * 
 * 'DrivingOperators.java', in plugin 'gama.extensions.traffic', is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 * 
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 * 
 * 
 **********************************************************************************************/
package gama.extensions.traffic;

import gama.processor.annotations.IConcept;
import gama.processor.annotations.ITypeProvider;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.example;
import gama.processor.annotations.GamlAnnotations.no_test;
import gama.processor.annotations.GamlAnnotations.operator;
import gama.common.interfaces.IAgent;
import gama.common.interfaces.IContainer;
import gama.metamodel.shape.IShape;
import gama.metamodel.topology.graph.GamaSpatialGraph;
import gama.runtime.scope.IScope;
import gama.util.graph.IGraph;
import gama.util.list.GamaListFactory;
import gama.util.list.IList;
import gaml.types.Types;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DrivingOperators {

	@operator(value = "as_driving_graph", content_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 2, index_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1, concept = {
			IConcept.GRAPH, IConcept.TRANSPORT })
	@doc(value = "creates a graph from the list/map of edges given as operand and connect the node to the edge", examples = {
			@example(value = "as_driving_graph(road,node)  --:  build a graph while using the road agents as edges and the node agents as nodes", isExecutable = false) }, see = {
					"as_intersection_graph", "as_distance_graph", "as_edge_graph" })
	@no_test
	public static IGraph spatialDrivingFromEdges(final IScope scope, final IContainer edges, final IContainer nodes) {
		final IGraph graph = new GamaSpatialGraph(edges, nodes, scope);
		for (final Object edge : edges.iterable(scope)) {
			if (edge instanceof IShape) {
				final IAgent ag = ((IShape) edge).getAgent();
				if (ag.hasAttribute(RoadSkill.LANES) && ag.hasAttribute(RoadSkill.AGENTS_ON)) {
					final int lanes = (Integer) ag.getAttribute(RoadSkill.LANES);
					if (lanes > 0) {
						final IList agentsOn = (IList) ag.getAttribute(RoadSkill.AGENTS_ON);
						for (int i = 0; i < lanes; i++) {
							final int nbSeg = ag.getInnerGeometry().getNumPoints() - 1;
							final IList lisSg = GamaListFactory.create(Types.NO_TYPE);
							for (int j = 0; j < nbSeg; j++) {
								lisSg.add(GamaListFactory.create(Types.NO_TYPE));
							}
							agentsOn.add(lisSg);
						}
					}
					ag.setAttribute(RoadSkill.AGENTS, GamaListFactory.create(Types.NO_TYPE));
				}

			}
		}
		return graph;
	}

}
