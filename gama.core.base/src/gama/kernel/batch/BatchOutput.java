/*******************************************************************************************************
 *
 * gama.kernel.batch.BatchOutput.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gama.kernel.batch;

import gama.common.interfaces.IKeyword;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.ISymbolKind;
import gama.processor.annotations.GamlAnnotations.facet;
import gama.processor.annotations.GamlAnnotations.facets;
import gama.processor.annotations.GamlAnnotations.inside;
import gama.processor.annotations.GamlAnnotations.symbol;
import gaml.compilation.Symbol;
import gaml.compilation.interfaces.ISymbol;
import gaml.descriptions.IDescription;
import gaml.types.IType;

@symbol (
		name = IKeyword.SAVE_BATCH,
		kind = ISymbolKind.BATCH_METHOD,
		with_sequence = false,
		concept = { IConcept.BATCH },
		internal = true)
@inside (
		kinds = { ISymbolKind.EXPERIMENT })
@facets (
		value = { @facet (
				name = IKeyword.TO,
				type = IType.LABEL,
				optional = false,
				internal = true),
				@facet (
						name = IKeyword.REWRITE,
						type = IType.BOOL,
						optional = true,
						internal = true),
				@facet (
						name = IKeyword.DATA,
						type = IType.NONE,
						optional = true,
						internal = true) },
		omissible = IKeyword.DATA)
public class BatchOutput extends Symbol {

	// A placeholder for a file output
	// TODO To be replaced by a proper "save" command, now that it accepts
	// new file types.

	public BatchOutput(final IDescription desc) {
		super(desc);
	}

	@Override
	public void setChildren(final Iterable<? extends ISymbol> commands) {}

}