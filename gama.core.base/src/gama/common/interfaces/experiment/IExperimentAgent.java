/*******************************************************************************************************
 *
 * gama.kernel.experiment.IExperimentAgent.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.common.interfaces.experiment;

import java.util.List;

import gama.kernel.simulation.SimulationAgent;
import gama.kernel.simulation.SimulationPopulation;

public interface IExperimentAgent extends ITopLevelAgent {

	@Override
	public abstract IExperimentPlan getSpecies();

	public String getWorkingPath();

	public List<String> getWorkingPaths();

	public abstract Boolean getWarningsAsErrors();

	public abstract Double getMinimumDuration();

	public abstract void setMinimumDuration(Double d);

	void closeSimulations();

	public abstract void closeSimulation(SimulationAgent simulationAgent);

	public abstract SimulationPopulation getSimulationPopulation();

	public boolean isMemorize();

	public boolean canStepBack();

	public abstract void informStatus();

	public abstract boolean isHeadless();

}
