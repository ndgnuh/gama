/*******************************************************************************************************
 *
 * msi.gama.util.GamaFont.java, in plugin msi.gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package msi.gama.util;

import java.awt.Font;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.common.interfaces.IValue;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.types.IType;
import msi.gaml.types.Types;
import ummisco.gama.processor.IConcept;
import ummisco.gama.processor.IOperatorCategory;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.example;
import ummisco.gama.processor.GamlAnnotations.getter;
import ummisco.gama.processor.GamlAnnotations.no_test;
import ummisco.gama.processor.GamlAnnotations.operator;
import ummisco.gama.processor.GamlAnnotations.variable;
import ummisco.gama.processor.GamlAnnotations.vars;

/**
 * Class GamaFont. A simple wrapper on an AWT Font
 *
 * @author drogoul
 * @since 22 mars 2015
 *
 */
@vars({ @variable(name = IKeyword.NAME, type = IType.STRING, doc = { @doc("Returns the name of this font") }),
		@variable(name = IKeyword.SIZE, type = IType.INT, doc = { @doc("Returns the size (in points) of this font") }),
		@variable(name = IKeyword.STYLE, type = IType.INT, doc = {
				@doc("Returns the style of this font (0 for plain, 1 for bold, 2 for italic, 3 for bold+italic)") }) })
public class GamaFont extends Font implements IValue {

	/**
	 * @param name
	 * @param style
	 * @param size
	 */
	public GamaFont(final String name, final int style, final int size) {
		super(name, style, size);
	}

	public GamaFont(final Font font) {
		super(font);
	}

	@Override
	@getter(IKeyword.NAME)
	public String getName() {
		return name;
	}

	@Override
	@getter(IKeyword.SIZE)
	public int getSize() {
		return size;
	}

	@Override
	@getter(IKeyword.STYLE)
	public int getStyle() {
		return style;
	}

	/**
	 * Method serialize()
	 * 
	 * @see msi.gama.common.interfaces.IGamlable#serialize(boolean)
	 */
	@Override
	public String serialize(final boolean includingBuiltIn) {
		String strStyle;

		if (isBold()) {
			strStyle = isItalic() ? "#bold + #italic" : "#bold";
		} else {
			strStyle = isItalic() ? "#italic" : "#plain";
		}

		return "font('" + name + "'," + pointSize + "," + strStyle + ")";
	}

	/**
	 * Method getType()
	 * 
	 * @see msi.gama.common.interfaces.ITyped#getGamlType()
	 */
	@Override
	public IType<?> getGamlType() {
		return Types.FONT;
	}

	/**
	 * Method stringValue(). Outputs to a format that is usable by
	 * Font.decode(String);
	 * 
	 * @see msi.gama.common.interfaces.IValue#stringValue(msi.gama.runtime.scope.IScope)
	 */
	@Override
	public String stringValue(final IScope scope) throws GamaRuntimeException {
		return toString();
	}

	@Override
	public String toString() {
		String strStyle;
		if (isBold()) {
			strStyle = isItalic() ? "bolditalic" : "bold";
		} else {
			strStyle = isItalic() ? "italic" : "plain";
		}
		return name + "-" + strStyle + "-" + size;
	}

	/**
	 * Method copy()
	 * 
	 * @see msi.gama.common.interfaces.IValue#copy(msi.gama.runtime.scope.IScope)
	 */
	@Override
	public IValue copy(final IScope scope) throws GamaRuntimeException {
		return new GamaFont(name, style, size);
	}

	@operator(value = "font", category = { IOperatorCategory.CASTING }, concept = { IConcept.TEXT,
			IConcept.DISPLAY }, can_be_const = true)
	@doc(value = "Creates a new font, by specifying its name (either a font face name like 'Lucida Grande Bold' or 'Helvetica', or a logical name like 'Dialog', 'SansSerif', 'Serif', etc.), a size in points and a style, either #bold, #italic or #plain or a combination (addition) of them.", examples = @example(value = "font ('Helvetica Neue',12, #bold + #italic)", equals = "a bold and italic face of the Helvetica Neue family", test = false))
	@no_test
	public static GamaFont font(final String name, final Integer size, final Integer style) {
		return new GamaFont(name, style, size);
	}

}
