package miat.gama.extensions.bdi;

import msi.gama.common.interfaces.IKeyword;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.descriptions.IDescription;
import msi.gaml.statements.AbstractStatementSequence;
import msi.gaml.types.IType;
import ummisco.gama.processor.IConcept;
import ummisco.gama.processor.ISymbolKind;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.facet;
import ummisco.gama.processor.GamlAnnotations.facets;
import ummisco.gama.processor.GamlAnnotations.inside;
import ummisco.gama.processor.GamlAnnotations.symbol;

@symbol(name = { SanctionStatement.SANCTION }, kind = ISymbolKind.BEHAVIOR, with_sequence = true, concept = {
		IConcept.BDI })
@inside(kinds = { ISymbolKind.SPECIES, ISymbolKind.MODEL })
@facets(value = {
		@facet(name = IKeyword.NAME, type = IType.ID, optional = true) }, omissible = IKeyword.NAME)
@doc("declare the actions an agent execute when enforcing norms of others during a perception")
public class SanctionStatement extends AbstractStatementSequence{

	public static final String SANCTION = "sanction";
	
	
	public SanctionStatement(IDescription desc) {
		super(desc);
		setName(desc.getName());
	}
	
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
//		if (_when == null || Cast.asBool(scope, _when.value(scope))) {
			return super.privateExecuteIn(scope);
//		}
//		return null;
	}
	
}
