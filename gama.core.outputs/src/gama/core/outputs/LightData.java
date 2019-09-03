/*******************************************************************************************************
 *
 * gama.core.outputs.LightData.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.outputs;

import gama.common.interfaces.outputs.ILightData;
import gama.metamodel.shape.GamaPoint;
import gama.util.GamaColor;

public class LightData implements ILightData {
	int id;
	boolean active = false;
	GamaColor color = new GamaColor(127, 127, 127, 255);
	GamaPoint position = new GamaPoint(0, 0, 20);
	TYPE type = TYPE.DIRECTION;
	float linearAttenuation = 0;
	float quadraticAttenuation = 0;
	boolean drawLight = false;
	GamaPoint direction = new GamaPoint(0.5, 0.5, -1);
	float spotAngle = 45.0f;

	public void setId(final int id) {
		this.id = id;
	}

	public void setType(final TYPE type) {
		this.type = type;
	}

	public void setLinearAttenuation(final float linearAttenuation) {
		this.linearAttenuation = linearAttenuation;
	}

	public void setQuadraticAttenuation(final float quadraticAttenuation) {
		this.quadraticAttenuation = quadraticAttenuation;
	}

	public void setDrawLight(final boolean drawLight) {
		this.drawLight = drawLight;
	}

	public void setDirection(final GamaPoint direction) {
		this.direction = direction;
	}

	public void setSpotAngle(final float spotAngle) {
		this.spotAngle = spotAngle;
	}

	public GamaPoint getColorPoint() {
		return new GamaPoint(color.red() / 255.0, color.green() / 255.0, color.blue() / 255.0);
	}

	public GamaColor getColor() {
		return color;
	}

	@Override
	public GamaPoint getPosition() {
		return position;
	}

	public GamaPoint getDirection() {
		return direction;
	}

	@Override
	public int getId() {
		return id;
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public TYPE getType() {
		return type;
	}

	public float getLinearAttenuation() {
		return linearAttenuation;
	}

	public float getQuadraticAttenuation() {
		return quadraticAttenuation;
	}

	public boolean isDrawLight() {
		return drawLight;
	}

	public float getSpotAngle() {
		return spotAngle;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	public void setColor(final GamaColor color) {
		this.color = color;
	}

	public void setPosition(final GamaPoint position) {
		this.position = position;
	}

}
