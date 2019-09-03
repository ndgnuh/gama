package gama.extensions.bdi;

import gama.processor.annotations.IConcept;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.type;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.types.GamaType;
import gaml.types.IType;

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
