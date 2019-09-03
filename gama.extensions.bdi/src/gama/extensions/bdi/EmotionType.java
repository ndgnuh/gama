package gama.extensions.bdi;

import gama.processor.annotations.IConcept;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.type;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.types.GamaType;
import gaml.types.IType;

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
