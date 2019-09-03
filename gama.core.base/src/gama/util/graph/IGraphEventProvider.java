/*******************************************************************************************************
 *
 * gama.util.graph.IGraphEventProvider.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.util.graph;

import gama.runtime.scope.IScope;

public interface IGraphEventProvider {

	public void addListener(IGraphEventListener listener);

	public void removeListener(IGraphEventListener listener);

	public void dispatchEvent(final IScope scope, GraphEvent event);

}
