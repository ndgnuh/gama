package gama.common.interfaces.outputs;

import gama.runtime.IUpdaterMessage;

public interface IOverlayInfo extends IUpdaterMessage {

	String[] getInfos();

	void setInfos(String[] infos);

	java.util.List<int[]> getColors();

	void setColors(java.util.List<int[]> colors);

}