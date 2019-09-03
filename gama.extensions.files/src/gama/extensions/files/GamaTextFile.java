/*******************************************************************************************************
 *
 * msi.gama.util.file.GamaTextFile.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.extensions.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import gama.processor.annotations.IConcept;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.example;
import gama.processor.annotations.GamlAnnotations.file;
import msi.gama.common.geometry.Envelope3D;
import msi.gama.common.util.TextBuilder;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gama.util.file.GamaFile;
import msi.gama.util.list.GamaListFactory;
import msi.gama.util.list.IList;
import msi.gaml.operators.Strings;
import msi.gaml.statements.Facets;
import msi.gaml.types.IContainerType;
import msi.gaml.types.IType;
import msi.gaml.types.Types;

@file (
		name = "text",
		extensions = { "txt", "data", "text" },
		buffer_type = IType.LIST,
		buffer_content = IType.STRING,
		buffer_index = IType.INT,
		concept = { IConcept.FILE, IConcept.TEXT, IConcept.CSV, IConcept.XML },
		doc = @doc ("Represents an arbitrary text file. The internal contents is a list of strings (lines)"))
public class GamaTextFile extends GamaFile<IList<String>, String> {

	@doc (
			value = "This file constructor allows to read a text file (.txt, .data, .text)",
			examples = { @example (
					value = "file f <-text_file(\"file.txt\");",
					isExecutable = false) })
	public GamaTextFile(final IScope scope, final String pathName) throws GamaRuntimeException {
		super(scope, pathName);
	}

	@doc (
			value = "This file constructor allows to store a list of string in a text file (it does not save it - just store it in memory)",
			examples = { @example (
					value = "file f <-text_file(\"file.txt\", [\"item1\",\"item2\",\"item3\"]);",
					isExecutable = false) })
	public GamaTextFile(final IScope scope, final String pathName, final IList<String> text) {
		super(scope, pathName, text);
	}

	@Override
	public IContainerType<?> getGamlType() {
		return Types.FILE.of(Types.INT, Types.STRING);
	}

	@Override
	public String _stringValue(final IScope scope) throws GamaRuntimeException {
		getContents(scope);
		try (TextBuilder sb = TextBuilder.create()) {
			for (final String s : getBuffer().iterable(scope)) {
				sb.append(s).append(Strings.LN);
			}
			sb.setLength(sb.length() - 1);
			return sb.toString();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see msi.gama.util.GamaFile#fillBuffer()
	 */
	@Override
	protected void fillBuffer(final IScope scope) throws GamaRuntimeException {
		if (getBuffer() != null)
			return;
		try (BufferedReader in = new BufferedReader(new FileReader(getFile(scope)))) {
			final IList<String> allLines = GamaListFactory.create(Types.STRING);
			String str = in.readLine();
			while (str != null) {
				allLines.add(str);
				str = in.readLine();
			}
			setBuffer(allLines);
		} catch (final IOException e) {
			throw GamaRuntimeException.create(e, scope);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see msi.gama.util.GamaFile#flushBuffer()
	 */
	@Override
	protected void flushBuffer(final IScope scope, final Facets facets) throws GamaRuntimeException {
		if (getBuffer() != null && !getBuffer().isEmpty()) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(scope)))) {
				for (final String s : getBuffer()) {
					writer.append(s).append(Strings.LN);
				}
				writer.flush();
			} catch (final IOException e) {
				throw GamaRuntimeException.create(e, scope);
			}
		}

	}

	@Override
	public Envelope3D computeEnvelope(final IScope scope) {
		return null;
	}

}
