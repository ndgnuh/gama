/*******************************************************************************************************
 *
 * msi.gaml.descriptions.IExpressionDescription.java, in plugin msi.gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gaml.descriptions;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import msi.gama.common.interfaces.IDisposable;
import msi.gama.common.interfaces.IGamlable;
import msi.gaml.expressions.IExpression;
import msi.gaml.types.IType;

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