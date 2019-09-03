/*******************************************************************************************************
 *
 * gama.core.outputs.layers.OverlayLayerData.java, in plugin msi.gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.outputs.layers;

import java.awt.Color;

import gama.common.interfaces.IKeyword;
import gama.common.interfaces.outputs.IGraphics;
import gama.common.interfaces.outputs.ILayerData;
import gama.common.interfaces.outputs.ILayerStatement;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.GamaColor;
import gaml.types.Types;

public class OverlayLayerData extends LayerData implements ILayerData.Overlay {

	final Attribute<GamaColor> border;
	final Attribute<GamaColor> background;
	final Attribute<Boolean> rounded;
	boolean computed;

	public OverlayLayerData(final ILayerStatement def) throws GamaRuntimeException {
		super(def);
		border = create(IKeyword.BORDER, Types.COLOR, null);
		background = create(IKeyword.BACKGROUND, Types.COLOR, new GamaColor(Color.black));
		rounded = create(IKeyword.ROUNDED, Types.BOOL, true);
	}

	public Color getBackgroundColor(final IScope scope) {
		return new Color(background.get().getRed(), background.get().getGreen(), background.get().getBlue(),
				(int) (getTransparency(scope) * 255));
	}

	@Override
	public void computePixelsDimensions(final IGraphics g) {
		if (computed)
			return;
		super.computePixelsDimensions(g);
		computed = true;
	}

	public Color getBorderColor() {
		return border.get();
	}

	public boolean isRounded() {
		return rounded.get();
	}

}
