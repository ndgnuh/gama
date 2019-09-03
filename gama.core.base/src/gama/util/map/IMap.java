package gama.util.map;

import static gama.util.map.GamaMapFactory.createWithoutCasting;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;

import com.google.common.base.Objects;
import com.google.common.collect.Iterators;

import gama.common.interfaces.BiConsumerWithPruning;
import gama.common.interfaces.ConsumerWithPruning;
import gama.common.interfaces.IAddressableContainer;
import gama.common.interfaces.IContainer;
import gama.common.interfaces.IModifiableContainer;
import gama.common.interfaces.IContainer.Modifiable;
import gama.metamodel.shape.GamaPoint;
import gama.processor.annotations.IConcept;
import gama.processor.annotations.IOperatorCategory;
import gama.processor.annotations.ITypeProvider;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.example;
import gama.processor.annotations.GamlAnnotations.getter;
import gama.processor.annotations.GamlAnnotations.operator;
import gama.processor.annotations.GamlAnnotations.test;
import gama.processor.annotations.GamlAnnotations.variable;
import gama.processor.annotations.GamlAnnotations.vars;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.GamaPair;
import gama.util.list.GamaListFactory;
import gama.util.list.IList;
import gama.util.matrix.GamaObjectMatrix;
import gama.util.matrix.IMatrix;
import gaml.types.GamaMapType;
import gaml.types.GamaType;
import gaml.types.IType;
import gaml.types.Types;

@vars ({ @variable (
		name = GamaMap.KEYS,
		type = IType.LIST,
		of = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
		doc = { @doc ("Returns the list of keys of this map (in their order of insertion)") }),
		@variable (
				name = GamaMap.VALUES,
				type = IType.LIST,
				of = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
				doc = { @doc ("Returns the list of values of this map (in their order of insertion)") }),
		@variable (
				name = GamaMap.PAIRS,
				type = IType.LIST,
				of = IType.PAIR,
				doc = { @doc ("Returns the list of pairs (key, value) that compose this map") }) })
@SuppressWarnings ("unchecked")

public interface IMap<K, V> extends Map<K, V>, IModifiableContainer<K, V, K, V>, IAddressableContainer<K, V, K, V> {

	interface IPairList<K, V> extends Set<Map.Entry<K, V>>, IList<Map.Entry<K, V>> {

		@Override
		default Spliterator<Entry<K, V>> spliterator() {
			return IList.super.spliterator();
		}

	}

	String KEYS = "keys";
	String VALUES = "values";
	String PAIRS = "pairs";

	@Override
	default String stringValue(final IScope scope) {
		return serialize(false);
	}

	/**
	 * Method add()
	 *
	 * @see gama.common.interfaces.IContainer#add(gama.runtime.scope.IScope, java.lang.Object)
	 */
	@Override
	default void addValue(final IScope scope, final V v) {
		if (v instanceof GamaPair) {
			setValueAtIndex(scope, (K) ((GamaPair) v).key, (V) ((GamaPair) v).value);
		} else {
			setValueAtIndex(scope, v, v);
		}
	}

	/**
	 * Method add()
	 *
	 * @see gama.common.interfaces.IContainer#add(gama.runtime.scope.IScope, java.lang.Object, java.lang.Object)
	 */
	@Override
	default void addValueAtIndex(final IScope scope, final Object index, final V value) {
		// Cf. discussion on mailing-list about making "add" a synonym of "put"
		// for maps
		// if ( !containsKey(index) ) {
		setValueAtIndex(scope, index, value);
		// }
	}

	/**
	 * Method addAll()
	 *
	 * @see gama.common.interfaces.IContainer#addAll(gama.runtime.scope.IScope, gama.common.interfaces.IContainer)
	 */
	@Override
	default void addValues(final IScope scope, final IContainer/* <?, GamaPair<K, V>> */ values) {
		for (final Object o : values.iterable(scope)) {
			addValue(scope, (V) o);
		}
	}

