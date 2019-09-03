/*********************************************************************************************
 *
 * 'ModelingPerspective.java, in plugin gama.ui.base.modeling, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package ummisco.gama.ui.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import gama.common.interfaces.gui.IGui;

public class ModelingPerspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(final IPageLayout layout) {
		layout.addShowViewShortcut(IGui.INTERACTIVE_CONSOLE_VIEW_ID);
		layout.setEditorAreaVisible(true);
	}
}
