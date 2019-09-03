/*******************************************************************************************************
 *
 * msi.gama.util.file.IGamaFile.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gama.util.file;

import java.awt.image.BufferedImage;
import java.io.File;

import org.locationtech.jts.geom.CoordinateFilter;

import msi.gama.common.geometry.AxisAngle;
import msi.gama.common.geometry.Envelope3D;
import msi.gama.common.interfaces.IAddressableContainer;
import msi.gama.common.interfaces.IKeyword;
import msi.gama.common.interfaces.IModifiableContainer;
import msi.gama.metamodel.shape.GamaPoint;
import msi.gama.metamodel.shape.IShape;
import msi.gama.metamodel.topology.projection.IProjection;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gama.util.list.IList;
import msi.gama.util.matrix.IMatrix;
import msi.gaml.statements.Facets;
import msi.gaml.types.IType;
import ummisco.gama.processor.ITypeProvider;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.getter;
import ummisco.gama.processor.GamlAnnotations.variable;
import ummisco.gama.processor.GamlAnnotations.vars;

/**
 * Written by drogoul Modified on 14 nov. 2011
 *
 * @todo Description
 *
 * @param <K>
 * @param <V>
 */
@vars ({ @variable (
		name = IKeyword.NAME,
		type = IType.STRING,
		doc = { @doc ("Returns the name of the receiver file") }),
		@variable (
				name = IKeyword.EXTENSION,
				type = IType.STRING,
				doc = { @doc ("Returns the extension of the receiver file") }),
		@variable (
				name = IKeyword.PATH,
				type = IType.STRING,
				doc = { @doc ("Returns the absolute path of the receiver file") }),
		@variable (
				name = IKeyword.EXISTS,
				type = IType.BOOL,
				doc = { @doc ("Returns whether the receiver file exists or not in the filesystem") }),
		@variable (
				name = IKeyword.ISFOLDER,
				type = IType.BOOL,
				doc = { @doc ("Returns whether the receiver file is a folder or not") }),
		@variable (
				name = IKeyword.READABLE,
				type = IType.BOOL,
				doc = { @doc ("Returns true if the contents of the receiver file can be read") }),
		@variable (
				name = IKeyword.WRITABLE,
				type = IType.BOOL,
				doc = { @doc ("Returns true if the contents of the receiver file can be written") }),
		@variable (
				name = IKeyword.ATTRIBUTES,
				type = IType.LIST,
				of = IType.STRING,
				doc = { @doc ("Retrieves the list of 'attributes' present in the receiver files that support this concept (and an empty list for the others). For instance, in a CSV file, the attributes represent the headers of the columns (if any); in a shape file, the attributes provided to the objects, etc.") }),
		@variable (
				name = IKeyword.CONTENTS,
				type = ITypeProvider.WRAPPED,
				of = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
				index = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
				doc = { @doc ("Returns the contents of the receiver file in the form of a container") }) })
@SuppressWarnings ({ "rawtypes" })
public interface IGamaFile<C extends IModifiableContainer, Contents>
		extends IAddressableContainer, IModifiableContainer {

	void setWritable(IScope scope, final boolean w);

	void setContents(final C cont) throws GamaRuntimeException;

	@Override
	IGamaFile copy(IScope scope);

	C getBuffer();

	@getter (
			value = IKeyword.EXISTS,
			initializer = true)
	Boolean exists(IScope scope);

	@getter (
			value = IKeyword.EXTENSION,
			initializer = true)
	String getExtension(IScope scope);

	@getter (
			value = IKeyword.NAME,
			initializer = true)
	String getName(IScope scope);

	@getter (
			value = IKeyword.PATH,
			initializer = true)
	String getPath(IScope scope);

	@getter (IKeyword.CONTENTS)
	C getContents(IScope scope) throws GamaRuntimeException;

	@getter (IKeyword.ATTRIBUTES)
	/**
	 * Retrieves the list of "attributes" present in files that support this concept (and an empty list for the others).
	 * For instance, in a CSV file, attributes represent the headers of the columns (if any); in a shape file, the
	 * attributes provided to the objects, etc.
	 *
	 * @param scope
	 * @return a list of string or an empty list (never null)
	 */
	IList<String> getAttributes(IScope scope);

	@getter (
			value = IKeyword.ISFOLDER,
			initializer = true)
	Boolean isFolder(IScope scope);

	@getter (
			value = IKeyword.READABLE,
			initializer = true)
	Boolean isReadable(IScope scope);

	@getter (
			value = IKeyword.WRITABLE,
			initializer = true)
	Boolean isWritable(IScope scope);

	Envelope3D computeEnvelope(final IScope scope);

	void save(IScope scope, Facets parameters);

	String getOriginalPath();

	@Override
	default boolean containsKey(final IScope scope, final Object o) {
		final C contents = getContents(scope);
		return contents != null && contents.contains(scope, o);
	}

	File getFile(IScope scope);

	void invalidateContents();

	interface Gis extends IGamaFile<IList<IShape>, IShape> {
		// The code to force reading the GIS data as already projected
		int ALREADY_PROJECTED_CODE = 0;
		CoordinateFilter ZERO_Z = coord -> ((GamaPoint) coord).z = 0;

		IProjection getGis(final IScope scope);
	}

	interface Geom extends IGamaFile<IList<IShape>, IShape> {

		IShape getGeometry(final IScope scope);

		AxisAngle getInitRotation();

		boolean is2D();
	}

	interface Image extends IGamaFile<IMatrix<Integer>, Integer> {

		BufferedImage getImage(IScope scope, boolean b);

		String getGeoDataFile(IScope scope);

		int getFrameCount();

		int getAverageDelay();

		boolean isAnimated();
	}

	public interface Grid extends Gis {

		int getNbRows(IScope scope);

		int getNbCols(IScope scope);

		int getNbBands();

		Double valueOf(IScope scope, GamaPoint location);

	}

}