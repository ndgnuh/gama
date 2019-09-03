/*******************************************************************************************************
 *
 * gama.common.interfaces.IGraphics.java, in plugin gama.core, is part of the source code of the GAMA modeling
 * and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.common.interfaces.outputs;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

import gama.metamodel.shape.GamaPoint;
import gama.util.file.IGamaFile;
import gaml.statements.draw.DrawingAttributes;
import gaml.statements.draw.FieldDrawingAttributes;
import gaml.statements.draw.TextDrawingAttributes;

/**
 * Written by drogoul Modified on 22 janv. 2011
 *
 * @todo Description
 *
 */
public interface IGraphics {

	public interface ThreeD extends IGraphics {

		@Override
		default boolean is2D() {
			return false;
		}

		GamaPoint getCameraPos();

		GamaPoint getCameraTarget();

		GamaPoint getCameraOrientation();
	}

	RenderingHints QUALITY_RENDERING = new RenderingHints(null);
	RenderingHints SPEED_RENDERING = new RenderingHints(null);
	RenderingHints MEDIUM_RENDERING = new RenderingHints(null);

	void setDisplaySurface(final IDisplaySurface surface);

	int getDisplayWidth();

	int getDisplayHeight();

	Rectangle2D drawFile(IGamaFile<?, ?> file, DrawingAttributes attributes);

	Rectangle2D drawField(final double[] values, final FieldDrawingAttributes attributes);

	Rectangle2D drawImage(final BufferedImage img, final DrawingAttributes attributes);

	Rectangle2D drawChart(IChartImageProvider chart);

	Rectangle2D drawString(final String string, final TextDrawingAttributes attributes);

	Rectangle2D drawShape(final Geometry shape, final DrawingAttributes attributes);

	void setOpacity(double i);

	void fillBackground(Color bgColor, double opacity);

	boolean beginDrawingLayers();

	void beginDrawingLayer(ILayer layer);

	void beginOverlay(ILayer.Overlay layer);

	void endOverlay();

	double getyRatioBetweenPixelsAndModelUnits();

	double getxRatioBetweenPixelsAndModelUnits();

	/*
	 * Returns the region of the current layer (in model units) that is visible on screen
	 */
	Envelope getVisibleRegion();

	void endDrawingLayer(ILayer layer);

	void endDrawingLayers();

	void beginHighlight();

	void endHighlight();

	double getXOffsetInPixels();

	double getYOffsetInPixels();

	Double getZoomLevel();

	default boolean is2D() {
		return true;
	}

	int getViewWidth();

	int getViewHeight();

	IDisplaySurface getSurface();

	default double getMaxEnvDim() {
		return getSurface().getData().getMaxEnvDim();
	}

	default double getEnvWidth() {
		return getSurface().getData().getEnvWidth();
	}

	default double getEnvHeight() {
		return getSurface().getData().getEnvHeight();
	}

	void dispose();

	boolean cannotDraw();

	boolean isNotReadyToUpdate();

	/**
	 * Ask the IGraphics instance to accumulate temporary envelopes
	 *
	 * @param env
	 */
	default void accumulateTemporaryEnvelope(final Rectangle2D env) {}

	default Rectangle2D getAndWipeTemporaryEnvelope() {
		return null;
	}

}