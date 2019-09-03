package gama.common.interfaces.outputs;

import java.awt.Color;
import java.util.List;

import gama.common.geometry.ICoordinates;
import gama.metamodel.shape.GamaPoint;
import gama.runtime.scope.IScope;
import gama.util.GamaColor;
import gaml.descriptions.IDescription;
import gaml.statements.Facets;

public interface IDisplayData {

	String JAVA2D = "java2D";
	String OPENGL = "opengl";
	String OPENGL2 = "opengl2";
	String THREED = "3D";
	Double INITIAL_ZOOM = 1.0;

	void addListener(IDisplayDataListener listener);

	void removeListener(IDisplayDataListener listener);

	void notifyListeners(IDisplayDataListener.Changes property, Object value);

	ICoordinates KEYSTONE_IDENTITY = ICoordinates.ofLength(4).setTo(0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0);

	void dispose();

	/**
	 * @return the backgroundColor
	 */
	Color getBackgroundColor();

	/**
	 * @param backgroundColor
	 *            the backgroundColor to set
	 */
	void setBackgroundColor(GamaColor backgroundColor);

	/**
	 * @return the autosave
	 */
	boolean isAutosave();

	/**
	 * @param autosave
	 *            the autosave to set
	 */
	void setAutosave(boolean autosave);

	boolean isWireframe();

	void setWireframe(boolean t);

	/**
	 * @return the ortho
	 */
	boolean isOrtho();

	/**
	 * @param ortho
	 *            the ortho to set
	 */
	void setOrtho(boolean ortho);

	/**
	 * @return the showfps
	 */
	boolean isShowfps();

	/**
	 * @param showfps
	 *            the showfps to set
	 */
	void setShowfps(boolean showfps);

	double getzNear();

	double getzFar();

	/**
	 * @return the drawEnv
	 */
	boolean isDrawEnv();

	/**
	 * @param drawEnv
	 *            the drawEnv to set
	 */
	void setDrawEnv(boolean drawEnv);

	/**
	 * @return the isLightOn
	 */
	boolean isLightOn();

	/**
	 * @param isLightOn
	 *            the isLightOn to set
	 */
	void setLightOn(boolean isLightOn);

	// Change lights to a possibly null structure instead of generating an array for each data
	List<ILightData> getDiffuseLights();

	void setLightActive(int lightId, boolean value);

	void setLightType(int lightId, String type);

	void setLightPosition(int lightId, GamaPoint position);

	void setLightDirection(int lightId, GamaPoint direction);

	void setDiffuseLightColor(int lightId, GamaColor color);

	void setSpotAngle(int lightId, float angle);

	void setLinearAttenuation(int lightId, float linearAttenuation);

	void setQuadraticAttenuation(int lightId, float quadraticAttenuation);

	void setDrawLight(int lightId, boolean value);

	void disableCameraInteractions(boolean disableCamInteract);

	boolean cameraInteractionDisabled();

	/**
	 * @return the ambientLightColor
	 */
	Color getAmbientLightColor();

	/**
	 * @param ambientLightColor
	 *            the ambientLightColor to set
	 */
	void setAmbientLightColor(GamaColor ambientLightColor);

	boolean isCameraPosDefined();

	boolean isCameraLookAtDefined();

	boolean isCameraUpVectorDefined();

	/**
	 * @return the cameraPos
	 */
	GamaPoint getCameraPos();

	/**
	 * @param cameraPos
	 *            the cameraPos to set
	 */
	void setCameraPos(GamaPoint point);

	/**
	 * @return the cameraLookPos
	 */
	GamaPoint getCameraLookPos();

	/**
	 * @param cameraLookPos
	 *            the cameraLookPos to set
	 */
	void setCameraLookPos(GamaPoint point);

	/**
	 * @return the cameraUpVector
	 */
	GamaPoint getCameraUpVector();

	/**
	 * @param cameraUpVector
	 *            the cameraUpVector to set
	 */
	void setCameraUpVector(GamaPoint point);

	/**
	 * @return the cameraLens
	 */
	int getCameralens();

	/**
	 * @param cameraLens
	 *            the cameraLens to set
	 */
	void setCameraLens(int cameraLens);

	/**
	 * @return the displayType
	 */
	String getDisplayType();

	/**
	 * @param displayType
	 *            the displayType to set
	 */
	void setDisplayType(String displayType);

	/**
	 * @return the imageDimension
	 */
	GamaPoint getImageDimension();

	/**
	 * @param imageDimension
	 *            the imageDimension to set
	 */
	void setImageDimension(GamaPoint imageDimension);

	/**
	 * @return the envWidth
	 */
	double getEnvWidth();

	/**
	 * @param envWidth
	 *            the envWidth to set
	 */
	void setEnvWidth(double envWidth);

	/**
	 * @return the envHeight
	 */
	double getEnvHeight();

	/**
	 * @param envHeight
	 *            the envHeight to set
	 */
	void setEnvHeight(double envHeight);

	double getMaxEnvDim();

	/**
	 * @return
	 */
	GamaColor getHighlightColor();

	void setHighlightColor(GamaColor hc);

	boolean isAntialias();

	void setAntialias(boolean a);

	/**
	 * @return
	 */
	boolean isContinuousRotationOn();

	void setContinuousRotation(boolean r);

	double getCurrentRotationAboutZ();

	void setZRotationAngle(double val);

	void incrementZRotation();

	void resetZRotation();

	/**
	 * @return
	 */
	boolean isArcBallCamera();

	void setArcBallCamera(boolean c);

	/**
	 * @return
	 */
	boolean isLayerSplitted();

	void setLayerSplitted(boolean s);

	Double getSplitDistance();

	void setSplitDistance(Double s);

	boolean isSynchronized();

	void setSynchronized(boolean isSynchronized);

	/**
	 * @return the zoomLevel
	 */
	Double getZoomLevel();

	/**
	 * @param zoomLevel
	 *            the zoomLevel to set
	 */
	void setZoomLevel(Double zoomLevel, boolean notify, boolean force);

	int fullScreen();

	void setFullScreen(int fs);

	void setKeystone(List<GamaPoint> value);

	void setKeystone(ICoordinates value);

	ICoordinates getKeystone();

	boolean isKeystoneDefined();

	void setPresetCamera(String newValue);

	String getPresetCamera();

	boolean isToolbarVisible();

	GamaColor getToolbarColor();

	void setToolbarVisible(boolean b);

	void initWith(IScope scope, IDescription desc);

	void update(IScope scope, Facets facets);

	boolean isOpenGL2();

	boolean isOpenGL();

	void clearListeners();

}