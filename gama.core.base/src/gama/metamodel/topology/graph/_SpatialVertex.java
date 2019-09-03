/*******************************************************************************************************
 *
 * gama.metamodel.topology.graph._SpatialVertex.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.metamodel.topology.graph;

import gama.common.util.StringUtils;
import gama.metamodel.shape.IShape;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.util.graph._Vertex;

public class _SpatialVertex extends _Vertex<IShape, IShape> {

	public _SpatialVertex(final GamaSpatialGraph graph, final Object vertex) throws GamaRuntimeException {
		super(graph);
		if (!(vertex instanceof IShape)) { throw GamaRuntimeException
				.error(StringUtils.toGaml(vertex, false) + " is not a geometry", graph.getScope()); }
	}

}