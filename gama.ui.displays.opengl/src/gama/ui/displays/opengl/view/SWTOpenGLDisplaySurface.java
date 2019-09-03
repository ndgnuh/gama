/*******************************************************************************************************
 *
 * gama.ui.displays.opengl.view.SWTOpenGLDisplaySurface.java, in plugin gama.ui.displays.opengl, is part of the
 * source code of the GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.ui.displays.opengl.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.locationtech.jts.geom.Envelope;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAnimatorControl;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.GLBuffers;

import gama.core.outputs.display.LayerManager;
import gama.core.outputs.layers.OverlayLayer;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.ui.base.resources.IGamaIcons;
import gama.ui.base.utils.GraphicsHelper;
import gama.ui.base.utils.WorkbenchHelper;
import gama.ui.displays.opengl.renderer.IOpenGLRenderer;
import gama.ui.displays.opengl.renderer.JOGLRenderer;
import gama.ui.experiment.menus.AgentsMenu;
import gama.ui.experiment.views.displays.DisplaySurfaceMenu;
import msi.gama.common.geometry.Envelope3D;
import msi.gama.common.interfaces.IAgent;
import msi.gama.common.interfaces.IKeyword;
import msi.gama.common.interfaces.gui.IGui;
import msi.gama.common.interfaces.outputs.IDisplayData;
import msi.gama.common.interfaces.outputs.IDisplayDataListener;
import msi.gama.common.interfaces.outputs.IDisplayOutput;
import msi.gama.common.interfaces.outputs.IDisplaySurface;
import msi.gama.common.interfaces.outputs.IEventLayerListener;
import msi.gama.common.interfaces.outputs.ILayer;
import msi.gama.common.interfaces.outputs.ILayerManager;
import msi.gama.common.preferences.GamaPreferences;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.metamodel.shape.IShape;
import msi.gama.metamodel.topology.filter.Different;
import msi.gama.runtime.GAMA;
import msi.gama.runtime.scope.IScope;
import msi.gaml.expressions.IExpression;
import msi.gaml.operators.Cast;
import msi.gaml.statements.draw.DrawingAttributes;

/**
 * Class OpenGLSWTDisplaySurface.
 *
 * @author drogoul
 * @since 25 mars 2015
 *
 */
// Now deprecated @display ("opengl")
@doc ("Displays that uses the OpenGL technology to display their layers in 3D")
public class SWTOpenGLDisplaySurface implements IDisplaySurface.OpenGL {

	GLAnimatorControl animator;
	IOpenGLRenderer renderer;
	protected boolean zoomFit = true;
	Set<IEventLayerListener> listeners = new HashSet<>();
	final IDisplayOutput.Layered output;
	final LayerManager layerManager;
	protected DisplaySurfaceMenu menuManager;
	protected IExpression temp_focus;
	IScope scope;
	volatile boolean disposed;
	private volatile boolean alreadyUpdating;

	public SWTOpenGLDisplaySurface(final IDisplayOutput.Layered objects) {
		output = objects;
		output.getData().addListener(this);
		output.setSurface(this);
		setDisplayScope(output.getScope().copy("in opengl display"));
		renderer = createRenderer();
		renderer.setDisplaySurface(this);
		layerManager = new LayerManager(this, output);
		temp_focus = output.getFacet(IKeyword.FOCUS);
	}

	protected IOpenGLRenderer createRenderer() {
		final IOpenGLRenderer r = new JOGLRenderer();
		return r;
	}

	public void setAnimatorFrom(final GLAutoDrawable drawable) {
		renderer.setCanvas(drawable);
		animator = drawable.getAnimator();
		animator.start();
	}

	public GLCapabilities getCapabilities() {
		final GLProfile profile = GLProfile.getDefault();
		final GLCapabilities cap = new GLCapabilities(profile);
		cap.setDepthBits(24);
		cap.setDoubleBuffered(true);
		cap.setHardwareAccelerated(true);
		cap.setSampleBuffers(true);
		cap.setAlphaBits(8);
		cap.setNumSamples(8);
		return cap;
	}

	@Override
	public void setMenuManager(final Object menuManager) {
		this.menuManager = (DisplaySurfaceMenu) menuManager;
	}

