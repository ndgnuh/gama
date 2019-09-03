/*******************************************************************************************************
 *
 * gaml.types.GamaStringType.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.types;

import gama.common.interfaces.IKeyword;
import gama.common.interfaces.INamed;
import gama.common.interfaces.IValue;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.type;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;

/**
 *
 *
 *
 * Written by drogoul Modified on 3 juin 2010
 *
 * @todo Description
 * 
 */
@SuppressWarnings ("unchecked")
@type (
		name = IKeyword.STRING,
		id = IType.STRING,
		wraps = { String.class },
		kind = ISymbolKind.Variable.REGULAR,
		concept = { IConcept.TYPE, IConcept.STRING },
		doc = @doc ("Strings are ordered list of characters"))
public class GamaStringType extends GamaType<String> {

	@Override
	public String cast(final IScope scope, final Object obj, final Object param, final boolean copy)
			throws GamaRuntimeException {
		return staticCast(scope, obj, copy);
	}

	public static String staticCast(final IScope scope, final Object obj, final boolean copy)
			throws GamaRuntimeException {
		if (obj == null) { return null; }
		if (obj instanceof IValue) { return ((IValue) obj).stringValue(scope); }
		if (obj instanceof INamed) { return ((INamed) obj).getName(); }
		return obj.toString();
	}

	@Override
	public String getDefault() {
		return null;
	}

	@Override
	public boolean canCastToConst() {
		return true;
	}

	@Override
	public boolean isDrawable() {
		return true;
	}

}
