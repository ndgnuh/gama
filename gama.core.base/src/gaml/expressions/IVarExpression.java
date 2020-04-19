/*******************************************************************************************************
 *
 * gaml.expressions.IVarExpression.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.expressions;

import gama.runtime.scope.IScope;
import gaml.descriptions.IDescription;

/**
 * VariableExpression.
 *
 * @author drogoul 4 sept. 07
 */
public interface IVarExpression extends IExpression {

	public interface Agent extends IVarExpression {

		IDescription getDefinitionDescription();
	}

	int GLOBAL = 0;
	int AGENT = 1;
	int TEMP = 2;
	int EACH = 3;
	int SELF = 4;
	int SUPER = 5;
	int MYSELF = 6;

	void setVal(IScope scope, Object v, boolean create);

	boolean isNotModifiable();

	IExpression getOwner();

	VariableExpression getVar();

}