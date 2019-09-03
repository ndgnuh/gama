/*******************************************************************************************************
 *
 * gama.util.graph.AbstractGraphEdgeAgent.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.util.graph;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IKeyword;
import gama.metamodel.agent.GamlAgent;
import gama.metamodel.population.IPopulation;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.species;
import gama.processor.annotations.GamlAnnotations.variable;
import gama.processor.annotations.GamlAnnotations.vars;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.types.GamaGeometryType;
import gaml.types.IType;

// FIXME: Add all the necessary variables and actions ?
// FIXME:
@species (
		name = "graph_edge",
		doc = @doc ("A species that represents an edge of a graph made of agents. The source and the target of the edge should be agents"))
@vars ({ @variable (
		name = IKeyword.SOURCE,
		type = IType.AGENT,
		doc = @doc ("The source agent of this edge")),
		@variable (
				name = IKeyword.TARGET,
				type = IType.AGENT,
				doc = @doc ("The target agent of this edge")) })
@doc ("A species that represents an edge of a graph made of agents. The source and the target of the edge should be agents")
public class AbstractGraphEdgeAgent extends GamlAgent {
	public AbstractGraphEdgeAgent(final IPopulation<? extends IAgent> s, final int index) throws GamaRuntimeException {
		super(s, index);
	}

	@Override
	public Object _step_(final IScope scope) {
		// if ( scope.interrupted() || dead() ) { return null; }
		final IAgent s = (IAgent) getAttribute(IKeyword.SOURCE);
		final IAgent t = (IAgent) getAttribute(IKeyword.TARGET);
		if (s == null || t == null) { return null; }
		setGeometry(GamaGeometryType.buildLine(s.getLocation(), t.getLocation()));
		return super._step_(scope);
	}

}
