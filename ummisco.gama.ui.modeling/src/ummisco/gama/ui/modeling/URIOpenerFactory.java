package ummisco.gama.ui.modeling;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;

import com.google.inject.Injector;

import ummisco.gama.ui.modeling.internal.ModelingActivator;
import ummisco.gama.ui.utils.IModelEditor;

public class URIOpenerFactory extends AbstractServiceFactory implements IModelEditor {

	final IURIEditorOpener opener;

	public URIOpenerFactory() {
		final Injector injector = ModelingActivator.getInstance().getInjector("msi.gama.lang.gaml.Gaml");
		opener = injector.getInstance(IURIEditorOpener.class);
	}

	@Override
	public Object create(final Class serviceInterface, final IServiceLocator parentLocator,
			final IServiceLocator locator) {
		return this;
	}

	/**
	 * @see ummisco.gama.ui.utils.IModelEditor#open(org.eclipse.emf.common.util.URI, boolean)
	 */
	@Override
	public IEditorPart open(final URI uri, final boolean select) {
		return opener.open(uri, select);
	}

	/**
	 * @see ummisco.gama.ui.utils.IModelEditor#open(org.eclipse.emf.common.util.URI, org.eclipse.emf.ecore.EReference,
	 *      int, boolean)
	 */
	@Override
	public IEditorPart open(final URI referenceOwnerURI, final EReference reference, final int indexInList,
			final boolean select) {
		return opener.open(referenceOwnerURI, reference, indexInList, select);
	}

}
