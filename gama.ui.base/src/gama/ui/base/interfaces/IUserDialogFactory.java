/*********************************************************************************************
 *
 * 'IUserDialogFactory.java, in plugin gama.ui.base.shared, is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package gama.ui.base.interfaces;

import msi.gama.runtime.scope.IScope;
import msi.gaml.architecture.user.UserPanelStatement;

public interface IUserDialogFactory {

	void openUserDialog(IScope scope, UserPanelStatement panel);

	void closeUserDialog();
}