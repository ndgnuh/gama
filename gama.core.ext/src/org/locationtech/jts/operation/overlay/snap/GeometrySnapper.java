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

package org.locationtech.jts.operation.overlay.snap;

import java.util.Set;
import java.util.TreeSet;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.util.GeometryTransformer;

/**
 * Snaps the vertices and segments of a {@link Geometry} to another Geometry's vertices. A snap distance tolerance is
 * used to control where snapping is performed. Snapping one geometry to another can improve robustness for overlay
 * operations by eliminating nearly-coincident edges (which cause problems during noding and intersection calculation).
 * It can also be used to eliminate artifacts such as narrow slivers, spikes and gores.
 * <p>
 * Too much snapping can result in invalid topology being created, so the number and location of snapped vertices is
 * decided using heuristics to determine when it is safe to snap. This can result in some potential snaps being omitted,
 * however.
 *
 * @author Martin Davis
 * @version 1.7
 */
public class GeometrySnapper {
	private static final double SNAP_PRECISION_FACTOR = 1e-9;

	/**
	 * Estimates the snap tolerance for a Geometry, taking into account its precision model.
	 * 
	 * @param g
	 *            a Geometry
	 * @return the estimated snap tolerance
	 */
	public static double computeOverlaySnapTolerance(final Geometry g) {
		double snapTolerance = computeSizeBasedSnapTolerance(g);

		/**
		 * Overlay is carried out in the precision model of the two inputs. If this precision model is of type FIXED,
		 * then the snap tolerance must reflect the precision grid size. Specifically, the snap tolerance should be at
		 * least the distance from a corner of a precision grid cell to the centre point of the cell.
		 */
		final PrecisionModel pm = g.getPrecisionModel();
		if (pm.getType() == PrecisionModel.FIXED) {
			final double fixedSnapTol = 1 / pm.getScale() * 2 / 1.415;
			if (fixedSnapTol > snapTolerance) {
				snapTolerance = fixedSnapTol;
			}
		}
		return snapTolerance;
	}

	public static double computeSizeBasedSnapTolerance(final Geometry g) {
		final Envelope env = g.getEnvelopeInternal();
		final double minDimension = Math.min(env.getHeight(), env.getWidth());
		final double snapTol = minDimension * SNAP_PRECISION_FACTOR;
		return snapTol;
	}

	public static double computeOverlaySnapTolerance(final Geometry g0, final Geometry g1) {
		return Math.min(computeOverlaySnapTolerance(g0), computeOverlaySnapTolerance(g1));
	}

	/**
	 * Snaps two geometries together with a given tolerance.
	 * 
	 * @param g0
	 *            a geometry to snap
	 * @param g1
	 *            a geometry to snap
	 * @param snapTolerance
	 *            the tolerance to use
	 * @return the snapped geometries
	 */
	public static Geometry[] snap(final Geometry g0, final Geometry g1, final double snapTolerance) {
		final Geometry[] snapGeom = new Geometry[2];
		final GeometrySnapper snapper0 = new GeometrySnapper(g0);
		snapGeom[0] = snapper0.snapTo(g1, snapTolerance);

		/**
		 * Snap the second geometry to the snapped first geometry (this strategy minimizes the number of possible
		 * different points in the result)
		 */
		final GeometrySnapper snapper1 = new GeometrySnapper(g1);
		snapGeom[1] = snapper1.snapTo(snapGeom[0], snapTolerance);

		// System.out.println(snap[0]);
		// System.out.println(snap[1]);
		return snapGeom;
	}

	/**
	 * Snaps a geometry to itself. Allows optionally cleaning the result to ensure it is topologically valid (which
	 * fixes issues such as topology collapses in polygonal inputs).
	 * <p>
	 * Snapping a geometry to itself can remove artifacts such as very narrow slivers, gores and spikes.
	 *
	 * @param geom
	 *            the geometry to snap
	 * @param snapTolerance
	 *            the snapping tolerance
	 * @param cleanResult
	 *            whether the result should be made valid
	 * @return a new snapped Geometry
	 */
	public static Geometry snapToSelf(final Geometry geom, final double snapTolerance, final boolean cleanResult) {
		final GeometrySnapper snapper0 = new GeometrySnapper(geom);
		return snapper0.snapToSelf(snapTolerance, cleanResult);
	}

