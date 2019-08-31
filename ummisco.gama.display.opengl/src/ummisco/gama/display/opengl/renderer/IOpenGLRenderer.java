package ummisco.gama.display.opengl.renderer;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import msi.gama.common.interfaces.IGraphics;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.outputs.LayeredDisplayData;
import ummisco.gama.display.opengl.OpenGL;
import ummisco.gama.display.opengl.renderer.helpers.CameraHelper;
import ummisco.gama.display.opengl.renderer.helpers.KeystoneHelper;
import ummisco.gama.display.opengl.renderer.helpers.LightHelper;
import ummisco.gama.display.opengl.renderer.helpers.PickingHelper;
import ummisco.gama.display.opengl.renderer.helpers.SceneHelper;
import ummisco.gama.display.opengl.view.SWTOpenGLDisplaySurface;

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

	default LayeredDisplayData getData() {
		return getSurface().getData();
	}

	int getLayerWidth();

	int getLayerHeight();

	default boolean useShader() {
		return false;
	}

	boolean isDisposed();

}