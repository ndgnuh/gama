/*******************************************************************************************************
 *
 * gama.util.graph.AbstractGraphNodeAgent.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.util.graph;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IKeyword;
import gama.common.interfaces.IStatement;
import gama.dev.utils.DEBUG;
import gama.metamodel.agent.GamlAgent;
import gama.metamodel.population.IPopulation;
import gama.metamodel.topology.graph.GamaSpatialGraph.VertexRelationship;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.GamlAnnotations.action;
import gama.processor.annotations.GamlAnnotations.arg;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.getter;
import gama.processor.annotations.GamlAnnotations.species;
import gama.processor.annotations.GamlAnnotations.variable;
import gama.processor.annotations.GamlAnnotations.vars;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.ExecutionResult;
import gama.runtime.scope.IScope;
import gaml.descriptions.ConstantExpressionDescription;
import gaml.operators.Cast;
import gaml.statements.Arguments;
import gaml.types.IType;

// FIXME: Add all the necessary variables (degree, neighbors, edges)
@species (
		name = "graph_node",
		concept = { IConcept.GRAPH, IConcept.NODE },
		doc = @doc ("A base species to use as a parent for species representing agents that are nodes of a graph"))
@vars ({ @variable (
		name = IKeyword.MYGRAPH,
		type = IType.GRAPH,
		doc = @doc ("A reference to the graph containing the agent")) })
@doc ("A base species to use as a parent for species representing agents that are nodes of a graph")
public class AbstractGraphNodeAgent extends GamlAgent {

	final static Arguments args = new Arguments();

	public static class NodeRelation implements VertexRelationship<AbstractGraphNodeAgent> {

		IStatement.WithArgs action;

		@Override
		public boolean related(final IScope scope, final AbstractGraphNodeAgent p1, final AbstractGraphNodeAgent p2) {
			args.put("other", ConstantExpressionDescription.create(p2));
			final ExecutionResult result = scope.execute(getAction(p1), p1, args);
			return Cast.asBool(scope, result.getValue());
		}

		@Override
		public boolean equivalent(final IScope scope, final AbstractGraphNodeAgent p1,
				final AbstractGraphNodeAgent p2) {
			return p1 == p2;
		}

		IStatement.WithArgs getAction(final AbstractGraphNodeAgent a1) {
			if (action == null) {
				action = a1.getAction();
			}
			return action;
		}

	}

	public AbstractGraphNodeAgent(final IPopulation<? extends IAgent> s, final int index) throws GamaRuntimeException {
		super(s, index);
	}

	IStatement.WithArgs getAction() {
		return getSpecies().getAction("related_to");
	}

	@action (
			doc = @doc ("This operator should never be called"),
			name = "related_to",
			virtual = true,
			args = { @arg (
					doc = @doc ("The other agent"),
					name = "other",
					optional = false,
					type = IType.AGENT) })
	public Boolean relatedTo(final IScope scope) {
		DEBUG.LOG("Should never be called !");
		return false;
	}

	@SuppressWarnings ("rawtypes")
	@getter (IKeyword.MYGRAPH)
	public GamaGraph getGraph() {
		return (GamaGraph) getTopology().getPlaces();
	}
}
