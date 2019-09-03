/*******************************************************************************************************
 *
 * gaml.factories.PlatformFactory.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.compilation.factories;

import static gama.processor.annotations.ISymbolKind.PLATFORM;

import java.util.Set;

import gama.processor.annotations.GamlAnnotations.factory;
import gaml.compilation.interfaces.IAgentConstructor;
import gaml.descriptions.PlatformSpeciesDescription;
import gaml.descriptions.SpeciesDescription;
import gaml.statements.Facets;

@factory (
		handles = { PLATFORM })
public class PlatformFactory extends SpeciesFactory {

	public PlatformFactory(final int... handles) {
		super(handles);
	}

	@Override
	public SpeciesDescription createBuiltInSpeciesDescription(final String name, final Class clazz,
			final SpeciesDescription superDesc, final SpeciesDescription parent, final IAgentConstructor helper,
			final Set<String> skills, final Facets userSkills, final String plugin) {
		DescriptionFactory.addSpeciesNameAsType(name);
		return new PlatformSpeciesDescription(name, clazz, superDesc, parent, helper, skills, userSkills, plugin);
	}

}
