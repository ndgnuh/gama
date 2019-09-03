/*********************************************************************************************
 *
 * 'GamaObjFile.java, in plugin ummisco.gama.display.opengl, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.extensions.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import gama.dev.utils.DEBUG;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.example;
import gama.processor.annotations.GamlAnnotations.file;
import gama.common.geometry.Envelope3D;
import gama.common.geometry.ICoordinates;
import gama.common.util.FileUtils;
import gama.metamodel.shape.GamaPoint;
import gama.metamodel.shape.IShape;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.GamaPair;
import gama.util.list.GamaListFactory;
import gama.util.list.IList;
import gaml.types.GamaGeometryType;
import gaml.types.IType;
import gaml.types.Types;

/**
 * Class GamaObjFile.
 *
 * @author drogoul
 * @since 30 déc. 2013
 *
 */
@file (
		name = "obj",
		extensions = { "obj", "OBJ" },
		buffer_type = IType.LIST,
		buffer_content = IType.GEOMETRY,
		doc = @doc ("'.obj' files are files containing 3D geometries. The internal representation is a list of one geometry"))
public class GamaObjFile extends Gama3DGeometryFile {

	public final ArrayList<double[]> setOfVertex = new ArrayList<>();
	public final ArrayList<double[]> setOfVertexNormals = new ArrayList<>();
	public final ArrayList<double[]> setOfVertexTextures = new ArrayList<>();
	public final ArrayList<int[]> faces = new ArrayList<>();
	public final ArrayList<int[]> facesTexs = new ArrayList<>();
	public final ArrayList<int[]> facesNorms = new ArrayList<>();
	public final ArrayList<String[]> matTimings = new ArrayList<>();
	public MtlLoader materials;
	public double toppoint = 0f;
	public double bottompoint = 0f;
	public double leftpoint = 0f;
	public double rightpoint = 0f;
	public double farpoint = 0f;
	public double nearpoint = 0f;
	public final String mtlPath;
	public boolean loaded = false;

	/**
	 * @param scope
	 * @param pathName
	 * @throws GamaRuntimeException
	 */
	@doc (
			value = "This file constructor allows to read an obj file. The associated mlt file have to have the same name as the file to be read.",
			examples = { @example (
					value = "file f <- obj_file(\"file.obj\");",
					isExecutable = false) })

	public GamaObjFile(final IScope scope, final String pathName) throws GamaRuntimeException {
		this(scope, pathName, (GamaPair<Double, GamaPoint>) null);
	}

	@doc (
			value = "This file constructor allows to read an obj file and apply an init rotation to it. The rotation"
					+ "is a pair angle::rotation vector. The associated mlt file have to have the same name as the file to be read.",
			examples = { @example (
					value = "file f <- obj_file(\"file.obj\", 90.0::{-1,0,0});",
					isExecutable = false) })

	public GamaObjFile(final IScope scope, final String pathName, final GamaPair<Double, GamaPoint> initRotation)
			throws GamaRuntimeException {
		this(scope, pathName, pathName.replace(".obj", ".mtl"), initRotation);
	}

	@doc (
			value = "This file constructor allows to read an obj file, using a specific mlt file",
			examples = { @example (
					value = "file f <- obj_file(\"file.obj\",\"file.mlt\");",
					isExecutable = false) })
	public GamaObjFile(final IScope scope, final String pathName, final String mtlPath) {
		this(scope, pathName, mtlPath, null);
	}

	@doc (
			value = "This file constructor allows to read an obj file, using a specific mlt file, and apply an init rotation to it. The rotation"
					+ "is a pair angle::rotation vector",
			examples = { @example (
					value = "file f <- obj_file(\"file.obj\",\"file.mlt\", 90.0::{-1,0,0});",
					isExecutable = false) })

	public GamaObjFile(final IScope scope, final String pathName, final String mtlPath,
			final GamaPair<Double, GamaPoint> initRotation) {
		super(scope, pathName, initRotation);
		if (mtlPath != null) {
			this.mtlPath = FileUtils.constructAbsoluteFilePath(scope, mtlPath, false);
		} else {
			this.mtlPath = null;
		}

	}

