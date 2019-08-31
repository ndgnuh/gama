/*******************************************************************************************************
 *
 * ummisco.gama.display.opengl.renderer.caches.GeometryCache.java, in plugin ummisco.gama.display.opengl, is part of the
 * source code of the GAMA modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package ummisco.gama.display.opengl.renderer.caches;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static java.util.concurrent.TimeUnit.MINUTES;
import static msi.gama.common.geometry.GeometryUtils.getTypeOf;
import static org.locationtech.jts.geom.ShapeType.CIRCLE;
import static org.locationtech.jts.geom.ShapeType.CONE;
import static org.locationtech.jts.geom.ShapeType.CUBE;
import static org.locationtech.jts.geom.ShapeType.CYLINDER;
import static org.locationtech.jts.geom.ShapeType.POINT;
import static org.locationtech.jts.geom.ShapeType.PYRAMID;
import static org.locationtech.jts.geom.ShapeType.SPHERE;
import static org.locationtech.jts.geom.ShapeType.SQUARE;

import java.io.File;
import java.nio.DoubleBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFilter;
import org.locationtech.jts.geom.ShapeType;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import msi.gama.common.geometry.Envelope3D;
import msi.gama.common.geometry.ICoordinates;
import msi.gama.common.preferences.GamaPreferences;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.metamodel.shape.IShape;
import msi.gama.runtime.GAMA;
import msi.gama.runtime.IScope;
import msi.gama.util.file.IGamaFile;
import ummisco.gama.display.opengl.OpenGL;
import ummisco.gama.display.opengl.renderer.IOpenGLRenderer;
import ummisco.gama.file.GamaObjFile;

public class GeometryCache {

	private static final double PI_2 = 2f * Math.PI;

	static double roundRect[] = { .92, 0, .933892, .001215, .947362, .004825, .96, .010718, .971423, .018716, .981284,
			.028577, .989282, .04, .995175, .052638, .998785, .066108, 1, .08, 1, .92, .998785, .933892, .995175,
			.947362, .989282, .96, .981284, .971423, .971423, .981284, .96, .989282, .947362, .995175, .933892, .998785,
			.92, 1, .08, 1, .066108, .998785, .052638, .995175, .04, .989282, .028577, .981284, .018716, .971423,
			.010718, .96, .004825, .947362, .001215, .933892, 0, .92, 0, .08, .001215, .066108, .004825, .052638,
			.010718, .04, .018716, .028577, .028577, .018716, .04, .010718, .052638, .004825, .066108, .001215, .08,
			0 };

	static DoubleBuffer db = Buffers.newDirectDoubleBuffer(roundRect.length).put(roundRect).rewind();

	public static class BuiltInGeometry {
		Integer bottom, top, faces;

		public static BuiltInGeometry assemble() {
			return new BuiltInGeometry(null, null, null);
		}

		public BuiltInGeometry top(final Integer top) {
			this.top = top;
			return this;
		}

		public BuiltInGeometry faces(final Integer faces) {
			this.faces = faces;
			return this;
		}

		public BuiltInGeometry bottom(final Integer bottom) {
			this.bottom = bottom;
			return this;
		}

		private BuiltInGeometry(final Integer bottom, final Integer top, final Integer faces) {
			super();
			this.bottom = bottom;
			this.top = top;
			this.faces = faces;
		}

		public void draw(final OpenGL gl) {
			if (bottom != null) {
				gl.drawList(bottom);
			}
			if (top != null) {
				gl.drawList(top);
			}
			gl.enableAlternateTexture();
			if (faces != null) {
				gl.drawList(faces);
			}
		}
	}

	private final Cache<ShapeType, BuiltInGeometry> builtInCache;
	private final LoadingCache<String, Integer> fileCache;
	private final Map<String, IGamaFile.Geom> fileMap = new ConcurrentHashMap<>();
	private final Map<String, IGamaFile.Geom> geometriesToProcess = new ConcurrentHashMap<>();
	private final Cache<String, Envelope3D> envelopes;
	private final IScope scope;
	private final Consumer<Geometry> drawer;

	public GeometryCache(final IOpenGLRenderer renderer) {
		this.scope = renderer.getSurface().getScope().copy("in opengl geometry cache");
		this.drawer = g -> renderer.getOpenGLHelper().getGeometryDrawer().drawGeometry(g, true, null, 0, getTypeOf(g));
		envelopes = newBuilder().expireAfterAccess(10, MINUTES).build();
		builtInCache = newBuilder().concurrencyLevel(2).initialCapacity(10).build();
		fileCache = newBuilder().expireAfterAccess(10, MINUTES).initialCapacity(10).removalListener((notif) -> {
			if (renderer.isDisposed())
				return;
			renderer.getOpenGLHelper().getGL().glDeleteLists((Integer) notif.getValue(), 1);

		}).build(new CacheLoader<String, Integer>() {

			@Override
			public Integer load(final String file) {
				return buildList(renderer.getOpenGLHelper(), file);
			}
		});
	}

	public Integer get(final IGamaFile.Geom file) {
		return fileCache.getUnchecked(file.getPath(scope));
	}

	public BuiltInGeometry get(final ShapeType id) {
		final BuiltInGeometry index = builtInCache.getIfPresent(id);
		return index;
	}

	Integer buildList(final OpenGL gl, final String name) {
		// DEBUG.OUT("Bulding OpenGL list for " + name);
		final IGamaFile.Geom file = fileMap.get(name);
		// We generate the list first

		final Integer index = gl.compileAsList(() -> {
			// We draw the file in the list
			if (file instanceof GamaObjFile) {
				final GamaObjFile f = (GamaObjFile) file;
				f.loadObject(scope, true);
				drawObjFile(f, gl);
			} else {
				final IShape shape = file.getGeometry(scope);
				if (shape == null)
					return;
				try {
					drawSimpleGeometry(gl, shape.getInnerGeometry());
				} catch (final ExecutionException e) {
					e.printStackTrace();
				}
			}
		});

		return index;
	}

	void drawSimpleGeometry(final OpenGL gl, final Geometry geom) throws ExecutionException {
		geom.apply((GeometryFilter) (g) -> drawer.accept(g));
	}

	public void dispose() {
		fileMap.clear();
		GAMA.releaseScope(scope);
	}

	public void processUnloaded() {
		for (final IGamaFile.Geom object : geometriesToProcess.values()) {
			get(object);
		}
		geometriesToProcess.clear();
	}

	public void process(final IGamaFile.Geom file) {
		if (file == null)
			return;
		final String path = file.getPath(scope);
		if (fileCache.getIfPresent(path) != null)
			return;
		fileMap.putIfAbsent(path, file);
		if (!geometriesToProcess.containsKey(path)) {
			geometriesToProcess.put(path, file);
		}
	}

	public Envelope3D getEnvelope(final IGamaFile.Geom file) {
		try {
			return envelopes.get(file.getPath(scope), () -> file.computeEnvelope(scope));
		} catch (final ExecutionException e) {
			return Envelope3D.EMPTY;
		}
	}

	public void put(final ShapeType key, final BuiltInGeometry value) {
		builtInCache.put(key, value);
	}

	public void initialize(final OpenGL gl) {

		final int slices = GamaPreferences.Displays.DISPLAY_SLICE_NUMBER.getValue();
		final int stacks = slices;
		put(SPHERE, BuiltInGeometry.assemble().faces(gl.compileAsList(() -> {
			gl.translateBy(0d, 0d, 1d);
			drawSphere(gl, 1.0, slices, stacks);
			gl.translateBy(0, 0, -1d);
		})));
		put(CYLINDER, BuiltInGeometry.assemble().bottom(gl.compileAsList(() -> {
			drawDisk(gl, 0d, 1d, slices, slices / 3);
		})).top(gl.compileAsList(() -> {
			gl.translateBy(0d, 0d, 1d);
			drawDisk(gl, 0d, 1d, slices, slices / 3);
			gl.translateBy(0d, 0d, -1d);
		})).faces(gl.compileAsList(() -> {
			drawCylinder(gl, 1.0d, 1.0d, 1.0d, slices, stacks);
		})));
		put(CONE, BuiltInGeometry.assemble().bottom(gl.compileAsList(() -> {
			drawDisk(gl, 0d, 1d, slices, slices / 3);
		})).faces(gl.compileAsList(() -> {
			drawCylinder(gl, 1.0, 0.0, 1.0, slices, stacks);
		})));
		final ICoordinates baseVertices = ICoordinates.ofLength(5);
		final ICoordinates faceVertices = ICoordinates.ofLength(5);
		baseVertices.setTo(-0.5, 0.5, 0, 0.5, 0.5, 0, 0.5, -0.5, 0, -0.5, -0.5, 0, -0.5, 0.5, 0);

		put(CUBE, BuiltInGeometry.assemble().bottom(gl.compileAsList(() -> {
			gl.drawSimpleShape(baseVertices, 4, true, false, true, null);
		})).top(gl.compileAsList(() -> {
			baseVertices.translateBy(0, 0, 1);
			gl.drawSimpleShape(baseVertices, 4, true, true, true, null);
			baseVertices.translateBy(0, 0, -1);
		})).faces(gl.compileAsList(() -> {
			baseVertices.visit((pj, pk) -> {
				faceVertices.setTo(pk.x, pk.y, pk.z, pk.x, pk.y, pk.z + 1, pj.x, pj.y, pj.z + 1, pj.x, pj.y, pj.z, pk.x,
						pk.y, pk.z);
				gl.drawSimpleShape(faceVertices, 4, true, true, true, null);
			});
		})));
		put(POINT, BuiltInGeometry.assemble().faces(gl.compileAsList(() -> {
			drawSphere(gl, 1.0, 5, 5);
		})));

		put(ShapeType.ROUNDED, BuiltInGeometry.assemble().bottom(gl.compileAsList(() -> {
			drawRoundedRectangle(gl.getGL());
		})));
		put(SQUARE, BuiltInGeometry.assemble().bottom(gl.compileAsList(() -> {
			gl.drawSimpleShape(baseVertices, 4, true, true, true, null);
		})));
		put(CIRCLE, BuiltInGeometry.assemble().bottom(gl.compileAsList(() -> {
			drawDisk(gl, 0.0, 1.0, slices, 1);
		})));
		final ICoordinates triangleVertices = ICoordinates.ofLength(4);
		final ICoordinates vertices = ICoordinates.ofLength(5);
		vertices.setTo(-0.5, -0.5, 0, -0.5, 0.5, 0, 0.5, 0.5, 0, 0.5, -0.5, 0, -0.5, -0.5, 0);
		put(PYRAMID, BuiltInGeometry.assemble().bottom(gl.compileAsList(() -> {
			gl.drawSimpleShape(vertices, 4, true, false, true, null);
		})).faces(gl.compileAsList(() -> {
			final GamaPoint top = new GamaPoint(0, 0, 1);
			vertices.visit((pj, pk) -> {
				triangleVertices.setTo(pj.x, pj.y, pj.z, top.x, top.y, top.z, pk.x, pk.y, pk.z, pj.x, pj.y, pj.z);
				gl.drawSimpleShape(triangleVertices, 3, true, true, true, null);

			});
		})));

	}

	public void drawRoundedRectangle(final GL2 gl) {
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL2.GL_DOUBLE, 0, db);
		gl.glDrawArrays(GL2.GL_TRIANGLE_FAN, 0, 40);
		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
	}

	public void drawDisk(final OpenGL gl, final double inner, final double outer, final int slices, final int loops) {
		double da, dr;
		/* Normal vectors */
		gl.outputNormal(0.0, 0.0, +1.0);
		da = PI_2 / slices;
		dr = (outer - inner) / loops;

		final double dtc = 2.0f * outer;
		double sa, ca;
		double r1 = inner;
		int l;
		gl.getGL().glFrontFace(GL.GL_CCW);
		for (l = 0; l < loops; l++) {
			final double r2 = r1 + dr;
			int s;
			gl.beginDrawing(GL2.GL_QUAD_STRIP);
			for (s = 0; s <= slices; s++) {
				double a;
				if (s == slices) {
					a = 0.0f;
				} else {
					a = s * da;
				}
				sa = Math.sin(a);
				ca = Math.cos(a);
				gl.outputTexCoord(0.5f + sa * r2 / dtc, 0.5f + ca * r2 / dtc);
				gl.outputVertex(r2 * sa, r2 * ca, 0);
				gl.outputTexCoord(0.5f + sa * r1 / dtc, 0.5f + ca * r1 / dtc);
				gl.outputVertex(r1 * sa, r1 * ca, 0);
			}
			gl.endDrawing();
			r1 = r2;
		}
		gl.getGL().glFrontFace(GL.GL_CW);
	}

	public void drawSphere(final OpenGL gl, final double radius, final int slices, final int stacks) {
		double rho, drho, theta, dtheta;
		double x, y, z;
		double s, t, ds, dt;
		int i, j, imin, imax;
		drho = Math.PI / stacks;
		dtheta = PI_2 / slices;
		ds = 1.0f / slices;
		dt = 1.0f / stacks;
		t = 1.0f; // because loop now runs from 0
		imin = 0;
		imax = stacks;
		gl.getGL().glFrontFace(GL.GL_CCW);
		// draw intermediate stacks as quad strips
		for (i = imin; i < imax; i++) {
			rho = i * drho;
			gl.beginDrawing(GL2.GL_QUAD_STRIP);
			s = 0.0f;
			for (j = 0; j <= slices; j++) {
				theta = j == slices ? 0.0f : j * dtheta;
				x = -Math.sin(theta) * Math.sin(rho);
				y = Math.cos(theta) * Math.sin(rho);
				z = Math.cos(rho);
				gl.outputNormal(x, y, z);
				gl.outputTexCoord(s, t);
				gl.outputVertex(x * radius, y * radius, z * radius);
				x = -Math.sin(theta) * Math.sin(rho + drho);
				y = Math.cos(theta) * Math.sin(rho + drho);
				z = Math.cos(rho + drho);
				gl.outputNormal(x, y, z);
				gl.outputTexCoord(s, t - dt);
				s += ds;
				gl.outputVertex(x * radius, y * radius, z * radius);
			}
			gl.endDrawing();
			t -= dt;
		}
		gl.getGL().glFrontFace(GL.GL_CW);
	}

	public void drawCylinder(final OpenGL gl, final double base, final double top, final double height,
			final int slices, final int stacks) {

		double da, r, dr, dz;
		double x, y, z, nz;
		int i, j;
		da = PI_2 / slices;
		dr = (top - base) / stacks;
		dz = height / stacks;
		nz = (base - top) / height;

		final double ds = 1.0f / slices;
		final double dt = 1.0f / stacks;
		float t = 0.0f;
		z = 0.0f;
		r = base;
		gl.getGL().glFrontFace(GL.GL_CCW);
		for (j = 0; j < stacks; j++) {
			float s = 0.0f;
			gl.beginDrawing(GL2.GL_QUAD_STRIP);
			for (i = 0; i <= slices; i++) {
				if (i == slices) {
					x = Math.sin(0.0f);
					y = Math.cos(0.0f);
				} else {
					x = Math.sin(i * da);
					y = Math.cos(i * da);
				}
				gl.outputNormal(x, y, nz);
				gl.outputTexCoord(s, t);
				gl.outputVertex(x * r, y * r, z);
				gl.outputNormal(x, y, nz);
				gl.outputTexCoord(s, t + dt);
				gl.outputVertex(x * (r + dr), y * (r + dr), z + dz);

				s += ds;
			} // for slices
			gl.endDrawing();
			r += dr;
			t += dt;
			z += dz;
		} // for stacks
		gl.getGL().glFrontFace(GL.GL_CW);
	}

	public static void drawObjFile(final GamaObjFile file, final OpenGL gl) {
		int nextmat = -1;
		int matcount = 0;
		final int totalmats = file.matTimings.size();
		String[] nextmatnamearray = null;
		String nextmatname = null;

		if (totalmats > 0 && file.materials != null) {
			nextmatnamearray = file.matTimings.get(matcount);
			nextmatname = nextmatnamearray[0];
			nextmat = Integer.parseInt(nextmatnamearray[1]);
		}
		Texture texture = null;
		final GamaPoint tex = new GamaPoint();
		final GamaPoint normal = new GamaPoint();
		final GamaPoint vertex = new GamaPoint();
		for (int i = 0; i < file.faces.size(); i++) {
			if (i == nextmat) {
				if (texture != null) {
					texture.destroy(gl.getGL());
					texture = null;
				}
				// gl.getGL().glEnable(GL2.GL_COLOR_MATERIAL);
				gl.setCurrentColor(file.materials.getKd(nextmatname)[0], file.materials.getKd(nextmatname)[1],
						file.materials.getKd(nextmatname)[2], file.materials.getd(nextmatname));

				final String mapKa = file.materials.getMapKa(nextmatname);
				final String mapKd = file.materials.getMapKd(nextmatname);
				final String mapd = file.materials.getMapd(nextmatname);
				if (mapKa != null || mapKd != null || mapd != null) {
					File f = new File(file.mtlPath);
					String path = f.getAbsolutePath().replace(f.getName(), "");
					if (mapd != null) {
						path += mapd;
					} else if (mapKa != null) {
						path += mapKa;
					} else if (mapKd != null) {
						path += mapKd;
					}
					f = new File(path);
					if (f.exists()) {
						// Solves Issue #1951. Asynchronous loading of textures
						// was not possible when displaying the file
						texture = gl.getTexture(f, false, true);
						gl.setCurrentTextures(texture.getTextureObject(), texture.getTextureObject());
						texture.setTexParameteri(gl.getGL(), GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
						texture.setTexParameteri(gl.getGL(), GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
					}

				}
				matcount++;
				if (matcount < totalmats) {
					nextmatnamearray = file.matTimings.get(matcount);
					nextmatname = nextmatnamearray[0];
					nextmat = Integer.parseInt(nextmatnamearray[1]);
				}
			}

			final int[] tempfaces = file.faces.get(i);
			final int[] norms = file.facesNorms.get(i);
			final int[] texs = file.facesTexs.get(i);

			//// Quad Begin Header ////
			final int polytype =
					tempfaces.length == 3 ? GL.GL_TRIANGLES : tempfaces.length == 4 ? GL2.GL_QUADS : GL2.GL_POLYGON;
			gl.beginDrawing(polytype);
			////////////////////////////

			boolean hasNormals = true;
			for (int w = 0; w < tempfaces.length; w++) {
				if (norms[w] == 0) {
					hasNormals = false;
					break;
				}
			}

			final double[] arrayOfVertices = new double[tempfaces.length * 3];
			for (int w = 0; w < tempfaces.length; w++) {
				final double[] ordinates = file.setOfVertex.get(tempfaces[w] - 1);
				for (int k = 0; k < 3; k++) {
					arrayOfVertices[w * 3 + k] = ordinates[k];
				}
			}
			final ICoordinates coords = ICoordinates.ofLength(tempfaces.length + 1);
			coords.setTo(arrayOfVertices);
			coords.completeRing();

			if (!hasNormals) {
				gl.setNormal(coords, !coords.isClockwise());
			}
			for (int w = 0; w < tempfaces.length; w++) {
				if (tempfaces[w] == 0) {
					continue;
				}
				final boolean hasNormal = norms[w] != 0;
				final boolean hasTex = texs[w] != 0;
				if (hasNormal) {
					final double[] temp_coords = file.setOfVertexNormals.get(norms[w] - 1);
					normal.setLocation(temp_coords[0], temp_coords[1], temp_coords[2]);
				}
				if (hasTex) {
					final double[] ordinates = file.setOfVertexTextures.get(texs[w] - 1);
					tex.setLocation(ordinates[0], ordinates[1], ordinates[2]);
					if (1d >= tex.y && -tex.y <= 0) {
						tex.y = 1d - tex.y;
					} else {
						tex.y = Math.abs(tex.y);
					}
				}
				final double[] temp_coords = file.setOfVertex.get(tempfaces[w] - 1);
				vertex.setLocation(temp_coords[0], temp_coords[1], temp_coords[2]);
				gl.drawVertex(vertex, hasNormal ? normal : null, hasTex ? tex : null);
			}

			//// Quad End Footer /////
			gl.endDrawing();
		}

		if (texture != null) {
			gl.disableTextures();
			texture = null;
		}
		// gl.glEndList();
	}

}
