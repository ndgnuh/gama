package msi.gaml.compilation.interfaces;

import java.net.URL;
import java.util.List;

import org.eclipse.emf.common.util.URI;

import msi.gama.common.interfaces.IModel;
import msi.gaml.compilation.GamlCompilationError;

public interface IGamlModelBuilder {

	void loadURLs(final List<URL> URLs);

	IModel compile(final URI uri, final List<GamlCompilationError> errors);

	IModel compile(final URL url, final List<GamlCompilationError> errors);

}