	/**
	 * Method getImage()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#getImage()
	 */
	@Override
	public BufferedImage getImage(final int w, final int h) {
		while (!isRendered()) {
			try {
				Thread.sleep(20);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		final GLAutoDrawable glad = renderer.getCanvas();
		if (glad == null || glad.getGL() == null || glad.getGL().getContext() == null)
			return null;
		final boolean current = glad.getGL().getContext().isCurrent();
		if (!current) {
			glad.getGL().getContext().makeCurrent();
		}
		final BufferedImage image = getImage(glad.getGL().getGL2(), w, h);
		// final AWTGLReadBufferUtil glReadBufferUtil = new AWTGLReadBufferUtil(glad.getGLProfile(), false);
		// final BufferedImage image = glReadBufferUtil.readPixelsToBufferedImage(glad.getGL(), true);
		if (!current) {
			glad.getGL().getContext().release();
		}
		return image;
		// return ImageUtils.resize(image, w, h);
	}

	ByteBuffer buffer;

	protected ByteBuffer getBuffer(final int w, final int h) {

		if (buffer == null || buffer.capacity() != w * h * 4) {
			buffer = GLBuffers.newDirectByteBuffer(w * h * 4);
		} else {
			buffer.rewind();
		}

		return buffer;
	}

	protected BufferedImage getImage(final GL2 gl3, final int ww, final int hh) {

		// See #2628 and https://github.com/sgothel/jogl/commit/ca7f0fb61b0a608b6e684a5bbde71f6ecb6e3fe0
		final int width = GraphicsHelper.scaleDownIfMac(ww);
		final int height = GraphicsHelper.scaleDownIfMac(hh);
		final BufferedImage screenshot = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics graphics = screenshot.getGraphics();

		final ByteBuffer buffer = getBuffer(width, height);
		// be sure you are reading from the right fbo (here is supposed to be the default one)
		// bind the right buffer to read from
		gl3.glReadBuffer(GL.GL_BACK);
		// if the width is not multiple of 4, set unpackPixel = 1
		gl3.glReadPixels(0, 0, width, height, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, buffer);

		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				// The color are the three consecutive bytes, it's like referencing
				// to the next consecutive array elements, so we got red, green, blue..
				// red, green, blue, and so on..+ ", "
				graphics.setColor(new Color(buffer.get() & 0xff, buffer.get() & 0xff, buffer.get() & 0xff));
				buffer.get(); // consume alpha
				graphics.drawRect(w, height - h - 1, 1, 1); // height - h is for flipping the image
			}
		}
		return screenshot;
	}

	/**
	 * Method updateDisplay()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#updateDisplay(boolean)
	 */
	@Override
	public void updateDisplay(final boolean force) {

		if (alreadyUpdating)
			return;
		try {
			alreadyUpdating = true;

			final boolean oldState = animator.isPaused();
			if (force) {
				animator.resume();
			}
			layerManager.drawLayersOn(renderer);

			// EXPERIMENTAL

			if (temp_focus != null) {
				final IShape geometry = Cast.asGeometry(getScope(), temp_focus.value(getScope()));
				if (geometry != null) {
					temp_focus = null;
					focusOn(geometry);
				}
			}
			if (force) {
				if (oldState) {
					animator.pause();
				}
			}
		} finally {
			alreadyUpdating = false;
		}
	}

	@Override
	public double getDisplayWidth() {
		return renderer.getWidth();
	}

	@Override
	public double getDisplayHeight() {
		return renderer.getHeight();
	}

	/**
	 * Method zoomIn()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#zoomIn()
	 */
	@Override
	public void zoomIn() {
		if (renderer.getData().cameraInteractionDisabled())
			return;
		renderer.getCameraHelper().zoom(true);
	}

	/**
	 * Method zoomOut()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#zoomOut()
	 */
	@Override
	public void zoomOut() {
		if (renderer.getData().cameraInteractionDisabled())
			return;
		renderer.getCameraHelper().zoom(false);
	}

	/**
	 * Method zoomFit()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#zoomFit()
	 */
	@Override
	public void zoomFit() {
		// if (renderer.getData().cameraInteractionDisabled())
		// return;
		renderer.getCameraHelper().initialize();
		output.getData().resetZRotation();
		output.getData().setZoomLevel(IDisplayData.INITIAL_ZOOM, true, true);
		zoomFit = true;

	}

	/**
	 * Method getManager()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#getManager()
	 */
	@Override
	public ILayerManager getManager() {
		return layerManager;
	}

	/**
	 * Method focusOn()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#focusOn(msi.gama.metamodel.shape.IShape)
	 */
	@Override
	public void focusOn(final IShape geometry) {
		// FIXME: Need to compute the depth of the shape to adjust ZPos value.
		// FIXME: Problem when the geometry is a point how to determine the
		// maxExtent of the shape?
		// FIXME: Problem when an agent is placed on a layer with a z_value how
		// to get this z_layer value to offset it?
		renderer.getCameraHelper().zoomFocus(geometry.getEnvelope().yNegated());
	}

