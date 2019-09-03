package msi.gama.common.interfaces.outputs;

public interface IDisplayDataListener {

	enum Changes {
		SPLIT_LAYER,
		CHANGE_CAMERA,
		CAMERA_POS,
		CAMERA_TARGET,
		CAMERA_UP,
		CAMERA_PRESET,
		BACKGROUND,
		HIGHLIGHT,
		ZOOM,
		KEYSTONE,
		ANTIALIAS,
		ROTATION;
	}

	void changed(Changes property, Object value);
}