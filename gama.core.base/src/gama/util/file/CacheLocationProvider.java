/*******************************************************************************************************
 *
 * gama.util.file.CacheLocationProvider.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.util.file;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.variableresolvers.PathVariableResolver;

import gama.common.util.FileUtils;

public class CacheLocationProvider extends PathVariableResolver {

	public static String NAME = "CACHE_LOC";

	@Override
	public String[] getVariableNames(final String variable, final IResource resource) {
		return new String[] { NAME };
	}

	@Override
	public String getValue(final String variable, final IResource resource) {
		return FileUtils.CACHE().toURI().toASCIIString();
	}

}
