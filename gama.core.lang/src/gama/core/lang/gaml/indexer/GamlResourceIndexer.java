/*********************************************************************************************
 *
 * 'GamlResourceIndexer.java, in plugin gama.core.gaml, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.core.lang.gaml.indexer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.EcoreUtil2;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.google.common.base.Objects;
import com.google.common.collect.Iterators;
import com.google.common.collect.LinkedHashMultimap;
import com.google.inject.Singleton;

import gama.core.lang.gaml.resource.GamlResource;
import gama.core.lang.gaml.resource.GamlResourceServices;
import msi.gama.common.interfaces.BiConsumerWithPruning;
import msi.gama.util.map.GamaMapFactory;
import msi.gama.util.map.IMap;
import ummisco.gama.gaml.ExperimentFileStructure;
import ummisco.gama.gaml.GamlPackage;
import ummisco.gama.gaml.Import;
import ummisco.gama.gaml.Model;
import ummisco.gama.gaml.impl.ModelImpl;

@Singleton
@SuppressWarnings ({ "unchecked", "rawtypes" })
public class GamlResourceIndexer {

	DirectedGraph<URI, Edge> index = new SimpleDirectedGraph(Edge.class);
	protected final static IMap EMPTY_MAP = GamaMapFactory.create();
	public static final Object IMPORTED_URIS = "ImportedURIs";

	public static GamlResourceIndexer INSTANCE = new GamlResourceIndexer();

	private GamlResourceIndexer() {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.addResourceChangeListener(event -> {
			if (event.getBuildKind() == IncrementalProjectBuilder.CLEAN_BUILD) {
				eraseIndex();
			}
		}, IResourceChangeEvent.PRE_BUILD);
	}

	protected IMap<URI, String> getImportsAsAbsoluteURIS(final URI baseURI, final Model m) {
		IMap<URI, String> result = EMPTY_MAP;
		if (((ModelImpl) m).eIsSet(GamlPackage.MODEL__IMPORTS)) {
			result = GamaMapFactory.create();
			for (final Import e : m.getImports()) {
				final String u = e.getImportURI();
				if (u != null) {
					URI uri = URI.createURI(u, true);
					uri = GamlResourceServices.properlyEncodedURI(uri.resolve(baseURI));
					final String label = e.getName();
					result.put(uri, label);
				}
			}
		}
		return result;
	}

	protected IMap<URI, String> getImportsAsAbsoluteURIS(final URI baseURI, final ExperimentFileStructure m) {
		final IMap<URI, String> result = GamaMapFactory.create();
		final String u = m.getExp().getImportURI();
		if (u != null) {
			URI uri = URI.createURI(u, true);
			uri = GamlResourceServices.properlyEncodedURI(uri.resolve(baseURI));
			result.put(uri, null);
		}

		return result;
	}

	public IMap<URI, String> allLabeledImportsOf(final GamlResource r) {
		return r.getCache().get(IMPORTED_URIS, r, () -> allLabeledImportsOf(r.getURI()));
	}

	static class Edge {
		String label;
		final URI target;

		Edge(final String l, final URI target) {
			this.label = l;
			this.target = target;
		}

		URI getTarget() {
			return target;
		}

		String getLabel() {
			return label;
		}

		public void setLabel(final String b) {
			label = b;
		}
	}

	void addImport(final URI from, final URI to, final String label) {
		index.addVertex(to);
		index.addVertex(from);
		index.addEdge(from, to, new Edge(label, to));
	}

	public void clearResourceSet(final ResourceSet resourceSet) {
		final boolean wasDeliver = resourceSet.eDeliver();
		try {
			resourceSet.eSetDeliver(false);
			resourceSet.getResources().clear();
		} finally {
			resourceSet.eSetDeliver(wasDeliver);
		}
	}

	/**
	 * Synchronized method to avoid concurrent errors in the graph in case of a parallel resource loader
	 */
	public synchronized EObject updateImports(final GamlResource r) {
		final URI baseURI = GamlResourceServices.properlyEncodedURI(r.getURI());
		final Set<Edge> nativeEdges = index.containsVertex(baseURI) ? index.outgoingEdgesOf(baseURI) : null;
		final Set<Edge> edges =
				nativeEdges == null || nativeEdges.isEmpty() ? Collections.EMPTY_SET : new HashSet(nativeEdges);
		if (r.getContents().isEmpty())
			return null;
		final EObject contents = r.getContents().get(0);
		if (contents == null)
			return null;
		final boolean isModel = contents instanceof Model;
		final boolean isExpe = contents instanceof ExperimentFileStructure;
		final IMap<URI, String> added;
		if (isModel) {
			added = getImportsAsAbsoluteURIS(baseURI, (Model) contents);
		} else if (isExpe) {
			added = getImportsAsAbsoluteURIS(baseURI, (ExperimentFileStructure) contents);
		} else
			return null;
		final EObject[] faulty = new EObject[1];
		if (added.forEachPair(new BiConsumerWithPruning<URI, String>() {

			@Override
			public boolean process(final URI uri, final String b) {
				if (baseURI.equals(uri))
					return true;
				final Iterator<Edge> iterator = edges.iterator();
				boolean found = false;
				while (iterator.hasNext()) {
					final Edge edge = iterator.next();
					if (edge.getTarget().equals(uri)) {
						found = true;
						if (!Objects.equal(edge.getLabel(), b)) {
							edge.setLabel(b);
						}
						iterator.remove();
						break;
					}
				}
				if (!found) {
					if (EcoreUtil2.isValidUri(r, uri)) {
						final boolean alreadyThere = index.containsVertex(uri);
						addImport(baseURI, uri, b);
						if (!alreadyThere) {
							// This call should trigger the recursive call to
							// updateImports()
							r.getResourceSet().getResource(uri, true);
						}
					} else {
						if (isModel) {
							faulty[0] = findImport((Model) contents, uri);
						} else {
							faulty[0] = findImport((ExperimentFileStructure) contents, uri);
						}
						return false;
					}
				}
				return true;
			}

			private EObject findImport(final ExperimentFileStructure model, final URI uri) {
				if (model.getExp().getImportURI().contains(URI.decode(uri.lastSegment())))
					return model;
				if (uri.equals(baseURI) && model.getExp().getImportURI().isEmpty())
					return model;
				return null;
			}

			private EObject findImport(final Model model, final URI uri) {
				for (final Import e : model.getImports()) {
					if (e.getImportURI().contains(URI.decode(uri.lastSegment())))
						return e;
					if (uri.equals(baseURI) && e.getImportURI().isEmpty())
						return e;
				}
				return null;
			}
		})) {
			index.removeAllEdges(edges);
			return null;
		}
		return faulty[0];

	}

	public LinkedHashMultimap<String, GamlResource> validateImportsOf(final GamlResource resource) {
		final IMap<URI, String> uris = allLabeledImportsOf(resource);
		uris.remove(GamlResourceServices.properlyEncodedURI(resource.getURI()));
		if (!uris.isEmpty()) {
			final LinkedHashMultimap<String, GamlResource> imports = LinkedHashMultimap.create();
			if (uris.forEachPair((a, b) -> {
				final GamlResource r = (GamlResource) resource.getResourceSet().getResource(a, true);
				if (r.hasErrors()) {
					resource.invalidate(r, "Errors detected");
					return false;
				}
				imports.put(b, r);
				return true;
			}))
				return imports;

		}
		return null;
	}

	public Set<URI> directImportersOf(final URI uri) {
		final URI newURI = GamlResourceServices.properlyEncodedURI(uri);
		if (index.containsVertex(newURI))
			return new HashSet(Graphs.predecessorListOf(index, newURI));
		return Collections.EMPTY_SET;
	}

	public Set<URI> directImportsOf(final URI uri) {
		final URI newURI = GamlResourceServices.properlyEncodedURI(uri);
		if (index.containsVertex(newURI))
			return new HashSet(Graphs.successorListOf(index, newURI));
		return Collections.EMPTY_SET;
	}

	private IMap<URI, String> allLabeledImportsOf(final URI uri) {
		final URI newURI = GamlResourceServices.properlyEncodedURI(uri);
		final IMap<URI, String> result = GamaMapFactory.create();
		allLabeledImports(newURI, null, result);
		return result;
	}

	private void allLabeledImports(final URI uri, final String currentLabel, final Map<URI, String> result) {
		if (!result.containsKey(uri)) {
			result.put(uri, currentLabel);
			if (indexes(uri)) {
				final Collection<Edge> edges = index.outgoingEdgesOf(uri);
				for (final Edge e : edges) {
					allLabeledImports(index.getEdgeTarget(e), e.getLabel() == null ? currentLabel : e.getLabel(),
							result);
				}
			}
		}

	}

	/**
	 * @see gama.core.gaml.indexer.IModelIndexer#allImportsOf(org.eclipse.emf.common.util.URI)
	 */
	public Iterator<URI> allImportsOf(final URI uri) {
		if (!indexes(uri))
			return Iterators.singletonIterator(uri);// .emptyIterator();
		final Iterator<URI> result = new BreadthFirstIterator(index, GamlResourceServices.properlyEncodedURI(uri));
		result.next(); // to eliminate the uri
		return result;
	}

	public boolean indexes(final URI uri) {
		return index.containsVertex(GamlResourceServices.properlyEncodedURI(uri));
	}

	public boolean equals(final URI uri1, final URI uri2) {
		if (uri1 == null)
			return uri2 == null;
		if (uri2 == null)
			return false;
		return GamlResourceServices.properlyEncodedURI(uri1).equals(GamlResourceServices.properlyEncodedURI(uri2));
	}

	public void eraseIndex() {
		// DEBUG.OUT("Erasing GAML indexer index");
		index = new SimpleDirectedGraph(Edge.class);
	}

	public boolean isImported(final GamlResource r) {
		return !directImportersOf(r.getURI()).isEmpty();
	}

}
