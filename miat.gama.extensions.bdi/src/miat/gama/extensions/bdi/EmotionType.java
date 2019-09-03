package miat.gama.extensions.bdi;

import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.types.GamaType;
import msi.gaml.types.IType;
import ummisco.gama.processor.IConcept;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.type;

@SuppressWarnings("unchecked")
@type(name = "emotion", id = EmotionType.id, wraps = { Emotion.class }, concept = { IConcept.TYPE, IConcept.BDI })
@doc("represents the type emotion")
public class EmotionType extends GamaType<Emotion> {

	public final static int id = IType.AVAILABLE_TYPES + 546656;

	@Override
	public boolean canCastToConst() {
		return true;
	}

	@Override
	@doc("cast an object instance of emotion as an emotion")
	public Emotion cast(final IScope scope, final Object obj, final Object val, final boolean copy)
			throws GamaRuntimeException {
		if (obj instanceof Emotion) {
			return (Emotion) obj;
		}
		return null;
	}

	@Override
	public Emotion getDefault() {
		// TODO Auto-generated method stub
		return null;
	}

}
