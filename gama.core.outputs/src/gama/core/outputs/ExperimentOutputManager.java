/*******************************************************************************************************
 *
 * gama.core.outputs.ExperimentOutputManager.java, in plugin msi.gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.core.outputs;

import static gama.common.interfaces.IKeyword.LAYOUT;
import static gama.common.preferences.GamaPreferences.Displays.CORE_DISPLAY_LAYOUT;
import static gama.common.preferences.GamaPreferences.Displays.LAYOUTS;

import gama.GAMA;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.example;
import gama.processor.annotations.GamlAnnotations.facet;
import gama.processor.annotations.GamlAnnotations.facets;
import gama.processor.annotations.GamlAnnotations.inside;
import gama.processor.annotations.GamlAnnotations.symbol;
import gama.processor.annotations.GamlAnnotations.usage;
import gama.common.interfaces.IKeyword;
import gama.common.interfaces.outputs.IOutput;
import gama.common.interfaces.outputs.IOutputManager;
import gama.runtime.scope.IScope;
import gaml.compilation.factories.DescriptionFactory;
import gaml.compilation.interfaces.ISymbol;
import gaml.descriptions.IDescription;
import gaml.types.IType;

/**
 * The Class OutputManager.
 *
 * @author Alexis Drogoul modified by Romain Lavaud 05.07.2010
 */
@symbol (
		name = IKeyword.PERMANENT,
		kind = ISymbolKind.OUTPUT,
		with_sequence = true,
		unique_in_context = true,
		concept = { IConcept.BATCH, IConcept.DISPLAY })

@facets (
		omissible = LAYOUT,
		value = { @facet (
				name = LAYOUT,
				type = IType.NONE,
				optional = true,
				doc = @doc (
						deprecated = "Use the layout statement inside 'output' or 'permanent'",
						value = "Either #none, to indicate that no layout will be imposed, or one of the four possible predefined layouts: #stack, #split, #horizontal or #vertical. This layout will be applied to both experiment and simulation display views. In addition, it is possible to define a custom layout using the horizontal() and vertical() operators")),
				@facet (
						name = "toolbars",
						type = IType.BOOL,
						optional = true,
						doc = @doc ("Whether the displays should show their toolbar or not")),
				@facet (
						name = "tabs",
						type = IType.BOOL,
						optional = true,
						doc = @doc ("Whether the displays should show their tab or not")) })

@inside (
		kinds = { ISymbolKind.EXPERIMENT })
@doc (
		value = "Represents the outputs of the experiment itself. In a batch experiment, the permanent section allows to define an output block that will NOT be re-initialized at the beginning of each simulation but will be filled at the end of each simulation.",
		usages = { @usage (
				value = "For instance, this permanent section will allow to display for each simulation the end value of the food_gathered variable:",
				examples = { @example (
						value = "permanent {",
						isExecutable = false),
						@example (
								value = "	display Ants background: rgb('white') refresh_every: 1 {",
								isExecutable = false),
						@example (
								value = "		chart \"Food Gathered\" type: series {",
								isExecutable = false),
						@example (
								value = "			data \"Food\" value: food_gathered;",
								isExecutable = false),
						@example (
								value = "		}",
								isExecutable = false),
						@example (
								value = "	}",
								isExecutable = false),
						@example (
								value = "}",
								isExecutable = false) }) })
public class ExperimentOutputManager extends AbstractOutputManager implements IOutputManager.Experiment {

	public static ExperimentOutputManager createEmpty() {
		return new ExperimentOutputManager(DescriptionFactory.create(IKeyword.PERMANENT, (String[]) null));
	}

	public ExperimentOutputManager(final IDescription desc) {
		super(desc);
	}

	@Override
	public boolean init(final IScope scope) {
		// scope.getGui().hideScreen();
		// DEBUG.OUT("ExperimentOutputManager init");
		final ISymbol layoutDefinition = layout == null ? this : layout;
		final String definitionFacet = layout == null ? LAYOUT : IKeyword.VALUE;
		final Object layoutObject =
				layoutDefinition.getFacetValue(scope, definitionFacet, LAYOUTS.indexOf(CORE_DISPLAY_LAYOUT.getValue()));
		super.init(scope);

		scope.getGui().applyLayout(scope, layoutObject);
		// scope.getGui().showScreen();
		if (scope.getExperiment().getSpecies().isAutorun()) {
			GAMA.startFrontmostExperiment();
		}
		return true;
	}

	// We dont allow permanent outputs for batch experiments to do their first step (to fix Issue
	// #1273) -- Conflicts with Issue #2204
	@Override
	protected boolean initialStep(final IScope scope, final IOutput output) {
		if (scope.getExperiment().getSpecies().isBatch())
			return true;
		return super.initialStep(scope, output);
	}

	@Override
	public void add(final IOutput output) {
		((AbstractOutput) output).setPermanent();
		super.add(output);
	}

	@Override
	public synchronized void dispose() {
		GAMA.getGui().cleanAfterExperiment();
		super.dispose();
	}

}
