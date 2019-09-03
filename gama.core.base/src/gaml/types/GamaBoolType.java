/*******************************************************************************************************
 *
 * gaml.types.GamaBoolType.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.types;

import java.io.File;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IContainer;
import gama.common.interfaces.IKeyword;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.type;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;

/**
 * Written by drogoul Modified on 1 ao�t 2010
 *
 * @todo Description
 *
 */
@SuppressWarnings ("unchecked")
@type (
		name = IKeyword.BOOL,
		id = IType.BOOL,
		wraps = { Boolean.class, boolean.class },
		kind = ISymbolKind.Variable.REGULAR,
		doc = { @doc ("Represents boolean values, either true or false") },
		concept = { IConcept.TYPE, IConcept.LOGICAL, IConcept.CONDITION })
public class GamaBoolType extends GamaType<Boolean> {

	@Override
	@doc ("Casts parameter into a bool. false if the parameter is nil, equal to zero, empty or dead, depending on its type")
	public Boolean cast(final IScope scope, final Object obj, final Object param, final boolean copy)
			throws GamaRuntimeException {
		return staticCast(scope, obj, param, copy);
	}

	@SuppressWarnings ("rawtypes")
	public static Boolean staticCast(final IScope scope, final Object obj, final Object param, final boolean copy) {
		if (obj == null) { return false; }
		if (obj instanceof Boolean) { return (Boolean) obj; }
		if (obj instanceof IAgent) { return !((IAgent) obj).dead(); }
		if (obj instanceof IContainer) { return !((IContainer) obj).isEmpty(scope); }
		if (obj instanceof File) { return ((File) obj).exists(); }
		if (obj instanceof Integer) { return (Integer) obj != 0; }
		if (obj instanceof Double) { return (Double) obj != 0d; }
		if (obj instanceof String) { return ((String) obj).equals("true"); }
		return false;
	}

	@Override
	public Boolean getDefault() {
		return false;
	}

	@Override
	public boolean canCastToConst() {
		return true;
	}

}
