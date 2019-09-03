/*******************************************************************************************************
 *
 * gaml.statements.draw.TextDrawingAttributes.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.statements.draw;

import org.locationtech.jts.geom.ShapeType;

import gama.common.geometry.AxisAngle;
import gama.common.geometry.Scaling3D;
import gama.metamodel.shape.GamaPoint;
import gama.util.GamaColor;
import gama.util.GamaFont;

@SuppressWarnings ({ "rawtypes", "unchecked" })
public class TextDrawingAttributes extends DrawingAttributes implements Cloneable {

	public final GamaFont font;
	public final boolean perspective;
	public final GamaPoint anchor;

	public TextDrawingAttributes(final Scaling3D size, final AxisAngle rotation, final GamaPoint location,
			final GamaPoint anchor, final GamaColor color, final GamaFont font, final Boolean perspective) {
		super(size, rotation, location, color, null, null);
		setType(ShapeType.POLYGON);
		this.font = font;
		this.anchor = anchor;
		this.perspective = perspective == null || perspective.booleanValue();
	}

	public TextDrawingAttributes copyTranslatedBy(final GamaPoint p) {
		return new TextDrawingAttributes(getSize(), getRotation(), getLocation().plus(p), getAnchor(), getColor(), font,
				perspective);
	}

	@Override
	public GamaPoint getAnchor() {
		return anchor;
	}

}