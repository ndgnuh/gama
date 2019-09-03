/*******************************************************************************************************
 *
 * gama.util.file.GamaFolderFile.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.util.file;

import static gama.util.list.GamaListFactory.createWithoutCasting;

import gama.common.geometry.Envelope3D;
import gama.common.interfaces.IContainer;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.list.GamaListFactory;
import gama.util.list.IList;
import gaml.operators.Files;
import gaml.statements.Facets;
import gaml.types.IContainerType;
import gaml.types.Types;

public class GamaFolderFile extends GamaFile<IList<String>, String> {

	public GamaFolderFile(final IScope scope, final String pathName) throws GamaRuntimeException {
		super(scope, pathName);
		// AD 27/04/13 Let the flags of the file remain the same. Can be turned
		// off and on using the "read" and
		// "write" operators, so no need to decide for a default here
		// setWritable(true);
	}

	@Override
	protected void checkValidity(final IScope scope) throws GamaRuntimeException {
		if (!getFile(scope).isDirectory())
			throw GamaRuntimeException.error(getFile(scope).getAbsolutePath() + "is not a folder", scope);
		if (!getFile(scope).exists())
			throw GamaRuntimeException.error("The folder " + getFile(scope).getAbsolutePath()
					+ " does not exist. Please use 'new_folder' instead", scope);
	}

	@Override
	public String serialize(final boolean includingBuiltIn) {
		return Files.FOLDER + "('" + /* StringUtils.toGamlString(getPath()) */getPath(null) + "')";
	}

	@Override
	public IContainerType<?> getGamlType() {
		return Types.FILE.of(Types.INT, Types.STRING);
	}

	@Override
	public IList<String> getAttributes(final IScope scope) {
		// No attributes to speak of
		return GamaListFactory.EMPTY_LIST;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gama.util.GamaFile#fillBuffer()
	 */
	@Override
	protected void fillBuffer(final IScope scope) throws GamaRuntimeException {
		if (getBuffer() != null)
			return;
		final String[] list = getFile(scope).list();
		final IList<String> result =
				list == null ? GamaListFactory.EMPTY_LIST : createWithoutCasting(Types.STRING, list);
		setBuffer(result);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gama.util.GamaFile#flushBuffer()
	 */
	@Override
	protected void flushBuffer(final IScope scope, final Facets facets) throws GamaRuntimeException {
		// Nothing to do
	}

	@SuppressWarnings ("rawtypes")
	@Override
	public Envelope3D computeEnvelope(final IScope scope) {
		final IContainer<Integer, String> files = getContents(scope);
		Envelope3D globalEnv = null;
		for (final String s : files.iterable(scope)) {
			final IGamaFile f = Files.from(scope, s);
			final Envelope3D env = f.computeEnvelope(scope);
			if (globalEnv == null) {
				globalEnv = env;
			} else {
				globalEnv.expandToInclude(env);
			}
		}
		return globalEnv;
	}

}
