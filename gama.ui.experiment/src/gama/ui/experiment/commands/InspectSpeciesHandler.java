/*********************************************************************************************
 *
 * 'InspectSpeciesHandler.java, in plugin gama.ui.experiment.experiment, is part of the source code of the GAMA modeling
 * and simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.experiment.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import gama.core.outputs.ValuedDisplayOutputFactory;
import gama.runtime.GAMA;
import gama.runtime.exceptions.GamaRuntimeException;
import gaml.species.ISpecies;

public class InspectSpeciesHandler extends AbstractHandler { // NO_UCD (unused code)

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		try {
			ValuedDisplayOutputFactory.browse(GAMA.getSimulation(), (ISpecies) null);
		} catch (final GamaRuntimeException e) {
			throw new ExecutionException(e.getMessage());
		}
		return null;
	}
}
