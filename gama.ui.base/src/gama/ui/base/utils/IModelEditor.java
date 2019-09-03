package gama.ui.base.utils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.IEditorPart;

public interface IModelEditor {

	IEditorPart open(URI uri, boolean select);

	IEditorPart open(URI referenceOwnerURI, EReference reference, int indexInList, boolean select);
}
