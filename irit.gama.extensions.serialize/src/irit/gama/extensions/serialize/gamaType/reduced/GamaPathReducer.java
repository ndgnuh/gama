package irit.gama.extensions.serialize.gamaType.reduced;

import irit.gama.extensions.serialize.gamaType.reference.ReferencePath;
import msi.gama.common.interfaces.IReference;
import msi.gama.kernel.simulation.SimulationAgent;
import msi.gama.runtime.scope.IScope;
import msi.gama.util.graph.IGraph;
import msi.gama.util.list.IList;
import msi.gama.util.path.GamaPath;
import msi.gama.util.path.PathFactory;

public class GamaPathReducer {

	IGraph<Object, Object> g;
	Object start;
	Object target;
	IList<Object> edges;

	public GamaPathReducer(final GamaPath p) {
		g = p.getGraph();
		start = p.getStartVertex();
		target = p.getEndVertex();
		edges = p.getEdgeList();
	}

	@SuppressWarnings ("unchecked")
	public void unreferenceReducer(final SimulationAgent sim) {
		g = (IGraph) IReference.getObjectWithoutReference(g, sim);
		start = IReference.getObjectWithoutReference(start, sim);
		target = IReference.getObjectWithoutReference(target, sim);
		edges = (IList) IReference.getObjectWithoutReference(edges, sim);
	}

	public GamaPath constructObject(final IScope scope) {

		GamaPath path = null;
		if (IReference.isReference(g) || IReference.isReference(start) || IReference.isReference(target)
				|| IReference.isReference(edges)) {
			path = new ReferencePath(this);
		} else {
			path = PathFactory.create(g, start, target, edges);
		}
		return path;
	}
}
