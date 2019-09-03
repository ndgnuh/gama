/*******************************************************************************************************
 *
 * gama.metamodel.topology.continuous.RootTopology.java, in plugin gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.metamodel.topology.continuous;

import gama.common.geometry.Envelope3D;
import gama.common.interfaces.IAgent;
import gama.metamodel.population.IPopulation;
import gama.metamodel.shape.IShape;
import gama.metamodel.topology.CompoundSpatialIndex;
import gama.metamodel.topology.GamaQuadTree;
import gama.metamodel.topology.ISpatialIndex;
import gama.metamodel.topology.ISpatialIndex.Compound;
import gama.runtime.scope.IScope;

public class RootTopology extends ContinuousTopology {

	private ISpatialIndex spatialIndex;
	private final boolean isTorus;

	public RootTopology(final IScope scope, final IShape geom, final boolean isTorus, final boolean hasParallelism) {
		super(scope, geom);
		final Envelope3D bounds = geom.getEnvelope();
		// spatialIndex = new CompoundSpatialIndex(bounds, hasParallelism);
		spatialIndex = GamaQuadTree.create(bounds, hasParallelism);
		this.isTorus = isTorus;
		root = this;
	}

	@Override
	public ISpatialIndex getSpatialIndex() {
		return spatialIndex;
	}

	public void updateEnvironment(final IShape newEnv) {
		spatialIndex.updateWith(newEnv.getEnvelope());
	}

	@Override
	public boolean isTorus() {
		return isTorus;
	}

	@Override
	public void setRoot(final IScope scope, final RootTopology root) {}

	public void turnSpatialIndexToCompound() {
		if (spatialIndex instanceof ISpatialIndex.Compound) { return; }
		final ISpatialIndex quad = spatialIndex;
		spatialIndex = new CompoundSpatialIndex(quad.getBounds(), quad.isParallel(), quad);
	}

	public void mergeWith(final RootTopology other) {
		turnSpatialIndexToCompound();
		other.turnSpatialIndexToCompound();
		((Compound) spatialIndex).mergeWith((Compound) other.spatialIndex);
	}

	@Override
	public void dispose() {
		super.dispose();
		spatialIndex.dispose();
	}

	public void addSpatialIndex(final ISpatialIndex index, final IPopulation<? extends IAgent> pop) {
		turnSpatialIndexToCompound();
		((Compound) spatialIndex).add(index, pop);
	}

	public void remove(final IPopulation<? extends IAgent> pop) {
		if (spatialIndex instanceof ISpatialIndex.Compound) {
			((Compound) spatialIndex).remove(pop);
		}
	}

}