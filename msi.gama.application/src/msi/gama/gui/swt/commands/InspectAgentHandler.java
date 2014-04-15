/*********************************************************************************************
 * 
 *
 * 'InspectAgentHandler.java', in plugin 'msi.gama.application', is part of the source code of the 
 * GAMA modeling and simulation platform.
 * (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 * 
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 * 
 * 
 **********************************************************************************************/
package msi.gama.gui.swt.commands;

import msi.gama.outputs.InspectDisplayOutput;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import org.eclipse.core.commands.*;

public class InspectAgentHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		try {
			new InspectDisplayOutput("Inspector", InspectDisplayOutput.INSPECT_AGENT).launch();
		} catch (GamaRuntimeException e) {
			throw new ExecutionException(e.getMessage());
		}
		return null;
	}
}
