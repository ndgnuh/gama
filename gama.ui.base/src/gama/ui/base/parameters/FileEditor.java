/*********************************************************************************************
 *
 * 'FileEditor.java, in plugin gama.ui.base.shared, is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.base.parameters;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;

import gama.ui.base.controls.FlatButton;
import gama.ui.base.interfaces.EditorListener;
import gama.ui.base.resources.IGamaColors;
import gama.ui.base.utils.WorkbenchHelper;
import msi.gama.common.interfaces.IAgent;
import msi.gama.common.interfaces.experiment.IParameter;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gama.util.file.IGamaFile;
import msi.gaml.operators.Files;
import msi.gaml.types.IType;
import msi.gaml.types.Types;

@SuppressWarnings ({ "rawtypes", "unchecked" })
public class FileEditor extends AbstractEditor<IGamaFile> {

	private FlatButton textBox;

	FileEditor(final IScope scope, final IAgent agent, final IParameter param, final EditorListener l) {
		super(scope, agent, param, l);
	}

	FileEditor(final IScope scope, final Composite parent, final String title, final String value,
			final EditorListener<IGamaFile> whenModified) {
		// Convenience method
		super(scope, new InputParameter(title, value), whenModified);
		this.createComposite(parent);
	}

	@Override
	public Control createCustomParameterControl(final Composite comp) {
		textBox = FlatButton.menu(comp, IGamaColors.NEUTRAL, "").light().small();
		textBox.setText("No file");
		textBox.addSelectionListener(this);
		// GridData d = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		// textBox.setLayoutData(d);
		return textBox;
	}

	@Override
	public void widgetSelected(final SelectionEvent e) {
		final FileDialog dialog = new FileDialog(WorkbenchHelper.getDisplay().getActiveShell(), SWT.NULL);
		IGamaFile file = currentValue;
		dialog.setFileName(file.getPath(getScope()));
		dialog.setText("Choose a file for parameter '" + param.getTitle() + "'");
		final String path = dialog.open();
		if (path != null) {
			file = Files.from(getScope(), path);
			modifyAndDisplayValue(file);
		}
	}

	@Override
	protected GridData getParameterGridData() {
		final GridData d = new GridData(SWT.FILL, SWT.TOP, true, false);
		d.minimumWidth = 50;
		return d;
	}

	@Override
	protected void displayParameterValue() {
		internalModification = true;
		if (currentValue == null) {
			textBox.setText("No file");
		} else {
			final IGamaFile file = currentValue;
			String path;
			try {
				path = file.getPath(getScope());
			} catch (final GamaRuntimeException e) {
				path = file.getOriginalPath();
			}

			textBox.setToolTipText(path);
			textBox.setText(path);
		}
		internalModification = false;
	}

	@Override
	public Control getEditorControl() {
		return textBox;
	}

	@Override
	public IType getExpectedType() {
		return Types.FILE;
	}

	@Override
	protected void applyEdit() {
		widgetSelected(null);
	}

	@Override
	protected int[] getToolItems() {
		return new int[] { EDIT, REVERT };
	}

}
