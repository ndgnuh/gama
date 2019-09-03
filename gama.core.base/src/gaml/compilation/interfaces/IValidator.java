/*******************************************************************************************************
 *
 * gaml.compilation.IValidator.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.compilation.interfaces;

import org.eclipse.emf.ecore.EObject;

import gama.common.interfaces.IGamlIssue;
import gama.common.interfaces.IKeyword;
import gaml.descriptions.IDescription;
import gaml.expressions.IExpression;

/**
 * 'Tagging' interface for IExpression and IDescription validators
 * 
 * @author A. Drogoul
 * @since July 2018
 *
 */
public interface IValidator extends IKeyword, IGamlIssue {

	boolean validate(IDescription description, EObject emfContext, IExpression... arguments);
}