	private final Geometry srcGeom;

	/**
	 * Creates a new snapper acting on the given geometry
	 * 
	 * @param srcGeom
	 *            the geometry to snap
	 */
	public GeometrySnapper(final Geometry srcGeom) {
		this.srcGeom = srcGeom;
	}

	/**
	 * Snaps the vertices in the component {@link LineString}s of the source geometry to the vertices of the given snap
	 * geometry.
	 *
	 * @param snapGeom
	 *            a geometry to snap the source to
	 * @return a new snapped Geometry
	 */
	public Geometry snapTo(final Geometry snapGeom, final double snapTolerance) {
		final Coordinate[] snapPts = extractTargetCoordinates(snapGeom);

		final SnapTransformer snapTrans = new SnapTransformer(snapTolerance, snapPts);
		return snapTrans.transform(srcGeom);
	}

	/**
	 * Snaps the vertices in the component {@link LineString}s of the source geometry to the vertices of the same
	 * geometry. Allows optionally cleaning the result to ensure it is topologically valid (which fixes issues such as
	 * topology collapses in polygonal inputs).
	 *
	 * @param snapTolerance
	 *            the snapping tolerance
	 * @param cleanResult
	 *            whether the result should be made valid
	 * @return a new snapped Geometry
	 */
	public Geometry snapToSelf(final double snapTolerance, final boolean cleanResult) {
		final Coordinate[] snapPts = extractTargetCoordinates(srcGeom);

		final SnapTransformer snapTrans = new SnapTransformer(snapTolerance, snapPts, true);
		final Geometry snappedGeom = snapTrans.transform(srcGeom);
		Geometry result = snappedGeom;
		if (cleanResult && result instanceof Polygonal) {
			// TODO: use better cleaning approach
			result = snappedGeom.buffer(0);
		}
		return result;
	}

	private Coordinate[] extractTargetCoordinates(final Geometry g) {
		// TODO: should do this more efficiently. Use CoordSeq filter to get points, KDTree for uniqueness & queries
		final Set<Coordinate> ptSet = new TreeSet<>();
		final Coordinate[] pts = g.getCoordinates();
		for (final Coordinate pt : pts) {
			ptSet.add(pt);
		}
		return ptSet.toArray(new Coordinate[0]);
	}

	/**
	 * Computes the snap tolerance based on the input geometries.
	 *
	 * @param ringPts
	 * @return
	 */
	private double computeSnapTolerance(final Coordinate[] ringPts) {
		final double minSegLen = computeMinimumSegmentLength(ringPts);
		// use a small percentage of this to be safe
		final double snapTol = minSegLen / 10;
		return snapTol;
	}

	private double computeMinimumSegmentLength(final Coordinate[] pts) {
		double minSegLen = Double.MAX_VALUE;
		for (int i = 0; i < pts.length - 1; i++) {
			final double segLen = pts[i].distance(pts[i + 1]);
			if (segLen < minSegLen) {
				minSegLen = segLen;
			}
		}
		return minSegLen;
	}

}

class SnapTransformer extends GeometryTransformer {
	private final double snapTolerance;
	private final Coordinate[] snapPts;
	private boolean isSelfSnap = false;

	SnapTransformer(final double snapTolerance, final Coordinate[] snapPts) {
		this.snapTolerance = snapTolerance;
		this.snapPts = snapPts;
	}

	SnapTransformer(final double snapTolerance, final Coordinate[] snapPts, final boolean isSelfSnap) {
		this.snapTolerance = snapTolerance;
		this.snapPts = snapPts;
		this.isSelfSnap = isSelfSnap;
	}

	@Override
	protected CoordinateSequence transformCoordinates(final CoordinateSequence coords, final Geometry parent) {
		final Coordinate[] srcPts = coords.toCoordinateArray();
		final Coordinate[] newPts = snapLine(srcPts, snapPts);
		return factory.getCoordinateSequenceFactory().create(newPts);
	}

	private Coordinate[] snapLine(final Coordinate[] srcPts, final Coordinate[] snapPts) {
		final LineStringSnapper snapper = new LineStringSnapper(srcPts, snapTolerance);
		snapper.setAllowSnappingToSourceVertices(isSelfSnap);
		return snapper.snapTo(snapPts);
	}
}
