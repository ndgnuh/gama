package gama.ui.experiment.commands;

import static gama.ui.base.utils.WorkbenchHelper.getShell;
import static org.eclipse.jface.dialogs.MessageDialog.QUESTION;
import static org.eclipse.jface.dialogs.MessageDialog.open;
import static org.eclipse.swt.SWT.SHEET;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import gama.ui.base.utils.WorkbenchHelper;
import gama.runtime.GAMA;

public class ResetModelingPerspective extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final String message =
				"Resetting the modeling perspective will lose memory of the current editors, navigator state and restart GAMA in a pristine state. Do you want to proceed ?";
		final boolean result = open(QUESTION, getShell(), "Reset modeling perspective", message, SHEET);
		if (result) {
			GAMA.getGui().clearInitialLayout(true);
			WorkbenchHelper.getWorkbench().restart();
		}
		return null;

	}

}