	/**
	 * Method waitForUpdateAndRun()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#waitForUpdateAndRun(java.lang.Runnable)
	 */
	@Override
	public void runAndUpdate(final Runnable r) {
		r.run();
		if (getScope().isPaused()) {
			updateDisplay(true);
		}
		if (animator.isPaused()) {
			animator.resume();
			animator.pause();
		}
	}

	/**
	 * Method getWidth()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#getWidth()
	 */
	@Override
	public int getWidth() {
		return renderer.getCanvas().getSurfaceWidth();
		// return size.x;
	}

	/**
	 * Method getHeight()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#getHeight()
	 */
	@Override
	public int getHeight() {
		return renderer.getCanvas().getSurfaceHeight();
		// return size.y;
	}

	/**
	 * Method outputReloaded()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#outputReloaded()
	 */
	@Override
	public void outputReloaded() {
		setDisplayScope(output.getScope().copy("in opengl display"));
		if (!GamaPreferences.Runtime.ERRORS_IN_DISPLAYS.getValue()) {
			getScope().disableErrorReporting();
		}
		renderer.initScene();
		layerManager.outputChanged();

		// resizeImage(getWidth(), getHeight(), true);
		if (zoomFit) {
			zoomFit();
		}
	}

	/**
	 * Method addMouseListener()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#addMouseListener(java.awt.event.MouseListener)
	 */
	@Override
	public void addListener(final IEventLayerListener listener) {
		listeners.add(listener);
	}

	/**
	 * Method removeMouseListener()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#removeMouseListener(java.awt.event.MouseListener)
	 */
	@Override
	public void removeListener(final IEventLayerListener listener) {
		listeners.remove(listener);

	}

	@Override
	public Collection<IEventLayerListener> getLayerListeners() {
		return listeners;
	}

	/**
	 * Method getEnvWidth()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#getEnvWidth()
	 */
	@Override
	public double getEnvWidth() {
		return output.getData().getEnvWidth();
	}

	/**
	 * Method getEnvHeight()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#getEnvHeight()
	 */
	@Override
	public double getEnvHeight() {
		return output.getData().getEnvHeight();
	}

	/**
	 * Method getModelCoordinates()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#getModelCoordinates()
	 */
	@Override
	public GamaPoint getModelCoordinates() {
		final GamaPoint mp = renderer.getCameraHelper().getMousePosition();
		if (mp == null)
			return null;
		final GamaPoint p = renderer.getRealWorldPointFromWindowPoint(mp);
		if (p == null)
			return null;
		return new GamaPoint(p.x, -p.y);
	}

	@Override
	public void getModelCoordinatesInfo(final StringBuilder sb) {
		boolean canObtainInfo = getManager().isProvidingCoordinates();
		if (!canObtainInfo) {
			sb.append("No world coordinates");
			return;
		}
		canObtainInfo = getManager().isProvidingWorldCoordinates();
		if (!canObtainInfo) {
			sb.append("No world coordinates");
			return;
		}
		// By default, returns the coordinates in the world.
		final GamaPoint point = getModelCoordinates();
		final String x = point == null ? "N/A" : String.format("%8.6f", point.getX());
		final String y = point == null ? "N/A" : String.format("%8.6f", point.getY());
		sb.append(String.format("X%15s | Y%15s", x, y));
	}

	@Override
	public Envelope getVisibleRegionForLayer(final ILayer currentLayer) {
		if (currentLayer instanceof OverlayLayer)
			return getScope().getSimulation().getEnvelope();
		Envelope e = currentLayer.getData().getVisibleRegion();
		if (e == null) {
			e = new Envelope();
			final Point origin = new Point(0, 0);
			int xc = -origin.x;
			int yc = -origin.y;
			e.expandToInclude(currentLayer.getModelCoordinatesFrom(xc, yc, this));
			xc = xc + getWidth();
			yc = yc + getHeight();
			e.expandToInclude(currentLayer.getModelCoordinatesFrom(xc, yc, this));
			currentLayer.getData().setVisibleRegion(e);
		}
		return e;
	}

	/**
	 * Method getModelCoordinatesFrom()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#getModelCoordinatesFrom(int, int, java.awt.Point, java.awt.Point)
	 */
	@Override
	public GamaPoint getModelCoordinatesFrom(final int xOnScreen, final int yOnScreen, final Point sizeInPixels,
			final Point positionInPixels) {
		final GamaPoint mp = new GamaPoint(xOnScreen, yOnScreen);
		final GamaPoint p = renderer.getRealWorldPointFromWindowPoint(mp);
		return new GamaPoint(p.x, -p.y);
	}

