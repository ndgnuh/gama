/*******************************************************************************************************
 *
 * gama.core.outputs.layers.OverlayLayer.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling
 * and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.outputs.layers;

import java.awt.geom.Rectangle2D;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IKeyword;
import gama.common.interfaces.outputs.IDisplaySurface;
import gama.common.interfaces.outputs.IGraphics;
import gama.common.interfaces.outputs.ILayer;
import gama.common.interfaces.outputs.ILayerStatement;
import gama.metamodel.shape.IShape;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;

/**
 * Class OverlayLayer.
 *
 * @author drogoul
 * @since 23 févr. 2016
 *
 */
public class OverlayLayer extends GraphicLayer implements ILayer.Overlay {

	public OverlayLayer(final ILayerStatement layer) {
		super(layer);
	}

	@Override
	public boolean isOverlay() {
		return true;
	}

	@Override
	protected OverlayLayerData createData() {
		return new OverlayLayerData(definition);
	}

	@Override
	public OverlayLayerData getData() {
		return (OverlayLayerData) super.getData();
	}

	@Override
	public Rectangle2D focusOn(final IShape geometry, final IDisplaySurface s) {
		return null;
	}

	@Override
	public String getType() {
		return IKeyword.OVERLAY;
	}

	@Override
	protected void privateDraw(final IScope scope, final IGraphics g) throws GamaRuntimeException {
		g.setOpacity(1);
		final IAgent agent = scope.getAgent();
		scope.execute(((OverlayStatement) definition).getAspect(), agent, null);
	}

	@Override
	public void draw(final IScope scope, final IGraphics g) throws GamaRuntimeException {
		if (scope == null)
			return;
		getData().compute(scope, g);
		g.beginDrawingLayer(this);
		g.setOpacity(1 - getData().getTransparency(scope));
		g.beginOverlay(this);
		privateDraw(scope, g);
		g.endOverlay();
		g.endDrawingLayer(this);
	}

	@Override
	public boolean isProvidingCoordinates() {
		return false; // by default
	}

	@Override
	public boolean isProvidingWorldCoordinates() {
		return false; // by default
	}

}
