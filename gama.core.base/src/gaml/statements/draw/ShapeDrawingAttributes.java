/*******************************************************************************************************
 *
 * gaml.statements.draw.ShapeDrawingAttributes.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.statements.draw;

import java.util.List;

import org.locationtech.jts.geom.ShapeType;

import gama.common.geometry.AxisAngle;
import gama.common.geometry.Scaling3D;
import gama.common.interfaces.IAgent;
import gama.metamodel.shape.GamaPoint;
import gama.metamodel.shape.IShape;
import gama.util.GamaColor;
import gama.util.GamaMaterial;
import gama.util.file.IGamaFile;

@SuppressWarnings ({ "rawtypes", "unchecked" })
public class ShapeDrawingAttributes extends FileDrawingAttributes {

	public ShapeDrawingAttributes(final Scaling3D size, final Double depth, final AxisAngle rotation,
			final GamaPoint location, final Boolean empty, final GamaColor color, /* final List<GamaColor> colors, */
			final GamaColor border, final List<IGamaFile.Image> textures, final GamaMaterial material,
			final IAgent agent, final ShapeType type, final Double lineWidth, final Boolean lighting) {
		super(size, rotation, location, color, border, agent, lineWidth, false, lighting);
		setHeight(depth);
		setEmpty(empty);
		setTextures(textures);
		setMaterial(material);
		setType(type);
		// setColors(colors);
	}

	public ShapeDrawingAttributes(final GamaPoint location, final GamaColor color, final GamaColor border,
			final ShapeType type) {
		super(null, null, location, color, border, null, null, false, null);
		setHeight(null);
		setEmpty(color == null);
		setTextures(null);
		setMaterial(null);
		setType(type);
		// this(null, null, null, location, color == null, color, /* null, */ border, null, null, null, type, null,
		// null);
	}

	public ShapeDrawingAttributes(final IShape shape, final IAgent agent, final GamaColor color,
			final GamaColor border) {
		this(shape, agent, color, border, shape.getGeometricalType(), null);
	}

	public ShapeDrawingAttributes(final IShape shape, final IAgent agent, final GamaColor color, final GamaColor border,
			final ShapeType type, final Double lineWidth) {
		this(null, null, null, shape.getLocation(), color == null, color, /* null, */ border, null, null, agent, type,
				lineWidth, null);
	}

}