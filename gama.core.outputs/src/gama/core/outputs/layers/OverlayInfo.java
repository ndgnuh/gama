package gama.core.outputs.layers;

import msi.gama.common.interfaces.outputs.IOverlayInfo;

public class OverlayInfo implements IOverlayInfo {

	private String[] infos;

	private java.util.List<int[]> colors;

	OverlayInfo(final String[] infos, final java.util.List<int[]> colors) {
		this.setInfos(infos);
		this.setColors(colors);
	}

	@Override
	public String[] getInfos() {
		return infos;
	}

	@Override
	public void setInfos(final String[] infos) {
		this.infos = infos;
	}

	@Override
	public java.util.List<int[]> getColors() {
		return colors;
	}

	@Override
	public void setColors(final java.util.List<int[]> colors) {
		this.colors = colors;
	}

}