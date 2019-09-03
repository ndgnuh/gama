/*******************************************************************************************************
 *
 * msi.gaml.factories.PlatformFactory.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gaml.compilation.factories;

import static ummisco.gama.processor.ISymbolKind.PLATFORM;

import java.util.Set;

import msi.gaml.compilation.interfaces.IAgentConstructor;
import msi.gaml.descriptions.PlatformSpeciesDescription;
import msi.gaml.descriptions.SpeciesDescription;
import msi.gaml.statements.Facets;
import ummisco.gama.processor.GamlAnnotations.factory;

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
