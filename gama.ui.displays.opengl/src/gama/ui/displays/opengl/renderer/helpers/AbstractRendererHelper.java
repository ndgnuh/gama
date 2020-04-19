package gama.ui.displays.opengl.renderer.helpers;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import gama.common.interfaces.outputs.IDisplayData;
import gama.ui.displays.opengl.OpenGL;
import gama.ui.displays.opengl.renderer.IOpenGLRenderer;
import gama.ui.displays.opengl.view.SWTOpenGLDisplaySurface;

public abstract class AbstractRendererHelper {

	public interface Pass extends AutoCloseable {

		@Override
		void close();

	}

	private final IOpenGLRenderer renderer;

	public AbstractRendererHelper(final IOpenGLRenderer renderer) {
		this.renderer = renderer;
	}

	protected IOpenGLRenderer getRenderer() {
		return renderer;
	}

	protected IDisplayData getData() {
		return renderer.getData();
	}

	protected GL2 getGL() {
		return renderer.getOpenGLHelper().getGL();
	}

	protected OpenGL getOpenGL() {
		return renderer.getOpenGLHelper();
	}

	protected GLAutoDrawable getCanvas() {
		return renderer.getCanvas();
	}

	protected SWTOpenGLDisplaySurface getSurface() {
		return renderer.getSurface();
	}

	public double getMaxEnvDim() {
		return renderer.getMaxEnvDim();
	}

	public double getZNear() {
		return renderer.getData().getzNear();
	}

	public double getZFar() {
		return renderer.getData().getzFar();
	}

	public abstract void initialize();

}
