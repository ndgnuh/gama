/*******************************************************************************************************
 *
 * msi.gama.util.path.GamaPath.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gama.util.path;

import org.jgrapht.GraphPath;

import msi.gama.common.interfaces.IAgent;
import msi.gama.common.util.Collector;
import msi.gama.common.util.Collector.AsList;
import msi.gama.metamodel.shape.IShape;
import msi.gama.metamodel.topology.ITopology;
import msi.gama.metamodel.topology.graph.GamaSpatialGraph;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gama.util.graph.IGraph;
import msi.gama.util.list.GamaListFactory;
import msi.gama.util.list.IList;
import msi.gama.util.map.IMap;
import msi.gaml.operators.Cast;
import msi.gaml.types.IType;
import msi.gaml.types.Types;

@SuppressWarnings ({ "unchecked", "rawtypes" })
public class GamaPath<V, E, G extends IGraph<V, E>> implements Comparable, GraphPath<V, E>, IPath<V, E, G> {

	V source, target;
	IList<E> edges;
	double weight = 0.0;
	G graph;
	int graphVersion;

	@Override
	public IType getGamlType() {
		return Types.PATH;
	}

	protected GamaPath() {}

	GamaPath(final G g, final V start, final V target, final IList<? extends E> _edges, final boolean modifyEdges) {
		this.graph = g;
		init(start, target, _edges, modifyEdges);

	}

	GamaPath(final IList<? extends V> nodes) {
		this.graph = null;
		try (AsList<E> _edges = Collector.newList()) {
			for (int i = 0; i < nodes.size() - 1; i++) {
				final E edge = createEdge(nodes.get(i), nodes.get(i + 1));
				if (edge != null) {
					_edges.add(edge);
				}
			}
			init(nodes.get(0), nodes.get(nodes.size() - 1), _edges.items(), false);
		}
	}

	protected E createEdge(final V v, final V v2) {
		// TODO to define !
		return null;
	}

	public void init(final V start, final V target, final IList<? extends E> _edges, final boolean modify_edges) {
		this.source = start;
		this.target = target;
		this.edges = GamaListFactory.create();
		graphVersion = 0;

		if (_edges != null && _edges.size() > 0) {
			for (final E edge : _edges) {
				edges.add(edge);
			}
		}
	}

	// /////////////////////////////////////////////////
	// Implements methods from GraphPath

	@Override
	public G getGraph() {
		return graph;
	}

	@Override
	public V getStartVertex() {
		return source;
	}

	@Override
	public V getEndVertex() {
		return target;
	}

	@Override
	public IList<E> getEdgeList() {
		return edges;
	}

	public void setWeight(final double weight) {
		this.weight = weight;
	}

	@Override
	public double getWeight() {
		final G graph = getGraph();
		if (graph == null) { return weight; }
		return graph.computeWeight(this);
	}

	// /////////////////////////////////////////////////
	// Implements methods from IValue

	@Override
	public String stringValue(final IScope scope) {
		return serialize(false);
	}

	@Override
	public GamaPath copy(final IScope scope) {
		return new GamaPath(graph, source, target, edges, true);
	}

	// /////////////////////////////////////////////////
	// Implements methods from IPath

	@Override
	public IList<V> getVertexList() {
		if (graph == null) { return GamaListFactory.EMPTY_LIST; }
		return GamaListFactory.<V> wrap(getGamlType().getKeyType(), GraphPath.super.getVertexList());
	}

	// TODO :to check
	@Override
	public double getWeight(final IShape line) throws GamaRuntimeException {
		return line.getGeometry().getPerimeter(); // workaround for the moment
	}

	@Override
	public String toString() {
		return "path between " + getStartVertex().toString() + " and " + getEndVertex().toString();
	}

	@Override
	// FIXME
	public void acceptVisitor(final IAgent agent) {
		agent.setAttribute("current_path", this); // ???
	}

	@Override
	// FIXME
	public void forgetVisitor(final IAgent agent) {
		agent.setAttribute("current_path", null); // ???
	}

	@Override
	// FIXME
	public int indexOf(final IAgent a) {
		return Cast.asInt(null, a.getAttribute("index_on_path")); // ???
	}

	@Override
	// FIXME
	public int indexSegmentOf(final IAgent a) {
		return Cast.asInt(null, a.getAttribute("index_on_path_segment")); // ???
	}

	@Override
	// FIXME
	public boolean isVisitor(final IAgent a) {
		return a.getAttribute("current_path") == this;
	}

	@Override
	// FIXME
	public void setIndexOf(final IAgent a, final int index) {
		a.setAttribute("index_on_path", index);
	}

	@Override
	// FIXME
	public void setIndexSegementOf(final IAgent a, final int indexSegement) {
		a.setAttribute("index_on_path_segment", indexSegement);
	}

	@Override
	public String serialize(final boolean includingBuiltIn) {
		return "(" + getEdgeList().serialize(includingBuiltIn) + ") as path";
	}

	@Override
	public int getLength() {
		return edges.size();
	}

	@Override
	public double getDistance(final IScope scope) {
		if (getEdgeList() == null || getEdgeList().isEmpty()) { return 0; }
		return getWeight();
	}

	@Override
	public ITopology getTopology(final IScope scope) {
		return graph instanceof GamaSpatialGraph ? ((GamaSpatialGraph) graph).getTopology(scope) : null;
	}

	@Override
	public void setRealObjects(final IMap<IShape, IShape> realObjects) {
		;
	}

	@Override
	public IShape getRealObject(final Object obj) {
		return null;
	}

	@Override
	public void setSource(final V source) {
		this.source = source;
	}

	@Override
	public void setTarget(final V target) {
		this.target = target;
	}

	@Override
	public int getGraphVersion() {
		return graphVersion;
	}

	@Override
	public IList<IShape> getEdgeGeometry() {
		return null;
	}

	@Override
	public IShape getGeometry() {
		return null;
	}

	@Override
	public void setGraph(final G graph) {
		this.graph = graph;
		graphVersion = graph.getVersion();

	}

	@Override
	public int compareTo(final Object o) {
		return (int) (this.getWeight() - ((GamaPath) o).getWeight());
	}
}
