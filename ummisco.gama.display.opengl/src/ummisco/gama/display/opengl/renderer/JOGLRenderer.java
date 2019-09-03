/*******************************************************************************************************
 *
 * ummisco.gama.display.opengl.renderer.JOGLRenderer.java, in plugin ummisco.gama.display.opengl, is part of the source
 * code of the GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package ummisco.gama.display.opengl.renderer;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.ShapeType;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.swt.GLCanvas;

import msi.gama.common.interfaces.outputs.IChartImageProvider;
import msi.gama.common.interfaces.outputs.IDisplaySurface;
import msi.gama.common.interfaces.outputs.ILayer;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.util.GamaColor;
import msi.gama.util.file.IGamaFile;
import msi.gaml.statements.draw.DrawingAttributes;
import msi.gaml.statements.draw.FieldDrawingAttributes;
import msi.gaml.statements.draw.FileDrawingAttributes;
import msi.gaml.statements.draw.ShapeDrawingAttributes;
import msi.gaml.statements.draw.TextDrawingAttributes;
import msi.gaml.types.GamaGeometryType;
import ummisco.gama.display.opengl.OpenGL;
import ummisco.gama.display.opengl.renderer.helpers.CameraHelper;
import ummisco.gama.display.opengl.renderer.helpers.KeystoneHelper;
import ummisco.gama.display.opengl.renderer.helpers.KeystoneHelper.Pass;
import ummisco.gama.display.opengl.renderer.helpers.LightHelper;
import ummisco.gama.display.opengl.renderer.helpers.PickingHelper;
import ummisco.gama.display.opengl.renderer.helpers.SceneHelper;
import ummisco.gama.display.opengl.scene.ModelScene;
import ummisco.gama.display.opengl.view.SWTOpenGLDisplaySurface;
import ummisco.gama.outputs.display.AbstractDisplayGraphics;
import ummisco.gama.ui.utils.GraphicsHelper;
import ummisco.gama.ui.utils.WorkbenchHelper;

/**
 * This class plays the role of Renderer and IGraphics. Class JOGLRenderer.
 *
 * @author drogoul
 * @since 27 avr. 2015
 *
 */
@SuppressWarnings ({ "rawtypes", "unchecked" })
public class JOGLRenderer extends AbstractDisplayGraphics implements IOpenGLRenderer {

	// Helpers
	private final KeystoneHelper keystoneHelper = createKeystoneHelper();
	private final PickingHelper pickingHelper = new PickingHelper(this);
	private final LightHelper lightHelper = new LightHelper(this);
	private final CameraHelper cameraHelper = new CameraHelper(this);
	private final SceneHelper sceneHelper = createSceneHelper();

	// OpenGL back-end
	protected OpenGL openGL;

	// State
	protected volatile boolean inited, visible, disposed;

	// Canvas
	protected GLAutoDrawable canvas;

	@Override
	public void setDisplaySurface(final IDisplaySurface d) {
		super.setDisplaySurface(d);
		d.getScope().setGraphics(this);
		openGL = new OpenGL(this);
	}

	protected SceneHelper createSceneHelper() {
		return new SceneHelper(this);
	}

	protected KeystoneHelper createKeystoneHelper() {
		return new KeystoneHelper(this);
	}

	@Override
	public void setCanvas(final GLAutoDrawable canvas) {
		this.canvas = canvas;
		canvas.addGLEventListener(this);
		cameraHelper.hook();
	}

	@Override
	public void init(final GLAutoDrawable drawable) {
		// WorkbenchHelper.asyncRun(() -> getCanvas().setVisible(visible));
		openGL.setGL2(drawable.getGL().getGL2());
		cameraHelper.initialize();
		openGL.initializeGLStates(data.getBackgroundColor());
		lightHelper.initialize();
		// We mark the renderer as inited
		inited = true;
	}

	@Override
	public void fillBackground(final Color bgColor, final double opacity) {
		openGL.setCurrentObjectAlpha(opacity);
	}

	@Override
	public SWTOpenGLDisplaySurface getSurface() {
		return (SWTOpenGLDisplaySurface) surface;
	}

	@Override
	public final GLCanvas getCanvas() {
		return (GLCanvas) canvas;
	}

