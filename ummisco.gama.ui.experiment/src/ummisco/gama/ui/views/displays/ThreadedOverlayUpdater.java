/*********************************************************************************************
 *
 * 'ThreadedOverlayUpdater.java, in plugin ummisco.gama.ui.experiment, is part of the source code of the GAMA modeling
 * and simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package ummisco.gama.ui.views.displays;

import msi.gama.common.interfaces.outputs.IOverlayInfo;
import msi.gama.common.interfaces.outputs.IOverlayProvider;
import ummisco.gama.ui.utils.ThreadedUpdater;

public class ThreadedOverlayUpdater extends ThreadedUpdater<IOverlayInfo> implements IOverlayProvider<IOverlayInfo> {

	public ThreadedOverlayUpdater(final DisplayOverlay displayOverlay) {
		super("Overlay refresh");
		setTarget(displayOverlay, null);
	}

}