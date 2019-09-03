/*******************************************************************************************************
 *
 * gaml.compilation.IAgentConstructor.java, in plugin gama.core, is part of the source code of the GAMA modeling
 * and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.compilation.interfaces;

import java.util.HashMap;
import java.util.Map;

import gama.common.interfaces.IAgent;
import gama.metamodel.agent.GamlAgent;
import gama.metamodel.agent.MinimalAgent;
import gama.metamodel.population.IPopulation;

/**
 * Written by drogoul Modified on 20 ao�t 2010
 *
 *
 */
@SuppressWarnings ({ "unchecked", "rawtypes" })
@FunctionalInterface
public interface IAgentConstructor<T extends IAgent> {

	public static class Minimal implements IAgentConstructor<MinimalAgent> {

		/**
		 * Method createOneAgent()
		 *
		 * @see gaml.compilation.interfaces.IAgentConstructor#createOneAgent(gama.metamodel.population.IPopulation)
		 */

		@Override
		public MinimalAgent createOneAgent(final IPopulation manager, final int index) {
			return new MinimalAgent(manager, index);
		}

	}

	public static class Gaml implements IAgentConstructor<GamlAgent> {

		@Override
		public GamlAgent createOneAgent(final IPopulation manager, final int index) {
			return new GamlAgent(manager, index);
		}

	}

	Map<Class<? extends IAgent>, IAgentConstructor<? extends IAgent>> CONSTRUCTORS =
			new HashMap<Class<? extends IAgent>, IAgentConstructor<? extends IAgent>>() {

				{
					put(GamlAgent.class, new Gaml());
					put(MinimalAgent.class, new Minimal());
				}
			};

	<T extends IAgent> T createOneAgent(IPopulation<T> manager, int index);

}
