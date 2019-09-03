/*******************************************************************************************************
 *
 * ummisco.gama.outputs.LayeredDisplayData.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling
 * and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package ummisco.gama.outputs;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import msi.gama.common.geometry.Envelope3D;
import msi.gama.common.geometry.ICoordinates;
import msi.gama.common.interfaces.IKeyword;
import msi.gama.common.interfaces.IPreferenceChangeListener.IPreferenceAfterChangeListener;
import msi.gama.common.interfaces.outputs.IDisplayData;
import msi.gama.common.interfaces.outputs.IDisplayDataListener;
import msi.gama.common.interfaces.outputs.ILightData;
import msi.gama.common.preferences.GamaPreferences;
import msi.gama.kernel.experiment.ExperimentAgent;
import msi.gama.kernel.simulation.SimulationAgent;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.runtime.scope.IScope;
import msi.gama.util.GamaColor;
import msi.gama.util.list.GamaListFactory;
import msi.gaml.descriptions.IDescription;
import msi.gaml.descriptions.ModelDescription;
import msi.gaml.expressions.IExpression;
import msi.gaml.operators.Cast;
import msi.gaml.statements.Facets;
import msi.gaml.types.Types;
import ummisco.gama.dev.utils.DEBUG;

/**
 */
public class LayeredDisplayData implements IDisplayData {

	static {
		DEBUG.OFF();
	}

	public final Set<IDisplayDataListener> listeners = new CopyOnWriteArraySet<>();

