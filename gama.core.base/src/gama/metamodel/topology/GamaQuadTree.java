/*******************************************************************************************************
 *
 * gama.metamodel.topology.GamaQuadTree.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/

package gama.metamodel.topology;

import static com.google.common.collect.Iterables.limit;

import java.util.Collection;
import java.util.Map;

import gama.common.geometry.Envelope3D;
import gama.common.interfaces.IAgent;
import gama.common.interfaces.ICollector;
import gama.common.util.Collector;
import gama.metamodel.shape.GamaPoint;
import gama.metamodel.shape.IShape;
import gama.metamodel.topology.filter.IAgentFilter;
import gama.runtime.scope.IScope;
import gama.util.list.GamaListFactory;
import gama.util.map.GamaMapFactory;
import gama.util.map.IMap;
import gaml.operators.Maths;

/**
 * A QuadTree allows to quickly find an object on a two-dimensional space.
 * <p>
 * QuadTree recursively subdivides a space into four rectangles. Each node of a QuadTree subdivides the space covered by
 * the rectangle of its parent node into four smaller rectangles covering the upper left, upper right, lower left and
 * lower right quadrant of the parent rectangle.
 *
 * @author Werner Randelshofer, adapted by Alexis Drogoul for GAMA
 * @version $Id: QuadTree.java 717 2010-11-21 12:30:57Z rawcoder $
 */
@SuppressWarnings ({ "unchecked", "rawtypes" })
public class GamaQuadTree implements ISpatialIndex {

	public static final int NW = 0;
	public static final int NE = 1;
	public static final int SW = 2;
	public static final int SE = 3;

	QuadNode root;
	final static int maxCapacity = 100;
	double minSize = 10;
	final boolean parallel;

	public static GamaQuadTree create(final Envelope3D envelope, final boolean parallel) {
		return new GamaQuadTree(envelope, parallel);
	}

	private GamaQuadTree(final Envelope3D bounds, final boolean sync) {
		// AD To address Issue 804, explictely converts the bounds to an
		// Envelope 2D, so that all computations are made
		// in 2D in the QuadTree
		this.parallel = sync;
		root = new QuadNode(bounds.flattened());
		minSize = bounds.getWidth() / 100d;
	}

	@Override
	public void dispose() {
		root.dispose();
	}

	@Override
	public void insert(final IAgent agent) {
		if (agent == null)
			return;
		if (agent.isPoint()) {
			root.add(agent.getLocation(), agent);
		} else {
			root.add(agent.getEnvelope(), agent);
		}
	}

	@Override
	public void updateWith(final Envelope3D envelope) {
		final Collection<IAgent> agents = allAgents();
		dispose();
		root = new QuadNode(envelope.flattened());
		for (final IAgent a : agents) {
			insert(a);
		}
	}

	private boolean isPoint(final Envelope3D env) {
		return env.getArea() == 0.0;
	}

	@Override
	public void remove(final Envelope3D previous, final IAgent agent) {
		final Envelope3D current = previous == null ? agent.getEnvelope() : previous;
		if (current == null)
			return;
		if (isPoint(current)) {
			root.remove(current.centre(), agent);
		} else {
			root.remove(current, agent);
		}
		current.dispose();
	}

	protected Collection<IAgent> findIntersects(final IScope scope, final IShape source, final Envelope3D r,
			final IAgentFilter filter) {
		// Adresses Issue 722 by explicitly shuffling the results with GAMA
		// random procedures and removing duplicates
		try (final ICollector<IAgent> list = Collector.newOrderedSet()) {
			root.findIntersects(r, list);
			if (list.isEmpty())
				return GamaListFactory.create();
			filter.filter(scope, source, list);
			list.shuffleInPlaceWith(scope.getRandom());
			return list.items();
		}
	}

	@Override
	public Collection<IAgent> allAtDistance(final IScope scope, final IShape source, final double dist,
			final IAgentFilter f) {
		// TODO filter result by topology's bounds
		final double exp = dist * Maths.SQRT2;
		final Envelope3D env = Envelope3D.of(source.getEnvelope());
		env.expandBy(exp);
		try {
			final Collection<IAgent> result = findIntersects(scope, source, env, f);
			if (result.isEmpty())
				return GamaListFactory.create();
			result.removeIf(each -> source.euclidianDistanceTo(each) > dist);
			return result;
		} finally {
			env.dispose();
		}
	}

	@Override
	public void firstAtDistance(final IScope scope, final IShape source, final double dist, final IAgentFilter f,
			final int number, final Collection<IAgent> result) {
		final double exp = dist * Maths.SQRT2;
		final Envelope3D env = Envelope3D.of(source.getEnvelope());
		env.expandBy(exp);
		try {
			limit(findIntersects(scope, source, env, f), number).forEach(a -> result.add(a));
		} finally {
			env.dispose();
		}
	}

