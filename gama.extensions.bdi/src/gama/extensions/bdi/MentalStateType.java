package gama.extensions.bdi;

import gama.processor.annotations.IConcept;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.type;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.types.GamaType;
import msi.gaml.types.IType;

@SuppressWarnings("unchecked")
@type(name = "mental_state", id = MentalStateType.id, wraps = { MentalState.class }, concept = { IConcept.TYPE, IConcept.BDI })
@doc("a type representing a mental state")
public class MentalStateType extends GamaType<MentalState>{

	public final static int id = IType.AVAILABLE_TYPES + 546658;
	
	@Override
	public boolean canCastToConst() {
		return true;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	@doc("cast an object as a mental state if it is an instance o a mental state")
	public MentalState cast(final IScope scope, final Object obj, final Object val, final boolean copy)
			throws GamaRuntimeException {
		if (obj instanceof MentalState) {
			return (MentalState) obj;
		}
		if (obj instanceof String) {
			return new MentalState((String) obj);
		}
		return null;
	}

	@Override
	public MentalState getDefault() {
		return null;
	}
	
}
