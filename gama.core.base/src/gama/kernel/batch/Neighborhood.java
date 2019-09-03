/*******************************************************************************************************
 *
 * gama.kernel.batch.Neighborhood.java, in plugin gama.core,
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
import gama.kernel.experiment.*;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;

public abstract class Neighborhood {

	protected final List<IParameter.Batch> variables;

	public Neighborhood(final List<IParameter.Batch> variables) {
		this.variables = variables;
	}

	public abstract List<ParametersSet> neighbor(IScope scope, final ParametersSet solution)
		throws GamaRuntimeException;

}