	@Override
	public IAgent firstAtDistance(final IScope scope, final IShape source, final double dist, final IAgentFilter f) {
		final double exp = dist * Maths.SQRT2;
		final Envelope3D env = source.getEnvelope();
		env.expandBy(exp);
		try {
			final Collection<IAgent> in_square = findIntersects(scope, source, env, f);
			if (in_square.isEmpty())
				return null;
			double min_distance = dist;
			IAgent min_agent = null;
			for (final IAgent a : in_square) {
				final Double dd = source.euclidianDistanceTo(a);
				if (dd < min_distance) {
					min_distance = dd;
					min_agent = a;
				}
			}
			return min_agent;
		} finally {
			env.dispose();
		}
	}

	@Override
	public Collection<IAgent> allInEnvelope(final IScope scope, final IShape source, final Envelope3D envelope,
			final IAgentFilter f, final boolean contained) {
		return findIntersects(scope, source, envelope, f);
	}

	@Override
	public Collection<IAgent> allAgents() {
		try (final ICollector<IAgent> result = Collector.newOrderedSet()) {
			root.findIntersects(root.bounds, result);
			return result.items();
		}
	}

	private class QuadNode {

		final Envelope3D bounds;
		private final double halfx, halfy;
		private volatile QuadNode[] nodes = null;
		// ** Addresses part of Issue 722 -- Need to keep the agents ordered
		// (by insertion order) **
		private final IMap<IAgent, Envelope3D> objects;
		private final boolean canSplit;

		public QuadNode(final Envelope3D bounds) {
			this.bounds = bounds;
			final double hw = bounds.getWidth();
			final double hh = bounds.getHeight();
			halfx = bounds.getMinX() + hw / 2;
			halfy = bounds.getMinY() + hh / 2;
			canSplit = hw > minSize && hh > minSize;
			objects = parallel ? GamaMapFactory.concurrentMap() : GamaMapFactory.create();
		}

		public void dispose() {
			if (objects != null) {
				objects.forEach((a, e) -> {
					if (e != null) {
						e.dispose();
					}
				});
				objects.clear();
			}
			if (nodes != null) {
				for (final QuadNode n : nodes) {
					n.dispose();
				}
				nodes = null;
			}
		}

		public void remove(final GamaPoint p, final IShape a) {
			if (nodes == null) {
				final Envelope3D env = objects.remove(a);
				if (env != null) {
					env.dispose();
				}
			} else {
				nodes[quadrant(p)].remove(p, a);
			}
		}

		public void remove(final Envelope3D env, final IShape a) {
			if (nodes == null) {
				objects.remove(a);
			} else {
				for (final QuadNode node : nodes) {
					if (node.bounds.intersects2D(env)) {
						node.remove(env, a);
					}
				}
			}
		}

		public boolean shouldSplit() {
			return canSplit && nodes == null && objects.size() >= maxCapacity;
		}

		public void add(final GamaPoint p, final IAgent a) {
			if (shouldSplit()) {
				split();
			}
			if (nodes == null) {
				objects.put(a, Envelope3D.of(p));
			} else {
				nodes[quadrant(p)].add(p, a);
			}
		}

		public void add(final Envelope3D env, final IAgent a) {
			if (shouldSplit()) {
				split();
			}
			if (nodes == null) {
				objects.put(a, env);
			} else {
				for (final QuadNode node : nodes) {
					if (node.bounds.intersects2D(env)) {
						node.add(env, a);
					}
				}
			}
		}

		int quadrant(final GamaPoint p) {
			final boolean north = p.y >= bounds.getMinY() && p.y < halfy;
			final boolean west = p.x >= bounds.getMinX() && p.x < halfx;
			return north ? west ? NW : NE : west ? SW : SE;
		}

		public void split() {
			final double maxx = bounds.getMaxX();
			final double minx = bounds.getMinX();
			final double miny = bounds.getMinY();
			final double maxy = bounds.getMaxY();
			nodes = new QuadNode[] { new QuadNode(Envelope3D.of(minx, halfx, miny, halfy, 0d, 0d)),
					new QuadNode(Envelope3D.of(halfx, maxx, miny, halfy, 0d, 0d)),
					new QuadNode(Envelope3D.of(minx, halfx, halfy, maxy, 0d, 0d)),
					new QuadNode(Envelope3D.of(halfx, maxx, halfy, maxy, 0d, 0d)) };
			wipeObjects();
		}

		private void wipeObjects() {
			for (final Map.Entry<IAgent, Envelope3D> entry : objects.entrySet()) {
				final IAgent agent = entry.getKey();
				if (agent != null && !agent.dead()) {
					final IShape g = agent.getGeometry();
					if (g.isPoint()) {
						add(g.getLocation(), agent);
					} else {
						add(g.getEnvelope(), agent);
					}
				}
			}
			objects.clear();
		}

		public void findIntersects(final Envelope3D r, final Collection<IAgent> result) {
			if (bounds.intersects(r)) {

				for (final Map.Entry<IAgent, Envelope3D> entry : objects.entrySet()) {
					final Envelope3D env = entry.getValue();
					if (env != null && env.intersects2D(r)) {
						result.add(entry.getKey());
					}
				}

				if (nodes != null) {
					for (final QuadNode node : nodes) {
						node.findIntersects(r, result);
					}
				}
			}

		}

	}

	@Override
	public boolean isParallel() {
		return parallel;
	}

	@Override
	public Envelope3D getBounds() {
		return root.bounds;
	}

}