	/**
	 * Method removeAt()
	 *
	 * @see gama.common.interfaces.IContainer#removeAt(gama.runtime.scope.IScope, java.lang.Object)
	 */
	@Override
	default void removeIndex(final IScope scope, final Object index) {
		remove(index);
	}

	@Override
	default boolean containsKey(final IScope scope, final Object o) throws GamaRuntimeException {
		return containsKey(o);
	}

	@Override
	default int length(final IScope scope) {
		return size();
	}

	@Override
	default boolean isEmpty(final IScope scope) {
		return length(scope) == 0;
	}

	@Override
	default V anyValue(final IScope scope) {
		final int size = length(scope);
		if (size == 0) { return null; }
		final K key = scope.getRandom().oneOf(keySet());
		return get(key);
	}

	@Override
	default V firstValue(final IScope scope) throws GamaRuntimeException {
		if (length(scope) == 0) { return null; }
		return Iterators.get(values().iterator(), 0);
	}

	@Override
	default V lastValue(final IScope scope) throws GamaRuntimeException {
		if (length(scope) == 0) { return null; }
		return Iterators.getLast(values().iterator());
	}

	default V valueAt(final int index) {
		if (size() == 0) { return null; }
		return Iterators.get(values().iterator(), index);
	}

	/**
	 * Method removeAll()
	 *
	 * @see gama.common.interfaces.IContainer#removeAll(gama.runtime.scope.IScope, gama.common.interfaces.IContainer)
	 */
	@Override
	default void removeValues(final IScope scope, final IContainer<?, ?> values) {
		// we suppose we have pairs
		for (final Object o : values.iterable(scope)) {
			removeValue(scope, o);
		}
	}

	/**
	 * Method checkBounds()
	 *
	 * @see gama.common.interfaces.IContainer#checkBounds(java.lang.Object, boolean)
	 */
	@Override
	default boolean checkBounds(final IScope scope, final Object index, final boolean forAdding) {
		return true;
	}

	/**
	 * Method removeIndexes()
	 *
	 * @see gama.common.interfaces.IContainer.Modifiable#removeIndexes(gama.runtime.scope.IScope, gama.common.interfaces.IContainer)
	 */
	@Override
	default void removeIndexes(final IScope scope, final IContainer<?, ?> index) {
		for (final Object key : index.iterable(scope)) {
			remove(key);
		}
	}

	/**
	 * Method buildValue()
	 *
	 * @see gama.common.interfaces.IContainer.Modifiable#buildValue(gama.runtime.scope.IScope, java.lang.Object,
	 *      gaml.types.IContainerType)
	 */
	default V buildValue(final IScope scope, final Object object) {
		// If we pass a pair to this method, but the content type is not a pair,
		// then it is is interpreted as a key + a value by addValue()
		if (object instanceof GamaPair) {
			if (!getGamlType().getContentType().isTranslatableInto(Types.PAIR)) { return (V) object; }
		}
		return (V) getGamlType().getContentType().cast(scope, object, null, false);
	}

	/**
	 * Method buildValues()
	 *
	 * @see gama.common.interfaces.IContainer.Modifiable#buildValues(gama.runtime.scope.IScope, gama.common.interfaces.IContainer,
	 *      gaml.types.IContainerType)
	 */
	default IContainer<?, GamaPair<K, V>> buildValues(final IScope scope, final IContainer objects) {
		return GamaMapType.staticCast(scope, objects, getGamlType().getKeyType(), getGamlType().getContentType(),
				false);
	}

	/**
	 * Method buildIndex()
	 *
	 * @see gama.common.interfaces.IContainer.Modifiable#buildIndex(gama.runtime.scope.IScope, java.lang.Object,
	 *      gaml.types.IContainerType)
	 */
	default K buildIndex(final IScope scope, final Object object) {
		return (K) getGamlType().getKeyType().cast(scope, object, null, false);
	}

