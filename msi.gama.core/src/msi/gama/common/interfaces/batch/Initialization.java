/*******************************************************************************************************
 *
 * msi.gama.kernel.batch.Initialization.java, in plugin msi.gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package msi.gama.common.interfaces.batch;

import java.util.List;

import msi.gama.common.interfaces.experiment.IParameter;
import msi.gama.kernel.batch.Chromosome;
import msi.gama.kernel.batch.GeneticAlgorithm;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;

public interface Initialization {

	public List<Chromosome> initializePop(IScope scope, List<IParameter.Batch> variables, GeneticAlgorithm algo) throws GamaRuntimeException;
}
