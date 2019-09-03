/*******************************************************************************************************
 *
 * gaml.types.GamaActionType.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.types;

import gama.common.interfaces.IKeyword;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.type;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.descriptions.IDescription;

@type (
		name = IKeyword.ACTION,
		id = IType.ACTION,
		wraps = { IDescription.class },
		kind = ISymbolKind.Variable.REGULAR,
		doc = { @doc ("The type of the variables that denote an action or an aspect of a species") },
		concept = { IConcept.TYPE, IConcept.ACTION, IConcept.SPECIES })
public class GamaActionType extends GamaType<IDescription> {

	@Override
	public boolean canCastToConst() {
		return false;
	}

	@Override
	public IDescription cast(final IScope scope, final Object obj, final Object param, final boolean copy)
			throws GamaRuntimeException {
		if (obj == null)
			return null;
		if (obj instanceof IDescription)
			return (IDescription) obj;
		if (obj instanceof String) {
			final String name = (String) obj;
			final IDescription action = scope.getAgent().getSpecies().getDescription().getAction(name);
			if (action != null)
				return action;
			return scope.getAgent().getSpecies().getDescription().getAspect(name);
		}
		return null;
	}

	@Override
	public IDescription getDefault() {
		// TODO Auto-generated method stub
		return null;
	}

}
