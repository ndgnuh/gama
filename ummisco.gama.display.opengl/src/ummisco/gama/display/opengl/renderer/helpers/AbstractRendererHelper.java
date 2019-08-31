package ummisco.gama.display.opengl.renderer.helpers;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import msi.gama.outputs.LayeredDisplayData;
import ummisco.gama.display.opengl.OpenGL;
import ummisco.gama.display.opengl.renderer.IOpenGLRenderer;
import ummisco.gama.display.opengl.view.SWTOpenGLDisplaySurface;

public abstract class AbstractRendererHelper {

	private final IOpenGLRenderer renderer;

	public AbstractRendererHelper(final IOpenGLRenderer renderer) {
		this.renderer = renderer;
	}

	protected IOpenGLRenderer getRenderer() {
		return renderer;
	}

	protected LayeredDisplayData getData() {
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
