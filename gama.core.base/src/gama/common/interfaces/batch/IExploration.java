/*******************************************************************************************************
 *
 * gama.kernel.batch.IExploration.java, in plugin gama.core,
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
import gama.kernel.experiment.*;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.compilation.interfaces.ISymbol;
import gaml.expressions.IExpression;

/**
 * The class IExploration.
 *
 * @author drogoul
 * @since 26 d�c. 2011
 *
 */
public interface IExploration extends ISymbol {// , Runnable {

	public final static short C_MAX = 0, C_MIN = 1, C_MEAN = 2;

	public abstract void initializeFor(IScope scope, final BatchAgent agent) throws GamaRuntimeException;

	public abstract String getCombinationName();

	public abstract void addParametersTo(final List<IParameter.Batch> exp, BatchAgent agent);

	public abstract Double getBestFitness();

	public abstract IExpression getFitnessExpression();

	public abstract ParametersSet getBestSolution();

	public abstract short getCombination();
	
	public abstract void updateBestFitness(ParametersSet solution, Double fitness);
		

	/**
	 * @param scope
	 */
	public abstract void run(IScope scope);

}