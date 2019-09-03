/*******************************************************************************************************
 *
 * gama.common.interfaces.ITyped.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.common.interfaces;

import gaml.types.IType;
import gaml.types.Types;

/**
 * Interface ITyped. Represents all the objects (incl. agents) that are provided and can return a GAML type
 * 
 * @author drogoul
 *
 */
public interface ITyped {

	/**
	 * Returns the type of this object with respect to the GAML available types
	 * 
	 * @return a GAML type or Types.NO_TYPE if none (never null)
	 * @see GamaType, IType
	 */
	default IType<?> getGamlType() {
		return Types.NO_TYPE;
	}

}
