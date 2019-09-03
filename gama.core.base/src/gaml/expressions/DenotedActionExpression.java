/*******************************************************************************************************
 *
 * gaml.expressions.DenotedActionExpression.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.expressions;

import java.util.Collection;

import gama.runtime.scope.IScope;
import gaml.descriptions.IDescription;
import gaml.descriptions.SpeciesDescription;
import gaml.descriptions.VariableDescription;
import gaml.types.Types;

public class DenotedActionExpression extends VariableExpression {

	public DenotedActionExpression(final IDescription action) {
		super(action.getName(), Types.ACTION, true, action);
	}

	@Override
	public Object _value(final IScope scope) {
		return getDefinitionDescription();
	}

	@Override
	public String getTitle() {
		return getDefinitionDescription().getTitle();
	}

	/**
	 * @see gaml.expressions.IExpression#getDocumentation()
	 */
	@Override
	public String getDocumentation() {
		return "This expression denotes the description of " + getTitle();
	}

	@Override
	public void setVal(final IScope scope, final Object v, final boolean create) {}

	/**
	 * Method collectPlugins()
	 *
	 * @see gama.common.interfaces.IGamlDescription#collectPlugins(java.util.Set)
	 */
	// @Override
	// public void collectMetaInformation(final GamlProperties meta) {
	// getDefinitionDescription().collectMetaInformation(meta);
	// }

	@Override
	public void collectUsedVarsOf(final SpeciesDescription species, final Collection<VariableDescription> result) {}

}