	@Override
	public void initScene() {
		final ModelScene scene = sceneHelper.getSceneToRender();
		if (scene != null) {
			scene.reload();
		}
	}

	@Override
	public boolean beginDrawingLayers() {
		while (!inited) {
			try {
				Thread.sleep(10);
			} catch (final InterruptedException e) {
				return false;
			}
		}
		return sceneHelper.beginUpdatingScene();

	}

	@Override
	public boolean isNotReadyToUpdate() {
		if (data.isSynchronized())
			return false;
		return sceneHelper.isNotReadyToUpdate();
	}

	@Override
	public void dispose() {
		super.dispose();
		dispose(canvas);
	}

	@Override
	public void beginDrawingLayer(final ILayer layer) {
		super.beginDrawingLayer(layer);
		sceneHelper.beginDrawingLayer(layer, currentLayerAlpha);
	}

	/**
	 * Method endDrawingLayers()
	 *
	 * @see msi.gama.common.interfaces.outputs.IGraphics#endDrawingLayers()
	 */
	@Override
	public void endDrawingLayers() {
		sceneHelper.endUpdatingScene();
		getSurface().invalidateVisibleRegions();
	}

	@Override
	public void display(final GLAutoDrawable drawable) {
		if (!sceneHelper.isReady())
			return;

		try (Pass c = keystoneHelper.render()) {
			openGL.beginScene();
			cameraHelper.update();
			lightHelper.draw();
			sceneHelper.draw();
			openGL.endScene();
		}

		if (!visible) {
			// We make the canvas visible only after a first display has occured
			WorkbenchHelper.asyncRun(() -> getCanvas().setVisible(true));
			visible = true;
		}

	}

	@SuppressWarnings ("restriction")
	@Override
	public void reshape(final GLAutoDrawable drawable, final int arg1, final int arg2, final int w, final int h) {
		int width = w, height = h;
		// See #2628 and https://github.com/sgothel/jogl/commit/ca7f0fb61b0a608b6e684a5bbde71f6ecb6e3fe0
		width = GraphicsHelper.scaleDownIfMac(width);
		height = GraphicsHelper.scaleDownIfMac(height);
		if (width <= 0 || height <= 0)
			return;
		if (openGL.getViewWidth() == width && openGL.getViewHeight() == height)
			return;
		// DEBUG.OUT("Reshaped to " + width + " x " + height);
		// DEBUG.OUT("Zoom size: " + DPIUtil.getDeviceZoom());
		// DEBUG.OUT("AutoScale down = ", false);
		// DEBUG.OUT(DPIUtil.autoScaleDown(new int[] { width, height }));
		// DEBUG.OUT("Size of window:" + ((GLCanvas) drawable).getClientArea());
		final GL2 gl = drawable.getContext().getGL().getGL2();
		keystoneHelper.reshape(width, height);
		openGL.reshape(gl, width, height);
		sceneHelper.reshape(width, height);
		surface.updateDisplay(true);
	}

	@Override
	public void dispose(final GLAutoDrawable drawable) {
		sceneHelper.garbageCollect(openGL);
		sceneHelper.dispose();
		openGL.dispose();
		keystoneHelper.dispose();
		cameraHelper.dispose();
		drawable.removeGLEventListener(this);
		disposed = true;
	}

	/**
	 *
	 * IGraphics DRAWING METHODS
	 *
	 */

	@Override
	public boolean cannotDraw() {
		final ModelScene scene = sceneHelper.getSceneToUpdate();
		return scene != null && scene.cannotAdd();
	}

	@Override
	public Rectangle2D drawFile(final IGamaFile file, final DrawingAttributes attributes) {
		if (file == null)
			return null;
		final ModelScene scene = sceneHelper.getSceneToUpdate();
		if (scene == null)
			return null;
		tryToHighlight(attributes);
		if (file instanceof IGamaFile.Geom) {
			scene.addGeometryFile((IGamaFile.Geom) file, attributes);
			openGL.cacheGeometry((IGamaFile.Geom) file);
		} else if (file instanceof IGamaFile.Image) {
			if (attributes.useCache()) {
				openGL.cacheTexture(file.getFile(getSurface().getScope()));
			}
			scene.addImage(file, attributes);
		}

		return rect;
	}

