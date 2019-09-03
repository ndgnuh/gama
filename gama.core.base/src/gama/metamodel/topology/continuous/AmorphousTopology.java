/*******************************************************************************************************
 *
 * gama.metamodel.topology.continuous.AmorphousTopology.java, in plugin gama.core, is part of the source code of
 * the GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.metamodel.topology.continuous;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.locationtech.jts.geom.Geometry;

import gama.common.geometry.Envelope3D;
import gama.common.interfaces.IAgent;
import gama.common.interfaces.IContainer;
import gama.metamodel.population.IPopulation;
import gama.metamodel.shape.GamaPoint;
import gama.metamodel.shape.GamaShape;
import gama.metamodel.shape.IShape;
import gama.metamodel.topology.ISpatialIndex;
import gama.metamodel.topology.ITopology;
import gama.metamodel.topology.filter.IAgentFilter;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.list.GamaListFactory;
import gama.util.list.IList;
import gama.util.path.GamaSpatialPath;
import gama.util.path.PathFactory;
import gaml.operators.Maths;
import gaml.operators.Spatial;
import gaml.types.GamaGeometryType;
import gaml.types.IType;
import gaml.types.Types;

/**
 * The class AmorphousTopology.
 *
 * @author drogoul
 * @since 2 d�c. 2011
 *
 */
public class AmorphousTopology implements ITopology {

	IShape expandableEnvironment = GamaGeometryType.createPoint(new GamaPoint(0, 0));

	/**
	 * @see gama.interfaces.IValue#type()
	 */
	// @Override
	// public IType type() {
	// return Types.TOPOLOGY;
	// }

	/**
	 * @see gama.interfaces.IValue#stringValue()
	 */
	@Override
	public String stringValue(final IScope scope) throws GamaRuntimeException {
		return "Expandable topology";
	}

	@Override
	public IType<?> getGamlType() {
		return Types.TOPOLOGY;
	}

	/**
	 * @see gama.interfaces.IValue#toGaml()
	 */
	@Override
	public String serialize(final boolean includingBuiltIn) {
		return "topology({0,0})";
	}

	/**
	 * @see gama.interfaces.IValue#copy()
	 */
	@Override
	public ITopology copy(final IScope scope) throws GamaRuntimeException {
		return new AmorphousTopology();
	}

	/**
	 * @see gama.environment.ITopology#initialize(gama.interfaces.IPopulation)
	 */
	@Override
	public void initialize(final IScope scope, final IPopulation<? extends IAgent> pop) throws GamaRuntimeException {}

	/**
	 * @see gama.environment.ITopology#updateAgent(gama.common.interfaces.interfaces.IAgent, boolean, gama.util.GamaPoint,
	 *      org.locationtech.jts.geom.Envelope)
	 */
	// @Override
	// public void updateAgent(final IAgent agent, final boolean
	// previousShapeIsPoint,
	// final GamaPoint previousLoc, final Envelope previousEnv) {
	// IShape ng =
	// Spatial.Operators.union(expandableEnvironment.getGeometry(),
	// agent.getGeometry());
	// expandableEnvironment.setGeometry(new
	// GamaShape(ng.getInnerGeometry().getEnvelope()));
	// }
	//
	@Override
	public void updateAgent(final Envelope3D previous, final IAgent agent) {
		final IShape ng =
				Spatial.Operators.union(agent.getScope(), expandableEnvironment.getGeometry(), agent.getGeometry());
		expandableEnvironment.setGeometry(new GamaShape(ng.getInnerGeometry().getEnvelope()));
	}

	/**
	 * @see gama.environment.ITopology#removeAgent(gama.common.interfaces.interfaces.IAgent)
	 */
	@Override
	public void removeAgent(final IAgent agent) {}

	/**
	 * @see gama.environment.ITopology#getAgentClosestTo(gama.interfaces.IGeometry,
	 *      gama.environment.IAgentFilter)
	 */

	@Override
	public IList<IAgent> getAgentClosestTo(final IScope scope, final IShape source, final IAgentFilter filter,
			final int number) {
		return GamaListFactory.EMPTY_LIST;
	}

	@Override
	public IAgent getAgentClosestTo(final IScope scope, final IShape source, final IAgentFilter filter) {
		return null;
	}

