/*********************************************************************************************
 *
 * 'GamlQualifiedNameProvider.java, in plugin gama.core.lang, is part of the source code of the GAMA modeling and
 * simulation platform. (c) 2007-2016 UMI 209 UMMISCO IRD/UPMC & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and developers contact.
 *
 *
 **********************************************************************************************/
package gama.core.lang.gaml.naming;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import gama.common.interfaces.IKeyword;
import gama.core.lang.gaml.GamlDefinition;
import gama.core.lang.gaml.Model;
import gama.core.lang.gaml.S_Reflex;
import gama.core.lang.gaml.util.GamlSwitch;
import gaml.descriptions.ModelDescription;

/**
 * GAML Qualified Name provider.
 *
 */
public class GamlQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {

	private final static String NULL = "";
	private final static GamlSwitch<String> SWITCH = new GamlSwitch<>() {

		@Override
		public String caseS_Reflex(final S_Reflex s) {
			if (s.getKey().equals(IKeyword.ASPECT))
				return s.getName();
			return NULL;
		}

		@Override
		public String caseModel(final Model o) {
			return o.getName() + ModelDescription.MODEL_SUFFIX;
		}

		@Override
		public String defaultCase(final EObject e) {
			return NULL;
		}

		@Override
		public String caseGamlDefinition(final GamlDefinition object) {
			return object.getName();
		}

	};

	@Override
	public QualifiedName getFullyQualifiedName(final EObject input) {
		final String string = SWITCH.doSwitch(input);
		if (string == null || string.equals(NULL))
			return null;
		return QualifiedName.create(string);
	}

}