/*******************************************************************************************************
 *
 * gama.metamodel.topology.CompoundSpatialIndex.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.metamodel.topology;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import gama.common.geometry.Envelope3D;
import gama.common.interfaces.IAgent;
import gama.common.interfaces.ICollector;
import gama.common.util.Collector;
import gama.metamodel.population.IPopulation;
import gama.metamodel.shape.IShape;
import gama.metamodel.topology.filter.IAgentFilter;
import gama.runtime.scope.IScope;
import gama.util.map.GamaMapFactory;

public class CompoundSpatialIndex extends Object implements ISpatialIndex.Compound {

	boolean disposed = false, unique = true;
	private Map<IPopulation<? extends IAgent>, ISpatialIndex> spatialIndexes;
	private final ICollector<ISpatialIndex> uniqueIndexes;
	private ISpatialIndex rootIndex;

	public CompoundSpatialIndex(final Envelope3D bounds, final boolean parallel) {
		this(bounds, parallel, GamaQuadTree.create(bounds, parallel));

	}

	public CompoundSpatialIndex(final Envelope3D bounds, final boolean parallel, final ISpatialIndex root) {
		spatialIndexes = new HashMap<>();
		rootIndex = root;
		uniqueIndexes = Collector.newOrderedSet();
		uniqueIndexes.add(rootIndex);
	}

	private ISpatialIndex findSpatialIndex(final IPopulation<? extends IAgent> s) {
		if (disposed)
			return null;
		if (unique)
			return rootIndex;
		final ISpatialIndex index = spatialIndexes == null ? null : spatialIndexes.get(s);
		return index == null ? rootIndex : index;
	}

	@Override
	public void insert(final IAgent a) {
		if (disposed)
			return;
		if (a == null)
			return;
		if (unique) {
			rootIndex.insert(a);
			return;
		}
		final ISpatialIndex si = findSpatialIndex(a.getPopulation());
		if (si != null) {
			si.insert(a);
		}
	}

	@Override
	public void remove(final Envelope3D previous, final IAgent agent) {
		if (disposed)
			return;
		if (agent == null)
			return;
		if (unique) {
			rootIndex.remove(previous, agent);
			return;
		}
		final ISpatialIndex si = findSpatialIndex(agent.getPopulation());
		if (si != null) {
			si.remove(previous, agent);
		}
	}

	@Override
	public void firstAtDistance(final IScope scope, final IShape source, final double dist, final IAgentFilter f,
			final int number, final Collection<IAgent> alreadyChosen) {
		if (disposed)
			return;
		final IPopulation<? extends IAgent> pop = f.getPopulation(scope);
		final ISpatialIndex id = findSpatialIndex(pop);
		if (id != rootIndex) {
			id.firstAtDistance(scope, source, dist, f, number - alreadyChosen.size(), alreadyChosen);
		} else {
			for (final ISpatialIndex si : getAllSpatialIndexes()) {
				si.firstAtDistance(scope, source, dist, f, number, alreadyChosen);
			}
		}
	}

	@Override
	public IAgent firstAtDistance(final IScope scope, final IShape source, final double dist, final IAgentFilter f) {
		if (disposed)
			return null;
		final IPopulation<? extends IAgent> pop = f.getPopulation(scope);
		final ISpatialIndex id = findSpatialIndex(pop);
		if (id != rootIndex)
			return id.firstAtDistance(scope, source, dist, f);
		else {
			IAgent min_agent = null;
			double min_dist = Double.MAX_VALUE;
			for (final ISpatialIndex si : getAllSpatialIndexes()) {
				final IAgent first = si.firstAtDistance(scope, source, dist, f);
				if (first != null) {
					final double dd = source.euclidianDistanceTo(first);
					if (dd < min_dist) {
						min_dist = dd;
						min_agent = first;
					}
				}
			}
			return min_agent;
		}

	}

	@Override
	public Collection<IAgent> allAtDistance(final IScope scope, final IShape source, final double dist,
			final IAgentFilter f) {
		if (disposed)
			return Collections.EMPTY_LIST;
		if (unique)
			return rootIndex.allAtDistance(scope, source, dist, f);

		final ISpatialIndex id = findSpatialIndex(f.getPopulation(scope));
		if (id == rootIndex) {
			try (final ICollector<IAgent> agents = Collector.newOrderedSet()) {
				for (final ISpatialIndex si : getAllSpatialIndexes()) {
					agents.addAll(si.allAtDistance(scope, source, dist, f));
				}
				return agents.items();
			}
		}
		return id.allAtDistance(scope, source, dist, f);
	}

	@Override
	public Collection<IAgent> allInEnvelope(final IScope scope, final IShape source, final Envelope3D envelope,
			final IAgentFilter f, final boolean contained) {
		if (disposed)
			return Collections.EMPTY_LIST;
		if (unique)
			return rootIndex.allInEnvelope(scope, source, envelope, f, contained);

		final ISpatialIndex id = findSpatialIndex(f.getPopulation(scope));
		if (id == rootIndex) {
			try (final ICollector<IAgent> agents = Collector.newOrderedSet()) {
				for (final ISpatialIndex si : getAllSpatialIndexes()) {
					agents.addAll(si.allInEnvelope(scope, source, envelope, f, contained));
				}
				agents.shuffleInPlaceWith(scope.getRandom());
				return agents.items();
			}
		}
		return id.allInEnvelope(scope, source, envelope, f, contained);
	}

	@Override
	public void add(final ISpatialIndex index, final IPopulation<? extends IAgent> species) {
		if (disposed)
			return;
		if (index == null)
			return;
		if (spatialIndexes == null) {
			spatialIndexes = GamaMapFactory.create();
		}
		spatialIndexes.put(species, index);
		uniqueIndexes.add(index);
		unique = false;

	}

	@Override
	public void remove(final IPopulation<? extends IAgent> species) {
		if (disposed)
			return;
		final ISpatialIndex index = spatialIndexes != null ? spatialIndexes.remove(species) : null;
		if (index != null) {
			uniqueIndexes.remove(index);
		}
	}

	@Override
	public void dispose() {
		if (spatialIndexes != null) {
			spatialIndexes.clear();
		}
		uniqueIndexes.clear();
		rootIndex = null;
		disposed = true;
	}

	@Override
	public void updateWith(final Envelope3D envelope) {
		rootIndex.updateWith(envelope);
	}

	@Override
	public Collection<IAgent> allAgents() {
		if (unique)
			return rootIndex.allAgents();
		try (final ICollector<IAgent> set = Collector.newOrderedSet()) {
			for (final ISpatialIndex i : getAllSpatialIndexes()) {
				set.addAll(i.allAgents());
			}
			return set.items();
		}
	}

	public Collection<ISpatialIndex> getAllSpatialIndexes() {
		return uniqueIndexes;
	}

	@Override
	public void mergeWith(final Compound comp) {
		final CompoundSpatialIndex other = (CompoundSpatialIndex) comp;
		other.spatialIndexes.forEach((species, index) -> {
			if (index != other.rootIndex) {
				add(index, species);
			}
		});
		other.dispose();
	}

	@Override
	public boolean isParallel() {
		return rootIndex.isParallel();
	}

	@Override
	public Envelope3D getBounds() {
		return rootIndex.getBounds();
	}

}
