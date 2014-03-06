package msi.gama.jogl.scene;

import java.awt.Color;
import java.awt.image.BufferedImage;
import msi.gama.jogl.utils.*;
import msi.gama.metamodel.agent.IAgent;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.runtime.*;
import msi.gama.runtime.GAMA.InScope;

public class ImageObject extends AbstractObject {

	final public BufferedImage image;
	final public IAgent agent;
	final public int layerId;
	final public GamaPoint location, dimensions;
	final public Double angle;
	final public boolean isDynamic;
	final public String name;

	public ImageObject(final BufferedImage image, final IAgent agent, final int layerId, final GamaPoint location,
		final Double alpha, final GamaPoint dimensions, final Double angle, final boolean isDynamic, final String name) {
		super(null, alpha);
		setZ_fighting_id((double) layerId);
		this.image = image;
		this.agent = agent;
		this.location = location;
		this.dimensions = dimensions;
		this.angle = angle;
		this.isDynamic = isDynamic;
		this.layerId = layerId;
		this.name = name;
	}

	@Override
	public void unpick() {
		picked = false;
	}

	public void pick() {
		picked = true;
	}

	@Override
	public Color getColor() {
		if ( picked ) { return pickedColor; }
		return super.getColor();
	}

	@Override
	public void draw(final ObjectDrawer drawer, final boolean picking) {
		if ( picking ) {
			JOGLAWTGLRenderer renderer = drawer.renderer;
			renderer.gl.glPushMatrix();
			renderer.gl.glLoadName(pickingIndex);
			if ( renderer.pickedObjectIndex == pickingIndex ) {
				if ( agent != null ) {
					renderer.setPicking(false);
					pick();
					renderer.currentPickedObject = this;
					// The picked image is the grid
					if ( this.name != null ) {
						final GamaPoint pickedPoint =
							GLUtil.getIntWorldPointFromWindowPoint(renderer,
								renderer.camera.getLastMousePressedPosition());
						IAgent ag = GAMA.run(new InScope<IAgent>() {

							@Override
							public IAgent run(final IScope scope) {
								return agent.getPopulationFor(name).getAgent(scope,
									new GamaPoint(pickedPoint.x, -pickedPoint.y));
							}
						});
						renderer.displaySurface.selectAgents(ag);
					} else {
						renderer.displaySurface.selectAgents(agent);
					}
				}
			}
			super.draw(drawer, picking);
			renderer.gl.glPopMatrix();
		} else {
			super.draw(drawer, picking);
		}
	}

	/**
	 * Method computeTexture()
	 * @see msi.gama.jogl.scene.AbstractObject#computeTexture(msi.gama.jogl.utils.JOGLAWTGLRenderer)
	 */
	@Override
	protected MyTexture computeTexture(final JOGLAWTGLRenderer renderer) {
		if ( image == null ) { return null; }
		return renderer.getScene().createTexture(image, isDynamic);
	}

}
