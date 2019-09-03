/*******************************************************************************************************
 *
 * gama.metamodel.topology.filter.Different.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.metamodel.topology.filter;

import java.util.Collection;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IContainer;
import gama.metamodel.population.IPopulation;
import gama.metamodel.shape.IShape;
import gama.runtime.scope.IScope;
import gama.util.list.GamaListFactory;
import gaml.species.ISpecies;

public class Different implements IAgentFilter {

	private static final Different instance = new Different();

	public static Different with() {
		return instance;
	}

	@Override
	public boolean accept(final IScope scope, final IShape source, final IShape a) {
		return a.getGeometry() != source.getGeometry();
	}

	/**
	 * @see gama.metamodel.topology.filter.IAgentFilter#getShapes()
	 */
	@Override
	public IContainer<?, ? extends IAgent> getAgents(final IScope scope) {
		return GamaListFactory.EMPTY_LIST;
	}

	@Override
	public ISpecies getSpecies() {
		return null;
	}

	@Override
	public IPopulation<? extends IAgent> getPopulation(final IScope scope) {
		return null;
	}

	@Override
	public boolean hasAgentList() {
		return false;
	}

	/**
	 * Method filter()
	 *
	 * @see gama.metamodel.topology.filter.IAgentFilter#filter(java.util.Collection)
	 */
	@Override
	public void filter(final IScope scope, final IShape source, final Collection<? extends IShape> internal_results) {
		internal_results.remove(source);
	}

}