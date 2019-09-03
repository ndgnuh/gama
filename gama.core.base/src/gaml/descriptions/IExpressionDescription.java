/*******************************************************************************************************
 *
 * gaml.descriptions.IExpressionDescription.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.descriptions;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import gama.common.interfaces.IDisposable;
import gama.common.interfaces.IGamlable;
import gaml.expressions.IExpression;
import gaml.types.IType;

/**
 * The class IExpressionDescription.
 *
 * @author drogoul
 * @since 31 mars 2012
 *
 */
public interface IExpressionDescription<T> extends IGamlable, IDisposable {

	void setExpression(final IExpression expr);

	IExpression compile(final IDescription context);

	IExpression getExpression();

	IExpressionDescription compileAsLabel();

	boolean equalsString(String o);

	EObject getTarget();

	void setTarget(EObject target);

	boolean isConst();

	Collection<String> getStrings(IDescription context, boolean skills);

	IExpressionDescription cleanCopy();

	IType<?> getDenotedType(IDescription context);

	// public abstract void collectMetaInformation(GamlProperties meta);

}