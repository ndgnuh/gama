/*******************************************************************************************************
 *
 * msi.gaml.types.GamaStringType.java, in plugin msi.gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package msi.gaml.types;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.common.interfaces.INamed;
import msi.gama.common.interfaces.IValue;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import ummisco.gama.processor.IConcept;
import ummisco.gama.processor.ISymbolKind;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.type;

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
