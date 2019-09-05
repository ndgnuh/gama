/*******************************************************************************************************
 *
 * gaml.factories.StatementFactory.java, in plugin gama.core, is part of the source code of the GAMA modeling
 * and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.compilation.factories;

import org.eclipse.emf.ecore.EObject;

import gama.common.interfaces.IKeyword;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.factory;
import gaml.descriptions.ActionDescription;
import gaml.descriptions.IDescription;
import gaml.descriptions.PrimitiveDescription;
import gaml.descriptions.StatementDescription;
import gaml.descriptions.StatementRemoteWithChildrenDescription;
import gaml.descriptions.StatementWithChildrenDescription;
import gaml.prototypes.SymbolProto;
import gaml.statements.Facets;

/**
 * Written by drogoul Modified on 8 févr. 2010
 *
 * @todo Description
 *
 */
@factory (
		handles = { ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.SINGLE_STATEMENT, ISymbolKind.BEHAVIOR,
				ISymbolKind.ACTION, ISymbolKind.LAYER, ISymbolKind.BATCH_METHOD, ISymbolKind.OUTPUT })
public class StatementFactory extends SymbolFactory implements IKeyword {

	public StatementFactory(final int... handles) {
		super(handles);
	}

	@Override
	protected StatementDescription buildDescription(final String keyword, final Facets facets, final EObject element,
			final Iterable<IDescription> children, final IDescription enclosing, final SymbolProto proto) {
		if (proto.isPrimitive()) { return new PrimitiveDescription(enclosing, element, children, facets, null); }
		if (keyword.equals(ACTION)) { return new ActionDescription(keyword, enclosing, children, element, facets); }
		if (proto.hasSequence() && children != null) {
			if (proto.isRemoteContext()) {
				return new StatementRemoteWithChildrenDescription(keyword, enclosing, children, proto.hasArgs(),
						element, facets, null);
			}
			return new StatementWithChildrenDescription(keyword, enclosing, children, proto.hasArgs(), element, facets,
					null);
		}
		return new StatementDescription(keyword, enclosing, proto.hasArgs(), /* children, */ element, facets, null);
	}

}
