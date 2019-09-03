/*******************************************************************************************************
 *
 * gama.metamodel.topology.grid.GridTopology.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.metamodel.topology.grid;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import gama.common.geometry.Envelope3D;
import gama.common.interfaces.IAgent;
import gama.common.interfaces.ICollector;
import gama.common.interfaces.IKeyword;
import gama.common.util.Collector;
import gama.metamodel.population.IPopulation;
import gama.metamodel.population.IPopulationSet;
import gama.metamodel.shape.GamaPoint;
import gama.metamodel.shape.IShape;
import gama.metamodel.topology.AbstractTopology;
import gama.metamodel.topology.ITopology;
import gama.metamodel.topology.filter.Different;
import gama.metamodel.topology.filter.DifferentList;
import gama.metamodel.topology.filter.IAgentFilter;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.file.IGamaFile;
import gama.util.list.GamaListFactory;
import gama.util.list.IList;
import gama.util.path.GamaSpatialPath;
import gaml.types.GamaGeometryType;
import gaml.types.Types;

public class GridTopology extends AbstractTopology {

	public GridTopology(final IScope scope, final IGrid matrix) {
		super(scope, matrix.getEnvironmentFrame(), null);
		places = matrix;
	}

	@Override
	public void updateAgent(final Envelope3D previous, final IAgent agent) {}

	@Override
	public void initialize(final IScope scope, final IPopulation<? extends IAgent> pop) throws GamaRuntimeException {
		getPlaces().setCellSpecies(pop);
		root.addSpatialIndex(getPlaces(), pop);
		super.initialize(scope, pop);
	}

	@Override
	protected boolean canCreateAgents() {
		return true;
	}

	@Override
	public boolean isContinuous() {
		return false;
	}

	public GridTopology(final IScope scope, final IShape environment, final int rows, final int columns,
			final boolean isTorus, final boolean usesVN, final boolean isHexagon, final boolean horizontalOrientation,
			final boolean useIndividualShapes, final boolean useNeighborsCache, final String optimizer)
			throws GamaRuntimeException {
		super(scope, environment, null);
		if (isHexagon) {
			places = new GamaSpatialMatrix(scope, environment, rows, columns, isTorus, usesVN, isHexagon,
					horizontalOrientation, useIndividualShapes, useNeighborsCache, optimizer);
		} else {
			places = new GamaSpatialMatrix(scope, environment, rows, columns, isTorus, usesVN, useIndividualShapes,
					useNeighborsCache, optimizer);
		}
		// FIXME Not sure it needs to be set
		// root.setTorus(isTorus);

	}

	public GridTopology(final IScope scope, final IShape environment, final IGamaFile.Grid file, final boolean isTorus,
			final boolean usesVN, final boolean useIndividualShapes, final boolean useNeighborsCache,
			final String optimizer) throws GamaRuntimeException {
		super(scope, environment, null);
		places = new GamaSpatialMatrix(scope, file, isTorus, usesVN, useIndividualShapes, useNeighborsCache, optimizer);
		// FIXME Not sure it needs to be set

		// root.setTorus(isTorus);
	}

	public GridTopology(final IScope scope, final IShape environment, final IList<IGamaFile.Grid> files,
			final boolean isTorus, final boolean usesVN, final boolean useIndividualShapes,
			final boolean useNeighborsCache, final String optimizer) throws GamaRuntimeException {
		super(scope, environment, null);
		places = new GamaSpatialMatrix(scope, files, isTorus, usesVN, useIndividualShapes, useNeighborsCache,
				optimizer);
		// FIXME Not sure it needs to be set

		// root.setTorus(isTorus);
	}

	@Override
	public IAgent getAgentClosestTo(final IScope scope, final IShape source, final IAgentFilter filter) {
		return ((GamaSpatialMatrix) getPlaces()).getAgentClosestTo(scope, source, filter);

	}

	/**
	 * @see gama.interfaces.IValue#stringValue()
	 */
	@Override
	public String stringValue(final IScope scope) throws GamaRuntimeException {
		return "Grid topology in " + environment.toString() + " as " + places.toString();
	}

	/**
	 * @see gama.environment.AbstractTopology#_toGaml()
	 */
	@Override
	protected String _toGaml(final boolean includingBuiltIn) {
		return IKeyword.TOPOLOGY + " (" + places.serialize(includingBuiltIn) + ")";
	}

	/**
	 * @throws GamaRuntimeException
	 * @see gama.environment.AbstractTopology#_copy()
	 */
	@Override
	protected ITopology _copy(final IScope scope) throws GamaRuntimeException {
		final IGrid grid = (IGrid) places;
		return new GridTopology(scope, environment, grid.getRows(scope), grid.getCols(scope), grid.isTorus(),
				grid.getNeighborhood().isVN(), grid.isHexagon(), grid.isHorizontalOrientation(),
				grid.usesIndiviualShapes(), grid.usesNeighborsCache(), grid.optimizer());
	}

	@Override
	public IGrid getPlaces() {
		return (IGrid) super.getPlaces();
	}

	/**
	 * @throws GamaRuntimeException
	 * @see gama.environment.ITopology#pathBetween(gama.interfaces.IGeometry, gama.interfaces.IGeometry)
	 */

	public GamaSpatialPath pathBetween(final IScope scope, final IShape source, final IShape target,
			final IList<IAgent> on) throws GamaRuntimeException {
		return getPlaces().computeShortestPathBetween(scope, source, target, this, on);
	}

	public GamaSpatialPath pathBetween(final IScope scope, final IShape source, final IShape target,
			final Map<IAgent, Object> on) throws GamaRuntimeException {
		return getPlaces().computeShortestPathBetweenWeighted(scope, source, target, this, on);
	}

	@Override
	public GamaSpatialPath pathBetween(final IScope scope, final IShape source, final IShape target)
			throws GamaRuntimeException {
		return getPlaces().computeShortestPathBetween(scope, source, target, this, null);
	}

	/**
	 * @see gama.environment.ITopology#isValidLocation(gama.util.GamaPoint)
	 */
	@Override
	public boolean isValidLocation(final IScope scope, final GamaPoint p) {
		return getPlaces().getPlaceAt(p) != null;

	}

	/**
	 * @see gama.environment.ITopology#isValidGeometry(gama.interfaces.IGeometry)
	 */
	@Override
	public boolean isValidGeometry(final IScope scope, final IShape g) {
		return isValidLocation(scope, g.getLocation());
	}

	/**
	 * @see gama.environment.ITopology#distanceBetween(gama.interfaces.IGeometry, gama.interfaces.IGeometry,
	 *      java.lang.Double)
	 */
	@Override
	public Double distanceBetween(final IScope scope, final IShape source, final IShape target) {
		if (!isValidGeometry(scope, source) || !isValidGeometry(scope, target))
			return Double.MAX_VALUE;
		// TODO null or Double.MAX_VALUE ?
		return (double) getPlaces().manhattanDistanceBetween(source, target);
	}

	@Override
	public Double distanceBetween(final IScope scope, final GamaPoint source, final GamaPoint target) {
		if (!isValidLocation(scope, source) || !isValidLocation(scope, target))
			return Double.MAX_VALUE;
		// TODO null or Double.MAX_VALUE ?
		return (double) getPlaces().manhattanDistanceBetween(source, target);
	}

	/**
	 * @see gama.environment.ITopology#directionInDegreesTo(gama.interfaces.IGeometry,
	 *      gama.interfaces.IGeometry)
	 */
	@Override
	public Double directionInDegreesTo(final IScope scope, final IShape source, final IShape target) {
		// TODO compute from the path
		return root.directionInDegreesTo(scope, source, target);
	}

	@Override
	public Collection<IAgent> getNeighborsOf(final IScope scope, final IShape source, final Double distance,
			final IAgentFilter filter) throws GamaRuntimeException {
		// We compute the neighboring cells of the "source" shape

		final Set<IAgent> placesConcerned =
				getPlaces().getNeighborsOf(scope, source, distance, getPlaces().getCellSpecies());
		// If we only accept cells from this topology, no need to look for other
		// agents
		if (filter.getSpecies() == getPlaces().getCellSpecies()) {
			// case where the filter is the complete population set
			if (filter instanceof IPopulationSet)
				return placesConcerned;
			else {
				// otherwise, we return only the accepted cells
				try (ICollector<IAgent> agents = Collector.newSet()) {
					for (final IAgent ag : placesConcerned) {
						if (filter.accept(scope, null, ag)) {
							agents.add(ag);
						}
					}
					return agents.items();
				}
			}

		}
		// Otherwise, we return all the agents that intersect the geometry
		// formed by the shapes of the cells (incl. the
		// cells themselves) and that are accepted by the filter
		final boolean normalFilter = filter.getSpecies() != null || !(filter instanceof Different);
		final IAgentFilter fDL = normalFilter ? filter
				: new DifferentList(getPlaces().getCellSpecies().listValue(scope, Types.NO_TYPE, false));
		final Collection<IAgent> agents = getAgentsIn(scope,
				GamaGeometryType.geometriesToGeometry(scope, GamaListFactory.wrap(Types.AGENT, placesConcerned)), fDL,
				false);
		if (!normalFilter) {
			agents.addAll(placesConcerned);
		}
		return agents;

	}

	@Override
	public void dispose() {
		super.dispose();
		getPlaces().dispose();
	}

	// @Override
	// public IList<GamaSpatialPath> KpathsBetween(final IScope scope, final IShape source, final IShape target,
	// final int k) {
	// // TODO for the moment, returns only 1 shortest path.... need to fix it!
	// return super.KpathsBetween(scope, source, target, k);
	// }
	//
	// @Override
	// public IList<GamaSpatialPath> KpathsBetween(final IScope scope, final GamaPoint source, final GamaPoint target,
	// final int k) {
	// // TODO for the moment, returns only 1 shortest path.... need to fix it!
	// return super.KpathsBetween(scope, source, target, k);
	// }

}
