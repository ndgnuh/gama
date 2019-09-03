/*******************************************************************************************************
 *
 * gama.util.graph.writer.IGraphWriter.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.util.graph.writer;

import gama.runtime.scope.IScope;
import gama.util.file.IGamaFile;
import gama.util.graph.IGraph;

/**
 * Represents a graph writer, independantly of its implements; it is able to write a gama graph to a file.
 *
 * @author Samuel Thiriot
 */
@SuppressWarnings ({ "rawtypes" })
public interface IGraphWriter {

	void writeGraph(final IScope scope, IGraph gamaGraph, IGamaFile gamaFile, String filename);

}
