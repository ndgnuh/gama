/*******************************************************************************************************
 *
 * msi.gama.util.file.GamaGraphMLFile.java, in plugin msi.gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.extensions.files;

import gama.common.geometry.Envelope3D;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.graph.IGraph;
import gaml.types.IContainerType;
import gaml.types.Types;

public class GamaGraphMLFile extends GamaGraphFile {

	public GamaGraphMLFile(final IScope scope, final String pn) throws GamaRuntimeException {
		super(scope, pn);
	}

	public GamaGraphMLFile(final IScope scope, final String pathName, final IGraph<?, ?> container) {
		super(scope, pathName, container);
	}

	@Override
	public Envelope3D computeEnvelope(final IScope scope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContainerType<?> getGamlType() {
		return Types.GRAPH;
	}

}
