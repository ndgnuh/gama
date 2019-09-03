/*********************************************************************************************
 *
 * 'GamlSortOutlineContribution.java, in plugin gama.ui.base.modeling, is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package gama.core.lang.ui.outline;

import org.eclipse.jface.action.Action;
import org.eclipse.xtext.ui.editor.outline.actions.SortOutlineContribution;

import gama.ui.base.resources.GamaIcons;

/**
 * The class GamlSortOutlineContribution.
 * 
 * @author drogoul
 * @since 24 nov. 2014
 * 
 */
public class GamlSortOutlineContribution extends SortOutlineContribution {

	/**
	 *
	 */
	public GamlSortOutlineContribution() {}

	@Override
	protected void configureAction(final Action action) {
		super.configureAction(action);
		action.setImageDescriptor(GamaIcons.create("navigator/navigator.sort2").descriptor());
	}

}
