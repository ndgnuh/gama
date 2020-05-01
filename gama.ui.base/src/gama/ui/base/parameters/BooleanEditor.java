/*********************************************************************************************
 *
 * 'BooleanEditor.java, in plugin gama.ui.base.shared, is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.base.parameters;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import gama.ui.base.controls.SwitchButton;
import gama.ui.base.interfaces.EditorListener;
import gama.ui.base.resources.GamaColors;
import gama.ui.base.resources.IGamaColors;
import gama.common.interfaces.IAgent;
import gama.common.interfaces.experiment.IParameter;
import gama.kernel.experiment.InputParameter;
import gama.runtime.scope.IScope;
import gama.util.GamaColor;
import gaml.types.IType;
import gaml.types.Types;

public class BooleanEditor extends AbstractEditor<Boolean> {

	private SwitchButton button;

	BooleanEditor(final IScope scope, final Composite parent, final String title, final boolean value,
			final EditorListener<Boolean> whenModified) {
		super(scope, new InputParameter(title, value), whenModified);
		acceptNull = false;
		this.createComposite(parent);
	}

	BooleanEditor(final IScope scope, final IAgent agent, final IParameter param, final EditorListener<Boolean> l) {
		super(scope, agent, param, l);
		acceptNull = false;
	}

	@Override
	public void widgetSelected(final SelectionEvent se) {
		if (!internalModification) {
			modifyAndDisplayValue(button.getSelection());
		}
	}

	@Override
	protected void hideToolbar() {
		super.hideToolbar();
		if (getEditor() != null) {
			this.getEditor().setBackground(getNormalBackground());
		}
	}

	@Override
	protected void showToolbar() {
		super.showToolbar();
		if (getEditor() != null) {
			this.getEditor().setBackground(HOVERED_BACKGROUND);
			// AD 26/12/15 Commented for the moment to not force the focus (see
			// Issues #1339 and #1248)
			// if ( combo != null ) {
			// combo.forceFocus();
			// } else {
			// Control c = getEditorControl();
			// if ( c != null ) {
			// c.forceFocus();
			// }
			// }
		}
	}

	@Override
	public Control createCustomParameterControl(final Composite comp) {
		final List<GamaColor> colors = getParam().getColor(getScope());
		Color left = IGamaColors.OK.color();
		Color right = IGamaColors.ERROR.color();
		if (colors != null) {
			if (colors.size() == 1) {
				left = right = GamaColors.get(colors.get(0)).color();
			} else if (colors.size() >= 2) {
				left = GamaColors.get(colors.get(0)).color();
				right = GamaColors.get(colors.get(1)).color();
			}
		}
		button = new SwitchButton(comp, SWT.CHECK, left, right);
		button.addSelectionListener(this);
		return button;
	}

	@Override
	protected void displayParameterValue() {
		internalModification = true;
		Boolean b = currentValue;
		if (b == null) {
			b = false;
		}
		button.setSelection(b);
		internalModification = false;

	}

	@Override
	public Control getEditorControl() {
		return button;
	}

	@Override
	public IType<Boolean> getExpectedType() {
		return Types.BOOL;
	}

	@Override
	protected int[] getToolItems() {
		return new int[] { REVERT };
	}

}
