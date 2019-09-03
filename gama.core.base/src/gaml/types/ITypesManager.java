/*******************************************************************************************************
 *
 * gaml.types.ITypesManager.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.types;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IDisposable;
import gaml.descriptions.ModelDescription;
import gaml.descriptions.SpeciesDescription;

public interface ITypesManager extends IDisposable {

	void alias(String existingTypeName, String otherTypeName);

	boolean containsType(String s);

	IType<?> get(String type);

	IType<? extends IAgent> addSpeciesType(SpeciesDescription species);

	void init(ModelDescription model);

	void setParent(ITypesManager typesManager);

	<Support> IType<Support> initType(String keyword, IType<Support> typeInstance, int id, int varKind,
			Class<Support> support);

	public Iterable<IType<?>> getAllTypes();

}