	private void centerit() {
		final double xshift = (rightpoint - leftpoint) / 2.0d;
		final double yshift = (toppoint - bottompoint) / 2.0d;
		final double zshift = (nearpoint - farpoint) / 2.0d;
		for (int i = 0; i < setOfVertex.size(); i++) {
			final double coords[] = new double[4];
			coords[0] = setOfVertex.get(i)[0] - leftpoint - xshift;
			coords[1] = setOfVertex.get(i)[1] - bottompoint - yshift;
			coords[2] = setOfVertex.get(i)[2] - farpoint - zshift;
			setOfVertex.set(i, coords);
		}

	}

	public void loadObject(final IScope scope, final boolean forDrawing) {

		try (BufferedReader br = new BufferedReader(new FileReader(getFile(scope)))) {
			loadObject(br);
		} catch (final IOException e) {
			DEBUG.ERR("Failed to read file: " /* + br.toString() */);
		} catch (final NumberFormatException e) {
			DEBUG.ERR("Malformed OBJ file: "/* + br.toString() */ + "\r \r" + e.getMessage());
		}

	}

	public void loadObject(final BufferedReader br) throws IOException {
		if (loaded) { return; }
		int facecounter = 0;
		boolean firstpass = true;
		String newline;
		while ((newline = br.readLine()) != null) {
			if (newline.length() > 0) {
				newline = newline.trim();

				// LOADS VERTEX COORDINATES
				if (newline.startsWith("v ")) {
					newline = newline.substring(2, newline.length());
					final StringTokenizer st = new StringTokenizer(newline, " ");
					final double coords[] = new double[st.countTokens()];
					for (int i = 0; st.hasMoreTokens(); i++) {
						coords[i] = Double.parseDouble(st.nextToken());
					}

					if (firstpass) {
						rightpoint = coords[0];
						leftpoint = coords[0];
						toppoint = coords[1];
						bottompoint = coords[1];
						nearpoint = coords[2];
						farpoint = coords[2];
						firstpass = false;
					}
					if (coords[0] > rightpoint) {
						rightpoint = coords[0];
					}
					if (coords[0] < leftpoint) {
						leftpoint = coords[0];
					}
					if (coords[1] > toppoint) {
						toppoint = coords[1];
					}
					if (coords[1] < bottompoint) {
						bottompoint = coords[1];
					}
					if (coords[2] > nearpoint) {
						nearpoint = coords[2];
					}
					if (coords[2] < farpoint) {
						farpoint = coords[2];
					}
					setOfVertex.add(coords);
				} else

				// LOADS VERTEX TEXTURE COORDINATES
				if (newline.startsWith("vt")) {
					final double coords[] = new double[4];
					// final String coordstext[] = new String[4];
					newline = newline.substring(3, newline.length());
					final StringTokenizer st = new StringTokenizer(newline, " ");
					for (int i = 0; st.hasMoreTokens(); i++) {
						coords[i] = Double.parseDouble(st.nextToken());
					}

					setOfVertexTextures.add(coords);
				} else

				// LOADS VERTEX NORMALS COORDINATES
				if (newline.startsWith("vn")) {
					final double coords[] = new double[4];
					// final String coordstext[] = new String[4];
					newline = newline.substring(3, newline.length());
					final StringTokenizer st = new StringTokenizer(newline, " ");
					for (int i = 0; st.hasMoreTokens(); i++) {
						coords[i] = Double.parseDouble(st.nextToken());
					}

					setOfVertexNormals.add(coords);
				} else

				// LOADS FACES COORDINATES
				if (newline.startsWith("f ")) {
					facecounter++;
					newline = newline.substring(2, newline.length());
					final StringTokenizer st = new StringTokenizer(newline, " ");
					final int count = st.countTokens();
					final int v[] = new int[count];
					final int vt[] = new int[count];
					final int vn[] = new int[count];
					for (int i = 0; i < count; i++) {
						final char chars[] = st.nextToken().toCharArray();
						final StringBuffer sb = new StringBuffer();
						char lc = 'x';
						for (final char c : chars) {
							if (c == '/' && lc == '/') {
								sb.append('0');
							}
							lc = c;
							sb.append(lc);
						}

						final StringTokenizer st2 = new StringTokenizer(sb.toString(), "/");
						final int num = st2.countTokens();
						v[i] = Integer.parseInt(st2.nextToken());
						if (num > 1) {
							vt[i] = Integer.parseInt(st2.nextToken());
						} else {
							vt[i] = 0;
						}
						if (num > 2) {
							vn[i] = Integer.parseInt(st2.nextToken());
						} else {
							vn[i] = 0;
						}
					}

					faces.add(v);
					facesTexs.add(vt);
					facesNorms.add(vn);
				} else

				// LOADS MATERIALS
				if (newline.charAt(0) == 'm' && newline.charAt(1) == 't' && newline.charAt(2) == 'l'
						&& newline.charAt(3) == 'l' && newline.charAt(4) == 'i' && newline.charAt(5) == 'b') {
					// String[] coordstext = new String[3];
					// coordstext = newline.split("\\s+");
					if (mtlPath != null) {
						loadMaterials();
					}
				} else

				// USES MATERIALS
				if (newline.charAt(0) == 'u' && newline.charAt(1) == 's' && newline.charAt(2) == 'e'
						&& newline.charAt(3) == 'm' && newline.charAt(4) == 't' && newline.charAt(5) == 'l') {
					final String[] coords = new String[2];
					final String[] coordstext = newline.split("\\s+");
					coords[0] = coordstext[1];
					coords[1] = facecounter + "";
					matTimings.add(coords);
				}
			}
		}
		centerit();
		// numPolys = faces.size();
		loaded = true;
	}

