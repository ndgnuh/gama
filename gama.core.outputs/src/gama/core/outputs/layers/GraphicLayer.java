/*******************************************************************************************************
 *
 * gama.core.outputs.layers.GraphicLayer.java, in plugin msi.gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.core.outputs.layers;

import msi.gama.common.interfaces.IAgent;
import msi.gama.common.interfaces.IKeyword;
import msi.gama.common.interfaces.outputs.IGraphics;
import msi.gama.common.interfaces.outputs.ILayerStatement;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;

public class GraphicLayer extends AbstractLayer {

	public GraphicLayer(final ILayerStatement layer) {
		super(layer);
	}

	@Override
	protected void privateDraw(final IScope scope, final IGraphics g) throws GamaRuntimeException {
		final IAgent agent = scope.getAgent();
		scope.execute(((GraphicLayerStatement) definition).getAspect(), agent, null);
	}

	@Override
	public String getType() {
		return IKeyword.GRAPHICS;
	}

}
