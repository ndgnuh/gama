/*******************************************************************************************************
 *
 * msi.gaml.compilation.ISymbol.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gaml.compilation.interfaces;

import org.eclipse.emf.common.util.URI;

import msi.gama.common.interfaces.INamed;
import msi.gama.runtime.scope.IScope;
import msi.gaml.descriptions.IDescription;
import msi.gaml.expressions.IExpression;

/**
 * Written by drogoul Modified on 19 mars 2010
 *
 * @todo Description
 *
 */
public interface ISymbol extends INamed {

	void dispose();

	int getOrder();

	void setOrder(int o);

	IDescription getDescription();

	URI getURI();

	/**
	 * Returns the expression located at the first facet of 'keys'
	 *
	 * @param keys
	 * @return
	 */
	IExpression getFacet(String... keys);

	boolean hasFacet(String key);

	void setChildren(Iterable<? extends ISymbol> children);

	String getTrace(IScope abstractScope);

	String getKeyword();

	void setEnclosing(ISymbol enclosing);

	<T> T getFacetValue(final IScope scope, final String key, final T defaultValue);

}
