/*********************************************************************************************
 *
 * 'StopSoundStatement.java, in plugin ummisco.gama.extensions.sound, is part of the source code of the GAMA modeling
 * and simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 * 
 *
 **********************************************************************************************/
package ummisco.gama.extensions.sound;

import msi.gama.common.interfaces.IAgent;
import msi.gama.common.interfaces.IKeyword;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.runtime.scope.IScope;
import msi.gaml.compilation.annotations.validator;
import msi.gaml.compilation.interfaces.IDescriptionValidator;
import msi.gaml.compilation.interfaces.ISymbol;
import msi.gaml.descriptions.IDescription;
import msi.gaml.statements.AbstractStatementSequence;
import ummisco.gama.extensions.sound.StopSoundStatement.StopSoundValidator;
import ummisco.gama.processor.IConcept;
import ummisco.gama.processor.ISymbolKind;
import ummisco.gama.processor.GamlAnnotations.doc;
import ummisco.gama.processor.GamlAnnotations.inside;
import ummisco.gama.processor.GamlAnnotations.symbol;

@symbol (
		name = IKeyword.STOP_SOUND,
		kind = ISymbolKind.SEQUENCE_STATEMENT,
		with_sequence = true,
		concept = { IConcept.SOUND })
@inside (
		kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT })
@validator (StopSoundValidator.class)
@doc ("Allows to stop the sound output")
public class StopSoundStatement extends AbstractStatementSequence {

	public static class StopSoundValidator implements IDescriptionValidator<IDescription> {

		/**
		 * Method validate()
		 * 
		 * @see msi.gaml.compilation.interfaces.IDescriptionValidator#validate(msi.gaml.descriptions.IDescription)
		 */
		@Override
		public void validate(final IDescription cd) {

			// what to validate?
		}
	}

	private AbstractStatementSequence sequence = null;

	public StopSoundStatement(final IDescription desc) {
		super(desc);
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
		soundPlayer.stop(scope, false);

		if (sequence != null) {
			scope.execute(sequence, currentAgent, null);
		}

		return null;
	}
}
