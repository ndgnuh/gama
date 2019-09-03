/*******************************************************************************************************
 *
 * msi.gaml.factories.SpeciesFactory.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gaml.compilation.factories;

import static ummisco.gama.processor.ISymbolKind.SPECIES;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import msi.gaml.compilation.interfaces.IAgentConstructor;
import msi.gaml.descriptions.IDescription;
import msi.gaml.descriptions.SpeciesDescription;
import msi.gaml.descriptions.SymbolProto;
import msi.gaml.descriptions.TypeDescription;
import msi.gaml.statements.Facets;
import ummisco.gama.processor.GamlAnnotations.factory;

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
