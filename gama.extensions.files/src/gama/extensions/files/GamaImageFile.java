/*******************************************************************************************************
 *
 * msi.gama.util.file.GamaImageFile.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.extensions.files;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import org.locationtech.jts.geom.Envelope;
import org.opengis.referencing.FactoryException;

import gama.extensions.files.metadata.GamaFileMetaData;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.example;
import gama.processor.annotations.GamlAnnotations.file;
import msi.gama.common.geometry.Envelope3D;
import msi.gama.common.util.ImageUtils;
import msi.gama.common.util.TextBuilder;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.metamodel.shape.GamaShape;
import msi.gama.metamodel.topology.projection.IProjection;
import msi.gama.runtime.GAMA;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.exceptions.GamaRuntimeException.GamaRuntimeFileException;
import msi.gama.runtime.scope.IScope;
import msi.gama.util.file.GamaFile;
import msi.gama.util.file.IGamaFile;
import msi.gama.util.list.GamaListFactory;
import msi.gama.util.list.IList;
import msi.gama.util.matrix.GamaIntMatrix;
import msi.gama.util.matrix.IMatrix;
import msi.gaml.operators.Spatial.Projections;
import msi.gaml.operators.Strings;
import msi.gaml.statements.Facets;
import msi.gaml.types.GamaMatrixType;
import msi.gaml.types.IContainerType;
import msi.gaml.types.IType;
import msi.gaml.types.Types;

@file (
		name = "image",
		extensions = { "tiff", "jpg", "jpeg", "png", "pict", "bmp" },
		buffer_type = IType.MATRIX,
		buffer_content = IType.INT,
		buffer_index = IType.POINT,
		concept = { IConcept.IMAGE, IConcept.FILE },
		doc = @doc ("Image files can be of 6 different formats: tiff, jpeg, png, pict or bmp. Their internal representation is a matrix of colors"))
@SuppressWarnings ({ "unchecked", "rawtypes" })
public class GamaImageFile extends GamaFile<IMatrix<Integer>, Integer> implements IGamaFile.Image {

	public static class ImageInfo extends GamaFileMetaData {
		/**
		 * Image format constant indicating an unknown image type (value is -1).
		 */
		public static final int IMAGE_UNDEFINED = -1;

		/**
		 * Image format constant indicating a Windows BMP format image (value is 0).
		 */
		public static final int IMAGE_BMP = 0;

		/**
		 * Image format constant indicating a run-length encoded Windows BMP format image (value is 1).
		 */
		public static final int IMAGE_BMP_RLE = 1;

		/**
		 * Image format constant indicating a GIF format image (value is 2).
		 */
		public static final int IMAGE_GIF = 2;

		/**
		 * Image format constant indicating a ICO format image (value is 3).
		 */
		public static final int IMAGE_ICO = 3;

		/**
		 * Image format constant indicating a JPEG format image (value is 4).
		 */
		public static final int IMAGE_JPEG = 4;

		/**
		 * Image format constant indicating a PNG format image (value is 5).
		 */
		public static final int IMAGE_PNG = 5;

		/**
		 * Image format constant indicating a TIFF format image (value is 6).
		 */
		public static final int IMAGE_TIFF = 6;

		/**
		 * Image format constant indicating an OS/2 BMP format image (value is 7).
		 */
		public static final int IMAGE_OS2_BMP = 7;

		/**
		 * GIF image disposal method constants indicating that the disposal method is unspecified (value is 0).
		 */
		public static final int DM_UNSPECIFIED = 0x0;

		public final static Map<String, Integer> formats = new HashMap<>();

		{
			formats.put("BMP", IMAGE_BMP);
			formats.put("GIF", IMAGE_GIF);
			formats.put("JPEG", IMAGE_JPEG);
			formats.put("JPG", IMAGE_JPEG);
			formats.put("PNG", IMAGE_PNG);
			formats.put("TIFF", IMAGE_TIFF);
			formats.put("TIF", IMAGE_TIFF);
			formats.put("Unknown Format", IMAGE_UNDEFINED);
			formats.put("ASC", 8);
			formats.put("PGM", 9);
			formats.put("ICO", IMAGE_ICO);
		}

		public final static Map<Integer, String> formatsShortNames = new HashMap<>() {

			{
				// Hack: Corresponds to SWT.IMAGE_xxx + ImagePropertyPage
				// constants
				put(0, "BMP");
				put(1, "BMP");
				put(7, "BMP");
				put(2, "GIF");
				put(4, "JPEG");
				put(5, "PNG");
				put(3, "ICO");
				put(6, "TIFF");
				put(-1, "Unknown Format");
				put(8, "ASCII");
				put(9, "PGM");
			}
		};

		// private Object thumbnail;
		private final int type;
		private final int width;
		private final int height;

		public ImageInfo(final long modificationStamp, /* final Object thumbnail, */final int origType,
				final int origWidth, final int origHeight) {
			super(modificationStamp);
			// this.thumbnail = thumbnail;
			this.type = origType;
			this.width = origWidth;
			this.height = origHeight;
		}

		public ImageInfo(final String propertyString) {
			super(propertyString);
			final String[] segments = split(propertyString);
			type = Integer.parseInt(segments[1]);
			width = Integer.parseInt(segments[2]);
			height = Integer.parseInt(segments[3]);
			// thumbnail = null;
		}

		public String getShortLabel(final int type) {
			return formatsShortNames.containsKey(type) ? formatsShortNames.get(type) : formatsShortNames.get(-1);
		}

		// public void setThumbnail(final Object thumb) {
		// thumbnail = thumb;
		// }

		@Override
		public String getSuffix() {
			return "" + width + "x" + height + ", " + getShortLabel(type) + "";
		}

		@Override
		public void appendSuffix(final StringBuilder sb) {
			sb.append(width).append("x").append(height).append(SUFFIX_DEL).append(getShortLabel(type));
		}

		@Override
		public String getDocumentation() {
			try (TextBuilder sb = TextBuilder.create()) {
				sb.append(getShortLabel(type)).append(" Image File").append(Strings.LN);
				sb.append("Dimensions: ").append(width + " pixels x " + height + " pixels").append(Strings.LN);
				return sb.toString();
			}
		}

		// @Override
		// public Object getThumbnail() {
		// return thumbnail;
		// }

		public int getType() {
			return type;
		}

		@Override
		public String toPropertyString() {
			return super.toPropertyString() + DELIMITER + type + DELIMITER + width + DELIMITER + height;
		}

		public static int getType(final String fileExtension) {
			String key = fileExtension.toUpperCase();
			if (formats.containsKey(key))
				return formats.get(key);
			return IMAGE_UNDEFINED;
		}
	}

	@file (
			name = "pgm",
			extensions = { "pgm" },
			buffer_type = IType.MATRIX,
			buffer_content = IType.INT,
			doc = @doc ("PGM files are special image files in 256 gray levels"))
	public static class GamaPgmFile extends GamaImageFile {

		/**
		 * @param scope
		 * @param pathName
		 * @throws GamaRuntimeException
		 */
		@doc (
				value = "This file constructor allows to read a pgm file",
				examples = { @example (
						value = "file f <-pgm_file(\"file.pgm\");",
						isExecutable = false) })

		public GamaPgmFile(final IScope scope, final String pathName) throws GamaRuntimeException {
			super(scope, pathName);
		}

		@Override
		protected boolean isPgmFile() {
			return true;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see msi.gama.util.GamaFile#flushBuffer()
		 */
		@Override
		protected void flushBuffer(final IScope scope, final Facets facets) throws GamaRuntimeException {
			throw GamaRuntimeException
					.error("Saving is not yet impletemented for files of type " + this.getExtension(scope), scope);
		}

	}

	// protected BufferedImage image;
	private boolean isGeoreferenced = false;

	@doc (
			value = "This file constructor allows to read an image file (tiff, jpg, jpeg, png, pict, bmp)",
			examples = { @example (
					value = "file f <-image_file(\"file.png\");",
					isExecutable = false) })

	public GamaImageFile(final IScope scope, final String pathName) throws GamaRuntimeException {
		super(scope, pathName);
	}

	@doc (
			value = "This file constructor allows to store a matrix in a image file (it does not save it - just store it in memory)",
			examples = { @example (
					value = "file f <-image_file(\"file.png\");",
					isExecutable = false) })
	public GamaImageFile(final IScope scope, final String pathName, final IMatrix<Integer> image) {
		super(scope, pathName, image);
		ImageUtils.getInstance().clearCache(getPath(scope));
	}

	@Override
	public IList<String> getAttributes(final IScope scope) {
		// No attributes
		return GamaListFactory.EMPTY_LIST;
	}

	@Override
	public IContainerType getGamlType() {
		return Types.FILE.of(Types.POINT, Types.INT);
	}

	@Override
	protected void fillBuffer(final IScope scope) throws GamaRuntimeException {
		if (getBuffer() != null)
			return;
		// Temporary workaround for pgm files, which can be read by ImageIO but
		// produce wrong results. See Issue 880.
		// TODO change this behavior
		setBuffer(isPgmFile() || getExtension(scope).equals("pgm") ? matrixValueFromPgm(scope, null)
				: matrixValueFromImage(scope, null));
	}

	protected boolean isPgmFile() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see msi.gama.util.GamaFile#flushBuffer()
	 */
	@Override
	protected void flushBuffer(final IScope scope, final Facets facets) throws GamaRuntimeException {
		if (!writable || getBuffer() == null || getBuffer().isEmpty(scope))
			return;
		try {
			final File f = getFile(scope);
			f.setWritable(true);
			ImageIO.write(imageFromMatrix(scope), getExtension(scope), f);
		} catch (final IOException e) {
			throw GamaRuntimeException.create(e, scope);
		}
	}

	@Override
	protected IMatrix _matrixValue(final IScope scope, final IType contentsType, final GamaPoint preferredSize,
			final boolean copy) throws GamaRuntimeException {
		getContents(scope);
		if (preferredSize != null)
			return matrixValueFromImage(scope, preferredSize).matrixValue(scope, contentsType, copy);
		return getBuffer().matrixValue(scope, contentsType, copy);
	}

	protected BufferedImage loadImage(final IScope scope, final boolean useCache) {
		// if (image == null) {
		final BufferedImage image;
		try {
			image = ImageUtils.getInstance().getImageFromFile(scope, getPath(scope), useCache);
			if (image == null)
				throw GamaRuntimeFileException.error("This image format (." + getExtension(scope)
						+ ") is not recognized. Please use a proper operator to read it (for example, pgm_file to read a .pgm format",
						scope);
		} catch (final IOException e) {
			GAMA.reportAndThrowIfNeeded(scope, GamaRuntimeFileException.create(e, scope), true);
			return null;
		}
		// }
		return image;
	}

	@Override
	public BufferedImage getImage(final IScope scope, final boolean useCache) {
		return loadImage(scope, useCache);
		// return image;
	}

	public int getWidth(final IScope scope) {
		final BufferedImage image = loadImage(scope, true);
		if (image == null)
			return 0;
		return image.getWidth();
	}

	public int getHeight(final IScope scope) {
		final BufferedImage image = loadImage(scope, true);
		if (image == null)
			return 0;
		return image.getHeight();

	}

	private IMatrix matrixValueFromImage(final IScope scope, final GamaPoint preferredSize)
			throws GamaRuntimeException {
		final BufferedImage image = loadImage(scope, true);
		return matrixValueFromImage(scope, image, preferredSize);
	}

	private IMatrix matrixValueFromImage(final IScope scope, final BufferedImage image, final GamaPoint preferredSize) {
		int xSize, ySize;
		BufferedImage resultingImage = image;
		if (preferredSize == null) {
			xSize = image.getWidth();
			ySize = image.getHeight();
		} else {
			xSize = (int) preferredSize.getX();
			ySize = (int) preferredSize.getY();
			resultingImage = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_RGB);
			final Graphics2D g = resultingImage.createGraphics();
			g.drawImage(image, 0, 0, xSize, ySize, null);
			g.dispose();
			// image = resultingImage;
		}
		final IMatrix matrix = new GamaIntMatrix(xSize, ySize);
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				matrix.set(scope, i, j, resultingImage.getRGB(i, j));
			}
		}
		return matrix;

	}

	private BufferedImage imageFromMatrix(final IScope scope) {
		final int xSize = getBuffer().getCols(scope);
		final int ySize = getBuffer().getRows(scope);
		final BufferedImage resultingImage = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				resultingImage.setRGB(i, j, getBuffer().get(scope, i, j));
			}
		}
		return resultingImage;

	}

	private IMatrix matrixValueFromPgm(final IScope scope, final GamaPoint preferredSize) throws GamaRuntimeException {
		// TODO PreferredSize is not respected here
		try (BufferedReader in = new BufferedReader(new FileReader(getFile(scope)))) {
			StringTokenizer tok;
			String str = in.readLine();
			if (str != null && !str.equals("P2"))
				throw new UnsupportedEncodingException("File is not in PGM ascii format");
			str = in.readLine();
			if (str == null)
				return GamaMatrixType.with(scope, 0, preferredSize, Types.INT);
			tok = new StringTokenizer(str);
			final int xSize = Integer.parseInt(tok.nextToken());
			final int ySize = Integer.parseInt(tok.nextToken());
			in.readLine();
			final StringBuilder buf = new StringBuilder();
			String line = in.readLine();
			while (line != null) {
				buf.append(line);
				buf.append(' ');
				line = in.readLine();
			}
			// in.close();
			str = buf.toString();
			tok = new StringTokenizer(str);
			final IMatrix matrix = new GamaIntMatrix(xSize, ySize);
			for (int j = 0; j < ySize; j++) {
				for (int i = 0; i < xSize; i++) {
					final Integer val = Integer.valueOf(tok.nextToken());
					matrix.set(scope, i, j, val);
				}
			}
			return matrix;
		} catch (final Throwable ex) {
			throw GamaRuntimeException.create(ex, scope);
		}

	}

	@Override
	public String getGeoDataFile(final IScope scope) {
		final String extension = getExtension(scope);
		String val = null;
		String geodataFile = getPath(scope).replaceAll(extension, "");
		if (extension.equals("jpg")) {
			geodataFile = geodataFile + "jgw";
		} else if (extension.equals("png")) {
			geodataFile = geodataFile + "pgw";
		} else if (extension.equals("tiff") || extension.equals("tif")) {
			geodataFile = geodataFile + "tfw";
			val = "";
		} else
			return null;
		final File infodata = new File(geodataFile);
		if (infodata.exists())
			return geodataFile;
		return val;
	}

	@Override
	public Envelope3D computeEnvelope(final IScope scope) {
		final String geodataFile = getGeoDataFile(scope);
		double cellSizeX = 1;
		double cellSizeY = 1;
		double xllcorner = 0;
		double yllcorner = 0;
		boolean xNeg = false;
		boolean yNeg = false;
		final String extension = getExtension(scope);
		if (geodataFile != null && !geodataFile.equals("")) {
			try (final InputStream ips = new FileInputStream(geodataFile);
					final InputStreamReader ipsr = new InputStreamReader(ips);
					final BufferedReader in = new BufferedReader(ipsr);) {
				String line = in.readLine();
				if (line != null) {
					final String[] cellSizeXStr = line.split(" ");
					cellSizeX = Double.valueOf(cellSizeXStr[cellSizeXStr.length - 1]);
				}
				xNeg = cellSizeX < 0;
				line = in.readLine();
				line = in.readLine();
				line = in.readLine();
				if (line != null) {
					final String[] cellSizeYStr = line.split(" ");
					cellSizeY = Double.valueOf(cellSizeYStr[cellSizeYStr.length - 1]);
				}
				yNeg = cellSizeY < 0;
				line = in.readLine();
				if (line != null) {
					final String[] xllcornerStr = line.split(" ");
					xllcorner = Double.valueOf(xllcornerStr[xllcornerStr.length - 1]);
				}
				line = in.readLine();
				if (line != null) {
					final String[] yllcornerStr = line.split(" ");
					yllcorner = Double.valueOf(yllcornerStr[yllcornerStr.length - 1]);
				}
				isGeoreferenced = true;

			} catch (final Throwable e) {
				throw GamaRuntimeException.create(e, scope);
			}
		} else if (extension.equals("tiff") || extension.equals("tif")) {
			final GamaGridFile file = new GamaGridFile(null, this.getPath(scope));

			final Envelope e = file.computeEnvelopeWithoutBuffer(scope);
			if (e != null) {
				GamaPoint minCorner = new GamaPoint(e.getMinX(), e.getMinY());
				GamaPoint maxCorner = new GamaPoint(e.getMaxX(), e.getMaxY());
				if (geodataFile != null) {
					IProjection pr;
					try {
						pr = scope.getSimulation().getProjectionFactory().forSavingWith(scope,
								file.gis.getTargetCRS(scope));
						minCorner = new GamaShape(pr.transform(minCorner.getInnerGeometry())).getLocation();
						maxCorner = new GamaShape(pr.transform(maxCorner.getInnerGeometry())).getLocation();
					} catch (final FactoryException e1) {
						e1.printStackTrace();
					}

				}
				isGeoreferenced = true;
				return Envelope3D.of(minCorner.x, maxCorner.x, minCorner.y, maxCorner.y, 0, 0);
			}

		}
		final int nbCols = getWidth(scope);
		final int nbRows = getHeight(scope);

		final double x1 = xllcorner;
		final double x2 = xllcorner + cellSizeX * nbCols;
		final double y1 = yllcorner;
		final double y2 = yllcorner + cellSizeY * nbRows;
		GamaPoint minCorner =
				new GamaPoint(xNeg ? Math.max(x1, x2) : Math.min(x1, x2), yNeg ? Math.max(y1, y2) : Math.min(y1, y2));
		GamaPoint maxCorner =
				new GamaPoint(xNeg ? Math.min(x1, x2) : Math.max(x1, x2), yNeg ? Math.min(y1, y2) : Math.max(y1, y2));
		if (geodataFile != null) {
			minCorner = Projections.to_GAMA_CRS(scope, minCorner).getLocation();
			maxCorner = Projections.to_GAMA_CRS(scope, maxCorner).getLocation();
		}

		return Envelope3D.of(minCorner.x, maxCorner.x, minCorner.y, maxCorner.y, 0, 0);

	}

	public boolean isGeoreferenced() {
		return isGeoreferenced;
	}

	//
	// public void setImage(final IScope scope, final BufferedImage image2) {
	// // AD QUESTION : Shouldnt we also erase the buffer in that case ?
	// setBuffer(matrixValueFromImage(scope, image2, null));
	// // image = image2;
	// }

	@Override
	public boolean isAnimated() {
		return false;
	}

	@Override
	public int getFrameCount() {
		return 1;
	}

	@Override
	public int getAverageDelay() {
		return 0;
	}

}
