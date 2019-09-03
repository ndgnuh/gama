package miat.gama.extensions.bdi;

import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.types.GamaType;
import msi.gaml.types.IType;
import ummisco.gama.processor.IConcept;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.type;

@type(name = "Norm", id = NormType.id, wraps = { Norm.class }, concept = { IConcept.TYPE, IConcept.BDI })
@doc("represents a norm")
public class NormType extends GamaType<Norm>{

	public final static int id = IType.AVAILABLE_TYPES + 546660;

	@Override
	public boolean canCastToConst() {
		return true;
	}

	@Override
	@doc("cast an object into a norm, if it is an instance of a norm")
	public Norm cast(IScope scope, Object obj, Object param, boolean copy) throws GamaRuntimeException {
		if (obj instanceof Norm) {
			return (Norm) obj;
		}
		return null;
	}

	@Override
	public Norm getDefault() {
		return null;
	}
	
}
