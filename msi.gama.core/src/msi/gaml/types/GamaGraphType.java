/*******************************************************************************************************
 *
 * msi.gaml.types.GamaGraphType.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gaml.types;

import java.util.Map;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.metamodel.topology.graph.GamaSpatialGraph;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gama.util.GamaPair;
import msi.gama.util.graph.GamaGraph;
import msi.gama.util.graph.IGraph;
import msi.gama.util.list.GamaListFactory;
import msi.gama.util.list.IList;
import msi.gama.util.map.IMap;
import msi.gaml.expressions.VariableExpression;
import msi.gaml.operators.Cast;
import ummisco.gama.processor.IConcept;
import ummisco.gama.processor.ISymbolKind;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.type;

@type (
		name = IKeyword.GRAPH,
		id = IType.GRAPH,
		wraps = { IGraph.class },
		kind = ISymbolKind.Variable.CONTAINER,
		concept = { IConcept.TYPE, IConcept.GRAPH },
		doc = @doc ("Special type of container composed of edges and vertices"))
@SuppressWarnings ({ "unchecked", "rawtypes" })
public class GamaGraphType extends GamaContainerType<IGraph> {

	@Override
	public IGraph cast(final IScope scope, final Object obj, final Object param, final IType keyType,
			final IType contentsType, final boolean copy) throws GamaRuntimeException {
		return staticCast(scope, obj, param, copy);
	}

	@Override
	public int getNumberOfParameters() {
		return 2;
	}

	public static IGraph staticCast(final IScope scope, final Object obj, final Object param, final boolean copy) {
		// param = true : spatial.

		if (obj == null)
			return null;
		if (obj instanceof IGraph)
			return (IGraph) obj;
		final boolean spatial = param != null && Cast.asBool(scope, param);

		if (obj instanceof IList)
			return from(scope, (IList) obj, spatial);

		if (obj instanceof VariableExpression)
			// in this case, attempt to decode it !
			return (IGraph) ((VariableExpression) obj).value(scope);

		if (obj instanceof IMap)
			return from(scope, (IMap) obj, spatial);

		return null;
	}

	public static IGraph from(final IScope scope, final IMap<?, ?> obj, final boolean spatial) {
		final IGraph result = spatial
				? new GamaSpatialGraph(GamaListFactory.EMPTY_LIST, false, false, null, null, scope,
						obj.getGamlType().getKeyType(), Types.NO_TYPE)
				: new GamaGraph(scope, GamaListFactory.EMPTY_LIST, false, false, null, null,
						obj.getGamlType().getKeyType(), Types.NO_TYPE);
		final GamaPair p = new GamaPair(null, null, Types.NO_TYPE, Types.NO_TYPE);
		for (final Map.Entry<?, ?> k : obj.entrySet()) {
			p.key = k.getKey();
			p.value = k.getValue();
			result.addEdge(p);
		}
		return result;
	}

	public static IGraph from(final IScope scope, final IList obj, final boolean spatial) {
		final IType nodeType = obj.getGamlType().getContentType();
		return spatial ? new GamaSpatialGraph(obj, false, false, null, null, scope, nodeType, Types.NO_TYPE)
				: new GamaGraph(scope, obj, false, false, null, null, nodeType, Types.NO_TYPE);
	}

	// GamaSpatialGraph(final IContainer edgesOrVertices, final boolean byEdge, final boolean directed,
	// final VertexRelationship rel, final ISpecies edgesSpecies, final IScope scope, final IType nodeType,
	// final IType edgeType) {

	// public GamaGraph(final IScope scope, final IContainer edgesOrVertices, final boolean byEdge, final boolean
	// directed,
	// final VertexRelationship rel, final ISpecies edgesSpecies, final IType nodeType, final IType edgeType) {

	public static IGraph from(final IScope scope, final IList edgesOrVertices, final boolean byEdge,
			final boolean directed, final boolean spatial, final IType nodeType, final IType edgeType) {
		return spatial ? new GamaSpatialGraph(edgesOrVertices, byEdge, directed, null, null, scope, nodeType, edgeType)
				: new GamaGraph(scope, edgesOrVertices, byEdge, directed, null, null, nodeType, edgeType);
	}

	public static IGraph useChacheForShortestPath(final IGraph source, final boolean useCache) {
		source.setSaveComputedShortestPaths(useCache);
		return source; // TODO Clone ?
	}

	public static IGraph asDirectedGraph(final IGraph source) {
		source.setDirected(true);
		return source; // TODO Clone ?
	}

	public static IGraph asUndirectedGraph(final IGraph source) {
		source.setDirected(false);
		return source; // TODO Clone ?
	}

	@Override
	public boolean canCastToConst() {
		return false;
	}

	@Override
	public boolean isDrawable() {
		return true;
	}

}
