/*******************************************************************************************************
 *
 * gama.ui.displays.opengl.scene.ResourceObject.java, in plugin gama.ui.displays.opengl, is part of the source
 * code of the GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.ui.displays.opengl.scene;

import gama.util.file.IGamaFile;
import gaml.statements.draw.DrawingAttributes;

public class ResourceObject extends AbstractObject<IGamaFile.Geom, DrawingAttributes> {

	public ResourceObject(final IGamaFile.Geom file, final DrawingAttributes attributes) {
		super(file, attributes, DrawerType.RESOURCE);
	}

}
