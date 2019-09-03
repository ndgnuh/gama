/*********************************************************************************************
 *
 * 'GLUtilLight.java, in plugin gama.ui.displays.opengl, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.ui.displays.opengl.renderer.helpers;

import java.awt.Color;
import java.util.List;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import gama.core.outputs.LightData;
import gama.ui.displays.opengl.OpenGL;
import gama.ui.displays.opengl.renderer.IOpenGLRenderer;
import gama.common.interfaces.outputs.IDisplayData;
import gama.common.interfaces.outputs.ILightData;
import gama.metamodel.shape.GamaPoint;
import gama.util.GamaColor;

public class LightHelper extends AbstractRendererHelper {

	public LightHelper(final IOpenGLRenderer renderer) {
		super(renderer);
	}

	// public static final int fogMode[] = { GL2.GL_EXP, GL2.GL_EXP2, GL2.GL_LINEAR };

	public void setAmbiantLight(final OpenGL gl, final Color ambientLightValue) {
		final float[] lightAmbientValue = { ambientLightValue.getRed() / 255.0f, ambientLightValue.getGreen() / 255.0f,
				ambientLightValue.getBlue() / 255.0f, 1.0f };
		gl.getGL().glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, lightAmbientValue, 0);
	}

	@Override
	public void initialize() {
		final OpenGL gl = getOpenGL();
		final IDisplayData data = getData();
		// ambient
		setAmbiantLight(gl, data.getAmbientLightColor());
		// deactivate diffuse light for the light0
		data.setLightActive(0, true);
		data.setLightType(0, "direction");
		// default value for diffuse light
		boolean useDefaultValueForLight1 = true;
		for (final ILightData lightProp : data.getDiffuseLights()) {
			if (lightProp.getId() == 1) {
				useDefaultValueForLight1 = false;
			}
		}
		if (useDefaultValueForLight1) {
			data.setLightActive(1, true);
			// directional light
			data.setLightType(1, "direction");
			data.setLightDirection(1, new GamaPoint(0.5, 0.5, -1));
			if (getRenderer().useShader()) {
				data.setDiffuseLightColor(1, new GamaColor(255, 255, 255, 255));
			} else {
				data.setDiffuseLightColor(1, new GamaColor(127, 127, 127, 255));
			}
		}

		// set material properties which will be assigned by glColor

		gl.getGL().glColorMaterial(GL.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
		gl.getGL().glLightModelf(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);
		// // enable color tracking
		gl.getGL().glEnable(GL2.GL_COLOR_MATERIAL);
	}

	public void updateDiffuseLightValue(final OpenGL openGL) {
		// final GL2 gl = openGL.getGL();
		final List<ILightData> lightPropertiesList = getData().getDiffuseLights();
		final double size = getMaxEnvDim() / 20;
		final double worldWidth = getRenderer().getEnvWidth();
		final double worldHeight = getRenderer().getEnvHeight();
		for (final ILightData lp : lightPropertiesList) {
			LightData lightProperties = (LightData) lp;
			if (lightProperties.isActive()) {
				openGL.getGL().glEnable(GL2.GL_LIGHT0 + lightProperties.getId());
				// GET AND SET ALL THE PROPERTIES OF THE LIGHT
				// Get the type of light (direction / point / spot)
				final LightData.TYPE type = lightProperties.getType();
				// Get and set the color (the diffuse color)
				final float[] diffuseColor = { lightProperties.getColor().getRed() / 255.0f,
						lightProperties.getColor().getGreen() / 255.0f, lightProperties.getColor().getBlue() / 255.0f,
						lightProperties.getColor().getAlpha() / 255.0f };
				openGL.getGL().glLightfv(GL2.GL_LIGHT0 + lightProperties.getId(), GL2.GL_DIFFUSE, diffuseColor, 0);
				// Get and set the position
				// the 4th value of the position determines weather of not the
				// distance object-light has to be computed
				float[] lightPosition;
				if (type == LightData.TYPE.DIRECTION) {
					lightPosition = new float[] { -(float) lightProperties.getDirection().x,
							(float) lightProperties.getDirection().y, -(float) lightProperties.getDirection().z, 0 };
				} else {
					lightPosition = new float[] { (float) lightProperties.getPosition().x,
							-(float) lightProperties.getPosition().y, (float) lightProperties.getPosition().z, 1 };
				}
				openGL.getGL().glLightfv(GL2.GL_LIGHT0 + lightProperties.getId(), GL2.GL_POSITION, lightPosition, 0);
				// Get and set the attenuation (if it is not a direction light)
				if (type != LightData.TYPE.DIRECTION) {
					final float linearAttenuation = lightProperties.getLinearAttenuation();
					final float quadraticAttenuation = lightProperties.getQuadraticAttenuation();
					openGL.getGL().glLightf(GL2.GL_LIGHT0 + lightProperties.getId(), GL2.GL_LINEAR_ATTENUATION,
							linearAttenuation);
					openGL.getGL().glLightf(GL2.GL_LIGHT0 + lightProperties.getId(), GL2.GL_QUADRATIC_ATTENUATION,
							quadraticAttenuation);
				}
				// Get and set spot properties (if the light is a spot light)
				if (type == LightData.TYPE.SPOT) {
					final float[] spotLight = { (float) lightProperties.getDirection().x,
							-(float) lightProperties.getDirection().y, (float) lightProperties.getDirection().z };
					openGL.getGL().glLightfv(GL2.GL_LIGHT0 + lightProperties.getId(), GL2.GL_SPOT_DIRECTION, spotLight,
							0);
					final float spotAngle = lightProperties.getSpotAngle();
					openGL.getGL().glLightf(GL2.GL_LIGHT0 + lightProperties.getId(), GL2.GL_SPOT_CUTOFF, spotAngle);
				}

				// DRAW THE LIGHT IF NEEDED
				if (lightProperties.isDrawLight() && lightProperties.getId() != 0) {
					// disable the lighting during the time the light is drawn
					final boolean previous = openGL.setLighting(false);
					drawLight(openGL, size, worldWidth, worldHeight, lightProperties, lightPosition);
					openGL.setLighting(previous);
				}
			} else {
				openGL.getGL().glDisable(GL2.GL_LIGHT0 + lightProperties.getId());
			}
		}
	}

	private void drawLight(final OpenGL openGL, final double size, final double worldWidth, final double worldHeight,
			final LightData lightProperties, final float[] lightPosition) {

		// save the current color to re-set it at the end of this
		// part
		final Color currentColor = openGL.swapCurrentColor(lightProperties.getColor());
		// change the current color to the light color (the
		// representation of the color will have the same color as
		// the light in itself)
		final GLUT glut = new GLUT();
		final double x = lightProperties.getDirection().x;
		final double y = -lightProperties.getDirection().y;
		final double z = lightProperties.getDirection().z;
		final double norm = Math.sqrt(x * x + y * y + z * z);
		final double zNorm = z / norm;
		final double xNorm = x / norm;
		final double yNorm = y / norm;
		final LightData.TYPE type = lightProperties.getType();
		if (type == LightData.TYPE.POINT) {
			openGL.pushMatrix();
			openGL.translateBy(lightPosition[0], lightPosition[1], lightPosition[2]);
			glut.glutSolidSphere(size, 16, 16);
			openGL.popMatrix();
		} else if (type == LightData.TYPE.SPOT) {
			openGL.pushMatrix();
			openGL.translateBy(lightPosition[0], lightPosition[1], lightPosition[2]);

			final double baseSize = Math.sin(Math.toRadians(lightProperties.getSpotAngle())) * size;

			// see :
			// http://opengl.developpez.com/tutoriels/opengl-tutorial/17-les-rotations-quaternions/
			// init vector : {0,0,-1}
			// dest vector : {x,y,z}
			// compute angle
			int flag = 1;
			if (zNorm < 0) {
				flag = -1;
			}
			final double cosAngle = flag * zNorm;
			// compute axis : dest vect init
			double[] axis = new double[3];
			final double angle = Math.acos(flag * cosAngle);
			axis = CrossProduct(new double[] { 0, 0, -1 }, new double[] { x, y, z });

			openGL.rotateBy(Math.toDegrees(-angle) + 180, axis[0], axis[1], axis[2]);
			openGL.translateBy(0, 0, -size);
			glut.glutSolidCone(baseSize, size, 16, 16);
			openGL.popMatrix();
		} else {
			// draw direction light : a line and an sphere at the end of the line.
			final int maxI = 3;
			final int maxJ = 3;
			for (int i = 0; i < maxI; i++) {
				for (int j = 0; j < maxJ; j++) {
					final double[] beginPoint =
							new double[] { i * worldWidth / maxI, -j * worldHeight / maxJ, size * 10 };
					final double[] endPoint = new double[] { i * worldWidth / maxI + xNorm * size * 3,
							-(j * worldHeight / maxJ) - yNorm * size * 3, size * 10 + zNorm * size * 3 };
					// draw the lines
					openGL.beginDrawing(GL2.GL_LINES);
					openGL.drawVertex(0, beginPoint[0], beginPoint[1], beginPoint[2]);
					openGL.drawVertex(0, endPoint[0], endPoint[1], endPoint[2]);
					openGL.endDrawing();
					// draw the small sphere
					openGL.pushMatrix();
					openGL.translateBy(endPoint[0], endPoint[1], endPoint[2]);
					glut.glutSolidSphere(size / 5, 16, 16);
					openGL.popMatrix();
				}
			}
		}
		openGL.setCurrentColor(currentColor);

	}

	public static double[] CrossProduct(final double[] vect1, final double[] vect2) {
		final double[] result = new double[3];
		result[0] = vect1[1] * vect2[2] - vect1[2] * vect2[1];
		result[1] = vect1[2] * vect2[0] - vect1[0] * vect2[2];
		result[2] = vect1[0] * vect2[1] - vect1[1] * vect2[0];
		return result;
	}

	public void draw() {
		if (isActive()) {
			final OpenGL openGL = getOpenGL();
			openGL.pushMatrix();
			setAmbiantLight(openGL, getData().getAmbientLightColor());
			updateDiffuseLightValue(openGL);
			openGL.popMatrix();
		}
	}

	public boolean isActive() {
		return getData().isLightOn();
	}

}
