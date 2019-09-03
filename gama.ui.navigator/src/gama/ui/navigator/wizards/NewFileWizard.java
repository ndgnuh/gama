/*********************************************************************************************
 *
 * 'NewFileWizard.java, in plugin gama.ui.base.navigator, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package gama.ui.navigator.wizards;

import org.eclipse.jface.viewers.ISelection;

public class NewFileWizard extends AbstractNewModelWizard {

	@Override
	public AbstractNewModelWizardPage createPage(final ISelection selection) {
		return new NewFileWizardPage(selection);
	}

	@Override
	protected String getDefaultFolderForModels() {
		return "models";
	}

}