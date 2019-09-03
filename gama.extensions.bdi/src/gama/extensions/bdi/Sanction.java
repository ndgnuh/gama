package gama.extensions.bdi;

import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.getter;
import gama.processor.annotations.GamlAnnotations.variable;
import gama.processor.annotations.GamlAnnotations.vars;
import msi.gama.common.interfaces.IValue;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.types.IType;
import msi.gaml.types.Types;

@vars ({ @variable (
		name = "name",
		type = IType.STRING,
		doc = @doc ("The name of this sanction")),

})
public class Sanction implements IValue{

	private SanctionStatement sanctionStatement;
	
	@getter ("name")
	public String getName() {
		return this.sanctionStatement.getName();
	}
	
	public SanctionStatement getSanctionStatement() {
		return this.sanctionStatement;
	}
	
	public Sanction(){
		super();
	}
	
	public Sanction(final SanctionStatement statement) {
		super();
		this.sanctionStatement = statement;
	}
	
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		final Sanction other = (Sanction) obj;
		if (sanctionStatement == null) {
			if (other.sanctionStatement != null) { return false; }
		} else if (!sanctionStatement.equals(other.sanctionStatement)) { return false; }
		return true;
	}
	
	@Override
	public String serialize(boolean includingBuiltIn) {
		return "Sanction( " + sanctionStatement + ")";
	}

	@Override
	public IType<?> getGamlType() {
		return Types.get(SanctionType.id);
//		return null;
	}

	@Override
	public String stringValue(IScope scope) throws GamaRuntimeException {
		return "Sanction( " + sanctionStatement + ")";
	}

	@Override
	public IValue copy(IScope scope) throws GamaRuntimeException {
		return new Sanction(sanctionStatement);
	}

}
