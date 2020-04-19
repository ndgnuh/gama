/*******************************************************************************************************
 *
 * gama.core.outputs.layers.EventLayerStatement.java, in plugin msi.gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.outputs.layers;

import gama.common.interfaces.IGamlIssue;
import gama.common.interfaces.IKeyword;
import gama.common.interfaces.outputs.IEventLayerDelegate;
import gama.common.interfaces.outputs.IEventLayerStatement;
import gama.core.outputs.layers.EventLayerStatement.EventLayerValidator;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.example;
import gama.processor.annotations.GamlAnnotations.facet;
import gama.processor.annotations.GamlAnnotations.facets;
import gama.processor.annotations.GamlAnnotations.inside;
import gama.processor.annotations.GamlAnnotations.symbol;
import gama.processor.annotations.GamlAnnotations.usage;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.compilation.annotations.validator;
import gaml.compilation.interfaces.IDescriptionValidator;
import gaml.descriptions.IDescription;
import gaml.descriptions.StatementDescription;
import gaml.expressions.IExpression;
import gaml.types.IType;

/**
 * Written by Marilleau Modified on 16 novembre 2012
 *
 * @todo Description
 *
 */
@symbol (
		name = IKeyword.EVENT,
		kind = ISymbolKind.LAYER,
		with_sequence = true,
		concept = { IConcept.GUI })
@inside (
		symbols = { IKeyword.DISPLAY })
@facets (
		value = { @facet (
				name = "unused",
				type = IType.ID,
				values = { IKeyword.MOUSE_UP, IKeyword.MOUSE_DOWN, IKeyword.MOUSE_MOVED, IKeyword.MOUSE_ENTERED,
						IKeyword.MOUSE_EXITED, IKeyword.MOUSE_MENU },
				optional = true,
				doc = @doc (
						value = "an unused facet that serves only for the purpose of declaring the string values"),
				internal = true),
				@facet (
						name = IKeyword.NAME,
						type = IType.ID,
						optional = false,
						doc = @doc ("the type of event captured: can be  \"mouse_up\", \"mouse_down\", \"mouse_move\", \"mouse_exit\", \"mouse_enter\", \"mouse_menu\" or a character")),
				@facet (
						name = IKeyword.TYPE,
						type = IType.STRING,
						optional = true,
						doc = @doc ("Type of peripheric used to generate events. Defaults to 'default', which encompasses keyboard and mouse")),
				@facet (
						name = IKeyword.ACTION,
						type = IType.ACTION,
						optional = false,
						doc = @doc ("Either a block of statements to execute in the context of the experiment or the identifier of the action to be executed in the context of the simulation. This action needs to be defined in 'global' or in the current experiment, without any arguments. The location of the mouse in the world can be retrieved in this action with the pseudo-constant #user_location")) },
		omissible = IKeyword.NAME)
@validator (EventLayerValidator.class)
@doc (
		value = "`" + IKeyword.EVENT
				+ "` allows to interact with the simulation by capturing mouse or key events and doing an action. This action needs to be defined in 'global' or in the current experiment, without any arguments. The location of the mouse in the world can be retrieved in this action with the pseudo-constant #user_location",
		usages = { @usage (
				value = "The general syntax is:",
				examples = { @example (
						value = "event [event_type] action: myAction;",
						isExecutable = false) }),
				@usage (
						value = "For instance:",
						examples = { @example (
								value = "global {",
								isExecutable = false),
								@example (
										value = "   // ... ",
										isExecutable = false),
								@example (
										value = "   action myAction () {",
										isExecutable = false),
								@example (
										value = "      point loc <- #user_location; // contains the location of the mouse in the world",
										isExecutable = false),
								@example (
										value = "      list<agent> selected_agents <- agents inside (10#m around loc); // contains agents clicked by the event",
										isExecutable = false),
								@example (
										value = "      ",
										isExecutable = false),
								@example (
										value = "      // code written by modelers",
										isExecutable = false),
								@example (
										value = "   }",
										isExecutable = false),
								@example (
										value = "}",
										isExecutable = false),
								@example (
										value = "",
										isExecutable = false),
								@example (
										value = "experiment Simple type:gui {",
										isExecutable = false),
								@example (
										value = "   display my_display {",
										isExecutable = false),
								@example (
										value = "      event mouse_up action: myAction;",
										isExecutable = false),
								@example (
										value = "   }",
										isExecutable = false),
								@example (
										value = "}",
										isExecutable = false) }) },
		see = { IKeyword.DISPLAY, IKeyword.AGENTS, IKeyword.CHART, "graphics", IKeyword.GRID_POPULATION, IKeyword.IMAGE,
				IKeyword.OVERLAY, IKeyword.POPULATION, })
