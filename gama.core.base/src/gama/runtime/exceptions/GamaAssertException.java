/*******************************************************************************************************
 *
 * gama.runtime.exceptions.GamaAssertException.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.runtime.exceptions;

import gama.runtime.scope.IScope;

public class GamaAssertException extends GamaRuntimeException {

	public GamaAssertException(final IScope scope, final String s, final boolean warning) {
		super(scope, s, warning);
	}

}
