/*********************************************************************************************
 *
 * 'ListEditor.java, in plugin gama.ui.base.shared, is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.base.parameters;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Button;

import gama.ui.base.interfaces.EditorListener;
import gama.ui.base.utils.WorkbenchHelper;
import msi.gama.common.interfaces.IAgent;
import msi.gama.common.interfaces.experiment.IParameter;
import msi.gama.runtime.scope.IScope;
import msi.gama.util.list.IList;
import msi.gaml.types.IType;
import msi.gaml.types.Types;

public class ListEditor extends ExpressionBasedEditor<java.util.List<?>> {

	ListEditor(final IScope scope, final IAgent agent, final IParameter param,
			final EditorListener<java.util.List<?>> l) {
		super(scope, agent, param, l);
	}

	@SuppressWarnings ("rawtypes")
	@Override
	public void applyEdit() {
		if (currentValue instanceof IList) {
			final ListEditorDialog d =
					new ListEditorDialog(WorkbenchHelper.getShell(), (IList) currentValue, param.getName());
			if (d.open() == IDialogConstants.OK_ID) {
				modifyAndDisplayValue(d.getList(ListEditor.this));
			}
		}
	}

	@Override
	protected void checkButtons() {
		final Button edit = items[EDIT];
		if (edit != null && !edit.isDisposed()) {
			edit.setEnabled(currentValue instanceof IList);
		}
	}

	@SuppressWarnings ({ "unchecked", "rawtypes" })
	@Override
	public IType getExpectedType() {
		return Types.LIST;
	}

	@Override
	protected int[] getToolItems() {
		return new int[] { EDIT, REVERT };
	}

}
