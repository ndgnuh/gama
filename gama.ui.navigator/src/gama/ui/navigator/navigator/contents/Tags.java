/*********************************************************************************************
 *
 * 'WrappedFolder.java, in plugin gama.ui.base.navigator, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.navigator.navigator.contents;

import java.util.Collection;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import gama.ui.base.resources.GamaFonts;
import gama.ui.base.resources.GamaIcons;
import gama.ui.base.resources.IGamaColors;
import gama.ui.base.resources.GamaColors.GamaUIColor;

/**
 * Class ImportFolder.
 *
 * @author drogoul
 * @since 5 févr. 2015
 *
 */
public class Tags extends VirtualContent<WrappedFile> {

	final Collection<String> tags;

	/**
	 * @param root
	 * @param name
	 */
	public Tags(final WrappedFile root, final Collection<String> object, final String name) {
		super(root, name);
		tags = object;
	}

	/**
	 * Method hasChildren()
	 *
	 * @see gama.ui.base.navigator.contents.VirtualContent#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		return !tags.isEmpty();
	}

	@Override
	public Font getFont() {
		return GamaFonts.getSmallFont(); // by default
	}

	@Override
	public WrappedFile getParent() {
		return super.getParent();
	}

	/**
	 * Method getNavigatorChildren()
	 *
	 * @see gama.ui.base.navigator.contents.VirtualContent#getNavigatorChildren()
	 */
	@Override
	public Object[] getNavigatorChildren() {
		if (tags.isEmpty()) { return EMPTY; }
		return tags.stream().map((each) -> new Tag(this, each)).toArray();
	}

	/**
	 * Method getImage()
	 *
	 * @see gama.ui.base.navigator.contents.VirtualContent#getImage()
	 */
	@Override
	public Image getImage() {
		return GamaIcons.create("gaml/_attributes").image();
	}

	/**
	 * Method getColor()
	 *
	 * @see gama.ui.base.navigator.contents.VirtualContent#getColor()
	 */
	@Override
	public Color getColor() {
		return IGamaColors.BLACK.color();
	}

	@Override
	public void getSuffix(final StringBuilder sb) {}

	@Override
	public int findMaxProblemSeverity() {
		return 0;
	}

	@Override
	public ImageDescriptor getOverlay() {
		return null;
	}

	@Override
	public VirtualContentType getType() {
		return VirtualContentType.CATEGORY;
	}

	@Override
	public String getStatusMessage() {
		return "Tags";
	}

	@Override
	public GamaUIColor getStatusColor() {
		return IGamaColors.GRAY_LABEL;
	}

	@Override
	public Image getStatusImage() {
		return getImage();
	}

}
