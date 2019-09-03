/*******************************************************************************************************
 *
 * gama.util.IList.java, in plugin gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.util.list;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import gama.common.interfaces.IAddressableContainer;
import gama.common.interfaces.IContainer;
import gama.common.interfaces.IModifiableContainer;
import gama.common.interfaces.IContainer.Modifiable;
import gama.common.util.StringUtils;
import gama.common.util.TextBuilder;
import gama.metamodel.shape.GamaPoint;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.map.GamaMapFactory;
import gama.util.map.IMap;
import gama.util.matrix.IMatrix;
import gaml.operators.Cast;
import gaml.types.GamaIntegerType;
import gaml.types.GamaMatrixType;
import gaml.types.GamaPairType;
import gaml.types.GamaType;
import gaml.types.IType;
import gaml.types.Types;

/**
 * The class IList. Interface for classes representing various lists in GAML (list, population, etc.)
 *
 * @author drogoul
 * @since 14 d�c. 2011
 *
 */
@SuppressWarnings ("unchecked")
public interface IList<E>
		extends IModifiableContainer<Integer, E, Integer, E>, IAddressableContainer<Integer, E, Integer, E>, List<E> {

	@Override
	default boolean containsKey(final IScope scope, final Object o) {
		if (o instanceof Integer) {
			final Integer i = (Integer) o;
			return i >= 0 && i < this.size();
		}
		return false;
	}

	@Override
	default IList<E> listValue(final IScope scope, final IType contentsType, final boolean copy) {
		if (!GamaType.requiresCasting(contentsType, getGamlType().getContentType())) {
			if (copy) { return GamaListFactory.createWithoutCasting(contentsType, this); }
			return this;
		}
		return GamaListFactory.create(scope, contentsType, this);
	}

	@Override
	default IMatrix<E> matrixValue(final IScope scope, final IType contentType, final boolean copy) {
		return GamaMatrixType.from(scope, this, contentType, null);
	}

	@Override
	default IMatrix<E> matrixValue(final IScope scope, final IType contentsType, final GamaPoint preferredSize,
			final boolean copy) {
		return GamaMatrixType.from(scope, this, contentsType, preferredSize);
	}

	@Override
	default String stringValue(final IScope scope) throws GamaRuntimeException {
		return serialize(false);
	}

	@Override
	default String serialize(final boolean includingBuiltIn) {
		try (TextBuilder sb = TextBuilder.create()) {
			sb.append('[');
			for (int i = 0; i < size(); i++) {
				if (i != 0) {
					sb.append(',');
				}
				sb.append(StringUtils.toGaml(get(i), includingBuiltIn));
			}
			sb.append(']');
			return sb.toString();
		}
	}

	@Override
	default IMap<?, ?> mapValue(final IScope scope, final IType keyType, final IType contentsType, final boolean copy) {
		// 08/01/14: Change of behavior. A list now returns a map containing its
		// contents casted to pairs.
		// Allows to build sets with the idiom: list <- map(list).values;
		final IType myCt = getGamlType().getContentType();
		final IType kt, ct;
		if (myCt.isParametricFormOf(Types.PAIR)) {
			// Issue #2607: specific treatment of lists of pairs
			kt = GamaType.findSpecificType(keyType, myCt.getKeyType());
			ct = GamaType.findSpecificType(contentsType, myCt.getContentType());
		} else {
			kt = GamaType.findSpecificType(keyType, myCt); // not keyType()
			ct = GamaType.findSpecificType(contentsType, myCt);

		}
		final IMap result = GamaMapFactory.create(kt, ct);
		for (final E e : this) {
			result.addValue(scope, GamaPairType.staticCast(scope, e, kt, ct, copy));
		}
		return result;
	}

	@Override
	default void addValue(final IScope scope, final E object) {
		add(buildValue(scope, object));
	}

	@Override
	default void addValueAtIndex(final IScope scope, final Object index, final E object) {
		add(buildIndex(scope, index), buildValue(scope, object));
	}

	@Override
	default void setValueAtIndex(final IScope scope, final Object index, final E value) {
		set(buildIndex(scope, index), buildValue(scope, value));
	}

	@Override
	default void addValues(final IScope scope, final IContainer values) {
		addAll(buildValues(scope, values));
	}

	@Override
	default void setAllValues(final IScope scope, final E value) {
		final E element = buildValue(scope, value);
		for (int i = 0, n = size(); i < n; i++) {
			set(i, element);
		}
	}

	@Override
	default void removeValue(final IScope scope, final Object value) {
		remove(value);
	}

	@Override
	default void removeIndex(final IScope scope, final Object index) {
		if (index instanceof Integer) {
			remove(((Integer) index).intValue());
		}
	}

	@Override
	default void removeValues(final IScope scope, final IContainer<?, ?> values) {
		if (values instanceof Collection) {
			removeAll((Collection) values);
		} else {
			removeAll(values.listValue(scope, Types.NO_TYPE, false));
		}
	}

	@Override
	default void removeAllOccurrencesOfValue(final IScope scope, final Object value) {
		removeIf(each -> Objects.equals(each, value));
	}

	@Override
	default E firstValue(final IScope scope) {
		if (size() == 0) { return null; }
		return get(0);
	}

	@Override
	default E lastValue(final IScope scope) {
		if (size() == 0) { return null; }
		return get(size() - 1);
	}

	@Override
	default E get(final IScope scope, final Integer index) {
		return get(index.intValue());
	}

	@Override
	default int length(final IScope scope) {
		return size();
	}

	@Override
	default IContainer<Integer, E> reverse(final IScope scope) {
		final IList list = copy(scope);
		Collections.reverse(list);
		return list;
	}

	@Override
	default IList<E> copy(final IScope scope) {
		return GamaListFactory.createWithoutCasting(getGamlType().getContentType(), this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gama.interfaces.IGamaContainer#checkBounds(java.lang.Object)
	 */
	@Override
	default boolean checkBounds(final IScope scope, final Object object, final boolean forAdding) {
		if (object instanceof Integer) {
			final Integer index = (Integer) object;
			final int size = size();
			final boolean upper = forAdding ? index <= size : index < size;
			return index >= 0 && upper;
		} else if (object instanceof IContainer) {
			for (final Object o : ((IContainer) object).iterable(scope)) {
				if (!checkBounds(scope, o, forAdding)) { return false; }
			}
		}
		return false;
	}

	@Override
	default E anyValue(final IScope scope) {
		if (isEmpty()) { return null; }
		final int i = scope.getRandom().between(0, size() - 1);
		return get(i);
	}

	@Override
	default boolean contains(final IScope scope, final Object o) throws GamaRuntimeException {
		return contains(o);
	}

	@Override
	default boolean isEmpty(final IScope scope) {
		return isEmpty();
	}

	@Override
	default Iterable<? extends E> iterable(final IScope scope) {
		return this;
	}

	@Override
	default E getFromIndicesList(final IScope scope, final IList indices) throws GamaRuntimeException {
		if (indices == null || indices.isEmpty()) { return null; }
		return get(scope, Cast.asInt(scope, indices.get(0)));
		// We do not consider the case where multiple indices are used. Maybe
		// could be used in the
		// future to return a list of values ?
	}

	/**
	 * Method removeIndexes()
	 *
	 * @see gama.common.interfaces.IContainer.Modifiable#removeIndexes(gama.runtime.scope.IScope, gama.common.interfaces.IContainer)
	 */
	@Override
	default void removeIndexes(final IScope scope, final IContainer<?, ?> index) {
		final IList<Integer> l = (IList<Integer>) index.listValue(scope, Types.INT, false);
		Collections.sort(l, Collections.reverseOrder());
		for (final Integer i : l) {
			remove(i.intValue());
		}
	}

	/**
	 * Method buildValue()
	 *
	 * @see gama.common.interfaces.IContainer.Modifiable#buildValue(gama.runtime.scope.IScope, java.lang.Object,
	 *      gaml.types.IContainerType)
	 */
	default E buildValue(final IScope scope, final Object object) {
		final IType ct = getGamlType().getContentType();
		return (E) ct.cast(scope, object, null, false);
	}

	/**
	 * Method buildValues()
	 *
	 * @see gama.common.interfaces.IContainer.Modifiable#buildValues(gama.runtime.scope.IScope, gama.common.interfaces.IContainer,
	 *      gaml.types.IContainerType)
	 */
	default IList<E> buildValues(final IScope scope, final IContainer objects) {
		return (IList<E>) getGamlType().cast(scope, objects, null, false);
	}

	/**
	 * Method buildIndex()
	 *
	 * @see gama.common.interfaces.IContainer.Modifiable#buildIndex(gama.runtime.scope.IScope, java.lang.Object,
	 *      gaml.types.IContainerType)
	 */
	default Integer buildIndex(final IScope scope, final Object object) {
		return GamaIntegerType.staticCast(scope, object, null, false);
	}

	default IContainer<?, Integer> buildIndexes(final IScope scope, final IContainer value) {
		final IList<Integer> result = GamaListFactory.create(Types.INT);
		for (final Object o : value.iterable(scope)) {
			result.add(buildIndex(scope, o));
		}
		return result;
	}

}
