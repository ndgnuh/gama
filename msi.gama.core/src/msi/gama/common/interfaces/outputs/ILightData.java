package msi.gama.common.interfaces.outputs;

import msi.gama.metamodel.shape.GamaPoint;

public interface ILightData {

	public enum TYPE {
		DIRECTION, POINT, SPOT
	}

	GamaPoint getPosition();

	TYPE getType();

	int getId();

	void setDrawLight(boolean b);

}