public class EventLayerStatement extends AbstractLayerStatement implements IEventLayerStatement {

	public static class EventLayerValidator implements IDescriptionValidator<StatementDescription> {

		@Override
		public void validate(final StatementDescription description) {
			final String name = description.getLitteral(NAME);
			if (name.length() > 1) {
				String error = "";
				boolean foundEventName = false;
				for (final IEventLayerDelegate delegate : DELEGATES) {
					error += delegate.getEvents() + " ";
					if (delegate.getEvents().contains(name)) {
						foundEventName = true;
					}
				}
				if (!foundEventName) {
					description.error("No event can be triggered for '" + name + "'. Acceptable values are " + error
							+ " or a character", IGamlIssue.UNKNOWN_ARGUMENT, NAME);
					return;
				}
			}

			final String actionName = description.getLitteral(ACTION);
			StatementDescription sd = description.getModelDescription().getAction(actionName);
			if (sd == null) {
				// we look into the experiment
				final IDescription superDesc = description.getSpeciesContext();
				sd = superDesc.getAction(actionName);
			}
			if (sd == null) {
				description.error("Action '" + actionName + "' is not defined in neither 'global' nor 'experiment'",
						IGamlIssue.UNKNOWN_ACTION, ACTION);
				return;
			} else if (sd.getPassedArgs().size() > 0) {
				description.error("Action '" + actionName
						+ "' cannot have arguments. Use '#user_location' inside to obtain the location of the mouse, and compute the selected agents in the action using GAML spatial operators",
						IGamlIssue.DIFFERENT_ARGUMENTS, ACTION);
			}
		}
	}

	private final boolean executesInSimulation;
	private final IExpression type;

	public EventLayerStatement(final IDescription desc) throws GamaRuntimeException {
		super(/* context, */desc);
		final String actionName = description.getLitteral(IKeyword.ACTION);
		final StatementDescription sd = description.getSpeciesContext().getAction(actionName);
		executesInSimulation = sd == null;
		type = getFacet(IKeyword.TYPE);
	}

	public boolean executesInSimulation() {
		return executesInSimulation;
	}

	@Override
	public boolean _init(final IScope scope) throws GamaRuntimeException {

		final Object source = getSource(scope);

		for (final IEventLayerDelegate delegate : DELEGATES) {
			if (delegate.acceptSource(scope, source)) {
				delegate.createFrom(scope, source, this);
			}
		}
		return true;
	}

	@Override
	public LayerType getType(final boolean output) {
		return LayerType.EVENT;
	}

	@Override
	public String toString() {
		return "Event layer: " + this.getFacet(IKeyword.NAME).literalValue();
	}

	/**
	 * Method _step()
	 *
	 * @see gama.core.outputs.layers.AbstractLayerStatement#_step(msi.gama.runtime.scope.IScope)
	 */
	@Override
	protected boolean _step(final IScope scope) {
		return true;
	}

	private Object getSource(final IScope scope) {
		final Object source = type == null ? IKeyword.DEFAULT : type.value(scope);
		return source;
	}
}
