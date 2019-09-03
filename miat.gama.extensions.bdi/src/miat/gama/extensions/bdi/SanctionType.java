package miat.gama.extensions.bdi;

import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.types.GamaType;
import msi.gaml.types.IType;
import ummisco.gama.processor.IConcept;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.type;

@type(name = "Sanction", id = SanctionType.id, wraps = { Sanction.class }, concept = { IConcept.TYPE, IConcept.BDI })
@doc("represents a sanction")
public class SanctionType extends GamaType<Sanction>{
//
	public final static int id = IType.AVAILABLE_TYPES + 546661;
	
	@Override
	public boolean canCastToConst() {
		return true;
	}

	@Override
	@doc("cast an object as a sanction, if it is an instance of a sanction")
	public Sanction cast(IScope scope, Object obj, Object param, boolean copy) throws GamaRuntimeException {
		if (obj instanceof Sanction) {
			return (Sanction) obj;
		}
		return null;
	}

	@Override
	public Sanction getDefault() {
		return null;
	}

}
