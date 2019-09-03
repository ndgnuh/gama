/*******************************************************************************************************
 *
 * gaml.factories.VariableFactory.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.compilation.factories;

import static gama.common.interfaces.IKeyword.ON_CHANGE;
import static gama.common.interfaces.IKeyword.PARAMETER;
import static gama.common.interfaces.IKeyword.VAR;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.factory;
import gaml.descriptions.FacetProto;
import gaml.descriptions.IDescription;
import gaml.descriptions.IExpressionDescription;
import gaml.descriptions.SymbolProto;
import gaml.descriptions.VariableDescription;
import gaml.statements.Facets;

/**
 * Written by drogoul Modified on 26 nov. 2008
 *
 * @todo Description
 */
@factory (
		handles = { ISymbolKind.Variable.CONTAINER, ISymbolKind.Variable.NUMBER, ISymbolKind.Variable.REGULAR,
				ISymbolKind.Variable.SIGNAL, ISymbolKind.PARAMETER })
public class VariableFactory extends SymbolFactory {

	public VariableFactory(final int... handles) {
		super(handles);
	}

	@Override
	protected IDescription buildDescription(final String keyword, final Facets facets, final EObject element,
			final Iterable<IDescription> children, final IDescription enclosing, final SymbolProto proto) {
		if (keyword.equals(PARAMETER)) {

			final Map<String, FacetProto> possibleFacets = proto.getPossibleFacets();
			// We copy the relevant facets from the targeted var of the
			// parameter
			final VariableDescription targetedVar = enclosing.getModelDescription().getAttribute(facets.getLabel(VAR));
			if (targetedVar != null) {
				for (final String key : possibleFacets.keySet()) {
					if (key.equals(ON_CHANGE)) {
						continue;
					}
					final IExpressionDescription expr = targetedVar.getFacet(key);
					if (expr != null) {
						facets.putIfAbsent(key, expr);
					}
				}

			}
		}
		return new VariableDescription(keyword, enclosing, element, facets);
	}

}
