package gama.extensions.bdi;

import gama.processor.annotations.IConcept;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.type;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.types.GamaType;
import msi.gaml.types.IType;

@SuppressWarnings("unchecked")
@type(name = "social_link", id = SocialLinkType.id, wraps = { SocialLink.class }, concept = { IConcept.TYPE,
		IConcept.BDI })
@doc("represents a social link")
public class SocialLinkType extends GamaType<SocialLink> {

	public final static int id = IType.AVAILABLE_TYPES + 546657;

	@Override
	public boolean canCastToConst() {
		return true;
	}

	@Override
	@doc("cast an object into a social link, if it is an instance of a social link")
	public SocialLink cast(final IScope scope, final Object obj, final Object param, final boolean copy)
			throws GamaRuntimeException {
		if (obj instanceof SocialLink) {
			return (SocialLink) obj;
		}
		return null;
	}

	@Override
	public SocialLink getDefault() {
		return null;
	}

}
