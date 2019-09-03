/*******************************************************************************************************
 *
 * msi.gama.util.graph.BaseGraphEdgeAgent.java, in plugin msi.gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package msi.gama.util.graph;

import msi.gama.common.interfaces.IAgent;
import msi.gama.metamodel.population.IPopulation;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.species;

@species (
		name = "base_edge",
		doc = @doc ("A built-in species for agents representing the edges of a graph, from which one can inherit"))
@doc ("A built-in species for agents representing the edges of a graph, from which one can inherit")
public class BaseGraphEdgeAgent extends AbstractGraphEdgeAgent {

	public BaseGraphEdgeAgent(final IPopulation<? extends IAgent> s, final int index) throws GamaRuntimeException {
		super(s, index);
	}

}