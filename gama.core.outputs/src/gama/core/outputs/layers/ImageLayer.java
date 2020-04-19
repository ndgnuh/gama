/*******************************************************************************************************
 *
 * gama.core.outputs.layers.ImageLayer.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling
 * and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.outputs.layers;

import static gama.runtime.exceptions.GamaRuntimeException.error;

import gama.common.geometry.Envelope3D;
import gama.common.geometry.Scaling3D;
import gama.common.interfaces.outputs.IGraphics;
import gama.common.interfaces.outputs.ILayerData;
import gama.common.interfaces.outputs.ILayerStatement;
import gama.metamodel.shape.GamaPoint;
import gama.runtime.exceptions.GamaRuntimeException.GamaRuntimeFileException;
import gama.runtime.scope.IScope;
import gama.util.file.IGamaFile;
import gama.util.file.IGamaFile.Image;
import gaml.expressions.IExpression;
import gaml.operators.Cast;
import gaml.statements.draw.FileDrawingAttributes;
import gaml.types.GamaFileType;
import gaml.types.Types;

/**
 * Written by drogoul Modified on 9 nov. 2009
 *
 * @todo Description
 *
 */
public class ImageLayer extends AbstractLayer {

	// Cache a copy of both to avoid reloading them each time.
	Envelope3D env;
	IGamaFile.Image cachedFile;
	IExpression file;
	boolean isPotentiallyVariable;
	boolean isFile;

	public ImageLayer(final IScope scope, final ILayerStatement layer) {
		super(layer);
		file = ((ImageLayerStatement) definition).file;
		isFile = file.getGamlType().getGamlType().equals(Types.FILE);
		isPotentiallyVariable = !file.isContextIndependant();
		if (!isFile) {
			if (file.isConst() || !isPotentiallyVariable) {
				final String constantFilePath = Cast.asString(scope, file.value(scope));
				cachedFile = createFileFromString(scope, constantFilePath);
				isFile = true;
			}
		} else {
			if (!isPotentiallyVariable) {
				cachedFile = createFileFromFileExpression(scope);
				isFile = true;
			}
		}
	}

	@Override
	protected ILayerData createData() {
		return new ImageLayerData(definition);
	}

	private IGamaFile.Image createFileFromFileExpression(final IScope scope) {
		final IGamaFile<?, ?> result = (IGamaFile<?, ?>) file.value(scope);
		return verifyFile(scope, result);
	}

	private IGamaFile.Image createFileFromString(final IScope scope, final String imageFileName) {
		final IGamaFile.Image result = (Image) GamaFileType.createFile(scope, imageFileName, null);
		return verifyFile(scope, result);
	}

	private IGamaFile.Image verifyFile(final IScope scope, final IGamaFile<?, ?> input) {
		if (input == cachedFile)
			return cachedFile;
		if (input == null)
			throw error("Not a file: " + file.serialize(false), scope);
		if (!(input instanceof IGamaFile.Image))
			throw error("Not an image:" + input.getPath(scope), scope);
		final IGamaFile.Image result = (IGamaFile.Image) input;
		try {
			result.getImage(scope, !getData().getRefresh());
		} catch (final GamaRuntimeFileException ex) {
			throw ex;
		} catch (final Throwable e) {
			throw GamaRuntimeFileException.create(e, scope);
		}
		cachedFile = result;
		env = computeEnvelope(scope, result);
		return result;
	}

	private Envelope3D computeEnvelope(final IScope scope, final IGamaFile.Image file) {
		return file.getGeoDataFile(scope) != null ? file.computeEnvelope(scope) : scope.getSimulation().getEnvelope();
	}

	protected IGamaFile.Image buildImage(final IScope scope) {
		if (!isPotentiallyVariable)
			return cachedFile;
		return isFile ? createFileFromFileExpression(scope)
				: createFileFromString(scope, Cast.asString(scope, file.value(scope)));
	}

	@Override
	public void privateDraw(final IScope scope, final IGraphics dg) {
		final IGamaFile.Image file = buildImage(scope);
		if (file == null)
			return;
		final FileDrawingAttributes attributes = new FileDrawingAttributes(null, true);
		attributes.setUseCache(!getData().getRefresh());
		if (env != null) {
			final GamaPoint loc;
			if (dg.is2D()) {
				loc = new GamaPoint(env.getMinX(), env.getMinY());
			} else {
				loc = new GamaPoint(env.getWidth() / 2 + env.getMinX(), env.getHeight() / 2 + env.getMinY());
			}
			attributes.setLocation(loc);
			attributes.setSize(Scaling3D.of(env.getWidth(), env.getHeight(), 0));
		}
		dg.drawFile(file, attributes);
	}

	@Override
	public void dispose() {
		super.dispose();
		cachedFile = null;
		env = null;
	}

	@Override
	public String getType() {
		return "Image layer";
	}

	/**
	 * @param newValue
	 */
	public void setImageFileName(final IScope scope, final String newValue) {
		createFileFromString(scope, newValue);
		isFile = true;
		isPotentiallyVariable = false;
	}

	public String getImageFileName(final IScope scope) {
		if (cachedFile != null && !isPotentiallyVariable)
			return cachedFile.getPath(scope);
		return "Unknown";
	}

}
