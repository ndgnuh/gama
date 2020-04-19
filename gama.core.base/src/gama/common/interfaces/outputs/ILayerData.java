/*******************************************************************************************************
 *
 * gama.outputs.layers.ILayerData.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.common.interfaces.outputs;

import java.awt.Color;
import java.awt.Point;

import org.locationtech.jts.geom.Envelope;

import gama.metamodel.shape.GamaPoint;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;

/**
 * The class IDisplayLayerBox.
 *
 * @author drogoul
 * @since 14 d�c. 2011
 *
 */
public interface ILayerData {

	public interface Overlay {

		Color getBackgroundColor(IScope scope);

		boolean isRounded();

		Color getBorderColor();

	}

	void compute(final IScope sim, IGraphics g) throws GamaRuntimeException;

	void setTransparency(final double f);

	Double getTransparency(final IScope scope);

	void setSize(final GamaPoint p);

	void setSize(final double width, final double height, final double depth);

	boolean isRelativePosition();

	boolean isRelativeSize();

	void setPosition(final GamaPoint p);

	void setPosition(final double x, final double y, final double z);

	GamaPoint getPosition();

	GamaPoint getSize();

	Boolean getRefresh();

	Integer getTrace();

	Boolean getFading();

	Boolean isSelectable();

	void setSelectable(Boolean b);

	Point getPositionInPixels();

	Point getSizeInPixels();

	void computePixelsDimensions(IGraphics g);

	void addElevation(double currentElevation);

	void setVisibleRegion(Envelope e);

	Envelope getVisibleRegion();

	double getAddedElevation();

	/**
	 * Whether the layer is to be refreshed dynamically everytime the surface displays itself
	 *
	 * @return true if the layer is dynamic, false otherwise
	 */
	default boolean isDynamic() {
		return getRefresh() == null || getRefresh();
	}

}