	/**
	 * Method fillBuffer(). Fills the buffer with the polygons built from the .obj vertices + faces
	 *
	 * @see gama.extensions.files.GamaFile#fillBuffer(msi.gama.runtime.scope.IScope)
	 */
	@Override
	protected void fillBuffer(final IScope scope) throws GamaRuntimeException {
		loadObject(scope, false);
		setBuffer(GamaListFactory.<IShape> create(Types.GEOMETRY));
		final IList<IShape> vertices = GamaListFactory.create(Types.POINT);
		for (final double[] coords : setOfVertex) {
			final GamaPoint pt = new GamaPoint(coords[0], -coords[1], coords[2]);
			vertices.add(pt);
		}
		for (final int[] vertexRefs : faces) {
			final IList<IShape> face = GamaListFactory.<IShape> create(Types.POINT);
			for (final int vertex : vertexRefs) {
				face.add(vertices.get(vertex - 1));
				getBuffer().add(GamaGeometryType.buildPolygon(face));
			}
		}
		envelope = Envelope3D.of(leftpoint, rightpoint, bottompoint, toppoint, nearpoint, farpoint);

	}

	@Override
	public IList<String> getAttributes(final IScope scope) {
		// TODO what to return ?
		return GamaListFactory.create();
	}

	private void loadMaterials() {
		final String refm = mtlPath;
		try (FileReader frm = new FileReader(refm); final BufferedReader brm = new BufferedReader(frm);) {
			materials = new MtlLoader(brm, mtlPath);
		} catch (final IOException e) {
			DEBUG.ERR("Could not open file: " + refm);
			materials = null;
		}
	}

	@SuppressWarnings ({ "rawtypes", "unchecked" })
	public class MtlLoader {

		public ArrayList Materials = new ArrayList<>();

		public class mtl {
			public String name;
			public int mtlnum;
			public float d = 1f;
			public float[] Ka = new float[3];
			public float[] Kd = new float[3];
			public float[] Ks = new float[3];
			public String map_Kd;
			public String map_Ka;
			public String map_d;

		}

		public MtlLoader(final BufferedReader ref, final String pathtoimages) {

			loadobject(ref, pathtoimages);
			cleanup();
		}

		private void cleanup() {}

		public int getSize() {
			return Materials.size();
		}

		public float getd(final String namepass) {
			final float returnfloat = 1f;
			for (int i = 0; i < Materials.size(); i++) {
				final mtl tempmtl = (mtl) Materials.get(i);
				if (tempmtl.name.matches(namepass)) {
					// returnfloat = tempmtl.d;
					return tempmtl.d;
				}
			}
			return returnfloat;
		}

		public float[] getKd(final String namepass) {
			final float[] returnfloat = new float[3];
			for (int i = 0; i < Materials.size(); i++) {
				final mtl tempmtl = (mtl) Materials.get(i);
				if (tempmtl.name.matches(namepass)) {
					// returnfloat = tempmtl.Kd;
					return tempmtl.Kd;
				}
			}
			return returnfloat;
		}