	/**
	 * Method selectAgent()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#selectAgent(int, int)
	 */
	@Override
	public Collection<IAgent> selectAgent(final int x, final int y) {
		final GamaPoint pp = getModelCoordinatesFrom(x, y, null, null);
		return scope.getRoot().getTopology().getNeighborsOf(scope, new GamaPoint(pp.getX(), pp.getY()),
				renderer.getMaxEnvDim() / 100, Different.with());
	}

	/**
	 * Method followAgent()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#followAgent(msi.gama.common.interfaces.IAgent)
	 */
	@Override
	public void followAgent(final IAgent a) {
		new Thread(
				() -> WorkbenchHelper.asyncRun(() -> renderer.getCameraHelper().zoomFocus(a.getEnvelope().yNegated())))
						.start();

	}

	/**
	 * Method getZoomLevel()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#getZoomLevel()
	 */
	@Override
	public double getZoomLevel() {
		if (output.getData().getZoomLevel() == null) {
			output.getData().setZoomLevel(computeInitialZoomLevel(), true, false);
		}
		return output.getData().getZoomLevel();
	}

	protected Double computeInitialZoomLevel() {
		return renderer.getCameraHelper().zoomLevel();
	}

	/**
	 * Method getDisplayScope()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#getDisplayScope()
	 */
	@Override
	public IScope getScope() {
		return scope;
	}

	/**
	 * Method getOutput()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#getOutput()
	 */
	@Override
	public IDisplayOutput.Layered getOutput() {
		return output;
	}

	/**
	 * Method setPaused()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface.OpenGL#setPaused(boolean)
	 */
	@Override
	public void setPaused(final boolean paused) {
		if (paused) {
			animator.pause();
		} else {
			animator.resume();
		}
	}

	final Runnable cleanup = () -> WorkbenchHelper.asyncRun(() -> renderer.getPickingHelper().setPicking(false));

	/**
	 * Method selectAgents()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface.OpenGL#selectAgents(msi.gama.common.interfaces.IAgent)
	 */
	@Override
	public void selectAgent(final DrawingAttributes attributes) {
		IAgent ag = null;
		boolean withHighlight = true;
		if (attributes != null) {
			if (attributes.getSpeciesName() != null) {
				// The picked image is a grid or an image of a grid
				withHighlight = false;
				final GamaPoint pickedPoint = renderer
						.getRealWorldPointFromWindowPoint(renderer.getCameraHelper().getLastMousePressedPosition());
				ag = scope.getRoot().getPopulationFor(attributes.getSpeciesName()).getAgent(scope,
						new GamaPoint(pickedPoint.x, -pickedPoint.y));
			} else {
				ag = attributes.getAgentIdentifier();
			}
		}
		if (withHighlight) {
			menuManager.buildMenu((int) renderer.getCameraHelper().getMousePosition().x,
					(int) renderer.getCameraHelper().getMousePosition().y, ag, cleanup,
					AgentsMenu.getHighlightActionFor(ag));
		} else {
			menuManager.buildMenu((int) renderer.getCameraHelper().getMousePosition().x,
					(int) renderer.getCameraHelper().getMousePosition().y, ag, cleanup);
		}
	}

	/**
	 * Method selectSeveralAgents()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface.OpenGL#selectSeveralAgents(java.util.Collection, int)
	 */
	@Override
	public void selectionIn(final Envelope3D env) {

		final Envelope3D envInWorld = Envelope3D.withYNegated(env);
		final Collection<IAgent> agents = scope.getTopology().getSpatialIndex().allInEnvelope(scope,
				envInWorld.centre(), envInWorld, new Different(), false);
		final Map<String, Runnable> actions = new LinkedHashMap<>();
		final Map<String, String> images = new HashMap<>();
		images.put(renderer.getOpenGLHelper().isStickyROI() ? "Hide region" : "Keep region visible",
				IGamaIcons.MENU_FOLLOW);
		images.put("Focus on region", IGamaIcons.DISPLAY_TOOLBAR_ZOOMFIT);
		actions.put(renderer.getOpenGLHelper().isStickyROI() ? "Hide region" : "Keep region visible",
				() -> renderer.getOpenGLHelper().toogleROI());
		actions.put("Focus on region", () -> renderer.getCameraHelper().zoomFocus(env));
		menuManager.openROIMenu(renderer.getCameraHelper().getMousePosition(), agents, actions, images, () -> {
			animator.pause();
		}, () -> {
			animator.resume();
			renderer.getOpenGLHelper().cancelROI();
		});

	}

