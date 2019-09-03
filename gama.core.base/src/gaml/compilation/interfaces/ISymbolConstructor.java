/*******************************************************************************************************
 *
 * gaml.compilation.ISymbolConstructor.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.compilation.interfaces;

import gaml.descriptions.IDescription;

/**
 * Written by drogoul Modified on 29 ao�t 2010
 * 
 * @todo Description
 * 
 */
@FunctionalInterface
public interface ISymbolConstructor {

	public ISymbol create(IDescription description);

}
