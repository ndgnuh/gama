/*********************************************************************************************
 *
 * 'EditorsDialog.java, in plugin gama.ui.base.shared, is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.base.parameters;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import gama.common.interfaces.experiment.IParameter;
import gama.runtime.scope.IScope;
import gama.ui.base.interfaces.EditorListener;
import gama.ui.base.resources.GamaColors;
import gama.ui.base.resources.IGamaColors;
import gama.util.map.GamaMapFactory;
import gama.util.map.IMap;

/**
 * The class EditorsDialog.
 *
 * @author drogoul
 * @since 10 mai 2012
 *
 */
public class EditorsDialog extends Dialog {

	private final IMap<String, Object> values = GamaMapFactory.createUnordered();
	private final List<IParameter> parameters;
	private final String title;
	private final IScope scope;

	public EditorsDialog(final IScope scope, final Shell parentShell, final List<IParameter> parameters,
			final String title) {
		super(parentShell);
		this.scope = scope;
		this.title = title;
		setShellStyle(SWT.RESIZE | SWT.BORDER);
		this.parameters = parameters;
		parameters.forEach((p) -> {
			values.put(p.getName(), p.getInitialValue(scope));
		});
	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
	}

	@Override
	protected Control createButtonBar(final Composite parent) {
		final Control composite = super.createButtonBar(parent);
		composite.setBackground(IGamaColors.WHITE.color());
		return composite;
	}

	/**
	 * Method createContents()
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(final Composite parent) {
		final Control composite = super.createContents(parent);
		composite.setBackground(IGamaColors.WHITE.color());
		return composite;
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		// getShell().setText(title);
		final Composite composite = (Composite) super.createDialogArea(parent);
		composite.setBackground(IGamaColors.WHITE.color());
		final GridLayout layout = (GridLayout) composite.getLayout();
		layout.numColumns = 2;
		final Label text = new Label(composite, SWT.None);
		text.setBackground(IGamaColors.OK.inactive());
		text.setForeground(GamaColors.getTextColorForBackground(text.getBackground()).color());
		text.setText(title);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		text.setLayoutData(data);
		final Label sep = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		data = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		data.heightHint = 20;
		sep.setLayoutData(data);
		parameters.forEach(param -> {
			final EditorListener<?> listener = newValue -> {
				param.setValue(scope, newValue);
				values.put(param.getName(), newValue);
			};
			EditorFactory.create(scope, composite, param, listener, false, false);
		});
		composite.layout();

		// composite.pack();
		// composite.setS

		return composite;
	}

	@Override
	protected Point getInitialSize() {
		final Point p = super.getInitialSize();
		return new Point(p.x * 2, p.y);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	public Map<String, Object> getValues() {
		return values;
	}

}
