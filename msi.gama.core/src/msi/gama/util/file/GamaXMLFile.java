/*********************************************************************************************
 *
 *
 * 'GamaXMLFile.java', in plugin 'msi.gama.core', is part of the source code of the
 * GAMA modeling and simulation platform.
 * (c) 2007-2014 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://code.google.com/p/gama-platform/ for license information and developers contact.
 *
 *
 **********************************************************************************************/
package msi.gama.util.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.vividsolutions.jts.geom.Envelope;

import msi.gama.precompiler.GamlAnnotations.file;
import msi.gama.precompiler.IConcept;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.util.GamaListFactory;
import msi.gama.util.GamaMap;
import msi.gama.util.GamaMapFactory;
import msi.gama.util.IList;
import msi.gaml.types.IContainerType;
import msi.gaml.types.IType;
import msi.gaml.types.Types;

/**
 * Class GamaXMLFile. TODO: Everything ! What kind of buffer should be returned
 * from here ? The current implementation does not make any sense at all.
 * 
 * @author drogoul
 * @since 9 janv. 2014
 *
 */
@file(name = "xml", extensions = "xml", buffer_type = IType.MAP, concept = { IConcept.FILE, IConcept.XML })
public class GamaXMLFile extends GamaFile<GamaMap<String, String>, String, String, String> {

	/**
	 * @param scope
	 * @param pathName
	 * @throws GamaRuntimeException
	 */
	public GamaXMLFile(final IScope scope, final String pathName) throws GamaRuntimeException {
		super(scope, pathName);
	}

	@Override
	public IContainerType getType() {
		return Types.FILE.of(Types.INT, Types.NO_TYPE);
	}

	@Override
	public IList<String> getAttributes(final IScope scope) {
		// TODO depends on the contents...
		return GamaListFactory.create();
	}

	/**
	 * Method computeEnvelope()
	 * 
	 * @see msi.gama.util.file.IGamaFile#computeEnvelope(msi.gama.runtime.IScope)
	 */
	@Override
	public Envelope computeEnvelope(final IScope scope) {
		return null;
	}

	/**
	 * Method fillBuffer()
	 * 
	 * @see msi.gama.util.file.GamaFile#fillBuffer(msi.gama.runtime.IScope)
	 */
	@Override
	protected void fillBuffer(final IScope scope) throws GamaRuntimeException {
		if (getBuffer() != null) {
			return;
		}
		try {
			final BufferedReader in = new BufferedReader(new FileReader(getFile(scope)));
			final GamaMap<String, String> allLines = GamaMapFactory.create(Types.STRING, Types.STRING);
			String str;
			str = in.readLine();
			while (str != null) {
				allLines.put(str, str + "\n");
				str = in.readLine();
			}
			in.close();
			setBuffer(allLines);
		} catch (final IOException e) {
			throw GamaRuntimeException.create(e, scope);
		}
	}

}
