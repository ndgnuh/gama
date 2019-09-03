/*******************************************************************************************************
 *
 * ummisco.gama.outputs.layers.charts.ChartLayer.java, in plugin msi.gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package ummisco.gama.outputs.layers.charts;

import java.awt.geom.Rectangle2D;

import msi.gama.common.interfaces.outputs.IDisplaySurface;
import msi.gama.common.interfaces.outputs.IGraphics;
import msi.gama.common.interfaces.outputs.ILayerStatement;
import msi.gama.metamodel.shape.IShape;
import msi.gama.runtime.scope.IScope;
import ummisco.gama.outputs.layers.AbstractLayer;

/**
 * Written by drogoul Modified on 1 avr. 2010
 *
 * @todo Description
 *
 */
public class ChartLayer extends AbstractLayer {

	public ChartLayer(final ILayerStatement model) {
		super(model);
	}

	@Override
	public Rectangle2D focusOn(final IShape geometry, final IDisplaySurface s) {
		return null;
	}

	private ChartOutput getChart() {
		return ((ChartLayerStatement) definition).getOutput();
	}

	@Override
	public String getType() {
		return "Chart layer";
	}

	@Override
	public void privateDraw(final IScope scope, final IGraphics dg) {
		dg.drawChart(getChart());
	}

	@Override
	public boolean stayProportional() {
		return false;
	}

	@Override
	public boolean isProvidingWorldCoordinates() {
		return false;
	}

	@Override
	public void getModelCoordinatesInfo(final int xOnScreen, final int yOnScreen, final IDisplaySurface g,
			final StringBuilder sb) {
		getChart().getModelCoordinatesInfo(xOnScreen, yOnScreen, g, getData().getPositionInPixels(), sb);
	}

}
