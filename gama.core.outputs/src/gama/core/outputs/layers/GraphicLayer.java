/*******************************************************************************************************
 *
 * gama.core.outputs.layers.GraphicLayer.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling
 * and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.outputs.layers;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IKeyword;
import gama.common.interfaces.outputs.IGraphics;
import gama.common.interfaces.outputs.ILayerStatement;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;

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

	// Just a trial to make sure that graphics + chart produce not proportional results.
	@Override
	public boolean stayProportional() {
		return false;
	}
}
