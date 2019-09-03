/*******************************************************************************************************
 *
 * gaml.expressions.IOperator.java, in plugin gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package gaml.expressions;

import gama.common.interfaces.IBenchmarkable;
import gaml.descriptions.OperatorProto;

/**
 * Written by drogoul Modified on 22 ao�t 2010
 * 
 * @todo Description
 * 
 */
public interface IOperator extends IExpression, IBenchmarkable {

	@FunctionalInterface
	public static interface IOperatorVisitor {
		void visit(IOperator operator);
	}

	public abstract void visitSuboperators(IOperatorVisitor visitor);

	public abstract IExpression arg(int i);

	public abstract OperatorProto getPrototype();

	@Override
	default String getNameForBenchmarks() {
		return serialize(true);
	}

}