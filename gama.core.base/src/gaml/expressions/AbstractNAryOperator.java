/*******************************************************************************************************
 *
 * gaml.expressions.AbstractNAryOperator.java, in plugin gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gaml.expressions;

import static gama.processor.annotations.ITypeProvider.ALL;
import static gama.processor.annotations.ITypeProvider.CONTENT_TYPE_AT_INDEX;
import static gama.processor.annotations.ITypeProvider.DENOTED_TYPE_AT_INDEX;
import static gama.processor.annotations.ITypeProvider.FIRST_CONTENT_TYPE_OR_TYPE;
import static gama.processor.annotations.ITypeProvider.FLOAT_IN_CASE_OF_INT;
import static gama.processor.annotations.ITypeProvider.INDEXED_TYPES;
import static gama.processor.annotations.ITypeProvider.KEY_TYPE_AT_INDEX;
import static gama.processor.annotations.ITypeProvider.SECOND_CONTENT_TYPE_OR_TYPE;
import static gama.processor.annotations.ITypeProvider.SECOND_DENOTED_TYPE;
import static gama.processor.annotations.ITypeProvider.TYPE_AT_INDEX;
import static gama.processor.annotations.ITypeProvider.WRAPPED;

import java.util.Arrays;
import java.util.function.Predicate;

import gama.common.interfaces.ICollector;
import gama.common.util.TextBuilder;
import gama.processor.annotations.ITypeProvider;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gaml.compilation.interfaces.GamaGetter;
import gaml.descriptions.IVarDescriptionUser;
import gaml.descriptions.SpeciesDescription;
import gaml.descriptions.VariableDescription;
import gaml.prototypes.OperatorProto;
import gaml.types.GamaType;
import gaml.types.IContainerType;
import gaml.types.IType;
import gaml.types.Types;

/**
 * AbstractBinaryOperator
 *
 * @author drogoul 23 august 07
 */
@SuppressWarnings ({ "rawtypes" })
public abstract class AbstractNAryOperator extends AbstractExpression implements IOperator {

	protected final IExpression[] exprs;
	protected OperatorProto prototype;

	public AbstractNAryOperator(final OperatorProto proto, final IExpression... expressions) {
		// Copy introduced in order to circumvent issue 1060
		if (expressions.length == 0 || expressions[0] == null) {
			exprs = null;
		} else {
			exprs = Arrays.copyOf(expressions, expressions.length);
		}
		this.prototype = proto;
		type = computeType();
	}

	@Override
	public OperatorProto getPrototype() {
		return prototype;
	}

	protected IType computeType() {
		if (prototype == null)
			return Types.NO_TYPE;
		IType result = computeType(prototype.typeProvider, 0, prototype.returnType, GamaType.TYPE);
		if (result.isContainer()) {
			final IType contentType = computeType(prototype.contentTypeProvider,
					prototype.contentTypeContentTypeProvider, result.getContentType(), GamaType.CONTENT);
			final IType keyType = computeType(prototype.keyTypeProvider, 0, result.getKeyType(), GamaType.KEY);
			result = GamaType.from(result, keyType, contentType);
		}
		return result;
	}

	protected IType computeType(final int tp, final int contentTypeProvider, final IType defaultType, final int kind) {
		IType result = defaultType;
		int typeProvider = tp;
		final boolean returnFloatsInsteadOfInts = typeProvider < FLOAT_IN_CASE_OF_INT;
		if (returnFloatsInsteadOfInts) {
			typeProvider = typeProvider - FLOAT_IN_CASE_OF_INT;
		}
		if (typeProvider >= 0) {
			result = Types.get(typeProvider);
		} else if (exprs != null) {
			switch (typeProvider) {
				case WRAPPED:
					result = exprs[0].getGamlType().getWrappedType();
					break;
				case ALL:
					result = GamaType.findCommonType(exprs, kind);
					break;
				case FIRST_CONTENT_TYPE_OR_TYPE:
					final IType leftType = exprs[0].getGamlType();
					final IType t2 = leftType.getContentType();
					if (t2 == Types.NO_TYPE) {
						result = leftType;
					} else {
						result = t2;
					}
					break;
				case SECOND_DENOTED_TYPE:
					result = exprs[1].getDenotedType();
					break;
				case SECOND_CONTENT_TYPE_OR_TYPE:
					final IType rightType = exprs[1].getGamlType();
					final IType t3 = rightType.getContentType();
					if (t3 == Types.NO_TYPE) {
						result = rightType;
					} else {
						result = t3;
					}
					break;
				default:
					if (typeProvider < INDEXED_TYPES) {
						int index = -1;
						int kindOfIndex = -1;
						if (typeProvider > TYPE_AT_INDEX) {
							index = typeProvider - TYPE_AT_INDEX - 1;
							kindOfIndex = GamaType.TYPE;
						} else if (typeProvider > CONTENT_TYPE_AT_INDEX) {
							index = typeProvider - CONTENT_TYPE_AT_INDEX - 1;
							kindOfIndex = GamaType.CONTENT;
						} else if (typeProvider > DENOTED_TYPE_AT_INDEX) {
							index = typeProvider - DENOTED_TYPE_AT_INDEX - 1;
							kindOfIndex = GamaType.DENOTED;
						} else if (typeProvider > KEY_TYPE_AT_INDEX) {
							index = typeProvider - KEY_TYPE_AT_INDEX - 1;
							kindOfIndex = GamaType.KEY;
						}
						if (index > -1 && index < exprs.length) {
							final IExpression expr = exprs[index];
							switch (kindOfIndex) {
								case GamaType.TYPE:
									result = expr.getGamlType();
									break;
								case GamaType.CONTENT:
									result = expr.getGamlType().getContentType();
									break;
								case GamaType.KEY:
									result = expr.getGamlType().getKeyType();
									break;
								case GamaType.DENOTED:
									result = expr.getDenotedType();
									break;
							}
						}
					}
			}
		}

		if (contentTypeProvider != ITypeProvider.NONE && result.isContainer()) {
			final IType c =
					computeType(contentTypeProvider, ITypeProvider.NONE, result.getContentType(), GamaType.CONTENT);
			if (c != Types.NO_TYPE) {
				result = ((IContainerType<?>) result).of(c);
			}
		}
		if (returnFloatsInsteadOfInts && result == Types.INT)
			return Types.FLOAT;
		return result;
	}

