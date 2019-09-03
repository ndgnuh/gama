package gama.ui.navigator.commands;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

import gama.ui.base.interfaces.IRefreshHandler;

public class RefreshServiceFactory extends AbstractServiceFactory {

	public RefreshServiceFactory() {}

	@Override
	public IRefreshHandler create(final Class serviceInterface, final IServiceLocator parentLocator,
			final IServiceLocator locator) {
		return new RefreshHandler();
	}

}
