/*********************************************************************************************
 *
 * 'MapEditor.java, in plugin gama.ui.base.shared, is part of the source code of the GAMA modeling and simulation
 * platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.base.parameters;

import java.util.Map;

import gama.ui.base.interfaces.EditorListener;
import msi.gama.common.interfaces.IAgent;
import msi.gama.common.interfaces.experiment.IParameter;
import msi.gama.runtime.scope.IScope;
import msi.gaml.types.IType;
import msi.gaml.types.Types;

public class MapEditor extends ExpressionBasedEditor<Map<?, ?>> {

	MapEditor(final IScope scope, final IAgent agent, final IParameter param, final EditorListener<Map<?, ?>> l) {
		super(scope, agent, param, l);
	}

	@SuppressWarnings ({ "unchecked", "rawtypes" })
	@Override
	public IType getExpectedType() {
		return Types.MAP;
	}

	@Override
	protected int[] getToolItems() {
		return new int[] { REVERT };
	}

}
