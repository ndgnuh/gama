/*******************************************************************************************************
 *
 * gama.util.GamaPair.java, in plugin gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.util;

import static java.util.Objects.hash;

import java.util.Map;
import java.util.Objects;

import gama.common.interfaces.IContainer;
import gama.common.util.StringUtils;
import gama.metamodel.shape.GamaPoint;
import gama.processor.annotations.ITypeProvider;
import gama.processor.annotations.GamlAnnotations.doc;
import gama.processor.annotations.GamlAnnotations.getter;
import gama.processor.annotations.GamlAnnotations.variable;
import gama.processor.annotations.GamlAnnotations.vars;
import gama.runtime.exceptions.GamaRuntimeException;
import gama.runtime.scope.IScope;
import gama.util.list.GamaListFactory;
import gama.util.list.IList;
import gama.util.map.GamaMapFactory;
import gama.util.map.IMap;
import gama.util.matrix.IMatrix;
import gaml.operators.Cast;
import gaml.types.GamaMatrixType;
import gaml.types.IContainerType;
import gaml.types.IType;
import gaml.types.Types;

/**
 * The Class GamaPair.
 */
@vars ({ @variable (
		name = GamaPair.KEY,
		type = ITypeProvider.KEY_TYPE_AT_INDEX + 1,
		doc = { @doc ("Returns the key of this pair (can be nil)") }),
		@variable (
				name = GamaPair.VALUE,
				type = ITypeProvider.CONTENT_TYPE_AT_INDEX + 1,
				doc = { @doc ("Returns the value of this pair (can be nil)") }) })
