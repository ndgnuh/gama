/*
 * Copyright (c) 2016 Vivid Solutions.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 and Eclipse Distribution License v. 1.0 which accompanies this distribution. The Eclipse Public
 * License is available at http://www.eclipse.org/legal/epl-v10.html and the Eclipse Distribution License is available
 * at
 *
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.locationtech.jts.awt;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.locationtech.jts.geom.Geometry;

/**
 * A {@link Shape} which contains a heterogeneous collection of other shapes representing JTS {@link Geometry}s.
 *
 * @author Martin Davis
 *
 */
public class GeometryCollectionShape implements Shape {
	private final ArrayList<Shape> shapes = new ArrayList<>();

	public GeometryCollectionShape() {}

	public void add(final Shape shape) {
		shapes.add(shape);
	}

	@Override
	public Rectangle getBounds() {
		/** @todo Implement this java.awt.Shape method */
		throw new java.lang.UnsupportedOperationException("Method getBounds() not yet implemented.");
	}

	@Override
	public Rectangle2D getBounds2D() {
		Rectangle2D rectangle = null;

		for (final Object element : shapes) {
			final Shape shape = (Shape) element;

			if (rectangle == null) {
				rectangle = shape.getBounds2D();
			} else {
				rectangle.add(shape.getBounds2D());
			}
		}

		return rectangle;
	}

	@Override
	public boolean contains(final double x, final double y) {
		/** @todo Implement this java.awt.Shape method */
		throw new java.lang.UnsupportedOperationException("Method contains() not yet implemented.");
	}

	@Override
	public boolean contains(final Point2D p) {
		/** @todo Implement this java.awt.Shape method */
		throw new java.lang.UnsupportedOperationException("Method contains() not yet implemented.");
	}

	@Override
	public boolean intersects(final double x, final double y, final double w, final double h) {
		/** @todo Implement this java.awt.Shape method */
		throw new java.lang.UnsupportedOperationException("Method intersects() not yet implemented.");
	}

	@Override
	public boolean intersects(final Rectangle2D r) {
		/** @todo Implement this java.awt.Shape method */
		throw new java.lang.UnsupportedOperationException("Method intersects() not yet implemented.");
	}

	@Override
	public boolean contains(final double x, final double y, final double w, final double h) {
		/** @todo Implement this java.awt.Shape method */
		throw new java.lang.UnsupportedOperationException("Method contains() not yet implemented.");
	}

	@Override
	public boolean contains(final Rectangle2D r) {
		/** @todo Implement this java.awt.Shape method */
		throw new java.lang.UnsupportedOperationException("Method contains() not yet implemented.");
	}

	@Override
	public PathIterator getPathIterator(final AffineTransform at) {
		return new ShapeCollectionPathIterator(shapes, at);
	}

	@Override
	public PathIterator getPathIterator(final AffineTransform at, final double flatness) {
		// since Geometry is linear, can simply delegate to the simple method
		return getPathIterator(at);
	}
}