	@Override
	default java.lang.Iterable<V> iterable(final IScope scope) {
		return values();
	}

	@Override
	default V getFromIndicesList(final IScope scope, final IList<K> indices) throws GamaRuntimeException {
		if (indices == null || indices.isEmpty()) { return null; }
		return get(scope, indices.get(0));
		// We do not consider the case where multiple indices are used. Maybe
		// could be used in the
		// future to return a list of values ?
	}

	@Override
	default boolean contains(final IScope scope, final Object o) {
		// AD: see Issue 918 and #2772
		return /* containsKey(o) || */containsValue(o);
	}

	/**
	 * Returns the list of values by default (NOT the list of pairs) Method listValue()
	 *
	 * @see gama.common.interfaces.IContainer#listValue(gama.runtime.scope.IScope)
	 */
	@Override
	default IList<V> listValue(final IScope scope, final IType contentsType, final boolean copy) {
		if (!GamaType.requiresCasting(contentsType, getGamlType().getContentType())) {
			if (!copy) { return GamaListFactory.wrap(contentsType, values()); }
			return GamaListFactory.createWithoutCasting(contentsType, values());
		} else {
			return GamaListFactory.create(scope, contentsType, values());
		}
	}

	@Override
	default IMatrix matrixValue(final IScope scope, final IType contentsType, final boolean copy)
			throws GamaRuntimeException {
		// No attempt to coerce the contentsType, as both keys and values should
		// be in the same matrix
		final GamaObjectMatrix matrix = new GamaObjectMatrix(2, size(), contentsType);
		int i = 0;
		for (final Map.Entry entry : entrySet()) {
			matrix.set(scope, 0, i, GamaType.toType(scope, entry.getKey(), contentsType, false));
			matrix.set(scope, 1, i, GamaType.toType(scope, entry.getValue(), contentsType, false));
			i++;
		}
		return matrix;
	}

	@Override
	default IMatrix matrixValue(final IScope scope, final IType contentsType, final GamaPoint preferredSize,
			final boolean copy) throws GamaRuntimeException {
		return matrixValue(scope, contentsType, copy);
	}

	@Override
	default String serialize(final boolean includingBuiltIn) {
		return "map(" + getPairs().serialize(includingBuiltIn) + ")";
	}

	@Override
	default IMap mapValue(final IScope scope, final IType keyType, final IType contentsType, final boolean copy) {
		final boolean coerceKey = GamaType.requiresCasting(keyType, getGamlType().getKeyType());
		final boolean coerceValue = GamaType.requiresCasting(contentsType, getGamlType().getContentType());
		if (coerceKey || coerceValue) {
			final IMap result = GamaMapFactory.create(keyType, contentsType, size());
			for (final Map.Entry<K, V> entry : entrySet()) {
				result.put(coerceKey ? result.buildIndex(scope, entry.getKey()) : entry.getKey(),
						coerceValue ? result.buildValue(scope, entry.getValue()) : entry.getValue());
			}
			return result;
		} else {
			if (copy) {
				final IMap result = copy(scope);
				return result;
			} else {
				return this;
			}
		}

	}

	@Override
	default IMap<K, V> copy(final IScope scope) {
		return createWithoutCasting((IType<K>) getGamlType().getKeyType(), (IType<V>) getGamlType().getContentType(),
				this, isOrdered());
	}

	/**
	 * Method put()
	 *
	 * @see gama.common.interfaces.IContainer#put(gama.runtime.scope.IScope, java.lang.Object, java.lang.Object)
	 */
	@Override
	default void setValueAtIndex(final IScope scope, final Object index, final V value) {
		final K key = buildIndex(scope, index);
		final V val = buildValue(scope, value);
		put(key, val);
	}

