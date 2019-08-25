/*******************************************************************************************************
 *
 * msi.gaml.expressions.SpeciesConstantExpression.java, in plugin msi.gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gaml.expressions;

import java.util.Collection;

import msi.gama.kernel.model.IModel;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.metamodel.population.IPopulation;
import msi.gama.runtime.IScope;
import msi.gaml.descriptions.ModelDescription;
import msi.gaml.descriptions.SpeciesDescription;
import msi.gaml.descriptions.VariableDescription;
import msi.gaml.species.ISpecies;
import msi.gaml.types.IType;

@SuppressWarnings ({ "rawtypes" })
public class SpeciesConstantExpression extends ConstantExpression<ISpecies> {

	String name;

	public SpeciesConstantExpression(final String string, final IType t) {
		super(null, t, string);
	}

	@Override
	public ISpecies _value(final IScope scope) {
		if (value == null) {
			final IAgent a = scope.getAgent();
			if (a != null) {
				// hqnghi if main description contains micro-description then
				// species comes from micro-model
				final IModel m = scope.getModel();
				final ModelDescription micro = this.getGamlType().getContentType().getSpecies().getModelDescription();
				final ModelDescription main = m == null ? null : (ModelDescription) scope.getModel().getDescription();
				final Boolean fromMicroModel = main == null || main.getMicroModel(micro.getAlias()) != null;
				if (!fromMicroModel) {
					final IPopulation pop = scope.getAgent().getPopulationFor(getName());
					if (pop != null) {
						value = pop.getSpecies();
					} else {
						value = scope.getModel().getSpecies(getName());
					}
				} else {
					final IPopulation pop =
							scope.getRoot().getExternMicroPopulationFor(micro.getAlias() + "." + getName());
					if (pop != null) {
						value = pop.getSpecies();
					} else {
						value = scope.getModel().getSpecies(getName(),
								this.getGamlType().getContentType().getSpecies());
					}
				}
				// end-hqnghi
			}
		}
		return super._value(scope);
	}

	@Override
	public boolean isConst() {
		return false;
	}

	@Override
	public String serialize(final boolean includingBuiltIn) {
		// if ( computed ) { return super.serialize(includingBuiltIn); }
		return getName();
	}

	@Override
	public String getDocumentation() {
		return getGamlType().getContentType().getSpecies().getDocumentationWithoutMeta();
	}

	@Override
	public boolean isContextIndependant() {
		return false;
	}

	@Override
	public void collectUsedVarsOf(final SpeciesDescription species, final Collection<VariableDescription> result) {
		if (species.hasAttribute(getName())) {
			result.add(species.getAttribute(getName()));
		}
	}

	@Override
	public void setName(final String n) {
		this.name = n;
	}

	@Override
	public String toString() {
		return name;
	}

}
