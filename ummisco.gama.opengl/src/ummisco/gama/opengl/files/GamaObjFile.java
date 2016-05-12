/*********************************************************************************************
 *
 *
 * 'GamaObjFile.java', in plugin 'msi.gama.jogl2', is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 *
 *
 **********************************************************************************************/
package ummisco.gama.opengl.files;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import msi.gama.common.util.*;
import msi.gama.metamodel.shape.*;
import msi.gama.precompiler.GamlAnnotations.file;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.util.*;
import msi.gama.util.file.Gama3DGeometryFile;
import msi.gaml.operators.fastmaths.FastMath;
import msi.gaml.types.*;
import ummisco.gama.opengl.utils.GLUtilGLContext;

/**
 * Class GamaObjFile.
 *
 * @author drogoul
 * @since 30 déc. 2013
 *
 */
@file(name = "obj", extensions = "obj", buffer_type = IType.LIST, buffer_content = IType.GEOMETRY)
public class GamaObjFile extends Gama3DGeometryFile {

	private final ArrayList<float[]> vertexSets = new ArrayList();
	private final ArrayList vertexsetsNorms = new ArrayList();
	private final ArrayList vertexSetsTexs = new ArrayList();
	private final ArrayList<int[]> faces = new ArrayList();
	private final ArrayList facesTexs = new ArrayList();
	private final ArrayList facesNorms = new ArrayList();
	private final ArrayList matTimings = new ArrayList();
	private MtlLoader materials;
	// private int objectList;
	// private int numPolys = 0;
	public float toppoint = 0f;
	public float bottompoint = 0f;
	public float leftpoint = 0f;
	public float rightpoint = 0f;
	public float farpoint = 0f;
	public float nearpoint = 0f;
	private final String mtlPath;
	boolean loaded = false;

	/**
	 * @param scope
	 * @param pathName
	 * @throws GamaRuntimeException
	 */
	public GamaObjFile(final IScope scope, final String pathName) throws GamaRuntimeException {
		this(scope, pathName, (GamaPair) null);
	}

	public GamaObjFile(final IScope scope, final String pathName, final GamaPair initRotation)
		throws GamaRuntimeException {
		this(scope, pathName, pathName.replace(".obj", ".mtl"), initRotation);
	}

	public GamaObjFile(final IScope scope, final String pathName, final String mtlPath) {
		this(scope, pathName, mtlPath, null);
	}

	public GamaObjFile(final IScope scope, final String pathName, final String mtlPath, final GamaPair initRotation) {
		super(scope, pathName, initRotation);
		if ( mtlPath != null ) {
			this.mtlPath = FileUtils.constructAbsoluteFilePath(scope, mtlPath, false);
		} else {
			this.mtlPath = null;
		}

	}

	private void centerit() {
		float xshift = (rightpoint - leftpoint) / 2.0F;
		float yshift = (toppoint - bottompoint) / 2.0F;
		float zshift = (nearpoint - farpoint) / 2.0F;
		for ( int i = 0; i < vertexSets.size(); i++ ) {
			float coords[] = new float[4];
			coords[0] = vertexSets.get(i)[0] - leftpoint - xshift;
			coords[1] = vertexSets.get(i)[1] - bottompoint - yshift;
			coords[2] = vertexSets.get(i)[2] - farpoint - zshift;
			vertexSets.set(i, coords);
		}

	}

