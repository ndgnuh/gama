/*********************************************************************************************
 *
 * 'StringEditor.java, in plugin gama.ui.base.shared, is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package gama.ui.base.parameters;

import java.util.List;

import org.eclipse.swt.widgets.Composite;

import gama.ui.base.interfaces.EditorListener;
import gama.common.interfaces.IAgent;
import gama.common.interfaces.experiment.IParameter;
import gama.runtime.scope.IScope;
import gaml.types.IType;
import gaml.types.Types;

public class StringEditor extends ExpressionBasedEditor<String> {

	StringEditor(final IScope scope, final IAgent agent, final IParameter param, final EditorListener<String> l) {
		super(scope, agent, param, l);
	}

	StringEditor(final IScope scope, final Composite parent, final String title, final Object value,
			final EditorListener<String> whenModified) {
		super(scope, new InputParameter(title, value), whenModified);
		this.createComposite(parent);
	}

	StringEditor(final IScope scope, final Composite parent, final String title, final String value,
			final List<String> among, final EditorListener<String> whenModified, final boolean asLabel) {
		super(scope, new InputParameter(title, value, Types.STRING, among), whenModified);
		this.createComposite(parent);
	}

	@Override
	public IType<String> getExpectedType() {
		return Types.STRING;
	}

	@Override
	protected int[] getToolItems() {
		return new int[] { REVERT };
	}

	@Override
	public boolean evaluateExpression() {
		return true;
	}

}
