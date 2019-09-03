/*********************************************************************************************
 *
 * 'GamaListReducer.java, in plugin ummisco.gama.serialize, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package irit.gama.extensions.serialize.gamaType.reduced;

import msi.gama.runtime.scope.IScope;
import msi.gama.util.list.GamaListFactory;
import msi.gama.util.list.IList;
import msi.gaml.types.Types;

@SuppressWarnings ({ "rawtypes" })
public class GamaListReducerNetwork extends GamaListReducer {

	public GamaListReducerNetwork(final IList l) {
		super(l);
	}

	@Override
	public IList constructObject(final IScope scope) {
		// System.out.println("read "+contentTypeListReducer+ " "+valuesListReducer );
		// scope.getAgent().getPopulationFor(speciesName)
		// (microSpeciesName)getMicroSpecies(contentTypeListReducer);
		return GamaListFactory.create(scope, Types.NO_TYPE, this.getValuesListReducer());
	}
}