@SuppressWarnings ({ "unchecked", "rawtypes" })
public class GamaPair<K, V>
		implements IContainer<Integer, Object>, IContainer.Addressable<Integer, Object>, Map.Entry<K, V> {

	// TODO Makes it inherit from Map.Entry<K,V> in order to tighten the link
	// between it and GamaMap
	// (have the entrySet() of GamaMap built from GamaPairs)
	// FIXME: This has still to be implemented

	public static final String KEY = "key";
	public static final String VALUE = "value";

	private final IContainerType type;
	public K key;
	public V value;

	public GamaPair(final K k, final V v, final IType keyType, final IType contentsType) {
		key = k;
		value = v;
		type = Types.PAIR.of(keyType, contentsType);
	}

	public GamaPair(final IScope scope, final K k, final V v, final IType keyType, final IType contentsType) {
		key = (K) keyType.cast(scope, k, null, false);
		value = (V) contentsType.cast(scope, v, null, false);
		type = Types.PAIR.of(keyType, contentsType);
	}

	public boolean equals(final GamaPair p) {
		return Objects.equals(key, p.key) && Objects.equals(value, p.value);
		// return key.equals(p.key) && value.equals(p.value);
	}

	@Override
	public int hashCode() {
		return hash(key, value);
	}

	@Override
	public boolean equals(final Object a) {
		if (a == null) { return false; }
		if (a instanceof GamaPair) { return equals((GamaPair) a); }
		return false;
	}

	@Override
	public IContainerType getGamlType() {
		return type;
	}

	@Override
	@getter (KEY)
	public K getKey() {
		return key;
	}

	// FIXME: To be removed
	public K first() {
		return key;
	}

	@Override
	@getter (VALUE)
	public V getValue() {
		return value;
	}

	// FIXME: To be removed
	public V last() {
		return value;
	}

	@Override
	public String stringValue(final IScope scope) throws GamaRuntimeException {
		return Cast.asString(scope, key) + "::" + Cast.asString(scope, value);
	}

	@Override
	public String serialize(final boolean includingBuiltIn) {
		return StringUtils.toGaml(key, includingBuiltIn) + "::" + StringUtils.toGaml(value, includingBuiltIn);
	}

	@Override
	public String toString() {
		return (key == null ? "nil" : key.toString()) + "::" + (value == null ? "nil" : value.toString());
	}

	@Override
	public GamaPair<K, V> copy(final IScope scope) {
		return new GamaPair(key, value, type.getKeyType(), type.getContentType());
	}

	@Override
	public V setValue(final V value) {
		this.value = value;
		return value;
	}

	/**
	 * Method get()
	 *
	 * @see gama.common.interfaces.IContainer#get(gama.runtime.scope.IScope, java.lang.Object)
	 */
	@Override
	public Object get(final IScope scope, final Integer index) throws GamaRuntimeException {
		return index == 0 ? key : value;
	}

	/**
	 * Method getFromIndicesList()
	 *
	 * @see gama.common.interfaces.IContainer#getFromIndicesList(gama.runtime.scope.IScope, gama.util.list.IList)
	 */
	@Override
	public Object getFromIndicesList(final IScope scope, final IList indices) throws GamaRuntimeException {
		return null;
	}

	/**
	 * Method contains()
	 *
	 * @see gama.common.interfaces.IContainer#contains(gama.runtime.scope.IScope, java.lang.Object)
	 */
	@Override
	public boolean contains(final IScope scope, final Object o) throws GamaRuntimeException {
		return o == null ? key == null || value == null : o.equals(key) || o.equals(value);
	}

	/**
	 * Method firstValue()
	 *
	 * @see gama.common.interfaces.IContainer#firstValue(gama.runtime.scope.IScope)
	 */
	@Override
	public Object firstValue(final IScope scope) throws GamaRuntimeException {
		return key;
	}

	/**
	 * Method lastValue()
	 *
	 * @see gama.common.interfaces.IContainer#lastValue(gama.runtime.scope.IScope)
	 */
	@Override
	public Object lastValue(final IScope scope) throws GamaRuntimeException {
		return value;
	}

	/**
	 * Method length()
	 *
	 * @see gama.common.interfaces.IContainer#length(gama.runtime.scope.IScope)
	 */
	@Override
	public int length(final IScope scope) {
		return 2;
	}

	/**
	 * Method isEmpty()
	 *
	 * @see gama.common.interfaces.IContainer#isEmpty(gama.runtime.scope.IScope)
	 */
	@Override
	public boolean isEmpty(final IScope scope) {
		return false;
	}

	/**
	 * Method reverse()
	 *
	 * @see gama.common.interfaces.IContainer#reverse(gama.runtime.scope.IScope)
	 */
	@Override
	public IContainer reverse(final IScope scope) throws GamaRuntimeException {
		return new GamaPair(value, key, type.getContentType(), type.getKeyType());
	}

	/**
	 * Method anyValue()
	 *
	 * @see gama.common.interfaces.IContainer#anyValue(gama.runtime.scope.IScope)
	 */
	@Override
	public Object anyValue(final IScope scope) {
		final int i = scope.getRandom().between(0, 1);
		return i == 0 ? key : value;
	}

	/**
	 * Method listValue()
	 *
	 * @see gama.common.interfaces.IContainer#listValue(gama.runtime.scope.IScope, gaml.types.IType)
	 */
	@Override
	public IList listValue(final IScope scope, final IType contentType, final boolean copy) {
		return GamaListFactory.wrap(contentType, contentType.cast(scope, key, null, copy),
				contentType.cast(scope, value, null, copy));
	}

	/**
	 * Method matrixValue()
	 *
	 * @see gama.common.interfaces.IContainer#matrixValue(gama.runtime.scope.IScope, gaml.types.IType)
	 */
	@Override
	public IMatrix matrixValue(final IScope scope, final IType contentType, final boolean copy) {
		return GamaMatrixType.from(scope, listValue(scope, contentType, copy), contentType, null);
	}

	/**
	 * Method matrixValue()
	 *
	 * @see gama.common.interfaces.IContainer#matrixValue(gama.runtime.scope.IScope, gaml.types.IType,
	 *      gama.metamodel.shape.GamaPoint)
	 */
	@Override
	public IMatrix matrixValue(final IScope scope, final IType contentType, final GamaPoint size, final boolean copy) {
		return GamaMatrixType.from(scope, listValue(scope, contentType, copy), contentType, size);
	}

	/**
	 * Method mapValue()
	 *
	 * @see gama.common.interfaces.IContainer#mapValue(gama.runtime.scope.IScope, gaml.types.IType, gaml.types.IType)
	 */
	@Override
	public IMap mapValue(final IScope scope, final IType keyType, final IType contentType, final boolean copy) {
		final IMap result = GamaMapFactory.create(keyType, contentType);
		result.setValueAtIndex(scope, key, value);
		return result;
	}

	/**
	 * Method iterable()
	 *
	 * @see gama.common.interfaces.IContainer#iterable(gama.runtime.scope.IScope)
	 */
	@Override
	public java.lang.Iterable iterable(final IScope scope) {
		return listValue(scope, Types.NO_TYPE, false);
	}

	@Override
	public boolean containsKey(final IScope scope, final Object o) throws GamaRuntimeException {
		return Objects.equals(key, o);
	}

}
