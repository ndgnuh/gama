/*******************************************************************************************************
 *
 * gama.core.outputs.layers.MouseEventLayerDelegate.java, in plugin msi.gama.core, is part of the source code of the
 * GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.outputs.layers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import gama.common.interfaces.IKeyword;
import gama.common.interfaces.outputs.IEventLayerDelegate;
import gama.common.interfaces.outputs.IEventLayerStatement;
import gama.runtime.scope.IScope;

public class MouseEventLayerDelegate implements IEventLayerDelegate {

	public static final Set<String> EVENTS = new HashSet<>(Arrays.asList(IKeyword.MOUSE_UP, IKeyword.MOUSE_DOWN,
			IKeyword.MOUSE_MOVED, IKeyword.MOUSE_ENTERED, IKeyword.MOUSE_EXITED, IKeyword.MOUSE_MENU));

	@Override
	public boolean acceptSource(final IScope scope, final Object source) {
		return Objects.equals(source, IKeyword.DEFAULT);
	}

	@Override
	public boolean createFrom(final IScope scope, final Object source, final IEventLayerStatement statement) {
		return true;
	}

	@Override
	public Set<String> getEvents() {
		return EVENTS;
	}

}