	@Override
	public Rectangle2D drawField(final double[] fieldValues, final FieldDrawingAttributes attributes) {
		final ModelScene scene = sceneHelper.getSceneToUpdate();
		if (scene == null)
			return null;
		final List<?> textures = attributes.getTextures();
		if (textures != null && !textures.isEmpty()) {
			for (final Object img : textures) {
				if (img instanceof IGamaFile.Image) {
					openGL.cacheTexture(((IGamaFile.Image) img).getFile(getSurface().getScope()));
				}
			}
		}
		scene.addField(fieldValues, attributes);
		/*
		 * This line has been removed to fix the issue 1174 if ( gridColor != null ) { drawGridLine(img, gridColor); }
		 */
		return rect;
	}

	/**
	 * Method drawShape. Add a given JTS Geometry in the list of all the existing geometry that will be displayed by
	 * openGl.
	 */
	@Override
	public Rectangle2D drawShape(final Geometry shape, final DrawingAttributes attributes) {
		if (shape == null)
			return null;
		final ModelScene scene = sceneHelper.getSceneToUpdate();
		if (scene == null)
			return null;
		tryToHighlight(attributes);
		scene.addGeometry(shape, attributes);
		return rect;
	}

	@Override
	public Rectangle2D drawImage(final BufferedImage img, final DrawingAttributes attributes) {
		if (img == null)
			return null;
		final ModelScene scene = sceneHelper.getSceneToUpdate();
		if (scene == null)
			return null;
		scene.addImage(img, attributes);
		tryToHighlight(attributes);
		if (attributes.getBorder() != null) {
			drawGridLine(new GamaPoint(img.getWidth(), img.getHeight()), attributes.getBorder());
		}
		return rect;
	}

	@Override
	public Rectangle2D drawChart(final IChartImageProvider chart) {
		final ModelScene scene = sceneHelper.getSceneToUpdate();
		if (scene == null)
			return null;
		int x = getLayerWidth();
		int y = getLayerHeight();
		x = (int) (Math.min(x, y) * 0.80);
		y = x;
		// TODO See if it not possible to generate directly a texture renderer instead
		final BufferedImage im = chart.getImage(x, y, getSurface().getData().isAntialias());
		scene.addImage(im, new FileDrawingAttributes(null, true));
		return rect;
	}

	protected void tryToHighlight(final DrawingAttributes attributes) {
		if (highlight) {
			attributes.setHighlighted(data.getHighlightColor());
		}
	}

	public void drawGridLine(final GamaPoint dimensions, final Color lineColor) {
		final ModelScene scene = sceneHelper.getSceneToUpdate();
		if (scene == null)
			return;
		double stepX, stepY;
		final double cellWidth = getEnvHeight() / dimensions.x;
		final double cellHeight = getEnvWidth() / dimensions.y;
		final GamaColor color = GamaColor.getInt(lineColor.getRGB());
		final DrawingAttributes attributes = new ShapeDrawingAttributes(null, color, color, ShapeType.GRIDLINE);
		attributes.setEmpty(true);
		for (double i = 0; i < dimensions.x; i++) {
			for (double j = 0; j < dimensions.y; j++) {
				stepX = i + 0.5;
				stepY = j + 0.5;
				final Geometry g = GamaGeometryType
						.buildRectangle(cellWidth, cellHeight, new GamaPoint(stepX * cellWidth, stepY * cellHeight))
						.getInnerGeometry();
				scene.addGeometry(g, attributes);
			}
		}
	}

	@Override
	public Rectangle2D drawString(final String string, final TextDrawingAttributes attributes) {
		if (string == null || string.isEmpty())
			return null;
		final ModelScene scene = sceneHelper.getSceneToUpdate();
		if (scene == null)
			return null;
		// Multiline: Issue #780
		if (string.contains("\n")) {
			int i = 0;
			final double shift = attributes.font.getSize() / this.getyRatioBetweenPixelsAndModelUnits();
			for (final String s : string.split("\n")) {
				// DEBUG.OUT("Attributes Font Size: " + attributes.font.getSize());
				// DEBUG.OUT("Get Y Ratio: " + getyRatioBetweenPixelsAndModelUnits());
				drawString(s, attributes.copyTranslatedBy(new GamaPoint(0, shift * i++)));
			}
			return null;
		}
		openGL.cacheFont(attributes.font);
		attributes.getLocation().setY(-attributes.getLocation().getY());
		scene.addString(string, attributes);
		return null;
	}