		public String getMapKa(final String namepass) {
			for (int i = 0; i < Materials.size(); i++) {
				final mtl tempmtl = (mtl) Materials.get(i);
				if (tempmtl.name.matches(namepass)) { return tempmtl.map_Ka; }
			}
			return null;
		}

		public String getMapKd(final String namepass) {
			for (int i = 0; i < Materials.size(); i++) {
				final mtl tempmtl = (mtl) Materials.get(i);
				if (tempmtl.name.matches(namepass)) { return tempmtl.map_Kd; }
			}
			return null;
		}

		public String getMapd(final String namepass) {
			for (int i = 0; i < Materials.size(); i++) {
				final mtl tempmtl = (mtl) Materials.get(i);
				if (tempmtl.name.matches(namepass)) { return tempmtl.map_d; }
			}
			return null;
		}

		private void loadobject(final BufferedReader br, final String pathtoimages) {
			int linecounter = 0;
			try {

				String newline;
				boolean firstpass = true;
				mtl matset = new mtl();
				int mtlcounter = 0;

				while ((newline = br.readLine()) != null) {
					linecounter++;
					newline = newline.trim();
					if (newline.length() > 0) {
						if (newline.charAt(0) == 'n' && newline.charAt(1) == 'e' && newline.charAt(2) == 'w') {
							if (firstpass) {
								firstpass = false;
							} else {
								Materials.add(matset);
								matset = new mtl();
							}
							String[] coordstext = new String[2];
							coordstext = newline.split("\\s+");
							matset.name = coordstext[1];
							matset.mtlnum = mtlcounter;
							mtlcounter++;
						} else if (newline.charAt(0) == 'K' && newline.charAt(1) == 'a') {
							final float[] coords = new float[3];
							String[] coordstext = new String[4];
							coordstext = newline.split("\\s+");
							for (int i = 1; i < coordstext.length; i++) {
								coords[i - 1] = Float.valueOf(coordstext[i]).floatValue();
							}
							matset.Ka = coords;
						} else if (newline.charAt(0) == 'K' && newline.charAt(1) == 'd') {
							final float[] coords = new float[3];
							String[] coordstext = new String[4];
							coordstext = newline.split("\\s+");
							for (int i = 1; i < coordstext.length; i++) {
								coords[i - 1] = Float.valueOf(coordstext[i]).floatValue();
							}
							matset.Kd = coords;
						} else if (newline.charAt(0) == 'K' && newline.charAt(1) == 's') {
							final float[] coords = new float[3];
							String[] coordstext = new String[4];
							coordstext = newline.split("\\s+");
							for (int i = 1; i < coordstext.length; i++) {
								coords[i - 1] = Float.valueOf(coordstext[i]).floatValue();
							}
							matset.Ks = coords;
						} else if (newline.charAt(0) == 'd') {
							final String[] coordstext = newline.split("\\s+");
							matset.d = Float.valueOf(coordstext[1]).floatValue();
						} else if (newline.contains("map_Ka")) {
							String texture = newline.replace("map_Ka ", "");
							while (texture.startsWith(" ")) {
								texture = texture.replaceFirst(" ", "");
							}
							matset.map_Ka = texture;
						} else if (newline.contains("map_Kd")) {
							String texture = newline.replace("map_Kd ", "");
							while (texture.startsWith(" ")) {
								texture = texture.replaceFirst(" ", "");
							}
							matset.map_Kd = texture;
						} else if (newline.contains("map_d")) {
							String texture = newline.replace("map_d ", "");
							while (texture.startsWith(" ")) {
								texture = texture.replaceFirst(" ", "");
							}
							matset.map_d = texture;
						}
					}
				}
				Materials.add(matset);

			} catch (final IOException e) {
				DEBUG.ERR("Failed to read file: " + br.toString());
				e.printStackTrace();
			} catch (final NumberFormatException e) {
				DEBUG.ERR("Malformed MTL (on line " + linecounter + "): " + br.toString() + "\r \r" + e.getMessage());
			} catch (final StringIndexOutOfBoundsException e) {
				DEBUG.ERR("Malformed MTL (on line " + linecounter + "): " + br.toString() + "\r \r" + e.getMessage());
			}
		}
	}

}
