/*********************************************************************************************
 * 
 *
 * 'MatrixEditor.java', in plugin 'msi.gama.application', is part of the source code of the 
 * GAMA modeling and simulation platform.
 * (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 * 
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 * 
 * 
 **********************************************************************************************/
package msi.gama.gui.parameters;

import msi.gama.common.interfaces.EditorListener;
import msi.gama.common.util.StringUtils;
import msi.gama.kernel.experiment.IParameter;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.util.matrix.IMatrix;
import msi.gaml.types.*;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

public class MatrixEditor extends AbstractEditor {

	private Button button;

	MatrixEditor(final IParameter param) {
		super(param);
	}

	MatrixEditor(final IAgent agent, final IParameter param) {
		this(agent, param, null);
	}

	MatrixEditor(final IAgent agent, final IParameter param, final EditorListener l) {
		super(agent, param, l);
	}

	MatrixEditor(final Composite parent, final String title, final Object value,
		final EditorListener<IMatrix> whenModified) {
		// Convenience method
		super(new InputParameter(title, value), whenModified);
		this.createComposite(parent);
	}

	@Override
	public Control createCustomParameterControl(final Composite comp) {
		button = new Button(comp, SWT.FLAT + SWT.CENTER);
		button.setAlignment(SWT.LEFT);
		button.addSelectionListener(this);

		currentValue = getOriginalValue();
		return button;

	}

	@Override
	public void widgetSelected(final SelectionEvent event) {
		MatrixEditorDialog d = new MatrixEditorDialog(Display.getCurrent().getActiveShell(), (IMatrix) currentValue);
		if ( d.open() == IDialogConstants.OK_ID ) {
			modifyValue(d.getMatrix());
		}
	}

	@Override
	protected void displayParameterValue() {
		button.setText(StringUtils.toGaml(currentValue));
	}

	@Override
	public Control getEditorControl() {
		return button;
	}

	@Override
	public IType getExpectedType() {
		return Types.get(IType.MATRIX);
	}

}
