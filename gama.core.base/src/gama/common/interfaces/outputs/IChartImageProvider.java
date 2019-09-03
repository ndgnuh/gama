package gama.common.interfaces.outputs;

import java.awt.image.BufferedImage;

public interface IChartImageProvider {
	BufferedImage getImage(final int sizeX, final int sizeY, final boolean antiAlias);

}
