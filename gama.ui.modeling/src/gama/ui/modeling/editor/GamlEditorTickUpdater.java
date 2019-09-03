/*********************************************************************************************
 *
 * 'GamlEditorTickUpdater.java, in plugin gama.ui.base.modeling, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.modeling.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.XtextEditorErrorTickUpdater;

import gama.ui.base.resources.GamaIcons;
import gama.ui.base.resources.IGamaIcons;

/**
 * The class GamlEditorTickUpdater.
 *
 * @author drogoul
 * @since 8 sept. 2013
 *
 */
public class GamlEditorTickUpdater extends XtextEditorErrorTickUpdater {

	@Override
	protected void updateEditorImage(final XtextEditor editor) {
		Severity severity = getSeverity(editor);
		ImageDescriptor descriptor = null;
		if (severity == null || severity == Severity.INFO) {
			descriptor = GamaIcons.create(IGamaIcons.OVERLAY_OK).descriptor();
		} else if (severity == Severity.ERROR) {
			descriptor = GamaIcons.create("navigator/overlay.error2").descriptor();
		} else if (severity == Severity.WARNING) {
			descriptor = GamaIcons.create("navigator/overlay.warning2").descriptor();
		} else {
			super.updateEditorImage(editor);
			return;
		}
		final DecorationOverlayIcon decorationOverlayIcon =
				new DecorationOverlayIcon(editor.getDefaultImage(), descriptor, IDecoration.BOTTOM_LEFT);
		scheduleUpdateEditor(decorationOverlayIcon);
	}

}