	protected abstract AbstractNAryOperator copy();

	@Override
	public IOperator resolveAgainst(final IScope scope) {
		final AbstractNAryOperator copy = copy();
		if (exprs != null) {
			for (int i = 0; i < exprs.length; i++) {
				copy.exprs[i] = exprs[i].resolveAgainst(scope);
			}
		}
		return copy;
	}

	@Override
	public boolean isConst() {
		if (!prototype.canBeConst)
			return false;
		if (exprs != null) {
			for (final IExpression expr : exprs) {
				if (!expr.isConst())
					return false;
			}
		}
		return true;
	}

	@Override
	public String getName() {
		return prototype.getName();
	}

	@Override
	public String toString() {
		String result = literalValue() + "(";
		if (exprs != null) {
			for (int i = 0; i < exprs.length; i++) {
				final String l = exprs[i] == null ? "null" : exprs[i].toString();
				result += l + (i != exprs.length - 1 ? "," : "");
			}
		}
		return result + ")";
	}

	@Override
	public String serialize(final boolean includingBuiltIn) {
		try (TextBuilder sb = TextBuilder.create()) {
			sb.append(literalValue());
			parenthesize(sb, exprs);
			return sb.toString();
		}
	}

	public int numArg() {
		return exprs == null ? 0 : exprs.length;
	}

	@Override
	public IExpression arg(final int i) {
		if (exprs == null)
			return null;
		if (i >= exprs.length)
			return null;
		return exprs[i];
	}

	@Override
	public String getTitle() {
		try (TextBuilder sb = TextBuilder.create()) {
			sb.append("operator ").append(getName()).append(" (");
			if (exprs != null) {
				for (final IExpression expr : exprs) {
					sb.append(expr == null ? "nil" : expr.getGamlType().getTitle());
					sb.append(',');
				}
				sb.setLength(sb.length() - 1);
			} else if (prototype.signature != null) {
				sb.append("Argument types: " + prototype.signature.toString());
			}
			sb.append(") returns ");
			sb.append(type.getTitle());
			return sb.toString();
		}
	}

	@Override
	public String getDocumentation() {
		return prototype.getDocumentation();
	}

	@Override
	public String getDefiningPlugin() {
		return prototype.getDefiningPlugin();
	}

	@Override
	public void collectUsedVarsOf(final SpeciesDescription species,
			final ICollector<IVarDescriptionUser> alreadyProcessed, final ICollector<VariableDescription> result) {
		if (alreadyProcessed.contains(this))
			return;
		alreadyProcessed.add(this);
		prototype.collectUsedVarsOf(species, alreadyProcessed, result);
		if (exprs != null) {
			for (final IExpression e : exprs) {
				if (e != null) {
					e.collectUsedVarsOf(species, alreadyProcessed, result);
				}
			}
		}
	}

	@Override
	public boolean isContextIndependant() {
		if (exprs != null) {
			for (final IExpression e : exprs) {
				if (e != null) {
					if (!e.isContextIndependant())
						return false;
				}
			}
		}
		return true;
	}

	@Override
	public void visitSuboperators(final IOperatorVisitor visitor) {
		if (exprs != null) {
			for (final IExpression e : exprs) {
				if (e instanceof IOperator) {
					visitor.visit((IOperator) e);
				}
			}
		}

	}

	@Override
	public Object _value(final IScope scope) throws GamaRuntimeException {
		final Object[] values = new Object[exprs == null ? 0 : exprs.length];
		try {
			for (int i = 0; i < values.length; i++) {
				values[i] = prototype.lazy[i] ? exprs[i] : exprs[i].value(scope);
			}
			return ((GamaGetter.NAry) prototype.helper).get(scope, values);
		} catch (final GamaRuntimeException e1) {
			e1.addContext("when applying the " + literalValue() + " operator on " + Arrays.toString(values));
			throw e1;
		} catch (final Throwable e) {
			final GamaRuntimeException ee = GamaRuntimeException.create(e, scope);
			ee.addContext("when applying the " + literalValue() + " operator on " + Arrays.toString(values));
			throw ee;
		}
	}

	@Override
	public boolean findAny(final Predicate<IExpression> predicate) {
		if (predicate.test(this))
			return true;
		if (exprs != null) {
			for (final IExpression e : exprs) {
				if (e.findAny(predicate))
					return true;
			}
		}
		return false;
	}
}
