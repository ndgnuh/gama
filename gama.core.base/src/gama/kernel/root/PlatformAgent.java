/*******************************************************************************************************
 *
 * gama.kernel.root.PlatformAgent.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.kernel.root;

import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import gama.GAMA;
import gama.common.interfaces.IExecutable;
import gama.common.interfaces.IKeyword;
import gama.common.interfaces.experiment.IExperimentAgent;
import gama.common.interfaces.experiment.ITopLevelAgent;
import gama.common.interfaces.outputs.IOutputManager;
import gama.common.preferences.GamaPreferences;
import gama.common.util.MemoryUtils;
import gama.common.util.RandomUtils;
import gama.kernel.simulation.SimulationAgent;
import gama.kernel.simulation.SimulationClock;
import gama.metamodel.agent.GamlAgent;
import gama.metamodel.population.GamaPopulation;
import gama.metamodel.population.IPopulation;
import gama.metamodel.topology.ITopology;
import gama.metamodel.topology.continuous.AmorphousTopology;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.getter;
import gama.processor.annotations.GamlAnnotations.species;
import gama.processor.annotations.GamlAnnotations.variable;
import gama.processor.annotations.GamlAnnotations.vars;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.ExecutionScope;
import gama.runtime.scope.IScope;
import gama.util.GamaColor;
import gama.util.list.IList;
import gaml.compilation.kernel.GamaMetaModel;
import gaml.expressions.IExpression;
import gaml.operators.Containers;
import gaml.species.ISpecies;
import gaml.types.IType;
import gaml.types.Types;
import one.util.streamex.StreamEx;

@species (
		name = IKeyword.PLATFORM,
		internal = true,
		doc = { @doc ("The species of the unique platform agent, called 'gama'") })
@vars ({ @variable (
		name = PlatformAgent.MACHINE_TIME,
		type = IType.FLOAT,
		doc = @doc (
				value = "Returns the current system time in milliseconds (i.e. number of milliseconds since UNIX epoch day)",
				comment = "The return value is a float number")),
		@variable (
				name = PlatformAgent.WORKSPACE_PATH,
				type = IType.STRING,
				constant = true,
				doc = @doc (
						value = "Contains the absolute path to the workspace of GAMA. Can be used to list all the projects and files present in the platform",
						comment = "Always terminated with a trailing separator",
						see = { "workspace" })),
		@variable (
				name = "version",
				type = IType.STRING,
				constant = true,
				doc = @doc (
						value = "Returns the version of the current GAMA installation")),
		@variable (
				name = "plugins",
				type = IType.LIST,
				of = IType.STRING,
				constant = true,
				doc = @doc (
						value = "Lists all the plugins present in this installation of GAMA")),
		@variable (
				name = "free_memory",
				type = IType.INT,
				constant = false,
				doc = @doc (
						value = "Returns the free memory available to GAMA in bytes")),
		@variable (
				name = "max_memory",
				type = IType.INT,
				constant = false,
				doc = @doc (
						value = "Returns the maximum amount of memory available to GAMA in bytes")),
		@variable (
				name = "workspace",
				type = IType.FILE,
				constant = true,
				doc = @doc (
						value = "A folder representing the workspace of GAMA. Can be used to list all the projects and files present in the platform",
						see = { "workspace_path" })), })
public class PlatformAgent extends GamlAgent implements ITopLevelAgent, IExpression {

	public static final String WORKSPACE_PATH = "workspace_path";
	public static final String MACHINE_TIME = "machine_time";
	private final Timer polling = new Timer();
	final IScope basicScope;
	private TimerTask currentTask;

	public PlatformAgent() {
		this(new GamaPopulation<PlatformAgent>(null,
				GamaMetaModel.INSTANCE.getAbstractModelSpecies().getMicroSpecies(IKeyword.PLATFORM)), 0);
	}

	public PlatformAgent(final IPopulation<PlatformAgent> pop, final int index) {
		super(pop, index);
		basicScope = new ExecutionScope(this, "Gama platform scope");
		if (GamaPreferences.Runtime.CORE_MEMORY_POLLING.getValue()) {
			startPollingMemory();
		}
		GamaPreferences.Runtime.CORE_MEMORY_POLLING.onChange((newValue) -> {
			if (newValue) {
				startPollingMemory();
			} else {
				stopPollingMemory();
			}
		});
		GamaPreferences.Runtime.CORE_MEMORY_FREQUENCY.onChange((newValue) -> {
			stopPollingMemory();
			startPollingMemory();
		});
	}

	private void startPollingMemory() {
		if (currentTask == null) {
			currentTask = new TimerTask() {
				@Override
				public void run() {
					if (MemoryUtils.memoryIsLow()) {
						final IExperimentAgent agent = getExperiment();
						if (agent != null) {
							final long mb = (long) (MemoryUtils.availableMemory() / 1000000d);
							final GamaRuntimeException e = GamaRuntimeException.warning("Memory is low (" + mb
									+ " megabytes). You should close the experiment, exit GAMA and give it more memory",
									agent.getScope());
							GAMA.reportError(basicScope, e, false);
						}
					}
				}
			};
		}
		polling.scheduleAtFixedRate(currentTask, 0,
				(long) 1000 * GamaPreferences.Runtime.CORE_MEMORY_FREQUENCY.getValue());
	}

	private void stopPollingMemory() {
		if (currentTask != null) {
			currentTask.cancel();
			currentTask = null;
		}
	}

	@Override
	public Object primDie(final IScope scope) {
		stopPollingMemory();
		polling.cancel();
		GAMA.closeAllExperiments(false, true);
		scope.getGui().exit();
		return null;
	}

	@Override
	public boolean isContextIndependant() {
		return false;
	}

	@Override
	public ITopology getTopology() {
		return new AmorphousTopology();
	}

	@Override
	public String getName() {
		return "gama";
	}

	@Override
	public String serialize(final boolean includingBuiltIn) {
		return "gama";
	}

	@Override
	public ISpecies getSpecies() {
		return getPopulation().getSpecies();
	}

	@Override
	public SimulationClock getClock() {
		return new SimulationClock(getScope());
	}

	@Override
	public IScope getScope() {
		return basicScope;
	}

	@Override
	public GamaColor getColor() {
		return GamaColor.NamedGamaColor.colors.get("gamaorange");
	}

	@Override
	public RandomUtils getRandomGenerator() {
		return new RandomUtils();
	}

	@Override
	public IOutputManager getOutputManager() {
		if (getExperiment() != null)
			return getExperiment().getOutputManager();
		return null;
	}

	@Override
	public void postEndAction(final IExecutable executable) {}

	@Override
	public void postDisposeAction(final IExecutable executable) {}

	@Override
	public void postOneShotAction(final IExecutable executable) {}

	@Override
	public void executeAction(final IExecutable executable) {}

	@Override
	public boolean isOnUserHold() {
		return false;
	}

	@Override
	public void setOnUserHold(final boolean state) {}

	@Override
	public SimulationAgent getSimulation() {
		return GAMA.getSimulation();
	}

	@Override
	public IExperimentAgent getExperiment() {
		if (GAMA.getExperiment() != null)
			return GAMA.getExperiment().getAgent();
		return null;
	}

	@getter (
			value = WORKSPACE_PATH,
			initializer = true)
	public String getWorkspacePath() {
		final URL url = Platform.getInstanceLocation().getURL();
		return url.getPath();
	}

	@SuppressWarnings ("unchecked")
	@getter (
			value = "plugins",
			initializer = true)
	public IList<String> getPluginsList() {
		final BundleContext bc = FrameworkUtil.getBundle(getClass()).getBundleContext();
		return StreamEx.of(bc.getBundles()).map(b -> b.getSymbolicName()).toCollection(Containers.listOf(Types.STRING));
	}

	@getter (
			value = "version",
			initializer = true)
	public String getVersion() {
		final BundleContext bc = FrameworkUtil.getBundle(getClass()).getBundleContext();
		return bc.getBundle().getVersion().toString();
	}

	@getter (
			value = "free_memory",
			initializer = true)
	public int getAvailableMemory() {
		final long allocatedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		final long presumableFreeMemory = Runtime.getRuntime().maxMemory() - allocatedMemory;
		return (int) presumableFreeMemory;
	}

	@getter (
			value = "max_memory",
			initializer = true)
	public int getMaxMemory() {
		return (int) Runtime.getRuntime().maxMemory();
	}

	@getter (PlatformAgent.MACHINE_TIME)
	public Double getMachineTime() {
		return (double) System.currentTimeMillis();
	}

	@Override
	public String getTitle() {
		return "gama platform agent";
	}

	@Override
	public String getDocumentation() {
		return "The unique instance of the platform species. Used to access GAMA platform properties.";
	}

	@Override
	public String getDefiningPlugin() {
		return "gama.core";
	}

	@Override
	public Object value(final IScope scope) throws GamaRuntimeException {
		return this;
	}

	@Override
	public boolean isConst() {
		return false;
	}

	@Override
	public String literalValue() {
		return IKeyword.GAMA;
	}

	@Override
	public IExpression resolveAgainst(final IScope scope) {
		return this;
	}

	@Override
	public boolean shouldBeParenthesized() {
		return false;
	}

	@Override
	public IType<?> getGamlType() {
		return Types.get(IKeyword.PLATFORM);
	}
}
