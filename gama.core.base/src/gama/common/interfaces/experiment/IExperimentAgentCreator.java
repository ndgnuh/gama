/*******************************************************************************************************
 *
 * gama.common.interfaces.IExperimentAgentCreator.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.common.interfaces.experiment;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IGamlDescription;
import gama.common.interfaces.IGamlable;
import gama.common.interfaces.INamed;
import gama.metamodel.population.IPopulation;

@FunctionalInterface
public interface IExperimentAgentCreator {

	public static class ExperimentAgentDescription implements IExperimentAgentCreator, IGamlDescription {

		private final IExperimentAgentCreator original;
		private final String name, plugin;

		public ExperimentAgentDescription(final IExperimentAgentCreator original, final String name,
				final String plugin) {
			this.original = original;
			this.name = name;
			this.plugin = plugin;
		}

		/**
		 * Method create()
		 * 
		 * @see gama.common.interfaces.experiment.IExperimentAgentCreator#create(java.lang.Object[])
		 */
		@Override
		public IExperimentAgent create(final IPopulation<? extends IAgent> pop, final int index) {
			return original.create(pop, index);
		}

		/**
		 * Method getName()
		 * 
		 * @see gama.common.interfaces.INamed#getName()
		 */
		@Override
		public String getName() {
			return name;
		}

		/**
		 * Method setName()
		 * 
		 * @see gama.common.interfaces.INamed#setName(java.lang.String)
		 */
		@Override
		public void setName(final String newName) {}

		/**
		 * Method serialize()
		 * 
		 * @see gama.common.interfaces.IGamlable#serialize(boolean)
		 */
		@Override
		public String serialize(final boolean includingBuiltIn) {
			return getName();
		}

		/**
		 * Method getTitle()
		 * 
		 * @see gama.common.interfaces.IGamlDescription#getTitle()
		 */
		@Override
		public String getTitle() {
			return "Experiment Agent supported by " + getName() + " technology";
		}

		/**
		 * Method getDocumentation()
		 * 
		 * @see gama.common.interfaces.IGamlDescription#getDocumentation()
		 */
		@Override
		public String getDocumentation() {
			return "";
		}

		/**
		 * Method getDefiningPlugin()
		 * 
		 * @see gama.common.interfaces.IGamlDescription#getDefiningPlugin()
		 */
		@Override
		public String getDefiningPlugin() {
			return plugin;
		}

		/**
		 * Method collectPlugins()
		 * 
		 * @see gama.common.interfaces.IGamlDescription#collectPlugins(java.util.Set)
		 */
		// @Override
		// public void collectMetaInformation(final GamlProperties meta) {
		// meta.put(GamlProperties.PLUGINS, plugin);
		// }
	}

	IExperimentAgent create(IPopulation<? extends IAgent> pop, int index);

}