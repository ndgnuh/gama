/*********************************************************************************************
 * 
 * 
 * 'BDIPlan.java', in plugin 'gama.extensions.bdi', is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 * 
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 * 
 * 
 **********************************************************************************************/
package gama.extensions.bdi;

import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.getter;
import gama.processor.annotations.GamlAnnotations.variable;
import gama.processor.annotations.GamlAnnotations.vars;
import gama.common.interfaces.IValue;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.types.IType;
import gaml.types.Types;

@vars ({ @variable (
		name = "name",
		type = IType.STRING,
		doc = @doc ("The name of this BDI plan")),
		@variable (
				name = "todo",
				type = IType.STRING,
				doc = @doc("represent the when facet of a plan")),
		@variable (
				name = SimpleBdiPlanStatement.INTENTION,
				type = MentalStateType.id,
				doc = @doc ("A string representing the current intention of this BDI plan")),
		@variable (
				name = SimpleBdiArchitecture.FINISHEDWHEN,
				type = IType.STRING,
				doc = @doc("a string representing the finished condition of this plan")),
		@variable (
				name = SimpleBdiArchitecture.INSTANTANEAOUS,
				type = IType.STRING, 
				doc = @doc("indicates if the plan is instantaneous"))
		/*
		 * @var(name = "value", type = IType.NONE),
		 * 
		 * @var(name = "parameters", type = IType.MAP),
		 */
		// @var(name = "values", type = IType.MAP), @var(name = "priority", type
		// = IType.FLOAT),
		// @var(name = "date", type = IType.FLOAT), @var(name = "subintentions",
		// type = IType.LIST),
		// @var(name = "on_hold_until", type = IType.NONE)
})
public class BDIPlan implements IValue {

	private SimpleBdiPlanStatement planstatement;

	@getter ("name")
	public String getName() {
		return this.planstatement.getName();
	}

	@getter ("todo")
	public String getWhen() {
		return this.planstatement._when.serialize(true);
	}

	@getter (SimpleBdiArchitecture.FINISHEDWHEN)
	public String getFinishedWhen() {
		return this.planstatement._executedwhen.serialize(true);
	}

	@getter (SimpleBdiPlanStatement.INTENTION)
	public Predicate getIntention(final IScope scope) {
		return (Predicate) this.planstatement._intention.value(scope);
	}

	@getter (SimpleBdiArchitecture.INSTANTANEAOUS)
	public String getInstantaneous() {
		return this.planstatement._instantaneous.serialize(true);
	}

	public SimpleBdiPlanStatement getPlanStatement() {
		return this.planstatement;
	}

	public BDIPlan() {
		super();
	}

	public BDIPlan(final SimpleBdiPlanStatement statement) {
		super();
		this.planstatement = statement;
	}

	public void setSimpleBdiPlanStatement(final SimpleBdiPlanStatement statement) {
		this.planstatement = statement;

	}

	@Override
	public String toString() {
		return serialize(true);
	}

	@Override
	public String serialize(final boolean includingBuiltIn) {
		return "BDIPlan(" + planstatement.getName()
		// +(values == null ? "" : "," + values) +
				+ ")";
	}

	@Override
	public String stringValue(final IScope scope) throws GamaRuntimeException {
		return "BDIPlan(" + planstatement.getName()+")"
		// +(values == null ? "" : "," + values)
		;
	}

	@Override
	public IValue copy(final IScope scope) throws GamaRuntimeException {
		return new BDIPlan(planstatement);
	}

	public boolean isSimilarName(final BDIPlan other) {
		if (this == other) { return true; }
		if (other == null) { return false; }
		if (planstatement == null) {
			if (other.planstatement != null) { return false; }
		} else if (!planstatement.equals(other.planstatement)) { return false; }
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (planstatement == null ? 0 : planstatement.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		final BDIPlan other = (BDIPlan) obj;
		if (planstatement == null) {
			if (other.planstatement != null) { return false; }
		} else if (!planstatement.equals(other.planstatement)) { return false; }
		return true;
	}

	/**
	 * Method getType()
	 * 
	 * @see msi.gama.common.interfaces.ITyped#getGamlType()
	 */
	@Override
	public IType<?> getGamlType() {
		return Types.get(BDIPlanType.id);
	}

}
