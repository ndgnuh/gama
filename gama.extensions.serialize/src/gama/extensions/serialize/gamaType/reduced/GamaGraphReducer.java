/*********************************************************************************************
 *
 * 'GamaGraphReducer.java, in plugin ummisco.gama.serialize, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.extensions.serialize.gamaType.reduced;

import gama.extensions.serialize.gamaType.reference.ReferenceGraph;
import gama.common.interfaces.IReference;
import gama.kernel.simulation.SimulationAgent;
import gama.metamodel.topology.graph.GamaSpatialGraph;
import gama.runtime.scope.IScope;
import gama.util.graph.GamaGraph;
import gama.util.list.GamaListFactory;
import gama.util.list.IList;
import gama.util.map.GamaMapFactory;
import gama.util.map.IMap;
import gaml.types.GamaGraphType;
import gaml.types.IType;
import gaml.types.Types;

@SuppressWarnings ({ "rawtypes" })
public class GamaGraphReducer {
	private final IType nodeTypeGraphReducer;
	private final IType edgeTypeGraphReducer;

	// private GamaMap valuesGraphReducer;
	private IList edgesGraphReducer;
	private IMap edgesWeightsGraphReducer;
	private final boolean spatial;
	private final boolean directed;

	@SuppressWarnings ("unchecked")
	public GamaGraphReducer(final IScope scope, final GamaGraph<?, ?> g) {
		spatial = g instanceof GamaSpatialGraph;
		directed = g.isDirected();

		nodeTypeGraphReducer = g.getGamlType().getKeyType();
		edgeTypeGraphReducer = g.getGamlType().getContentType();

		// Map of keys = pair(source,target), values = edge
		// valuesGraphReducer = g.mapValue(scope, nodeTypeGraphReducer, edgeTypeGraphReducer, false);
		edgesGraphReducer = GamaListFactory.create(scope, edgeTypeGraphReducer, g.edgeSet());

		// edgesWeightsGraphReducer = new GamaMap<>(valuesGraphReducer.capacity(), edgeTypeGraphReducer, new
		// GamaPairType());
		edgesWeightsGraphReducer = GamaMapFactory.create(edgeTypeGraphReducer, Types.PAIR, edgesGraphReducer.size());

		// for (final Object edge : valuesGraphReducer.values()) {
		for (final Object edge : edgesGraphReducer) {
			// edgesWeightsGraphReducer.put(k.getKey(), new EdgeReducer(k.getValue(), g.getWeightOf(k.getValue())));
			edgesWeightsGraphReducer.put(edge, g.getWeightOf(edge));
		}

	}

	// public GamaMap getValuesGraphReducer() {return valuesGraphReducer; }
	public IList getEdgesGraphReducer() {
		return edgesGraphReducer;
	}

	public IMap getWeightsGraphReducer() {
		return edgesWeightsGraphReducer;
	}

	// public void setValuesGraphReducer(GamaMap m) { valuesGraphReducer = m; }
	public void setEdgesGraphReducer(final IList m) {
		edgesGraphReducer = m;
	}

	public void setEdgesWeightsGraphReducer(final IMap<Object, Object> w) {
		edgesWeightsGraphReducer = w;
	}

	public GamaGraph constructObject(final IScope scope) {
		// GamaGraph graph = (GamaGraph) GamaGraphType.from(scope, valuesGraphReducer, spatial);
		GamaGraph graph;
		// if(IReference.isReference(valuesGraphReducer) || IReference.isReference(edgesWeightsGraphReducer)) {
		if (IReference.isReference(edgesGraphReducer) || IReference.isReference(edgesWeightsGraphReducer)) {
			graph = new ReferenceGraph(this);
		} else {
			// graph = (GamaGraph) GamaGraphType.from(scope, valuesGraphReducer.getValues(),
			graph = (GamaGraph) GamaGraphType.from(scope, edgesGraphReducer, true, directed, spatial,
					nodeTypeGraphReducer, edgeTypeGraphReducer);

			graph.setWeights(edgesWeightsGraphReducer);
			// for (final Object el : edgesWeightsGraphReducer.entrySet()) {
			// Map.Entry entry = (Map.Entry) el;
			// graph.setEdgeWeight(e, weight);
			// }
		}
		return graph;
	}

	@SuppressWarnings ("unchecked")
	public void unreferenceReducer(final SimulationAgent sim) {
		// valuesGraphReducer = (GamaMap)IReference.getObjectWithoutReference(valuesGraphReducer,sim);
		edgesGraphReducer = (IList) IReference.getObjectWithoutReference(edgesGraphReducer, sim);
		edgesWeightsGraphReducer = (IMap) IReference.getObjectWithoutReference(edgesWeightsGraphReducer, sim);
	}
}

/*
 * class EdgeReducer { private Object edge; private double weight;
 *
 * public EdgeReducer(Object _o, double _w) { edge = _o; weight = _w; } }
 */
