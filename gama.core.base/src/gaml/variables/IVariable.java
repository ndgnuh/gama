/*******************************************************************************************************
 *
 * gaml.variables.IVariable.java, in plugin gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.variables;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.experiment.IParameter;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.compilation.interfaces.ISymbol;

/**
 * @author drogoul
 */
public interface IVariable extends ISymbol, IParameter {

	boolean isUpdatable();

	boolean isNotModifiable();

	boolean isParameter();

	boolean isFunction();

	boolean isMicroPopulation();

	void initializeWith(IScope scope, IAgent gamaObject, Object object) throws GamaRuntimeException;

	void setVal(IScope scope, IAgent agent, Object v) throws GamaRuntimeException;

	Object value(IScope scope, IAgent agent) throws GamaRuntimeException;

	Object getUpdatedValue(final IScope scope);

}