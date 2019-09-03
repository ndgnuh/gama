/*******************************************************************************************************
 *
 * gama.common.interfaces.IEnvelopeComputer.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.common.interfaces;

import gama.common.geometry.Envelope3D;
import gama.runtime.scope.IScope;

public interface IEnvelopeComputer {

	Envelope3D computeEnvelopeFrom(final IScope scope, final Object obj);

}