	@operator (
			value = "reverse",
			can_be_const = true,
			type = IType.MAP,
			content_type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
			index_type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
			category = { IOperatorCategory.CONTAINER },
			concept = { IConcept.CONTAINER })
	@doc (
			value = "Specialization of the reverse operator for maps. Reverses keys and values",
			comment = "",
			examples = { @example ("map<int,int> m <- [1::111,2::222, 3::333, 4::444];"), @example (
					value = "reverse(m)",
					equals = "map([111::1,222::2,333::3,444::4])") })

	@test ("map<int,int> m2 <- [1::111,2::222, 3::333, 4::444]; reverse(m2) = map([111::1,222::2,333::3,444::4])")

	@Override
	default IMap reverse(final IScope scope) {
		final IMap map = GamaMapFactory.create(getGamlType().getContentType(), getGamlType().getKeyType());
		for (final Map.Entry<K, V> entry : entrySet()) {
			map.put(entry.getValue(), entry.getKey());
		}
		return map;
	}

	/**
	 * Method removeAll()
	 *
	 * @see gama.common.interfaces.IContainer#removeAll(gama.runtime.scope.IScope, java.lang.Object)
	 */
	@Override
	default void removeAllOccurrencesOfValue(final IScope scope, final Object value) {
		values().removeIf((v) -> Objects.equal(value, v));
	}

	/**
	 * Method remove()
	 *
	 * @see gama.common.interfaces.IContainer#remove(gama.runtime.scope.IScope, java.lang.Object)
	 */
	@Override
	default void removeValue(final IScope scope, final Object value) {
		// Dont know what to do... Removing the first pair with value = value ?
		final Collection<V> values = values();
		final Iterator<V> it = values.iterator();
		while (it.hasNext()) {
			final V v = it.next();
			if (Objects.equal(v, value)) {
				values.remove(v);
				return;
			}
		}
	}

	@getter ("keys")
	default IList<K> getKeys() {
		// See issue #2792. key can be used to modify the map...
		return GamaListFactory.<K> createWithoutCasting(getGamlType().getKeyType(), keySet());
	}

	@getter ("values")
	default IList<V> getValues() {
		return GamaListFactory.<V> wrap(getGamlType().getContentType(), values());
	}

	@Override
	default V get(final IScope scope, final K index) throws GamaRuntimeException {
		return get(index);
	}

	/**
	 * Method setAll()
	 *
	 * @see gama.common.interfaces.IContainer#setAll(gama.runtime.scope.IScope, java.lang.Object)
	 */
	@Override
	default void setAllValues(final IScope scope, final V value) {
		replaceAll((k, v) -> value);
	}

	@getter ("pairs")
	default IPairList getPairs() {
		// FIXME: in the future, this method will be directly operating upon the
		// entry set (so as to
		// avoir duplications). See GamaPair
		final GamaPairList<K, V> pairs = new GamaPairList<>(this);
		forEach((key, value) -> pairs
				.add(new GamaPair<>(key, value, getGamlType().getKeyType(), getGamlType().getContentType())));
		return pairs;
	}

	default boolean forEachPair(final BiConsumerWithPruning<K, V> visitor) {
		final Iterator<Map.Entry<K, V>> it = entrySet().iterator();
		while (it.hasNext()) {
			final Map.Entry<K, V> entry = it.next();
			if (!visitor.process(entry.getKey(), entry.getValue())) { return false; }
		}
		return true;
	}

	boolean isOrdered();

	default boolean forEachValue(final ConsumerWithPruning<? super V> visitor) {
		final Iterator<Map.Entry<K, V>> it = entrySet().iterator();
		while (it.hasNext()) {
			final Map.Entry<K, ? extends V> entry = it.next();
			if (!visitor.process(entry.getValue())) { return false; }
		}
		return true;
	}

	default boolean forEachKey(final ConsumerWithPruning<K> visitor) {
		final Iterator<Map.Entry<K, V>> it = entrySet().iterator();
		while (it.hasNext()) {
			final Map.Entry<K, V> entry = it.next();
			if (!visitor.process(entry.getKey())) { return false; }
		}
		return true;
	}

}