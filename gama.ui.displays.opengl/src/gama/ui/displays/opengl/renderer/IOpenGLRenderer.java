package gama.ui.displays.opengl.renderer;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import gama.ui.displays.opengl.OpenGL;
import gama.ui.displays.opengl.renderer.helpers.CameraHelper;
import gama.ui.displays.opengl.renderer.helpers.KeystoneHelper;
import gama.ui.displays.opengl.renderer.helpers.LightHelper;
import gama.ui.displays.opengl.renderer.helpers.PickingHelper;
import gama.ui.displays.opengl.renderer.helpers.SceneHelper;
import gama.ui.displays.opengl.view.SWTOpenGLDisplaySurface;
import gama.common.interfaces.outputs.IDisplayData;
import gama.common.interfaces.outputs.IGraphics;
import gama.metamodel.shape.GamaPoint;

public interface IOpenGLRenderer extends GLEventListener, IGraphics.ThreeD {

	void setCanvas(GLAutoDrawable canvas);

	GLAutoDrawable getCanvas();

	void initScene();

	double getWidth();

	double getHeight();

	GamaPoint getRealWorldPointFromWindowPoint(final GamaPoint mouse);

	@Override
	SWTOpenGLDisplaySurface getSurface();

	CameraHelper getCameraHelper();

	KeystoneHelper getKeystoneHelper();

	PickingHelper getPickingHelper();

	OpenGL getOpenGLHelper();

	LightHelper getLightHelper();

	SceneHelper getSceneHelper();

	default IDisplayData getData() {
		return getSurface().getData();
	}

	int getLayerWidth();

	int getLayerHeight();

	default boolean useShader() {
		return false;
	}

	boolean isDisposed();

}