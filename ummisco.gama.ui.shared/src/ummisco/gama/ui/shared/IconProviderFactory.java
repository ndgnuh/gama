package ummisco.gama.ui.shared;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

import ummisco.gama.ui.resources.GamaIcons;
import ummisco.gama.ui.utils.IIconProvider;

public class IconProviderFactory extends AbstractServiceFactory {

	public IconProviderFactory() {}

	@Override
	public IIconProvider create(final Class serviceInterface, final IServiceLocator parentLocator,
			final IServiceLocator locator) {
		return GamaIcons.getInstance();
	}

}
