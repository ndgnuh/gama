/*********************************************************************************************
 *
 * 'PreferencesHelper.java, in plugin gama.ui.base.shared, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.base;

import static gama.ui.base.menus.GamaColorMenu.SORT_NAMES;
import static gama.ui.base.menus.GamaColorMenu.byBrightness;
import static gama.ui.base.menus.GamaColorMenu.byLuminescence;
import static gama.ui.base.menus.GamaColorMenu.byName;
import static gama.ui.base.menus.GamaColorMenu.byRGB;
import static gama.ui.base.menus.GamaColorMenu.colorComp;
import static gama.ui.base.resources.GamaColors.toGamaColor;
import static gama.ui.base.resources.GamaFonts.baseSize;
import static gama.ui.base.resources.GamaFonts.getBaseFont;
import static gama.ui.base.resources.GamaFonts.setLabelFont;
import static gama.ui.base.resources.IGamaColors.WARNING;
import static gama.common.interfaces.gui.IGui.NAVIGATOR_LIGHTWEIGHT_DECORATOR_ID;
import static gama.common.preferences.GamaPreferences.create;
import static gama.common.preferences.GamaPreferences.Interface.APPEARANCE;
import static gama.common.preferences.GamaPreferences.Interface.MENUS;
import static gama.common.preferences.GamaPreferences.Interface.NAME;
import static gama.util.GamaColor.getNamed;
import static org.eclipse.ui.PlatformUI.getWorkbench;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IDecoratorManager;

import gama.ui.base.menus.GamaColorMenu;
import gama.ui.base.resources.IGamaColors;
import gama.common.preferences.GamaPreferences;
import gama.common.preferences.Pref;
import gama.util.GamaColor;
import gama.util.GamaFont;
import gaml.types.IType;

public class GamaUIPreferences {

	public static void initializePrefs() {

	}

	public static final Pref<Boolean> CORE_EDITORS_HIGHLIGHT =
			create("pref_editors_highligth", "Highlight in yellow the title of value editors when they change", true,
					IType.BOOL, true).in(NAME, APPEARANCE);

	public static final Pref<GamaColor> SHAPEFILE_VIEWER_FILL = create("pref_shapefile_background_color",
			"Shapefile viewer fill color", () -> getNamed("lightgray"), IType.COLOR, false).in(NAME, APPEARANCE);

	public static final Pref<GamaColor> SHAPEFILE_VIEWER_LINE_COLOR = create("pref_shapefile_line_color",
			"Shapefile viewer line color", () -> getNamed("black"), IType.COLOR, false).in(NAME, APPEARANCE);

	public static final Pref<GamaColor> ERROR_TEXT_COLOR =
			create("pref_error_text_color", "Text color of errors", () -> toGamaColor(IGamaColors.ERROR.inactive()),
					IType.COLOR, true).in(GamaPreferences.Runtime.NAME, GamaPreferences.Runtime.ERRORS);

	public static final Pref<GamaColor> WARNING_TEXT_COLOR =
			create("pref_warning_text_color", "Text color of warnings", () -> toGamaColor(WARNING.inactive()),
					IType.COLOR, true).in(GamaPreferences.Runtime.NAME, GamaPreferences.Runtime.ERRORS);

	public static final Pref<GamaColor> IMAGE_VIEWER_BACKGROUND =
			create("pref_image_background_color", "Image viewer background color", () -> GamaColor.getNamed("white"),
					IType.COLOR, false).in(NAME, APPEARANCE);

	public static final Pref<GamaFont> BASE_BUTTON_FONT = create("pref_button_font", "Font of buttons and dialogs",
			() -> new GamaFont(getBaseFont(), SWT.BOLD, baseSize), IType.FONT, false).in(NAME, APPEARANCE)
					.onChange(newValue -> setLabelFont(newValue));

	public static Pref<String> COLOR_MENU_SORT =
			create("pref_menu_colors_sort", "Sort colors menu by", "RGB value", IType.STRING, false).among(SORT_NAMES)
					.activates("menu.colors.reverse", "menu.colors.group").in(NAME, MENUS).onChange(pref -> {
						if (pref.equals(SORT_NAMES[0])) {
							colorComp = byRGB;
						} else if (pref.equals(SORT_NAMES[1])) {
							colorComp = byName;
						} else if (pref.equals(SORT_NAMES[2])) {
							colorComp = byBrightness;
						} else {
							colorComp = byLuminescence;
						}
					});
	public static Pref<Boolean> COLOR_MENU_REVERSE =
			create("pref_menu_colors_reverse", "Reverse order", false, IType.BOOL, false).in(NAME, MENUS)
					.onChange(pref -> GamaColorMenu.setReverse(pref ? -1 : 1));
	public static Pref<Boolean> COLOR_MENU_GROUP =
			create("pref_menu_colors_group", "Group colors", false, IType.BOOL, false).in(NAME, MENUS)
					.onChange(pref -> GamaColorMenu.breakdown = pref);
	public static final Pref<Boolean> NAVIGATOR_METADATA =
			create("pref_navigator_display_metadata", "Display metadata in navigator", true, IType.BOOL, false)
					.in(NAME, APPEARANCE).onChange(newValue -> {
						final IDecoratorManager mgr = getWorkbench().getDecoratorManager();
						try {
							mgr.setEnabled(NAVIGATOR_LIGHTWEIGHT_DECORATOR_ID, newValue);
						} catch (final CoreException e) {
							e.printStackTrace();
						}

					});

}
