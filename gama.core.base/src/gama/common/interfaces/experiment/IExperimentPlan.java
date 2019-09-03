/*******************************************************************************************************
 *
 * gama.kernel.experiment.IExperimentPlan.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.common.interfaces.experiment;

import java.util.Map;

import gama.common.interfaces.IModel;
import gama.common.interfaces.batch.IExploration;
import gama.common.interfaces.experiment.IParameter.Batch;
import gama.common.interfaces.outputs.IOutput;
import gama.common.interfaces.outputs.IOutputManager;
import gama.kernel.experiment.ExperimentAgent;
import gama.kernel.simulation.SimulationAgent;
import gama.runtime.scope.IScope;
import gaml.descriptions.ExperimentDescription;
import gaml.species.ISpecies;

/**
 * Written by drogoul Modified on 31 mai 2011
 *
 * @todo Description
 *
 */
public interface IExperimentPlan extends ISpecies {

	String BATCH_CATEGORY_NAME = "Exploration method";
	String TEST_CATEGORY_NAME = "Configuration of tests";
	String EXPLORABLE_CATEGORY_NAME = "Parameters to explore";
	String SYSTEM_CATEGORY_PREFIX = "Random number generation";

	IModel getModel();

	void setModel(final IModel model);

	IOutputManager.Simulation getOriginalSimulationOutputs();

	void refreshAllOutputs();

	void pauseAllOutputs();

	void resumeAllOutputs();

	void synchronizeAllOutputs();

	void unSynchronizeAllOutputs();

	void closeAllOutputs();

	IOutputManager.Experiment getExperimentOutputs();

	boolean isGui();

	boolean hasParameter(String name);

	ExperimentAgent getAgent();

	IScope getExperimentScope();

	void open();

	void reload();

	SimulationAgent getCurrentSimulation();

	Map<String, IParameter> getParameters();

	IExploration getExplorationAlgorithm();

	IOutput.FileBased getLog();

	boolean isBatch();

	boolean isMemorize();

	Map<String, Batch> getExplorableParameters();

	IExperimentController getController();

	/**
	 * @return
	 */
	boolean isHeadless();

	void setHeadless(boolean headless);

	String getExperimentType();

	boolean keepsSeed();

	boolean keepsSimulations();

	boolean hasParametersOrUserCommands();

	void recomputeAndRefreshAllOutputs();

	Iterable<IOutputManager> getActiveOutputManagers();

	boolean isAutorun();

	boolean isTest();

	@Override
	ExperimentDescription getDescription();

	boolean shouldBeBenchmarked();

}