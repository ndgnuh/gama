/*******************************************************************************************************
 *
 * gama.kernel.batch.Mutation.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.common.interfaces.batch;

import java.util.List;

import gama.common.interfaces.experiment.IParameter;
import gama.kernel.batch.Chromosome;
import gama.runtime.scope.IScope;

public interface Mutation {

	public Chromosome mutate(IScope scope, Chromosome chromosome, List<IParameter.Batch> variables);

}
