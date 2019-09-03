package gama.ui.navigator.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.ui.actions.WorkspaceAction;

import gama.ui.base.commands.TestsRunner;
import gama.ui.navigator.contents.ResourceManager;
import gama.ui.navigator.contents.TestModelsFolder;

public class RunAllTestsAction extends WorkspaceAction {

	protected RunAllTestsAction(final IShellProvider provider, final String text) {
		super(provider, text);
	}

	@Override
	protected String getOperationMessage() {
		return "Running all tests";
	}

	@Override
	public boolean updateSelection(final IStructuredSelection event) {
		return event.getFirstElement() instanceof TestModelsFolder;
	}

	@Override
	public void run() {
		TestsRunner.start();
		ResourceManager.finishTests();
	}

}
