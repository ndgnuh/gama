/*********************************************************************************************
 *
 * 'SpeedDisplayerFactory.java, in plugin gama.ui.experiment.experiment, is part of the source code of the
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

import gama.ui.experiment.controls.SimulationSpeedContributionItem;

public class SpeedDisplayerFactory extends AbstractServiceFactory {

	@Override
	public Object create(final Class serviceInterface, final IServiceLocator parentLocator,
			final IServiceLocator locator) {
		return SimulationSpeedContributionItem.getInstance();
	}

}
