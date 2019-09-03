/*********************************************************************************************
 *
 * 'UserDialogFactory.java, in plugin gama.ui.experiment.experiment, is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package gama.ui.experiment.factories;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

import gama.ui.experiment.views.user.UserControlDialog;
import msi.gama.runtime.scope.IScope;
import msi.gaml.architecture.user.UserPanelStatement;

public class UserDialogFactory extends AbstractServiceFactory implements gama.ui.base.interfaces.IUserDialogFactory {

	@Override
	public void openUserDialog(final IScope scope, final UserPanelStatement panel) {
		final UserControlDialog dialog = new UserControlDialog(scope, panel);
		dialog.open();
	}

	@Override
	public void closeUserDialog() {
		final UserControlDialog d = UserControlDialog.current;
		if (d != null) {
			d.close();
		}
	}

	@Override
	public Object create(final Class serviceInterface, final IServiceLocator parentLocator,
			final IServiceLocator locator) {
		return this;
	}

}
