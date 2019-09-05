/*******************************************************************************************************
 *
 * gaml.factories.SpeciesFactory.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.compilation.factories;

import static gama.processor.annotations.ISymbolKind.SPECIES;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import gama.processor.annotations.GamlAnnotations.factory;
import gaml.compilation.interfaces.IAgentConstructor;
import gaml.descriptions.IDescription;
import gaml.descriptions.SpeciesDescription;
import gaml.descriptions.TypeDescription;
import gaml.prototypes.SymbolProto;
import gaml.statements.Facets;

/**
 * SpeciesFactory.
 *
 * @author drogoul 25 oct. 07
 */

@factory (
		handles = { SPECIES })
@SuppressWarnings ({ "rawtypes" })
public class SpeciesFactory extends SymbolFactory {

	public SpeciesFactory(final int... handles) {
		super(handles);
	}

	@Override
	protected TypeDescription buildDescription(final String keyword, final Facets facets, final EObject element,
			final Iterable<IDescription> children, final IDescription sd, final SymbolProto proto) {
		return new SpeciesDescription(keyword, null, (SpeciesDescription) sd, null, children, element, facets);
	}

	public SpeciesDescription createBuiltInSpeciesDescription(final String name, final Class clazz,
			final SpeciesDescription superDesc, final SpeciesDescription parent, final IAgentConstructor helper,
			final Set<String> skills, final Facets userSkills, final String plugin) {
		DescriptionFactory.addSpeciesNameAsType(name);
		return new SpeciesDescription(name, clazz, superDesc, parent, helper, skills, userSkills, plugin);
	}

}
