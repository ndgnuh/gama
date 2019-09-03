/*******************************************************************************************************
 *
 * gaml.statements.PrimitiveStatement.java, in plugin gama.core, is part of the source code of the GAMA modeling
 * and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.statements;

import gama.common.interfaces.IAgent;
import gama.common.interfaces.IKeyword;
import gama.common.interfaces.ISkill;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.facet;
import gama.processor.annotations.GamlAnnotations.facets;
import gama.processor.annotations.GamlAnnotations.inside;
import gama.processor.annotations.GamlAnnotations.symbol;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.compilation.annotations.validator;
import gaml.compilation.interfaces.IGamaHelper;
import gaml.compilation.interfaces.ISymbol;
import gaml.compilation.interfaces.IDescriptionValidator.NullValidator;
import gaml.descriptions.IDescription;
import gaml.descriptions.PrimitiveDescription;
import gaml.species.AbstractSpecies;
import gaml.types.IType;

/**
 * The Class ActionCommand.
 *
 * @author drogoul
 */
@symbol (
		name = IKeyword.PRIMITIVE,
		kind = ISymbolKind.BEHAVIOR,
		with_sequence = true,
		with_args = true,
		internal = true,
		concept = { IConcept.ACTION, IConcept.SYSTEM })
@inside (
		kinds = { ISymbolKind.SPECIES, ISymbolKind.EXPERIMENT, ISymbolKind.MODEL },
		symbols = IKeyword.CHART)
@facets (
		value = { @facet (
				name = IKeyword.NAME,
				type = IType.ID,
				optional = false,
				doc = { @doc ("The name of this primitive") }),
				@facet (
						name = IKeyword.VIRTUAL,
						type = IType.BOOL,
						optional = true,
						doc = { @doc ("Indicates if this primitive is virtual or not. A virtual primitive does not contain code and must be redefined in the species that implement the skill or extend the species that contain it") }),
				@facet (
						name = IKeyword.TYPE,
						type = IType.TYPE_ID,
						optional = true,
						doc = { @doc ("The type of the value returned by this primitive") }) },
		omissible = IKeyword.NAME)
// Necessary to avoid running the validator from ActionStatement
@validator (NullValidator.class)
@doc ("A primitve is an action written in Java (as opposed to GAML for regular actions")
@SuppressWarnings ({ "rawtypes" })
public class PrimitiveStatement extends ActionStatement {

	// Declaring a null validator because primites dont need to be checked

	private ISkill skill = null;
	private final IGamaHelper helper;

	public PrimitiveStatement(final IDescription desc) {
		super(desc);
		helper = getDescription().getHelper();
	}

	@Override
	public PrimitiveDescription getDescription() {
		return (PrimitiveDescription) description;
	}

	@Override
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {
		Object result = null;
		final Arguments myArgs = actualArgs.get();
		scope.stackArguments(myArgs);
		final IAgent agent = scope.getAgent();
		result = helper.run(scope, agent, skill == null ? agent : skill);
		return result;
	}

	@Override
	public void setRuntimeArgs(final IScope scope, final Arguments args) {
		actualArgs.set(args);
	}

	@Override
	public void setEnclosing(final ISymbol enclosing) {
		if (enclosing instanceof AbstractSpecies) {
			skill = ((AbstractSpecies) enclosing).getSkillInstanceFor(helper.getSkillClass());
		}
	}

}
