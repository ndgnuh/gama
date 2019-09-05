/*******************************************************************************************************
 *
 * gaml.expressions.AbstractExpression.java, in plugin gama.core, is part of the source code of the GAMA
 * modeling and simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.expressions;

import gama.GAMA;
import gama.common.util.TextBuilder;
import gama.runtime.benchmark.StopWatch;
import gama.runtime.concurrent.GamaExecutorService;
import gama.runtime.scope.IScope;
import gaml.types.IType;
import gaml.types.Types;

/**
 * Abstract class that defines the structure of all expression classes.
 *
 * @author drogoul
 */
@SuppressWarnings ("rawtypes")
public abstract class AbstractExpression implements IExpression {

	protected IType type = null;

	@Override
	public IType<?> getGamlType() {
		return type == null ? Types.NO_TYPE : type;
	}

	protected final static void parenthesize(final TextBuilder sb, final IExpression... exp) {
		if (exp.length == 1 && !exp[0].shouldBeParenthesized()) {
			sb.append(exp[0].serialize(false));
		} else {
			surround(sb, '(', ')', exp);
		}
	}

	protected final static String surround(final TextBuilder sb, final char first, final char last,
			final IExpression... exp) {
		sb.append(first);
		for (int i = 0; i < exp.length; i++) {
			if (i > 0) {
				sb.append(',');
			}
			sb.append(exp[i] == null ? "nil" : exp[i].serialize(false));
		}
		final int length = sb.length();
		if (length > 2 && sb.getBuilder().charAt(length - 1) == ' ') {
			sb.setLength(length - 1);
		}
		sb.append(last);
		// sb.append(' ');
		return sb.toString();
	}

	@Override
	public String getTitle() {
		// Serialized version by default
		return serialize(false);
	}

	@Override
	public final Object value(final IScope scope) {
		try (StopWatch w = GAMA.benchmark(scope, this)) {
			return _value(scope);
		} catch (final OutOfMemoryError e) {
			GamaExecutorService.EXCEPTION_HANDLER.uncaughtException(Thread.currentThread(), e);
			return null;
		}
	}

	protected abstract Object _value(IScope scope);

}
