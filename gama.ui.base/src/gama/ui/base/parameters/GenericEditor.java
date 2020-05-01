/*********************************************************************************************
 *
 * 'GenericEditor.java, in plugin gama.ui.base.shared, is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.base.parameters;

import org.eclipse.swt.widgets.Composite;

import gama.ui.base.interfaces.EditorListener;
import gama.common.interfaces.IAgent;
import gama.common.interfaces.experiment.IParameter;
import gama.kernel.experiment.InputParameter;
import gama.runtime.scope.IScope;
import gaml.types.GamaType;
import gaml.types.IType;

public class GenericEditor<T> extends ExpressionBasedEditor<T> {

	IType<?> expectedType;

	GenericEditor(final IScope scope, final IAgent agent, final IParameter param, final EditorListener<T> l) {
		super(scope, agent, param, l);
		expectedType = param.getType();
	}

	GenericEditor(final IScope scope, final Composite parent, final String title, final T value,
			final EditorListener<T> whenModified) {
		// Convenience method
		super(scope, new InputParameter(title, value), whenModified);
		expectedType = GamaType.of(value);
		this.createComposite(parent);
	}

	@Override
	public IType<?> getExpectedType() {
		return expectedType;
	}

	@Override
	protected int[] getToolItems() {
		return new int[] { REVERT };
	}

}
