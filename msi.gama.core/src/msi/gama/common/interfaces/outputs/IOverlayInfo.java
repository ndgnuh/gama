package msi.gama.common.interfaces.outputs;

import msi.gama.runtime.IUpdaterMessage;

public interface IOverlayInfo extends IUpdaterMessage {

	String[] getInfos();

	void setInfos(String[] infos);

	java.util.List<int[]> getColors();

	void setColors(java.util.List<int[]> colors);

}