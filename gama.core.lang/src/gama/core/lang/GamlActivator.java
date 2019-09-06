package gama.core.lang;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class GamlActivator implements BundleActivator {

	@Override
	public void start(final BundleContext context) throws Exception {
		GamlRuntimeModule.staticInitialize();
	}

	@Override
	public void stop(final BundleContext context) throws Exception {}

}
