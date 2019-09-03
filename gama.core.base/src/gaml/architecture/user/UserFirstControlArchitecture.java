/*******************************************************************************************************
 *
 * gaml.architecture.user.UserFirstControlArchitecture.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.architecture.user;

import gama.common.interfaces.IKeyword;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.skill;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;

// @vars({ @var(name = IKeyword.STATE, type = IType.STRING),
// @var(name = IKeyword.STATES, type = IType.LIST, constant = true) })
@skill (
		name = IKeyword.USER_FIRST,
		concept = { IConcept.GUI, IConcept.ARCHITECTURE },
		doc = @doc ("A control architecture, based on FSM, where the user is being given control before states / reflexes of the agent are executed"))
public class UserFirstControlArchitecture extends UserControlArchitecture {

	@Override
	public Object executeOn(final IScope scope) throws GamaRuntimeException {
		executeCurrentState(scope);
		return executeReflexes(scope);
	}

	@Override
	public boolean init(final IScope scope) throws GamaRuntimeException {
		if (initPanel != null) {
			scope.execute(initPanel);
		}
		return super.init(scope);
	}
}