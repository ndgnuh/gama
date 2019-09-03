/*******************************************************************************************************
 *
 * gama.kernel.experiment.ITopLevelAgent.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.common.interfaces.experiment;

import gama.common.interfaces.IExecutable;
import gama.common.interfaces.IMacroAgent;
import gama.common.interfaces.outputs.IOutputManager;
import gama.common.util.RandomUtils;
import gama.kernel.simulation.SimulationAgent;
import gama.kernel.simulation.SimulationClock;
import gama.util.GamaColor;

/**
 * Class ITopLevelAgent.
 *
 * @author drogoul
 * @since 27 janv. 2016
 *
 */
public interface ITopLevelAgent extends IMacroAgent {

	SimulationClock getClock();

	GamaColor getColor();

	RandomUtils getRandomGenerator();

	IOutputManager getOutputManager();

	void postEndAction(IExecutable executable);

	void postDisposeAction(IExecutable executable);

	void postOneShotAction(IExecutable executable);

	void executeAction(IExecutable executable);

	boolean isOnUserHold();

	void setOnUserHold(boolean state);

	SimulationAgent getSimulation();

	IExperimentAgent getExperiment();

}
