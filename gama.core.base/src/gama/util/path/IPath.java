/*******************************************************************************************************
 *
 * gama.util.path.IPath.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.util.path;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IKeyword;
import gama.common.interfaces.IValue;
import gama.metamodel.shape.IShape;
import gama.metamodel.topology.ITopology;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.getter;
import gama.processor.annotations.GamlAnnotations.variable;
import gama.processor.annotations.GamlAnnotations.vars;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.graph.IGraph;
import gama.util.list.IList;
import gama.util.map.IMap;
import gaml.types.IType;

/**
 * The class IPath.
 *
 * @author drogoul
 * @since 14 d�c. 2011
 *
 */
@vars ({ @variable (
		name = IKeyword.TARGET,
		type = IType.NONE,
		doc = @doc ("The target (i.e. last element) of this path")),
		@variable (
				name = IKeyword.SOURCE,
				type = IType.NONE,
				doc = @doc ("The source (i.e. first element) of this path")),
		@variable (
				name = IKeyword.GRAPH,
				type = IType.GRAPH,
				doc = @doc ("The graph this path refers to")),
		@variable (
				name = IKeyword.SHAPE,
				type = IType.GEOMETRY,
				doc = @doc ("The shape obtained by all the points of this path")),
		@variable (
				name = IKeyword.SEGMENTS,
				type = IType.LIST,
				of = IType.GEOMETRY,
				doc = { @doc ("Returns the list of segments that compose this path") }),
		@variable (
				name = "distance",
				type = IType.FLOAT,
				doc = { @doc ("Returns the total lenght of all the segments that compose this path") }),
		@variable (
				name = "weight",
				type = IType.FLOAT,
				doc = @doc ("The addition of all the weights of the vertices that compose this path, with respect to the graph they belong to")),
		@variable (
				name = "edges",
				type = IType.LIST,
				of = IType.GEOMETRY,
				doc = @doc ("The list of edges of the underlying graph that compose this path")),
		@variable (
				name = "vertices",
				type = IType.LIST,
				doc = @doc ("The list of vertices of the underlying graph that compose this path"))
		// @var(name = IKeyword.AGENTS, type = IType.LIST, of = IType.AGENT),
		// Could be replaced by "geometries"
		/*
		 * Normally not necessary as it is inherited from GamaGeometry @var(name = GamaPath.POINTS, type = IType.LIST,
		 * of = IType.POINT)
		 */
})
public interface IPath<V, E, G extends IGraph<V, E>> extends IValue {// extends IShape {

	@getter (IKeyword.SOURCE)
	V getStartVertex();

	@getter (IKeyword.TARGET)
	V getEndVertex();

	@getter (IKeyword.GRAPH)
	G getGraph();

	@getter (IKeyword.SEGMENTS)
	IList<IShape> getEdgeGeometry();

	@getter ("vertices")
	IList<V> getVertexList();

	@getter ("edges")
	IList<E> getEdgeList();

	@getter ("shape")
	IShape getGeometry();

	// @getter(IKeyword.AGENTS)
	// public abstract List<IShape> getAgentList();

	@getter ("weight")
	double getWeight();

	double getWeight(final IShape line) throws GamaRuntimeException;

	void acceptVisitor(final IAgent agent);

	void forgetVisitor(final IAgent agent);

	int indexOf(final IAgent a);

	int indexSegmentOf(final IAgent a);

	boolean isVisitor(final IAgent a);

	void setIndexOf(final IAgent a, final int index);

	void setIndexSegementOf(final IAgent a, final int indexSegement);

	int getLength();

	@getter ("distance")
	double getDistance(IScope scope);

	ITopology getTopology(IScope scope);

	void setRealObjects(final IMap<IShape, IShape> realObjects);

	IShape getRealObject(final Object obj);

	void setSource(V source);

	void setTarget(V target);

	int getGraphVersion();

	void setGraph(G graph);

}