/*******************************************************************************************************
 *
 * msi.gama.kernel.experiment.ITopLevelAgent.java, in plugin msi.gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gama.common.interfaces.experiment;

import msi.gama.common.interfaces.IExecutable;
import msi.gama.common.interfaces.IMacroAgent;
import msi.gama.common.interfaces.outputs.IOutputManager;
import msi.gama.common.util.RandomUtils;
import msi.gama.kernel.simulation.SimulationAgent;
import msi.gama.kernel.simulation.SimulationClock;
import msi.gama.util.GamaColor;

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
