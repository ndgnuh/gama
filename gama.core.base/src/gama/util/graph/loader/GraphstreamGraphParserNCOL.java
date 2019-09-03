/*******************************************************************************************************
 *
 * gama.util.graph.loader.GraphstreamGraphParserNCOL.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.util.graph.loader;

import gama.core.ext.graphstream.FileSource;
import gama.core.ext.graphstream.FileSourceNCol;

public class GraphstreamGraphParserNCOL extends GraphStreamGraphParserAbstract {

	@Override
	protected FileSource getFileSource() {
		return new FileSourceNCol();
	}

}