	private void loadObject() {
		if ( loaded ) { return; }
		int facecounter = 0;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(getFile()));
			boolean firstpass = true;
			String newline;
			while ((newline = br.readLine()) != null) {
				if ( newline.length() > 0 ) {
					newline = newline.trim();

					// LOADS VERTEX COORDINATES
					if ( newline.startsWith("v ") ) {
						newline = newline.substring(2, newline.length());
						StringTokenizer st = new StringTokenizer(newline, " ");
						float coords[] = new float[st.countTokens()];
						for ( int i = 0; st.hasMoreTokens(); i++ ) {
							coords[i] = Float.parseFloat(st.nextToken());
						}

						if ( firstpass ) {
							rightpoint = coords[0];
							leftpoint = coords[0];
							toppoint = coords[1];
							bottompoint = coords[1];
							nearpoint = coords[2];
							farpoint = coords[2];
							firstpass = false;
						}
						if ( coords[0] > rightpoint ) {
							rightpoint = coords[0];
						}
						if ( coords[0] < leftpoint ) {
							leftpoint = coords[0];
						}
						if ( coords[1] > toppoint ) {
							toppoint = coords[1];
						}
						if ( coords[1] < bottompoint ) {
							bottompoint = coords[1];
						}
						if ( coords[2] > nearpoint ) {
							nearpoint = coords[2];
						}
						if ( coords[2] < farpoint ) {
							farpoint = coords[2];
						}
						vertexSets.add(coords);
					} else

					// LOADS VERTEX TEXTURE COORDINATES
					if ( newline.startsWith("vt") ) {
						float coords[] = new float[4];
						String coordstext[] = new String[4];
						newline = newline.substring(3, newline.length());
						StringTokenizer st = new StringTokenizer(newline, " ");
						for ( int i = 0; st.hasMoreTokens(); i++ ) {
							coords[i] = Float.parseFloat(st.nextToken());
						}

						vertexSetsTexs.add(coords);
					} else

					// LOADS VERTEX NORMALS COORDINATES
					if ( newline.startsWith("vn") ) {
						float coords[] = new float[4];
						String coordstext[] = new String[4];
						newline = newline.substring(3, newline.length());
						StringTokenizer st = new StringTokenizer(newline, " ");
						for ( int i = 0; st.hasMoreTokens(); i++ ) {
							coords[i] = Float.parseFloat(st.nextToken());
						}

						vertexsetsNorms.add(coords);
					} else

					// LOADS FACES COORDINATES
					if ( newline.startsWith("f ") ) {
						facecounter++;
						newline = newline.substring(2, newline.length());
						StringTokenizer st = new StringTokenizer(newline, " ");
						int count = st.countTokens();
						int v[] = new int[count];
						int vt[] = new int[count];
						int vn[] = new int[count];
						for ( int i = 0; i < count; i++ ) {
							char chars[] = st.nextToken().toCharArray();
							StringBuffer sb = new StringBuffer();
							char lc = 'x';
							for ( int k = 0; k < chars.length; k++ ) {
								if ( chars[k] == '/' && lc == '/' ) {
									sb.append('0');
								}
								lc = chars[k];
								sb.append(lc);
							}

							StringTokenizer st2 = new StringTokenizer(sb.toString(), "/");
							int num = st2.countTokens();
							v[i] = Integer.parseInt(st2.nextToken());
							if ( num > 1 ) {
								vt[i] = Integer.parseInt(st2.nextToken());
							} else {
								vt[i] = 0;
							}
							if ( num > 2 ) {
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
					if ( newline.charAt(0) == 'm' && newline.charAt(1) == 't' && newline.charAt(2) == 'l' &&
						newline.charAt(3) == 'l' && newline.charAt(4) == 'i' && newline.charAt(5) == 'b' ) {
						String[] coordstext = new String[3];
						coordstext = newline.split("\\s+");
						if ( mtlPath != null ) {
							loadMaterials();
						}
					} else

					// USES MATELIALS
					if ( newline.charAt(0) == 'u' && newline.charAt(1) == 's' && newline.charAt(2) == 'e' &&
						newline.charAt(3) == 'm' && newline.charAt(4) == 't' && newline.charAt(5) == 'l' ) {
						String[] coords = new String[2];
						String[] coordstext = new String[3];
						coordstext = newline.split("\\s+");
						coords[0] = coordstext[1];
						coords[1] = facecounter + "";
						matTimings.add(coords);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Failed to read file: " /* + br.toString() */);
		} catch (NumberFormatException e) {
			System.out.println("Malformed OBJ file: "/* + br.toString() */ + "\r \r" + e.getMessage());
		} finally {
			if ( br != null ) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		centerit();
		// numPolys = faces.size();
		loaded = true;
	}

	/**
	 * Method fillBuffer(). Fills the buffer with the polygons built from the .obj vertices + faces
	 * @see msi.gama.util.file.GamaFile#fillBuffer(msi.gama.runtime.IScope)
	 */
	@Override
	protected void fillBuffer(final IScope scope) throws GamaRuntimeException {
		loadObject();
		setBuffer(GamaListFactory.<IShape> create(Types.GEOMETRY));
		IList<IShape> vertices = GamaListFactory.create(Types.POINT);
		for ( float[] coords : vertexSets ) {
			GamaPoint pt = new GamaPoint(coords[0], -coords[1], coords[2]);
			vertices.add(pt);
		}
		for ( int[] vertexRefs : faces ) {
			IList<IShape> face = GamaListFactory.<IShape> create(Types.POINT);
			for ( int vertex : vertexRefs ) {
				face.add(vertices.get(vertex - 1));
				((IList) getBuffer()).add(GamaGeometryType.buildPolygon(face));
			}
		}
		envelope = new Envelope3D(leftpoint, rightpoint, bottompoint, toppoint, nearpoint, farpoint);

	}

	@Override
	public IList<String> getAttributes(final IScope scope) {
		// TODO what to return ?
		return GamaListFactory.EMPTY_LIST;
	}

	private void loadMaterials() {
		FileReader frm;
		String refm = mtlPath;

		try {
			frm = new FileReader(refm);
			BufferedReader brm = new BufferedReader(frm);
			materials = new MtlLoader(brm, mtlPath);
			frm.close();
		} catch (IOException e) {
			System.out.println("Could not open file: " + refm);
			materials = null;
		}
	}

	/**
	 * Method flushBuffer()
	 * @see msi.gama.util.file.GamaFile#flushBuffer()
	 */
	@Override
	protected void flushBuffer() throws GamaRuntimeException {}

	public void drawToOpenGL(final GL2 gl) {
		loadObject();
		int nextmat = -1;
		int matcount = 0;
		int totalmats = matTimings.size();
		String[] nextmatnamearray = null;
		String nextmatname = null;

		if ( totalmats > 0 && materials != null ) {
			nextmatnamearray = (String[]) matTimings.get(matcount);
			nextmatname = nextmatnamearray[0];
			nextmat = Integer.parseInt(nextmatnamearray[1]);
		}
		Texture texture = null;

		for ( int i = 0; i < faces.size(); i++ ) {
			if ( i == nextmat ) {
				if ( texture != null ) {
					texture.disable(gl);
					texture.destroy(gl);
					texture = null;
				}
				gl.glEnable(GLLightingFunc.GL_COLOR_MATERIAL);
				GLUtilGLContext.SetCurrentColor(gl, new float[] {materials.getKd(nextmatname)[0], materials.getKd(nextmatname)[1],
						materials.getKd(nextmatname)[2], materials.getd(nextmatname)});
				String mapKa = materials.getMapKa(nextmatname);
				String mapKd = materials.getMapKd(nextmatname);
				String mapd = materials.getMapd(nextmatname);
				if ( mapKa != null || mapKd != null || mapd != null ) {
					File f = new File(mtlPath);
					String path = f.getAbsolutePath().replace(f.getName(), "");
					if ( mapd != null ) {
						path += mapd;
					} else if ( mapKa != null ) {
						path += mapKa;
					} else if ( mapKd != null ) {
						path += mapKd;
					}
					f = new File(path);
					if ( f.exists() ) {
						BufferedImage im;
						try {
							im = ImageUtils.getInstance().getImageFromFile(f);
							TextureData data = AWTTextureIO.newTextureData(gl.getGLProfile(), im, false);
							texture = new Texture(gl, data);
							texture.enable(gl);
							texture.bind(gl);
						} catch (IOException e) {
							// e.printStackTrace();
						}
					}

				}
				matcount++;
				if ( matcount < totalmats ) {
					nextmatnamearray = (String[]) matTimings.get(matcount);
					nextmatname = nextmatnamearray[0];
					nextmat = Integer.parseInt(nextmatnamearray[1]);
				}
			}

			int[] tempfaces = faces.get(i);
			int[] tempfacesnorms = (int[]) facesNorms.get(i);
			int[] tempfacestexs = (int[]) facesTexs.get(i);

			//// Quad Begin Header ////
			int polytype;
			if ( tempfaces.length == 3 ) {
				polytype = GL.GL_TRIANGLES;
			} else if ( tempfaces.length == 4 ) {
				polytype = GL2ES3.GL_QUADS;
			} else {
				polytype = GL2.GL_POLYGON;
			}
			gl.glBegin(polytype);
			////////////////////////////

			for ( int w = 0; w < tempfaces.length; w++ ) {
				if ( tempfacesnorms[w] != 0 ) {
					float normtempx = ((float[]) vertexsetsNorms.get(tempfacesnorms[w] - 1))[0];
					float normtempy = ((float[]) vertexsetsNorms.get(tempfacesnorms[w] - 1))[1];
					float normtempz = ((float[]) vertexsetsNorms.get(tempfacesnorms[w] - 1))[2];
					gl.glNormal3f(normtempx, normtempy, normtempz);
				}

				if ( tempfacestexs[w] != 0 ) {
					float textempx = ((float[]) vertexSetsTexs.get(tempfacestexs[w] - 1))[0];
					float textempy = ((float[]) vertexSetsTexs.get(tempfacestexs[w] - 1))[1];
					float textempz = ((float[]) vertexSetsTexs.get(tempfacestexs[w] - 1))[2];
					float valy = 1f - textempy;
					if ( valy >= 0 && valy <= 1.0 ) {
						gl.glTexCoord3f(textempx, valy, textempz);
					} else {
						gl.glTexCoord3f(textempx, FastMath.abs(textempy), textempz);
					}
				}

				float tempx = vertexSets.get(tempfaces[w] - 1)[0];
				float tempy = vertexSets.get(tempfaces[w] - 1)[1];
				float tempz = vertexSets.get(tempfaces[w] - 1)[2];
				gl.glVertex3f(tempx, tempy, tempz);
			}

			//// Quad End Footer /////
			gl.glEnd();
			///////////////////////////

		}
		if ( texture != null ) {
			texture.disable(gl);
			texture = null;
		}
		// gl.glEndList();
	}

}
