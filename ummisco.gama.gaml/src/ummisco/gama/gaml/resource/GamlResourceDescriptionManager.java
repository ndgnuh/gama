/*********************************************************************************************
 *
 * 'GamlResourceDescriptionManager.java, in plugin ummisco.gama.gaml, is part of the source code of the GAMA modeling
 * and simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package ummisco.gama.gaml.resource;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager;

import com.google.inject.Inject;

import msi.gama.common.interfaces.ICollector;
import msi.gama.common.util.Collector;
import ummisco.gama.gaml.indexer.GamlResourceIndexer;
import ummisco.gama.scoping.BuiltinGlobalScopeProvider;

/**
 * The class GamlResourceDescriptionManager.
 *
 * @author drogoul
 * @since 20 avr. 2012
 *
 */
public class GamlResourceDescriptionManager extends DefaultResourceDescriptionManager
		implements IResourceDescription.Manager.AllChangeAware {

	// @Inject private DescriptionUtils descriptionUtils;

	@Inject BuiltinGlobalScopeProvider provider;

	@Override
	protected IResourceDescription internalGetResourceDescription(final Resource resource,
			final IDefaultResourceDescriptionStrategy strategy) {
		return new GamlResourceDescription(resource, strategy, getCache(), provider);
	}

	@Override
	public boolean isAffected(final Collection<Delta> deltas, final IResourceDescription candidate,
			final IResourceDescriptions context) {
		// final boolean result = false;
		final URI newUri = candidate.getURI();
		try (ICollector<URI> deltaUris = Collector.newSet()) {
			for (final Delta d : deltas) {
				deltaUris.add(GamlResourceServices.properlyEncodedURI(d.getUri()));
			}
			final Iterator<URI> it = GamlResourceIndexer.INSTANCE.allImportsOf(newUri);
			while (it.hasNext()) {
				final URI next = it.next();
				if (deltaUris.contains(next))
					return true;
			}
			return super.isAffected(deltas, candidate, context);
		}
	}

	@Override
	public boolean isAffectedByAny(final Collection<Delta> deltas, final IResourceDescription candidate,
			final IResourceDescriptions context) throws IllegalArgumentException {
		return isAffected(deltas, candidate, context);
	}
}
