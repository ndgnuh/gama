/*********************************************************************************************
 *
 * 'BoxDecoratorPartListener.java, in plugin ummisco.gama.ui.modeling, is part of the source code of the GAMA modeling
 * and simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package ummisco.gama.ui.editbox;

import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;

import ummisco.gama.ui.editor.GamlEditor;

public class BoxDecoratorPartListener implements IPartListener2 {

	private GamlEditor getEditor(final IWorkbenchPartReference ref) {
		final IWorkbenchPart part = ref.getPart(false);
		if (part == null)
			return null;
		return part instanceof GamlEditor ? (GamlEditor) part : null;
	}

	@Override
	public void partActivated(final IWorkbenchPartReference partRef) {
		final GamlEditor editor = getEditor(partRef);
		if (editor != null && editor.isDecorationEnabled()) {
			editor.decorate(true);
			editor.enableUpdates(true);
		}
	}

	@Override
	public void partBroughtToTop(final IWorkbenchPartReference partRef) {
		final GamlEditor editor = getEditor(partRef);
		if (editor != null && editor.isDecorationEnabled()) {
			editor.decorate(true);
			editor.enableUpdates(true);
		}
	}

	@Override
	public void partClosed(final IWorkbenchPartReference partRef) {
		final GamlEditor editor = getEditor(partRef);
		if (editor != null) {
			editor.decorate(false);
		}
	}

	@Override
	public void partDeactivated(final IWorkbenchPartReference partRef) {}

	@Override
	public void partHidden(final IWorkbenchPartReference partRef) {
		final GamlEditor editor = getEditor(partRef);
		if (editor != null && editor.isDecorationEnabled()) {
			editor.decorate(true);
			editor.enableUpdates(false);
		}
	}

	@Override
	public void partInputChanged(final IWorkbenchPartReference partRef) {
		final GamlEditor editor = getEditor(partRef);
		if (editor != null && editor.isDecorationEnabled()) {
			editor.decorate(true);
			editor.enableUpdates(false);
			editor.decorate(true);
			editor.enableUpdates(true);
		}
	}

	@Override
	public void partOpened(final IWorkbenchPartReference partRef) {}

	@Override
	public void partVisible(final IWorkbenchPartReference partRef) {
		final GamlEditor editor = getEditor(partRef);
		if (editor != null && editor.isDecorationEnabled()) {
			editor.decorate(true);
			editor.enableUpdates(true);
		}
	}
}