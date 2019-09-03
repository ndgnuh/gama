package org.locationtech.jts.geom;

import java.util.HashMap;
import java.util.Map;

public enum ShapeType {
	BOX("3D"),
	CIRCLE("3D"),
	CONE("3D"),
	CUBE("3D"),
	SQUARE("3D"),
	ROUNDED(""),
	CYLINDER("3D"),
	GRIDLINE(""),
	LINEARRING("LinearRing"),
	LINESTRING("LineString"),
	MULTILINESTRING("MultiLineString"),
	MULTIPOINT("MultiPoint"),
	MULTIPOLYGON("MultiPolygon"),
	NULL(""),
	PLAN("3D"),
	POINT("Point"),
	POLYGON("Polygon"),
	POLYHEDRON("3D"),
	POLYPLAN("3D"),
	PYRAMID("3D"),
	SPHERE("3D"),
	TEAPOT("3D"),
	LINECYLINDER("3D"),
	THREED_FILE("");

	public static final Map<String, ShapeType> JTS_TYPES = new HashMap<>();

	static {
		JTS_TYPES.put("Point", POINT);
		JTS_TYPES.put("MultiPoint", MULTIPOINT);
		JTS_TYPES.put("MultiPolygon", MULTIPOLYGON);
		JTS_TYPES.put("LinearRing", LINEARRING);
		JTS_TYPES.put("LineString", LINESTRING);
		JTS_TYPES.put("MultiLineString", MULTILINESTRING);
		JTS_TYPES.put("Polygon", POLYGON);
	}

	public final boolean is3D;

	ShapeType(final String name) {
		is3D = name.equals("3D");
	}
}