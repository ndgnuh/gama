package gama.ui.base.parameters;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import gama.ui.base.controls.FlatButton;
import gama.ui.base.interfaces.EditorListener;
import gama.ui.base.interfaces.EditorListener.Command;
import gama.ui.base.resources.GamaColors;
import gama.ui.base.resources.IGamaColors;
import gama.ui.base.resources.GamaColors.GamaUIColor;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.statements.UserCommandStatement;

public class CommandEditor extends AbstractStatementEditor<UserCommandStatement> {

	public CommandEditor(final IScope scope, final UserCommandStatement command, final EditorListener.Command l) {
		super(scope, command, l);
	}

	@Override
	protected EditorListener.Command getListener() {
		return (Command) super.getListener();
	}

	@Override
	protected Control createCustomParameterControl(final Composite composite) throws GamaRuntimeException {
		GamaUIColor color = GamaColors.get(getStatement().getColor(getScope()));
		if (color == null)
			color = IGamaColors.NEUTRAL;
		textBox = FlatButton.button(composite, color, "").light().small();
		textBox.setText(getStatement().getName() + "  ");
		textBox.addSelectionListener(getListener());
		return textBox;

	}

}
