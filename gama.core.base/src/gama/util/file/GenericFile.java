/*******************************************************************************************************
 *
 * gama.util.file.GenericFile.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.util.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import gama.GAMA;
import gama.common.geometry.Envelope3D;
import gama.common.util.OldFileUtils;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.list.GamaListFactory;
import gama.util.list.IList;
import gaml.statements.Facets;
import gaml.types.IContainerType;
import gaml.types.Types;

public class GenericFile extends GamaFile<IList<String>, String> {

	public GenericFile(final String pathName) throws GamaRuntimeException {
		super(GAMA.getRuntimeScope(), pathName);
	}

	public GenericFile(final String pathName, final boolean shouldExist) {
		super(GAMA.getRuntimeScope(), pathName, shouldExist);
	}

	public GenericFile(final IScope scope, final String pathName) throws GamaRuntimeException {
		super(scope, pathName, false);
	}

	@Override
	public IContainerType<?> getGamlType() {
		return Types.FILE;
	}

	@Override
	public Envelope3D computeEnvelope(final IScope scope) {
		return Envelope3D.EMPTY;
	}

	@Override
	protected void fillBuffer(final IScope scope) throws GamaRuntimeException {
		if (getBuffer() != null) { return; }
		if (OldFileUtils.isBinaryFile(scope, getFile(scope))) {
			GAMA.reportAndThrowIfNeeded(scope, GamaRuntimeException
					.warning("Problem identifying the contents of " + getFile(scope).getAbsolutePath(), scope), false);
			setBuffer(GamaListFactory.EMPTY_LIST);
		} else {
			try (final BufferedReader in = new BufferedReader(new FileReader(getFile(scope)))) {
				final IList<String> allLines = GamaListFactory.create(Types.STRING);
				String str;
				str = in.readLine();
				while (str != null) {
					allLines.add(str);
					str = in.readLine();
				}
				setBuffer(allLines);
			} catch (final IOException e) {
				throw GamaRuntimeException.create(e, scope);
			}
		}

	}

	@Override
	protected void flushBuffer(final IScope scope, final Facets facets) throws GamaRuntimeException {}

}