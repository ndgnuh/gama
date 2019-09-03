/*******************************************************************************************************
 *
 * gama.kernel.batch.LocalSearchAlgorithm.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.kernel.batch;

import java.util.List;

import gama.common.interfaces.experiment.IParameter;
import gama.kernel.experiment.BatchAgent;
import gama.kernel.experiment.ParametersSet;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.descriptions.IDescription;

public abstract class LocalSearchAlgorithm extends ParamSpaceExploAlgorithm {

	protected Neighborhood neighborhood;
	protected ParametersSet solutionInit;

	public LocalSearchAlgorithm(final IDescription species) {
		super(species);
	}

	@Override
	public void initializeFor(final IScope scope, final BatchAgent agent) throws GamaRuntimeException {
		super.initializeFor(scope, agent);
		final List<IParameter.Batch> v = agent.getParametersToExplore();
		neighborhood = new Neighborhood1Var(v);
		solutionInit = new ParametersSet(scope, v, true);
	}

}
