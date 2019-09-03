package gama.ui.base.parameters;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolBar;

import gama.ui.base.controls.FlatButton;
import gama.ui.base.interfaces.EditorListener;
import gama.ui.base.resources.GamaColors;
import gama.ui.base.resources.IGamaColors;
import gama.ui.base.resources.GamaColors.GamaUIColor;
import msi.gama.runtime.GAMA;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.statements.test.AbstractSummary;
import msi.gaml.statements.test.AssertionSummary;
import msi.gaml.statements.test.TestState;

public class AssertEditor extends AbstractStatementEditor<AbstractSummary<?>> {

	public AssertEditor(final IScope scope, final AbstractSummary<?> command) {
		super(scope, command, (EditorListener<Object>) null);
		isSubParameter = command instanceof AssertionSummary;
		name = command.getTitle();
	}

	@Override
	protected Control createCustomParameterControl(final Composite composite) throws GamaRuntimeException {
		textBox = FlatButton.button(composite, getColor(), getText()).small();
		textBox.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				GAMA.getGui().editModel(getStatement().getURI());
			}
		});
		return textBox;
	}

	@Override
	protected ToolBar createToolbar2() {
		return null;
	}

	private GamaUIColor getColor() {
		GamaUIColor color = GamaColors.get(getStatement().getColor());
		if (color == null) {
			color = IGamaColors.NEUTRAL;
		}
		return color;
	}

	private String getText() {
		final AbstractSummary<?> summary = getStatement();

		if (summary instanceof AssertionSummary && getStatement().getState() == TestState.ABORTED) {
			return getStatement().getState().toString() + ": " + ((AssertionSummary) getStatement()).getError() + "  ";
		}
		return getStatement().getState().toString() + "  ";
	}

}
