/*******************************************************************************************************
 *
 * gaml.statements.test.SetUpStatement.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.statements.test;

import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.example;
import gama.processor.annotations.GamlAnnotations.inside;
import gama.processor.annotations.GamlAnnotations.symbol;
import gama.processor.annotations.GamlAnnotations.usage;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.descriptions.IDescription;
import gaml.statements.AbstractStatementSequence;

@symbol (
		name = { "setup" },
		kind = ISymbolKind.SEQUENCE_STATEMENT,
		with_sequence = true,
		concept = { IConcept.TEST })
@inside (
		kinds = { ISymbolKind.SPECIES, ISymbolKind.EXPERIMENT, ISymbolKind.MODEL })
@doc (
		value = "The setup statement is used to define the set of instructions that will be executed before every [#test test].",
		usages = { @usage (
				value = "As every test should be independent from the others, the setup will mainly contain initialization of variables that will be used in each test.",
				examples = { @example (
						value = "species Tester {",
						isExecutable = false),
						@example (
								value = "    int val_to_test;",
								isExecutable = false),
						@example (
								value = "",
								isExecutable = false),
						@example (
								value = "    setup {",
								isExecutable = false),
						@example (
								value = "        val_to_test <- 0;",
								isExecutable = false),
						@example (
								value = "    }",
								isExecutable = false),
						@example (
								value = "",
								isExecutable = false),
						@example (
								value = "    test t1 {",
								isExecutable = false),
						@example (
								value = "       // [set of instructions, including asserts]",
								isExecutable = false),
						@example (
								value = "    }",
								isExecutable = false),
						@example (
								value = "}",
								isExecutable = false) }) },
		see = { "test", "assert" })
public class SetUpStatement extends AbstractStatementSequence {
	public SetUpStatement(final IDescription desc) {
		super(desc);
		setName("setup");
	}

	@Override
	public Object executeOn(final IScope scope) throws GamaRuntimeException {
		// does nothing when called « normally »
		return null;
	}

	public Object setup(final IScope scope) throws GamaRuntimeException {
		// calls the « normal » execution defined in the superclass
		return super.executeOn(scope);
	}

}
