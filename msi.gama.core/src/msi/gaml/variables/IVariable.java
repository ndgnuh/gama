/*******************************************************************************************************
 *
 * msi.gaml.variables.IVariable.java, in plugin msi.gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package msi.gaml.variables;

import msi.gama.common.interfaces.IAgent;
import msi.gama.common.interfaces.experiment.IParameter;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.compilation.interfaces.ISymbol;

/**
 * @author drogoul
 */
public interface IVariable extends ISymbol, IParameter {

	public abstract boolean isUpdatable();

	public abstract boolean isParameter();

	public abstract boolean isFunction();

	public abstract boolean isMicroPopulation();

	public abstract boolean isConst();

	public abstract void initializeWith(IScope scope, IAgent gamaObject, Object object) throws GamaRuntimeException;

	public abstract void setVal(IScope scope, IAgent agent, Object v) throws GamaRuntimeException;

	public abstract Object value(IScope scope, IAgent agent) throws GamaRuntimeException;

	public abstract Object getUpdatedValue(final IScope scope);
}