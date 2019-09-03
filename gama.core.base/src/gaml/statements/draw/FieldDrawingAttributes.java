/*******************************************************************************************************
 *
 * gaml.statements.draw.FieldDrawingAttributes.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.statements.draw;

import gama.metamodel.shape.GamaPoint;
import gama.util.GamaColor;

public class FieldDrawingAttributes extends FileDrawingAttributes {

	public String speciesName;
	public boolean triangulated;
	public boolean grayScaled;
	public boolean withText;
	public GamaPoint cellSize;

	/**
	 * @param name
	 * @param lineColor
	 */
	public FieldDrawingAttributes(final String name, final GamaColor border, final boolean isImage) {
		super(null, isImage);
		speciesName = name;
	}

	public void setSpeciesName(final String name) {
		speciesName = name;
	}

	@Override
	public String getSpeciesName() {
		return speciesName;
	}

	public void setCellSize(final GamaPoint p) {
		cellSize = p;

	}

	public GamaPoint getCellSize() {
		return cellSize;
	}

}