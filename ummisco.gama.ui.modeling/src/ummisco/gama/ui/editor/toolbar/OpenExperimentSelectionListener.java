/*********************************************************************************************
 *
 * 'OpenExperimentSelectionListener.java, in plugin ummisco.gama.ui.modeling, is part of the source code of the GAMA
 * modeling and simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package ummisco.gama.ui.editor.toolbar;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;

import msi.gama.common.interfaces.IModel;
import msi.gama.common.preferences.GamaPreferences;
import msi.gama.runtime.GAMA;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.compilation.GAML;
import ummisco.gama.ui.controls.FlatButton;
import ummisco.gama.ui.editor.GamlEditor;
import ummisco.gama.ui.editor.GamlEditorState;
import ummisco.gama.ui.utils.WorkbenchHelper;
import ummisco.gama.ui.views.toolbar.Selector;

/**
 * The class CreateExperimentSelectionListener.
 *
 * @author drogoul
 * @since 27 août 2016
 *
 */
public class OpenExperimentSelectionListener implements Selector {

	GamlEditor editor;
	GamlEditorState state;

	/**
	 *
	 */
	public OpenExperimentSelectionListener(final GamlEditor editor, final GamlEditorState state) {
		this.editor = editor;
		this.state = state;
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(final SelectionEvent e) {

		// final IGui gui = GAMA.getRegularGui();
		// We refuse to run if there is no XtextGui available.
		editor.doSave(null);
		if (GamaPreferences.Modeling.EDITOR_SAVE.getValue()) {
			WorkbenchHelper.getPage().saveAllEditors(GamaPreferences.Modeling.EDITOR_SAVE_ASK.getValue());
		}
		String name = (String) ((FlatButton) e.widget).getData("exp");
		final int i = state.abbreviations.indexOf(name);
		if (i == -1) { return; }
		name = state.experiments.get(i);
		final IXtextDocument doc = editor.getDocument();
		IModel model = null;
		try {
			model = doc.readOnly(state -> GAML.compile(state.getURI(), null));
		} catch (final GamaRuntimeException ex) {
			GAMA.getGui().error("Experiment cannot be instantiated because of the following error: " + ex.getMessage());
		}
		GAMA.runModel(model, name, false);

	}

}