	@Override
	public IAgent getAgentFarthestTo(final IScope scope, final IShape source, final IAgentFilter filter) {
		return null;
	}

	/**
	 * @see gama.environment.ITopology#getNeighborsOf(gama.interfaces.IGeometry, java.lang.Double,
	 *      gama.environment.IAgentFilter)
	 */
	@Override
	public Set<IAgent> getNeighborsOf(final IScope scope, final IShape source, final Double distance,
			final IAgentFilter filter) throws GamaRuntimeException {
		return Collections.EMPTY_SET;
	}

	/**
	 * @see gama.environment.ITopology#getAgentsIn(gama.interfaces.IGeometry, gama.environment.IAgentFilter,
	 *      boolean)
	 */
	@Override
	public Set<IAgent> getAgentsIn(final IScope scope, final IShape source, final IAgentFilter f,
			final boolean covered) {
		return Collections.EMPTY_SET;
	}

	/**
	 * @see gama.environment.ITopology#distanceBetween(gama.interfaces.IGeometry, gama.interfaces.IGeometry)
	 */
	@Override
	public Double distanceBetween(final IScope scope, final IShape source, final IShape target) {
		return source.euclidianDistanceTo(target);
	}

	@Override
	public Double distanceBetween(final IScope scope, final GamaPoint source, final GamaPoint target) {
		return source.euclidianDistanceTo(target);
	}

	/**
	 * @see gama.environment.ITopology#pathBetween(gama.interfaces.IGeometry, gama.interfaces.IGeometry)
	 */
	@Override
	public GamaSpatialPath pathBetween(final IScope scope, final IShape source, final IShape target)
			throws GamaRuntimeException {
		// return new GamaPath(this, GamaList.with(source, target));
		return PathFactory.create(scope, this, GamaListFactory.wrap(Types.GEOMETRY, new IShape[] { source, target }));
	}

	/**
	 * @see gama.environment.ITopology#getDestination(gama.util.GamaPoint, int, double, boolean)
	 */
	@Override
	public GamaPoint getDestination(final GamaPoint source, final double direction, final double distance,
			final boolean nullIfOutside) {
		final double cos = distance * Maths.cos(direction);
		final double sin = distance * Maths.sin(direction);
		return new GamaPoint(source.getX() + cos, source.getY() + sin);

	}

	/**
	 * @see gama.environment.ITopology#getDestination(gama.util.GamaPoint, int, double, boolean)
	 */
	@Override
	public GamaPoint getDestination3D(final GamaPoint source, final double heading, final double pitch,
			final double distance, final boolean nullIfOutside) {
		final double x = distance * Maths.cos(pitch) * Maths.cos(heading);
		final double y = distance * Maths.cos(pitch) * Maths.sin(heading);
		final double z = distance * Maths.sin(pitch);
		return new GamaPoint(source.getX() + x, source.getY() + y, source.getZ() + z);
	}

	/**
	 * @see gama.environment.ITopology#getRandomLocation()
	 */
	@Override
	public GamaPoint getRandomLocation(final IScope scope) {
		return new GamaPoint(scope.getRandom().next(), scope.getRandom().next());
	}

	/**
	 * @see gama.environment.ITopology#getPlaces()
	 */
	@Override
	public IContainer<?, IShape> getPlaces() {
		final IList<IShape> result = GamaListFactory.create(Types.GEOMETRY);
		result.add(expandableEnvironment);
		return result;
	}

	/**
	 * @see gama.environment.ITopology#getEnvironment()
	 */
	@Override
	public IShape getEnvironment() {
		return expandableEnvironment;
	}

	/**
	 * @see gama.environment.ITopology#normalizeLocation(gama.util.GamaPoint, boolean)
	 */
	@Override
	public GamaPoint normalizeLocation(final GamaPoint p, final boolean nullIfOutside) {
		return p;
	}

	/**
	 * @see gama.environment.ITopology#shapeChanged(gama.interfaces.IPopulation)
	 */
	// @Override
	// public void shapeChanged(final IPopulation pop) {}

	/**
	 * @see gama.environment.ITopology#getWidth()
	 */
	@Override
	public double getWidth() {
		return expandableEnvironment.getEnvelope().getWidth();
	}

