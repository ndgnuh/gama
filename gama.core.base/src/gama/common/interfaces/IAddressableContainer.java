/*******************************************************************************************************
 *
 * gama.util.IAddressableContainer.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.common.interfaces;

/**
 * Class IAddressableContainer.
 * 
 * @author drogoul
 * @since 24 janv. 2014
 * 
 */
public interface IAddressableContainer<K, V, AK, AV> extends IContainer<K, V>, IContainer.Addressable<AK, AV> {}