	protected void setDisplayScope(final IScope scope) {
		if (this.scope != null) {
			GAMA.releaseScope(this.scope);
		}
		this.scope = scope;
	}

	@Override
	public void dispose() {
		if (disposed)
			return;
		disposed = true;
		if (layerManager != null) {
			layerManager.dispose();
		}
		if (animator != null && animator.isStarted()) {
			animator.stop();
		}
		this.menuManager = null;
		this.listeners.clear();
		this.renderer = null;
		GAMA.releaseScope(getScope());
		setDisplayScope(null);
	}

	@Override
	public IDisplayData getData() {
		return output.getData();
	}

	/**
	 * Method changed()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplayDataListener#changed(int, boolean)
	 */
	@Override
	public void changed(final IDisplayDataListener.Changes property, final Object value) {
		if (renderer == null)
			return;
		switch (property) {

			case CHANGE_CAMERA:
				renderer.getCameraHelper().setupCamera();
				break;
			case SPLIT_LAYER:
				final double gap = (Double) value;
				// if (DEBUG.IS_ON()) {
				// DEBUG.OUT("Value received by SWTOpenGLDisplaySurface= " + value);
				// }
				double currentElevation = 0;

				for (final ILayer layer : this.getManager().getItems()) {
					layer.getData().addElevation(currentElevation);
					currentElevation += gap;
				}
				renderer.getSceneHelper().layerOffsetChanged();

				break;
			case CAMERA_POS:
				renderer.getCameraHelper().updatePosition();
				break;
			case CAMERA_UP:
				renderer.getCameraHelper().updateOrientation();
				break;
			case CAMERA_TARGET:
				renderer.getCameraHelper().updateTarget();
				break;
			case CAMERA_PRESET:
				renderer.getCameraHelper().applyPreset((String) value);
				break;
			case ZOOM:
				renderer.getCameraHelper().zoom((Double) value);
				break;
			default:
				break;

		}

	}

	/**
	 * Method setSize()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#setSize(int, int)
	 */
	@Override
	public void setSize(final int x, final int y) {}

	@Override
	public void layersChanged() {
		renderer.getSceneHelper().layersChanged();

	}

	public void invalidateVisibleRegions() {
		for (final ILayer layer : layerManager.getItems()) {
			layer.getData().setVisibleRegion(null);
		}
	}

	/**
	 * Method getFPS()
	 *
	 * @see msi.gama.common.interfaces.outputs.IDisplaySurface#getFPS()
	 */
	@Override
	public int getFPS() {
		return (int) this.animator.getTotalFPS();
	}

	@Override
	public boolean isRealized() {
		if (renderer == null)
			return false;
		final GLAutoDrawable d = renderer.getCanvas();
		if (d == null)
			return false;
		return d.isRealized();
	}

	@Override
	public boolean isRendered() {
		if (renderer == null || renderer.getSceneHelper().getSceneToRender() == null)
			return false;
		return renderer.getSceneHelper().getSceneToRender().rendered();
	}

	@Override
	public boolean isDisposed() {
		return disposed;
	}

	@Override
	public Envelope3D getROIDimensions() {
		return renderer.getOpenGLHelper().getROIEnvelope();
	}

	@Override
	public void dispatchKeyEvent(final char e) {
		for (final IEventLayerListener gl : listeners) {
			gl.keyPressed(String.valueOf(e));
		}
	}

	@Override
	public void dispatchMouseEvent(final int swtMouseEvent) {
		final GamaPoint p = renderer.getCameraHelper().getMousePosition();
		final int x = (int) p.x;
		final int y = (int) p.y;
		for (final IEventLayerListener gl : listeners) {
			switch (swtMouseEvent) {
				case IGui.MouseDown:
					gl.mouseDown(x, y, 1);
					break;
				case IGui.MouseUp:
					gl.mouseUp(x, y, 1);
					break;
				case IGui.MouseMove:
					gl.mouseMove(x, y);
					break;
				case IGui.MouseEnter:
					gl.mouseEnter(x, y);
					break;
				case IGui.MouseExit:
					gl.mouseExit(x, y);
					break;
			}
		}
	}

	@Override
	public void setMousePosition(final int x, final int y) {
		// Nothing to do (taken in charge by the camera)
	}

	@Override
	public void selectAgentsAroundMouse() {
		// Nothing to do (taken in charge by the camera)
	}

	@Override
	public void draggedTo(final int x, final int y) {
		// Nothing to do (taken in charge by the camera
	}

}