	/**
	 * @see gama.environment.ITopology#getHeight()
	 */
	@Override
	public double getHeight() {
		return expandableEnvironment.getEnvelope().getHeight();
	}

	/**
	 * @see gama.environment.ITopology#dispose()
	 */
	@Override
	public void dispose() {}

	/**
	 * @see gama.environment.ITopology#isValidLocation(gama.util.GamaPoint)
	 */
	@Override
	public boolean isValidLocation(final IScope scope, final GamaPoint p) {
		return true;
	}

	/**
	 * @see gama.environment.ITopology#isValidGeometry(gama.interfaces.IGeometry)
	 */
	@Override
	public boolean isValidGeometry(final IScope scope, final IShape g) {
		return true;
	}

	/**
	 * @see gama.environment.ITopology#directionInDegreesTo(gama.interfaces.IGeometry,
	 *      gama.interfaces.IGeometry)
	 */
	@Override
	public Double directionInDegreesTo(final IScope scope, final IShape g1, final IShape g2) {
		final GamaPoint source = g1.getLocation();
		final GamaPoint target = g2.getLocation();
		final double x2 = /* translateX(source.x, target.x); */target.getX();
		final double y2 = /* translateY(source.y, target.y); */target.getY();
		final double dx = x2 - source.getX();
		final double dy = y2 - source.getY();
		final double result = Maths.atan2(dy, dx);
		return Maths.checkHeading(result);
	}

	/**
	 * @see gama.metamodel.topology.ITopology#pathBetween(gama.metamodel.shape.GamaPoint,
	 *      gama.metamodel.shape.GamaPoint)
	 */
	@Override
	public GamaSpatialPath pathBetween(final IScope scope, final GamaPoint source, final GamaPoint target)
			throws GamaRuntimeException {
		return PathFactory.create(scope, this,
				GamaListFactory.create(scope, Types.POINT, new IShape[] { source, target }));
	}

	@Override
	public List<Geometry> listToroidalGeometries(final Geometry geom) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public boolean isTorus() {
		return false;
	}

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public ISpatialIndex getSpatialIndex() {
		return new ISpatialIndex() {

			@Override
			public void insert(final IAgent agent) {}

			@Override
			public void remove(final Envelope3D previous, final IAgent agent) {}

			@Override
			public IAgent firstAtDistance(final IScope scope, final IShape source, final double dist,
					final IAgentFilter f) {
				return null;
			}

			@Override
			public void firstAtDistance(final IScope scope, final IShape source, final double dist,
					final IAgentFilter f, final int number, final Collection<IAgent> alreadyChosen) {}

			@Override
			public Collection<IAgent> allInEnvelope(final IScope scope, final IShape source, final Envelope3D envelope,
					final IAgentFilter f, final boolean contained) {
				return Collections.EMPTY_LIST;
			}

			@Override
			public Collection<IAgent> allAtDistance(final IScope scope, final IShape source, final double dist,
					final IAgentFilter f) {
				return Collections.EMPTY_LIST;
			}

			@Override
			public void dispose() {}

			@Override
			public Collection<IAgent> allAgents() {
				return Collections.EMPTY_LIST;
			}

			@Override
			public boolean isParallel() {
				return false;
			}

			@Override
			public void updateWith(final Envelope3D envelope) {}

			@Override
			public Envelope3D getBounds() {
				return expandableEnvironment.getEnvelope();
			}
		};
	}

	@Override
	public IList<GamaSpatialPath> KpathsBetween(final IScope scope, final IShape source, final IShape target,
			final int k) {
		final IList<GamaSpatialPath> paths = GamaListFactory.create(Types.PATH);
		paths.add(pathBetween(scope, source, target));
		return paths;
	}

	@Override
	public IList<GamaSpatialPath> KpathsBetween(final IScope scope, final GamaPoint source, final GamaPoint target,
			final int k) {
		final IList<GamaSpatialPath> paths = GamaListFactory.create(Types.PATH);
		paths.add(pathBetween(scope, source, target));
		return paths;
	}

	@Override
	public void setRoot(final IScope scope, final RootTopology rt) {
		// TODO Auto-generated method stub

	}
}
