/*********************************************************************************************
 *
 * 'OpenGLDisplayView.java, in plugin gama.ui.displays.opengl, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.displays.opengl.view;

import static gama.GAMA.getGui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.jogamp.opengl.FPSCounter;
import com.jogamp.opengl.swt.GLCanvas;
import com.jogamp.opengl.util.GLDrawableUtil;

import gama.dev.utils.DEBUG;
import gama.ui.displays.opengl.renderer.helpers.CameraHelper;
import gama.ui.experiment.views.displays.SWTDisplayView;

/**
 * Class OpenGLLayeredDisplayView.
 *
 * @author drogoul
 * @since 25 mars 2015
 *
 */
public class OpenGLDisplayView extends SWTDisplayView {

	{
		DEBUG.OFF();
	}

	@Override
	public SWTOpenGLDisplaySurface getDisplaySurface() {
		return (SWTOpenGLDisplaySurface) super.getDisplaySurface();
	}

	private GLCanvas createCanvas(final Composite parent, final SWTOpenGLDisplaySurface surface) {
		GLCanvas canvas = new GLCanvas(parent, SWT.NONE, surface.getCapabilities(), null);
		canvas.setAutoSwapBufferMode(true);
		final SWTGLAnimator animator = new SWTGLAnimator(canvas);
		animator.setUpdateFPSFrames(FPSCounter.DEFAULT_FRAMES_PER_INTERVAL, null);
		surface.setAnimatorFrom(canvas);
		final FillLayout gl = new FillLayout();
		canvas.setLayout(gl);
		return canvas;
	}

	@Override
	protected Composite createSurfaceComposite(final Composite parent) {
		final SWTOpenGLDisplaySurface surface = (SWTOpenGLDisplaySurface) getGui().getDisplaySurfaceFor(getOutput());
		surfaceComposite = createCanvas(parent, surface);
		surface.outputReloaded();
		return surfaceComposite;
	}

	@Override
	public boolean forceOverlayVisibility() {
		final SWTOpenGLDisplaySurface surface = getDisplaySurface();
		return surface != null && surface.getROIDimensions() != null;
	}

	@Override
	public List<String> getCameraNames() {
		return new ArrayList<>(CameraHelper.PRESETS.keySet());
	}

	@Override
	public void fullScreenSet() {
		// DEBUG.OUT("Invoking explicit reshape + display");
		getDisplaySurface().renderer.getCanvas().invoke(false,
				new GLDrawableUtil.ReshapeGLEventListener(getDisplaySurface().renderer, true));
	}

}
