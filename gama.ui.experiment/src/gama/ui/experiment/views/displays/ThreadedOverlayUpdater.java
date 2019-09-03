/*********************************************************************************************
 *
 * 'ThreadedOverlayUpdater.java, in plugin gama.ui.experiment.experiment, is part of the source code of the GAMA modeling
 * and simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.experiment.views.displays;

import gama.ui.base.utils.ThreadedUpdater;
import msi.gama.common.interfaces.outputs.IOverlayInfo;
import msi.gama.common.interfaces.outputs.IOverlayProvider;

public class ThreadedOverlayUpdater extends ThreadedUpdater<IOverlayInfo> implements IOverlayProvider<IOverlayInfo> {

	public ThreadedOverlayUpdater(final DisplayOverlay displayOverlay) {
		super("Overlay refresh");
		setTarget(displayOverlay, null);
	}

}