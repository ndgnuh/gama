/*********************************************************************************************
 *
 * 'MatrixEditor.java, in plugin ummisco.gama.ui.shared, is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package ummisco.gama.ui.parameters;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Button;

import msi.gama.common.interfaces.IAgent;
import msi.gama.common.interfaces.experiment.IParameter;
import msi.gama.runtime.scope.IScope;
import msi.gama.util.matrix.IMatrix;
import msi.gaml.types.IType;
import msi.gaml.types.Types;
import ummisco.gama.ui.interfaces.EditorListener;
import ummisco.gama.ui.utils.WorkbenchHelper;

public class MatrixEditor extends ExpressionBasedEditor<IMatrix<?>> {

	MatrixEditor(final IScope scope, final IAgent agent, final IParameter param, final EditorListener<IMatrix<?>> l) {
		super(scope, agent, param, l);
	}

	@Override
	public void applyEdit() {

		final MatrixEditorDialog d = new MatrixEditorDialog(getScope(), WorkbenchHelper.getShell(), currentValue);
		if (d.open() == IDialogConstants.OK_ID) {
			modifyValue(d.getMatrix());
		}

	}

	@Override
	protected void checkButtons() {
		final Button edit = items[EDIT];
		if (edit != null && !edit.isDisposed()) {
			edit.setEnabled(true);
		}
	}

	@SuppressWarnings ({ "unchecked", "rawtypes" })
	@Override
	public IType getExpectedType() {
		return Types.MATRIX;
	}

	@Override
	protected int[] getToolItems() {
		return new int[] { EDIT, REVERT };
	}

}
