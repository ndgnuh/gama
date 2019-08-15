package ummisco.gama.ui.shared;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

import ummisco.gama.ui.utils.PerspectiveHelper;

public class PerspectiveHelperFactory extends AbstractServiceFactory {

	final PerspectiveHelper helper = new PerspectiveHelper();

	@Override
	public Object create(final Class serviceInterface, final IServiceLocator parentLocator,
			final IServiceLocator locator) {
		return helper;
	}

}
