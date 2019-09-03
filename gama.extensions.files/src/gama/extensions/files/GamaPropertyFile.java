/*******************************************************************************************************
 *
 * msi.gama.util.file.GamaPropertyFile.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling
 * and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.extensions.files;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import gama.processor.annotations.IConcept;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.example;
import gama.processor.annotations.GamlAnnotations.file;
import gama.common.geometry.Envelope3D;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.file.GamaFile;
import gama.util.list.GamaListFactory;
import gama.util.list.IList;
import gama.util.map.GamaMapFactory;
import gama.util.map.IMap;
import gaml.statements.Facets;
import gaml.types.IContainerType;
import gaml.types.IType;
import gaml.types.Types;

@file (
		name = "property",
		extensions = { "properties" },
		buffer_type = IType.MAP,
		buffer_content = IType.STRING,
		buffer_index = IType.STRING,
		concept = { IConcept.FILE },
		doc = @doc ("Represents property files"))
public class GamaPropertyFile extends GamaFile<IMap<String, String>, String> {

	@doc (
			value = "This file constructor allows to read a property file (.properties)",
			examples = { @example (
					value = "file f <-property_file(\"file.properties\");",
					isExecutable = false) })
	public GamaPropertyFile(final IScope scope, final String pathName) throws GamaRuntimeException {
		super(scope, pathName);
	}

	@doc (
			value = "This file constructor allows to store a map in a property file (it does not save it - just store it in memory)",
			examples = { @example (
					value = "file f <-property_file(\"file.properties\", map([\"param1\"::1.0,\"param3\"::10.0 ]));",
					isExecutable = false) })
	public GamaPropertyFile(final IScope scope, final String pathName, final IMap<String, String> buffer)
			throws GamaRuntimeException {
		super(scope, pathName, buffer);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see msi.gama.util.GamaFile#fillBuffer()
	 */
	@SuppressWarnings ({ "unchecked", "rawtypes" })
	@Override
	protected void fillBuffer(final IScope scope) throws GamaRuntimeException {
		final Properties p = new Properties();
		final IMap m = GamaMapFactory.create(Types.STRING, Types.STRING);
		try (FileReader f = new FileReader(getFile(scope))) {
			p.load(f);
		} catch (final IOException e) {
			throw GamaRuntimeException.create(e, scope);
		}
		m.putAll(p);
		setBuffer(m);
	}

	@Override
	public IContainerType<?> getGamlType() {
		return Types.FILE.of(Types.STRING, Types.STRING);
	}

	@Override
	public IList<String> getAttributes(final IScope scope) {
		// TODO return the keys of the map as "attributes"
		return GamaListFactory.EMPTY_LIST;
	}

	@Override
	protected void flushBuffer(final IScope scope, final Facets facets) throws GamaRuntimeException {
		final Properties p = new Properties();
		if (getBuffer() != null && !getBuffer().isEmpty()) {
			getBuffer().forEach((a, b) -> {
				p.setProperty(a, b);
			});
		}
		try (FileWriter fw = new FileWriter(getFile(scope))) {
			p.store(fw, null);
		} catch (final IOException e) {}

	}

	@Override
	public Envelope3D computeEnvelope(final IScope scope) {
		// TODO Probably possible to get some information there
		return null;
	}

}