	/**
	 *
	 * DIMENSIONS, RATIOS AND LOCATIONS METHODS
	 *
	 */

	@Override
	public final GamaPoint getCameraPos() {
		return cameraHelper.getPosition();
	}

	@Override
	public final GamaPoint getCameraTarget() {
		return cameraHelper.getTarget();
	}

	@Override
	public final GamaPoint getCameraOrientation() {
		return cameraHelper.getOrientation();
	}

	@Override
	public double getxRatioBetweenPixelsAndModelUnits() {
		return openGL.getRatios().x;
	}

	@Override
	public double getyRatioBetweenPixelsAndModelUnits() {
		return openGL.getRatios().y;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ummisco.gama.display.opengl.renderer.IOpenGLRenderer#getWidth()
	 */
	@SuppressWarnings ("restriction")
	@Override
	public final double getWidth() {
		// DEBUG.OUT(
		// "Result of getWidth: "
		// + PlatformHelper.autoScaleDown(canvas.getSurfaceWidth()) * (float) surface.getZoomLevel(),
		// false);
		// DEBUG.OUT(" -- Canvas surface width " + canvas.getSurfaceWidth(), false);
		// DEBUG.OUT("Canvas size " + canvas.getSize().x, false);
		// DEBUG.OUT(" -- Width of world in pixels computed by OpenGL " + openGL.getPixelWidthAndHeightOfWorld()[0]);
		// return canvas.getSize().x * surface.getZoomLevel();
		return openGL.getPixelWidthAndHeightOfWorld()[0] * (float) surface.getZoomLevel();
		// return PlatformHelper.autoScaleDown(canvas.getSurfaceWidth()) *
		// PlatformHelper.autoScaleDown(canvas.getSurfaceWidth()) * (float) surface.getZoomLevel();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ummisco.gama.display.opengl.renderer.IOpenGLRenderer#getHeight()
	 */
	@Override
	public final double getHeight() {
		return openGL.getPixelWidthAndHeightOfWorld()[1] * (float) surface.getZoomLevel();
		// return canvas.getSurfaceHeight() * surface.getZoomLevel();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ummisco.gama.display.opengl.renderer.IOpenGLRenderer#getRealWorldPointFromWindowPoint (java.awt.Point)
	 */
	@Override
	public GamaPoint getRealWorldPointFromWindowPoint(final GamaPoint mouse) {
		return openGL.getWorldPositionFrom(new GamaPoint(mouse.x, mouse.y));
	}

	@Override
	public final int getDisplayWidth() {
		return (int) Math.round(getWidth());
	}

	@Override
	public final int getDisplayHeight() {
		return (int) Math.round(getHeight());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ummisco.gama.display.opengl.renderer.IOpenGLRenderer#getCameraHelper()
	 */

	@Override
	public CameraHelper getCameraHelper() {
		return cameraHelper;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ummisco.gama.display.opengl.renderer.IOpenGLRenderer#getKeystoneHelper()
	 */
	@Override
	public KeystoneHelper getKeystoneHelper() {
		return keystoneHelper;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ummisco.gama.display.opengl.renderer.IOpenGLRenderer#getPickingHelper()
	 */
	@Override
	public PickingHelper getPickingHelper() {
		return pickingHelper;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ummisco.gama.display.opengl.renderer.IOpenGLRenderer#getOpenGLHelper()
	 */
	@Override
	public OpenGL getOpenGLHelper() {
		return openGL;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ummisco.gama.display.opengl.renderer.IOpenGLRenderer#getLightHelper()
	 */
	@Override
	public LightHelper getLightHelper() {
		return lightHelper;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ummisco.gama.display.opengl.renderer.IOpenGLRenderer#getSceneHelper()
	 */
	@Override
	public SceneHelper getSceneHelper() {
		return sceneHelper;
	}

	@Override
	public boolean isDisposed() {
		return disposed;
	}

}