	@Override
	public void addListener(final IDisplayDataListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(final IDisplayDataListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void notifyListeners(final IDisplayDataListener.Changes property, final Object value) {
		for (final IDisplayDataListener listener : listeners) {
			listener.changed(property, value);
		}
	}

	/**
	 * Colors
	 */
	private GamaColor backgroundColor = GamaPreferences.Displays.CORE_BACKGROUND.getValue();
	private GamaColor ambientColor = new GamaColor(64, 64, 64, 255);
	private GamaColor highlightColor = GamaPreferences.Displays.CORE_HIGHLIGHT.getValue();
	private GamaColor toolbarColor = GamaColor.NamedGamaColor.getNamed("white");

	/**
	 * Properties
	 */
	private boolean isAutosaving = false;
	private boolean isToolbarVisible = GamaPreferences.Displays.CORE_DISPLAY_TOOLBAR.getValue();
	private boolean isSynchronized = GamaPreferences.Runtime.CORE_SYNC.getValue();
	private String displayType =
			GamaPreferences.Displays.CORE_DISPLAY.getValue().equalsIgnoreCase(JAVA2D) ? JAVA2D : OPENGL;
	private double envWidth = 0d;
	private double envHeight = 0d;
	private boolean isAntialiasing = GamaPreferences.Displays.CORE_ANTIALIAS.getValue();
	private GamaPoint imageDimension = new GamaPoint(-1, -1);
	private Double zoomLevel = INITIAL_ZOOM;
	private final LightData lights[] = new LightData[8];
	private final ICoordinates keystone = (ICoordinates) KEYSTONE_IDENTITY.clone();
	private double zRotationAngleDelta = 0;
	private double currentRotationAboutZ = 0;
	private boolean isOpenGL;

	/**
	 * OpenGL
	 */

	private boolean isWireframe = false;
	private boolean ortho = false;
	private boolean disableCameraInteraction = false; // "fixed_camera" facet
	private boolean isShowingFPS = false; // GamaPreferences.CORE_SHOW_FPS.getValue();
	private boolean isDrawingEnvironment = GamaPreferences.Displays.CORE_DRAW_ENV.getValue();
	private boolean isLightOn = true; // GamaPreferences.CORE_IS_LIGHT_ON.getValue();
	private GamaPoint cameraPos = null;
	private GamaPoint cameraLookPos = null;
	private GamaPoint cameraUpVector = null;
	private String presetCamera = "";
	private int cameraLens = 45;
	private Double splitDistance;
	private boolean isRotating;
	private boolean isUsingArcBallCamera = true;
	private boolean isSplittingLayers;
	private boolean constantBackground = true;
	private boolean constantAmbientLight = true;
	private boolean constantCamera = true;
	private boolean constantCameraLook = true;
	private double zNear = -1.0;
	private double zFar = -1.0;
	/**
	 * Overlay
	 */

	// private boolean isDisplayingScale = GamaPreferences.Displays.CORE_SCALE.getValue();
	private int fullScreen = -1;

	/**
	 *
	 */

	IPreferenceAfterChangeListener<GamaColor> highlightListener = newValue -> setHighlightColor(newValue);

	public LayeredDisplayData() {
		GamaPreferences.Displays.CORE_HIGHLIGHT.addChangeListener(highlightListener);
	}

	@Override
	public void dispose() {
		GamaPreferences.Displays.CORE_HIGHLIGHT.removeChangeListener(highlightListener);
	}

	/**
	 * @return the backgroundColor
	 */
	@Override
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor
	 *            the backgroundColor to set
	 */
	@Override
	public void setBackgroundColor(final GamaColor backgroundColor) {
		this.backgroundColor = backgroundColor;
		notifyListeners(IDisplayDataListener.Changes.BACKGROUND, backgroundColor);
	}

	/**
	 * @return the autosave
	 */
	@Override
	public boolean isAutosave() {
		return isAutosaving;
	}

	/**
	 * @param autosave
	 *            the autosave to set
	 */
	@Override
	public void setAutosave(final boolean autosave) {
		this.isAutosaving = autosave;
	}

	@Override
	public boolean isWireframe() {
		return isWireframe;
	}

	@Override
	public void setWireframe(final boolean t) {
		isWireframe = t;
	}

	/**
	 * @return the ortho
	 */
	@Override
	public boolean isOrtho() {
		return ortho;
	}

	/**
	 * @param ortho
	 *            the ortho to set
	 */
	@Override
	public void setOrtho(final boolean ortho) {
		this.ortho = ortho;
	}

	// /**
	// * @return the displayScale
	// */
	// public boolean isDisplayScale() {
	// return isDisplayingScale;
	// }

	// /**
	// * @param displayScale
	// * the displayScale to set
	// */
	// public void setDisplayScale(final boolean displayScale) {
	// this.isDisplayingScale = displayScale;
	// }

	/**
	 * @return the showfps
	 */
	@Override
	public boolean isShowfps() {
		return isShowingFPS;
	}

	/**
	 * @param showfps
	 *            the showfps to set
	 */
	@Override
	public void setShowfps(final boolean showfps) {
		this.isShowingFPS = showfps;
	}

	@Override
	public double getzNear() {
		return zNear;
	}

	@Override
	public double getzFar() {
		return zFar;
	}

	/**
	 * @return the drawEnv
	 */
	@Override
	public boolean isDrawEnv() {
		return isDrawingEnvironment;
	}

	/**
	 * @param drawEnv
	 *            the drawEnv to set
	 */
	@Override
	public void setDrawEnv(final boolean drawEnv) {
		this.isDrawingEnvironment = drawEnv;
	}

	/**
	 * @return the isLightOn
	 */
	@Override
	public boolean isLightOn() {
		return isLightOn;
	}

	/**
	 * @param isLightOn
	 *            the isLightOn to set
	 */
	@Override
	public void setLightOn(final boolean isLightOn) {
		this.isLightOn = isLightOn;
	}

	// Change lights to a possibly null structure instead of generating an array for each data
	@Override
	public List<ILightData> getDiffuseLights() {
		final ArrayList<ILightData> result = new ArrayList<>();
		for (final ILightData lightProp : lights) {
			if (lightProp != null) {
				// TODO : check if the light is active
				result.add(lightProp);
			}
		}
		return result;
	}

	@Override
	public void setLightActive(final int lightId, final boolean value) {
		if (lights[lightId] == null) {
			lights[lightId] = new LightData();
		}
		lights[lightId].id = lightId;
		lights[lightId].active = value;
	}

	@Override
	public void setLightType(final int lightId, final String type) {
		if (type.compareTo("direction") == 0) {
			lights[lightId].type = ILightData.TYPE.DIRECTION;
		} else if (type.compareTo("point") == 0) {
			lights[lightId].type = ILightData.TYPE.POINT;
		} else {
			lights[lightId].type = ILightData.TYPE.SPOT;
		}
	}

	@Override
	public void setLightPosition(final int lightId, final GamaPoint position) {
		lights[lightId].position = position;
	}

	@Override
	public void setLightDirection(final int lightId, final GamaPoint direction) {
		lights[lightId].direction = direction;
	}

	@Override
	public void setDiffuseLightColor(final int lightId, final GamaColor color) {
		lights[lightId].color = color;
	}

	@Override
	public void setSpotAngle(final int lightId, final float angle) {
		lights[lightId].spotAngle = angle;
	}

	@Override
	public void setLinearAttenuation(final int lightId, final float linearAttenuation) {
		lights[lightId].linearAttenuation = linearAttenuation;
	}

	@Override
	public void setQuadraticAttenuation(final int lightId, final float quadraticAttenuation) {
		lights[lightId].quadraticAttenuation = quadraticAttenuation;
	}

	@Override
	public void setDrawLight(final int lightId, final boolean value) {
		lights[lightId].drawLight = value;
	}

	@Override
	public void disableCameraInteractions(final boolean disableCamInteract) {
		this.disableCameraInteraction = disableCamInteract;
	}

	@Override
	public boolean cameraInteractionDisabled() {
		return disableCameraInteraction;
	}

	/**
	 * @return the ambientLightColor
	 */
	@Override
	public Color getAmbientLightColor() {
		return ambientColor;
	}

	/**
	 * @param ambientLightColor
	 *            the ambientLightColor to set
	 */
	@Override
	public void setAmbientLightColor(final GamaColor ambientLightColor) {
		this.ambientColor = ambientLightColor;
	}

	@Override
	public boolean isCameraPosDefined() {
		return cameraPos != null;
	}

	@Override
	public boolean isCameraLookAtDefined() {
		return cameraLookPos != null;
	}

	@Override
	public boolean isCameraUpVectorDefined() {
		return cameraUpVector != null;
	}

	/**
	 * @return the cameraPos
	 */
	@Override
	public GamaPoint getCameraPos() {
		return cameraPos;
	}

	/**
	 * @param cameraPos
	 *            the cameraPos to set
	 */
	@Override
	public void setCameraPos(final GamaPoint point) {
		if (point == null)
			return;
		final GamaPoint c = point;
		if (cameraPos != null) {
			if (c.equals(cameraPos))
				return;
			else {
				cameraPos.setLocation(c);
			}
		} else {
			cameraPos = new GamaPoint(c);
		}

		notifyListeners(IDisplayDataListener.Changes.CAMERA_POS, cameraPos);
	}

	/**
	 * @return the cameraLookPos
	 */
	@Override
	public GamaPoint getCameraLookPos() {
		return cameraLookPos;
	}

	/**
	 * @param cameraLookPos
	 *            the cameraLookPos to set
	 */
	@Override
	public void setCameraLookPos(final GamaPoint point) {
		if (point == null)
			return;
		final GamaPoint c = point;
		if (cameraLookPos != null) {
			if (c.equals(cameraLookPos))
				return;
			else {
				cameraLookPos.setLocation(c);
			}
		} else {
			cameraLookPos = new GamaPoint(c);
		}

		notifyListeners(IDisplayDataListener.Changes.CAMERA_TARGET, cameraLookPos);
	}

	/**
	 * @return the cameraUpVector
	 */
	@Override
	public GamaPoint getCameraUpVector() {
		return cameraUpVector;
	}

	/**
	 * @param cameraUpVector
	 *            the cameraUpVector to set
	 */
	@Override
	public void setCameraUpVector(final GamaPoint point) {
		if (point == null)
			return;
		final GamaPoint c = point;
		if (cameraUpVector != null) {
			if (c.equals(cameraUpVector))
				return;
			else {
				DEBUG.OUT("UpVectors different: x " + point.x + " != " + cameraUpVector.x);
				cameraUpVector.setLocation(c);
			}
		} else {
			cameraUpVector = new GamaPoint(c);
		}

		notifyListeners(IDisplayDataListener.Changes.CAMERA_UP, cameraUpVector);
	}

	/**
	 * @return the cameraLens
	 */
	@Override
	public int getCameralens() {
		return cameraLens;
	}

	/**
	 * @param cameraLens
	 *            the cameraLens to set
	 */
	@Override
	public void setCameraLens(final int cameraLens) {
		if (this.cameraLens != cameraLens) {
			this.cameraLens = cameraLens;
		}
	}

	/**
	 * @return the displayType
	 */
	@Override
	public String getDisplayType() {
		return displayType;
	}

	/**
	 * @param displayType
	 *            the displayType to set
	 */
	@Override
	public void setDisplayType(final String displayType) {
		this.displayType = displayType;
		isOpenGL = displayType.equals(OPENGL) || displayType.equals(THREED) || displayType.equals(OPENGL2);

	}

	/**
	 * @return the imageDimension
	 */
	@Override
	public GamaPoint getImageDimension() {
		return imageDimension;
	}

	/**
	 * @param imageDimension
	 *            the imageDimension to set
	 */
	@Override
	public void setImageDimension(final GamaPoint imageDimension) {
		this.imageDimension = imageDimension;
	}

	/**
	 * @return the envWidth
	 */
	@Override
	public double getEnvWidth() {
		return envWidth;
	}

	/**
	 * @param envWidth
	 *            the envWidth to set
	 */
	@Override
	public void setEnvWidth(final double envWidth) {
		this.envWidth = envWidth;
	}

	/**
	 * @return the envHeight
	 */
	@Override
	public double getEnvHeight() {
		return envHeight;
	}

	/**
	 * @param envHeight
	 *            the envHeight to set
	 */
	@Override
	public void setEnvHeight(final double envHeight) {
		this.envHeight = envHeight;
	}

	@Override
	public double getMaxEnvDim() {
		return envWidth > envHeight ? envWidth : envHeight;
	}

	/**
	 * @return
	 */
	@Override
	public GamaColor getHighlightColor() {
		return highlightColor;
	}

	@Override
	public void setHighlightColor(final GamaColor hc) {
		highlightColor = hc;
		notifyListeners(IDisplayDataListener.Changes.HIGHLIGHT, highlightColor);
	}

	@Override
	public boolean isAntialias() {
		return isAntialiasing;
	}

	@Override
	public void setAntialias(final boolean a) {
		isAntialiasing = a;
		notifyListeners(IDisplayDataListener.Changes.ANTIALIAS, a);
	}

	/**
	 * @return
	 */
	@Override
	public boolean isContinuousRotationOn() {
		return isRotating;
	}

	@Override
	public void setContinuousRotation(final boolean r) {
		isRotating = r;
		if (r && zRotationAngleDelta == 0) {
			zRotationAngleDelta = 0.2;
		}
		if (!r) {
			zRotationAngleDelta = 0;
		}
	}

	@Override
	public double getCurrentRotationAboutZ() {
		return currentRotationAboutZ;
	}

	@Override
	public void setZRotationAngle(final double val) {
		zRotationAngleDelta = val;
		currentRotationAboutZ = val;
		// notifyListeners(Changes.ROTATION, val);
	}

	@Override
	public void incrementZRotation() {
		currentRotationAboutZ += zRotationAngleDelta;
	}

	@Override
	public void resetZRotation() {
		currentRotationAboutZ = zRotationAngleDelta;
	}

	/**
	 * @return
	 */
	@Override
	public boolean isArcBallCamera() {
		return isUsingArcBallCamera;
	}

	@Override
	public void setArcBallCamera(final boolean c) {
		isUsingArcBallCamera = c;
		notifyListeners(IDisplayDataListener.Changes.CHANGE_CAMERA, c);
	}

	/**
	 * @return
	 */
	@Override
	public boolean isLayerSplitted() {
		return isSplittingLayers;
	}

	@Override
	public void setLayerSplitted(final boolean s) {
		isSplittingLayers = s;
		if (s) {
			notifyListeners(IDisplayDataListener.Changes.SPLIT_LAYER, splitDistance);
		} else {
			notifyListeners(IDisplayDataListener.Changes.SPLIT_LAYER, 0d);
		}
	}

	@Override
	public Double getSplitDistance() {
		if (splitDistance == null) {
			splitDistance = 0.05;
		}
		return splitDistance;
	}

	@Override
	public void setSplitDistance(final Double s) {
		splitDistance = s;
		if (isSplittingLayers) {
			notifyListeners(IDisplayDataListener.Changes.SPLIT_LAYER, s);
		}
	}

	@Override
	public boolean isSynchronized() {
		return isSynchronized;
	}

	@Override
	public void setSynchronized(final boolean isSynchronized) {
		this.isSynchronized = isSynchronized;
	}

	/**
	 * @return the zoomLevel
	 */
	@Override
	public Double getZoomLevel() {
		return zoomLevel;
	}

	/**
	 * @param zoomLevel
	 *            the zoomLevel to set
	 */
	@Override
	public void setZoomLevel(final Double zoomLevel, final boolean notify, final boolean force) {
		if (this.zoomLevel != null && this.zoomLevel.equals(zoomLevel))
			return;
		this.zoomLevel = zoomLevel;
		if (notify) {
			notifyListeners(IDisplayDataListener.Changes.ZOOM, this.zoomLevel);
		}
	}

	@Override
	public int fullScreen() {
		return fullScreen;
	}

	@Override
	public void setFullScreen(final int fs) {
		fullScreen = fs;
	}

	@Override
	public void setKeystone(final List<GamaPoint> value) {
		if (value == null)
			return;
		this.keystone.setTo(value.toArray(new GamaPoint[4]));
		notifyListeners(IDisplayDataListener.Changes.KEYSTONE, this.keystone);
	}

	@Override
	public void setKeystone(final ICoordinates value) {
		if (value == null)
			return;
		this.keystone.setTo(value.toCoordinateArray());
		notifyListeners(IDisplayDataListener.Changes.KEYSTONE, this.keystone);
	}

	@Override
	public ICoordinates getKeystone() {
		return this.keystone;
	}

	@Override
	public boolean isKeystoneDefined() {
		return !keystone.equals(KEYSTONE_IDENTITY);
	}

	@Override
	public void setPresetCamera(final String newValue) {
		presetCamera = newValue;
		notifyListeners(IDisplayDataListener.Changes.CAMERA_PRESET, newValue);
	}

	@Override
	public String getPresetCamera() {
		return presetCamera;
	}

	@Override
	public boolean isToolbarVisible() {
		return this.isToolbarVisible;
	}

	@Override
	public GamaColor getToolbarColor() {
		return toolbarColor;
	}

	@Override
	public void setToolbarVisible(final boolean b) {
		isToolbarVisible = b;
	}

	@Override
	public void initWith(final IScope scope, final IDescription desc) {
		final Facets facets = desc.getFacets();
		// Initializing the size of the environment
		SimulationAgent sim = scope.getSimulation();
		// hqnghi if layer come from micro-model
		final ModelDescription micro = desc.getModelDescription();
		final ModelDescription main = (ModelDescription) scope.getModel().getDescription();
		final Boolean fromMicroModel = main.getMicroModel(micro.getAlias()) != null;
		if (fromMicroModel) {
			final ExperimentAgent exp = (ExperimentAgent) scope.getRoot()
					.getExternMicroPopulationFor(micro.getAlias() + "." + desc.getOriginName()).getAgent(0);
			sim = exp.getSimulation();
		}
		// end-hqnghi
		Envelope3D env = null;
		if (sim != null) {
			env = sim.getEnvelope();
		} else {
			env = Envelope3D.of(0, 100, 0, 100, 0, 0);
		}
		setEnvWidth(env.getWidth());
		setEnvHeight(env.getHeight());
		env.dispose();

		final IExpression auto = facets.getExpr(IKeyword.AUTOSAVE);
		if (auto != null) {
			if (auto.getGamlType().equals(Types.POINT)) {
				setAutosave(true);
				setImageDimension(Cast.asPoint(scope, auto.value(scope)));
			} else {
				setAutosave(Cast.asBool(scope, auto.value(scope)));
			}
		}
		final IExpression toolbar = facets.getExpr(IKeyword.TOOLBAR);
		if (toolbar != null) {
			if (toolbar.getGamlType() == Types.BOOL) {
				setToolbarVisible(Cast.asBool(scope, toolbar.value(scope)));
			} else {
				setToolbarVisible(true);
				toolbarColor = Cast.asColor(scope, toolbar.value(scope));
			}
		}
		final IExpression fps = facets.getExpr(IKeyword.SHOWFPS);
		if (fps != null) {
			setShowfps(Cast.asBool(scope, fps.value(scope)));
		}

		final IExpression nZ = facets.getExpr("z_near");
		if (nZ != null) {
			setZNear(Cast.asFloat(scope, nZ.value(scope)));
		}

		final IExpression fZ = facets.getExpr("z_far");
		if (fZ != null) {
			setZFar(Cast.asFloat(scope, fZ.value(scope)));
		}
		final IExpression denv = facets.getExpr(IKeyword.DRAWENV);
		if (denv != null) {
			setDrawEnv(Cast.asBool(scope, denv.value(scope)));
		}

		final IExpression ortho = facets.getExpr(IKeyword.ORTHOGRAPHIC_PROJECTION);
		if (ortho != null) {
			setOrtho(Cast.asBool(scope, ortho.value(scope)));
		}

		final IExpression fixed_cam = facets.getExpr(IKeyword.CAMERA_INTERACTION);
		if (fixed_cam != null) {
			disableCameraInteractions(!Cast.asBool(scope, fixed_cam.value(scope)));
		}

		final IExpression keystone_exp = facets.getExpr(IKeyword.KEYSTONE);
		if (keystone_exp != null) {
			@SuppressWarnings ("unchecked") final List<GamaPoint> val =
					GamaListFactory.create(scope, Types.POINT, Cast.asList(scope, keystone_exp.value(scope)));
			if (val.size() >= 4) {
				setKeystone(val);
			}
		}

		final IExpression rotate_exp = facets.getExpr(IKeyword.ROTATE);
		if (rotate_exp != null) {
			final double val = Cast.asFloat(scope, rotate_exp.value(scope));
			setZRotationAngle(val);
		}

		final IExpression lightOn = facets.getExpr(IKeyword.IS_LIGHT_ON);
		if (lightOn != null) {
			setLightOn(Cast.asBool(scope, lightOn.value(scope)));
		}

		final IExpression light2 = facets.getExpr(IKeyword.DIFFUSE_LIGHT);
		// this facet is deprecated...
		if (light2 != null) {
			setLightActive(1, true);
			if (light2.getGamlType().equals(Types.COLOR)) {
				setDiffuseLightColor(1, Cast.asColor(scope, light2.value(scope)));
			} else {
				final int meanValue = Cast.asInt(scope, light2.value(scope));
				setDiffuseLightColor(1, new GamaColor(meanValue, meanValue, meanValue, 255));
			}
		}

		final IExpression light3 = facets.getExpr(IKeyword.DIFFUSE_LIGHT_POS);
		// this facet is deprecated...
		if (light3 != null) {
			setLightActive(1, true);
			setLightDirection(1, Cast.asPoint(scope, light3.value(scope)));
		}

		final IExpression drawLights = facets.getExpr(IKeyword.DRAW_DIFFUSE_LIGHT);
		if (drawLights != null) {
			if (Cast.asBool(scope, drawLights.value(scope)) == true) {
				// set the drawLight attribute to true for all the already
				// existing light
				for (int i = 0; i < 8; i++) {
					boolean lightAlreadyCreated = false;
					for (final ILightData lightProp : getDiffuseLights()) {
						if (lightProp.getId() == i) {
							lightProp.setDrawLight(true);
							lightAlreadyCreated = true;
						}
					}
					// if the light does not exist yet, create it by using the
					// method "setLightActive", and set the drawLight attr to
					// true.
					if (!lightAlreadyCreated) {
						if (i < 2) {
							setLightActive(i, true);
						} else {
							setLightActive(i, false);
						}
						setDrawLight(i, true);
					}
					lightAlreadyCreated = false;
				}
			}
		}

		// Set the up vector of the opengl Camera (see gluPerspective)
		final IExpression cameraUp = facets.getExpr(IKeyword.CAMERA_UP_VECTOR);
		if (cameraUp != null) {
			final GamaPoint location = Cast.asPoint(scope, cameraUp.value(scope));
			location.setY(-location.getY()); // y component need to be reverted
			setCameraUpVector(location);
		}

		// Set the up vector of the opengl Camera (see gluPerspective)
		final IExpression cameraLens = facets.getExpr(IKeyword.CAMERA_LENS);
		if (cameraLens != null) {
			final int lens = Cast.asInt(scope, cameraLens.value(scope));
			setCameraLens(lens);
		}

		final IExpression fs = facets.getExpr(IKeyword.FULLSCREEN);
		if (fs != null) {
			int monitor;
			if (fs.getGamlType() == Types.BOOL) {
				monitor = Cast.asBool(scope, fs.value(scope)) ? 0 : -1;
			} else {
				monitor = Cast.asInt(scope, fs.value(scope));
			}
			setFullScreen(monitor);
		}

		// final IExpression use_shader = facets.getExpr("use_shader");
		// if (use_shader != null) {
		// this.useShader = Cast.asBool(scope, use_shader.value(scope));
		// }

		final IExpression color = facets.getExpr(IKeyword.BACKGROUND);
		if (color != null) {
			setBackgroundColor(Cast.asColor(scope, color.value(scope)));
			constantBackground = color.isConst();
		}

		final IExpression light = facets.getExpr(IKeyword.AMBIENT_LIGHT);
		if (light != null) {
			if (light.getGamlType().equals(Types.COLOR)) {
				setAmbientLightColor(Cast.asColor(scope, light.value(scope)));
			} else {
				final int meanValue = Cast.asInt(scope, light.value(scope));
				setAmbientLightColor(new GamaColor(meanValue, meanValue, meanValue, 255));
			}
			constantAmbientLight = light.isConst();
		}

		final IExpression camera = facets.getExpr(IKeyword.CAMERA_POS);
		if (camera != null) {
			final GamaPoint location = Cast.asPoint(scope, camera.value(scope));
			location.y = -location.y; // y component need to be reverted
			setCameraPos(location);
			constantCamera = camera.isConst();
			// cameraFix = true;
		}

		final IExpression cameraLook = facets.getExpr(IKeyword.CAMERA_LOOK_POS);
		if (cameraLook != null) {
			final GamaPoint location = Cast.asPoint(scope, cameraLook.value(scope));
			location.setY(-location.getY()); // y component need to be reverted
			setCameraLookPos(location);
			constantCameraLook = cameraLook.isConst();
		}

	}

	private void setZFar(final Double zF) {
		zFar = zF;

	}

	private void setZNear(final Double zN) {
		zNear = zN;
	}

	@Override
	public void update(final IScope scope, final Facets facets) {
		final IExpression auto = facets.getExpr(IKeyword.AUTOSAVE);
		if (auto != null) {
			if (auto.getGamlType().equals(Types.POINT)) {
				setAutosave(true);
				setImageDimension(Cast.asPoint(scope, auto.value(scope)));
			} else {
				setAutosave(Cast.asBool(scope, auto.value(scope)));
			}
		}
		// /////////////// dynamic Lighting ///////////////////

		if (!constantBackground) {

			final IExpression color = facets.getExpr(IKeyword.BACKGROUND);
			if (color != null) {
				setBackgroundColor(Cast.asColor(scope, color.value(scope)));
			}

		}

		if (!constantAmbientLight) {
			final IExpression light = facets.getExpr(IKeyword.AMBIENT_LIGHT);
			if (light != null) {
				if (light.getGamlType().equals(Types.COLOR)) {
					setAmbientLightColor(Cast.asColor(scope, light.value(scope)));
				} else {
					final int meanValue = Cast.asInt(scope, light.value(scope));
					setAmbientLightColor(new GamaColor(meanValue, meanValue, meanValue, 255));
				}
			}
		}

		// /////////////////// dynamic camera ///////////////////
		if (!constantCamera) {
			final IExpression camera = facets.getExpr(IKeyword.CAMERA_POS);
			if (camera != null) {
				final GamaPoint location = Cast.asPoint(scope, camera.value(scope));
				if (location != null) {
					location.y = -location.y; // y component need to be
				}
				// reverted
				setCameraPos(location);
			}
			// graphics.setCameraPosition(getCameraPos());
		}

		if (!constantCameraLook) {
			final IExpression cameraLook = facets.getExpr(IKeyword.CAMERA_LOOK_POS);
			if (cameraLook != null) {
				final GamaPoint location = Cast.asPoint(scope, cameraLook.value(scope));
				if (location != null) {
					location.setY(-location.getY()); // y component need to be
				}
				// reverted
				setCameraLookPos(location);
			}
		}

	}

	@Override
	public boolean isOpenGL2() {
		return displayType.equals(OPENGL2);
	}

	@Override
	public boolean isOpenGL() {
		return isOpenGL;
	}

	@Override
	public void clearListeners() {
		listeners.clear();
	}

}