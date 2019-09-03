/*******************************************************************************************************
 *
 * gama.metamodel.topology.continuous.MultipleTopology.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.metamodel.topology.continuous;

import gama.common.interfaces.IContainer;
import gama.common.interfaces.IKeyword;
import gama.metamodel.shape.IShape;
import gama.metamodel.topology.ITopology;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.types.GamaGeometryType;

/**
 * The class GamaMultipleTopology.
 * 
 * @author drogoul
 * @since 30 nov. 2011
 * 
 */
public class MultipleTopology extends ContinuousTopology {

	/**
	 * @throws GamaRuntimeException
	 * @param scope
	 * @param environment
	 */
	public MultipleTopology(final IScope scope, final IContainer<?, IShape> places) throws GamaRuntimeException {
		// For the moment, use the geometric envelope in order to simplify the "environment".
		super(scope, GamaGeometryType.geometriesToGeometry(scope, places).getGeometricEnvelope());
		this.places = places;
	}

	@Override
	protected boolean canCreateAgents() {
		return true;
	}

	/**
	 * @see gama.interfaces.IValue#stringValue()
	 */
	@Override
	public String stringValue(final IScope scope) throws GamaRuntimeException {
		return "Multiple topology in " + environment.toString();
	}

	/**
	 * @see gama.environment.AbstractTopology#_toGaml()
	 */
	@Override
	protected String _toGaml(final boolean includingBuiltIn) {
		return IKeyword.TOPOLOGY + "(" + places.serialize(includingBuiltIn) + ")";
	}

	/**
	 * @see gama.environment.AbstractTopology#_copy()
	 */
	@Override
	protected ITopology _copy(final IScope scope) {
		return new MultipleTopology(scope, places/* , isTorus */);
	}

}
