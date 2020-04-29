/*******************************************************************************************************
 *
 * gaml.architecture.user.UserInputStatement.java, in plugin gama.core, is part of the source code of the GAMA modeling
 * and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.architecture.user;

import java.util.List;

import gama.common.interfaces.IKeyword;
import gama.common.interfaces.experiment.IParameter;
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
import gama.util.GamaColor;
import gaml.descriptions.IDescription;
import gaml.expressions.IExpression;
import gaml.operators.Cast;
import gaml.statements.AbstractPlaceHolderStatement;
import gaml.types.IType;
import gaml.types.Types;

/**
 * Written by drogoul Modified on 7 févr. 2010
 *
 * @todo Description
 *
 */
@symbol (
		name = { IKeyword.USER_INPUT },
		kind = ISymbolKind.SINGLE_STATEMENT,
		with_sequence = false,
		concept = { IConcept.GUI })
@inside (
		symbols = IKeyword.USER_COMMAND)
@facets (
		value = { @facet (
				name = IKeyword.NAME,
				type = IType.LABEL,
				optional = true,
				doc = @doc ("the displayed name")),
				@facet (
						name = IKeyword.TYPE,
						type = IType.TYPE_ID,
						optional = true,
						doc = @doc ("the variable type")),
				@facet (
						name = IKeyword.INIT,
						type = IType.NONE,
						optional = false,
						doc = @doc ("the init value")),
				@facet (
						name = IKeyword.MIN,
						type = IType.FLOAT,
						optional = true,
						doc = @doc ("the minimum value")),
				@facet (
						name = "slider",
						type = IType.BOOL,
						optional = true,
						doc = @doc ("Whether to display a slider or not when applicable")),
				@facet (
						name = IKeyword.MAX,
						type = IType.FLOAT,
						optional = true,
						doc = @doc ("the maximum value")),
				@facet (
						name = IKeyword.RETURNS,
						type = IType.NEW_TEMP_ID,
						optional = false,
						doc = @doc ("a new local variable containing the value given by the user")),
				@facet (
						name = IKeyword.AMONG,
						type = IType.LIST,
						of = IType.STRING,
						optional = true,
						doc = @doc ("the set of acceptable values")) },
		omissible = IKeyword.NAME)
@doc (
		value = "It allows to let the user define the value of a variable.",
		usages = { @usage (
				value = "",
				examples = { @example (
						value = "user_panel \"Advanced Control\" {",
						isExecutable = false),
						@example (
								value = "	user_input \"Location\" returns: loc type: point <- {0,0};",
								isExecutable = false),
						@example (
								value = "	create cells number: 10 with: [location::loc];",
								isExecutable = false),
						@example (
								value = "}",
								isExecutable = false) }) },
		see = { IKeyword.USER_COMMAND, IKeyword.USER_INIT, IKeyword.USER_PANEL })
@SuppressWarnings ({ "rawtypes" })
public class UserInputStatement extends AbstractPlaceHolderStatement implements IParameter {

	// int order;
	// static int index;
	boolean isValued;
	Object initialValue, currentValue;
	IExpression min, max, among, init, slider;
	String tempVar;

	public UserInputStatement(final IDescription desc) {
		super(desc);
		// order = index++;
		init = getFacet(IKeyword.INIT);
		min = getFacet(IKeyword.MIN);
		max = getFacet(IKeyword.MAX);
		among = getFacet(IKeyword.AMONG);
		slider = getFacet("slider");
		tempVar = getLiteral(IKeyword.RETURNS);
	}

	@Override
	public String getTitle() {
		return description.getName();
	}

	@Override
	public String getCategory() {
		return null;
	}

	@Override
	public String getUnitLabel(final IScope scope) {
		return null;
	}

	// @Override
	// public Integer getDefinitionOrder() {
	// return order;
	// }

	@Override
	public void setValue(final IScope scope, final Object value) {
		currentValue = value;
	}

	@Override
	public Object value(final IScope scope) throws GamaRuntimeException {
		if (!isValued) {
			if (init != null) {
				currentValue = initialValue = init.value(scope);
			}
			isValued = true;
		}
		return currentValue;
	}

	@Override
	public IType getType() {
		final IType type = description.getGamlType();
		if (type != Types.NO_TYPE)
			return type;
		if (init == null)
			return Types.NO_TYPE;
		return init.getGamlType();
	}

	@Override
	public Object getInitialValue(final IScope scope) {
		return initialValue;
	}

	@Override
	public Number getMinValue(final IScope scope) {
		return min == null ? null : (Number) min.value(scope);
	}

	@Override
	public Number getMaxValue(final IScope scope) {
		return max == null ? null : (Number) max.value(scope);
	}

	@Override
	protected Object privateExecuteIn(final IScope scope) {
		scope.addVarWithValue(tempVar, currentValue);
		return currentValue;
	}

	public String getTempVarName() {
		return tempVar;
	}

	@Override
	public List getAmongValue(final IScope scope) {
		return among == null ? null : (List) among.value(scope);
	}

	@Override
	public boolean isEditable() {
		return true;
	}

	@Override
	public Number getStepValue(final IScope scope) {
		return null;
	}

	/**
	 * Method setUnitLabel()
	 *
	 * @see gama.common.interfaces.experiment.IParameter#setUnitLabel(java.lang.String)
	 */
	@Override
	public void setUnitLabel(final String label) {}

	/**
	 * Method isDefined()
	 *
	 * @see gama.common.interfaces.experiment.IParameter#isDefined()
	 */
	@Override
	public boolean isDefined() {
		return true;
	}

	/**
	 * Method setDefined()
	 *
	 * @see gama.common.interfaces.experiment.IParameter#setDefined(boolean)
	 */
	@Override
	public void setDefined(final boolean b) {}

	@Override
	public boolean acceptsSlider(final IScope scope) {
		if (slider == null)
			return true;
		return Cast.asBool(scope, slider.value(scope));
	}

	@Override
	public List<GamaColor> getColor(final IScope scope) {
		return null;
	}

}
