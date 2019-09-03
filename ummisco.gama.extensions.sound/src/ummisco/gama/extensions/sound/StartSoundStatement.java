/*********************************************************************************************
 *
 * 'StartSoundStatement.java, in plugin ummisco.gama.extensions.sound, is part of the source code of the GAMA modeling
 * and simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package ummisco.gama.extensions.sound;

import java.io.File;

import msi.gama.common.interfaces.IAgent;
import msi.gama.common.interfaces.IKeyword;
import msi.gama.common.util.FileUtils;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.compilation.annotations.validator;
import msi.gaml.compilation.interfaces.IDescriptionValidator;
import msi.gaml.compilation.interfaces.ISymbol;
import msi.gaml.descriptions.IDescription;
import msi.gaml.expressions.IExpression;
import msi.gaml.statements.AbstractStatementSequence;
import msi.gaml.types.IType;
import ummisco.gama.extensions.sound.StartSoundStatement.StartSoundValidator;
import ummisco.gama.processor.IConcept;
import ummisco.gama.processor.ISymbolKind;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.facet;
import ummisco.gama.processor.GamlAnnotations.facets;
import ummisco.gama.processor.GamlAnnotations.inside;
import ummisco.gama.processor.GamlAnnotations.symbol;

@symbol (
		name = IKeyword.START_SOUND,
		kind = ISymbolKind.SEQUENCE_STATEMENT,
		concept = { IConcept.SOUND },
		with_sequence = true,
		doc = @doc ("Starts playing a music file. The supported formats are aif, au, mp3, wav. One agent"))
@inside (
		kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT })
@facets (
		value = { @facet (
				name = IKeyword.SOURCE,
				type = IType.STRING,
				optional = false,
				doc = @doc ("The path to music file. This path is relative to the path of the model.")),
				@facet (
						name = IKeyword.MODE,
						type = IType.ID,
						values = { IKeyword.OVERWRITE, IKeyword.IGNORE },
						optional = true,
						doc = @doc ("Mode of ")),
				@facet (
						name = IKeyword.REPEAT,
						type = IType.BOOL,
						optional = true,
						doc = @doc ("")) })
@validator (StartSoundValidator.class)
@SuppressWarnings ({ "rawtypes" })
@doc ("Allows to start the sound output")
public class StartSoundStatement extends AbstractStatementSequence {

	public static class StartSoundValidator implements IDescriptionValidator {

		/**
		 * Method validate()
		 * 
		 * @see msi.gaml.compilation.interfaces.IDescriptionValidator#validate(msi.gaml.descriptions.IDescription)
		 */
		@Override
		public void validate(final IDescription cd) {

		}
	}

	private final IExpression source;
	private final IExpression mode;
	private final IExpression repeat;

	private AbstractStatementSequence sequence = null;

	public StartSoundStatement(final IDescription desc) {
		super(desc);

		source = getFacet(IKeyword.SOURCE);
		mode = getFacet(IKeyword.MODE);
		repeat = getFacet(IKeyword.REPEAT);
	}

	@Override
	public void setChildren(final Iterable<? extends ISymbol> com) {
		sequence = new AbstractStatementSequence(description);
		sequence.setName("commands of " + getName());
		sequence.setChildren(com);
	}

	@Override
	public Object privateExecuteIn(final IScope scope) throws GamaRuntimeException {

		final IAgent currentAgent = scope.getAgent();

		final GamaSoundPlayer soundPlayer = SoundPlayerBroker.getInstance().getSoundPlayer(currentAgent);
		final String soundFilePath = FileUtils.constructAbsoluteFilePath(scope, (String) source.value(scope), false);

		if (soundPlayer != null) {
			soundPlayer.play(scope, new File(soundFilePath),
					mode != null ? (String) mode.value(scope) : GamaSoundPlayer.OVERWRITE_MODE,
					repeat != null ? (Boolean) repeat.value(scope) : false);
		} else {
			// DEBUG.LOG("No more player in pool!");
		}

		if (sequence != null) {
			scope.execute(sequence, currentAgent, null);
		}

		return null;
	}
}
