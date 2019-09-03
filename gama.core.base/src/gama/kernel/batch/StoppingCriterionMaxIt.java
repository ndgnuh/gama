/*******************************************************************************************************
 *
 * gama.kernel.batch.StoppingCriterionMaxIt.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.kernel.batch;

import java.util.Map;

import gama.common.interfaces.batch.StoppingCriterion;

public class StoppingCriterionMaxIt implements StoppingCriterion {

	private final int maxIt;

	public StoppingCriterionMaxIt(final int maxIt) {
		super();
		this.maxIt = maxIt;
	}

	@Override
	@SuppressWarnings("boxing")
	public boolean stopSearchProcess(final Map<String, Object> parameters) {
		return (Integer) parameters.get("Iteration") > maxIt;
	}

}
