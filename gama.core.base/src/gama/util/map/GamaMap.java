/*******************************************************************************************************
 *
 * gama.util.GamaMap.java, in plugin gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.util.map;

import java.util.LinkedHashMap;

import gama.common.interfaces.IContainer;
import gama.runtime.scope.IScope;
import gama.util.list.GamaListFactory;
import gama.util.list.IList;
import gaml.types.IContainerType;
import gaml.types.IType;
import gaml.types.Types;

/**
 * The Class GamaMap. Use GamaMapFactory to create GamaMaps
 */

@SuppressWarnings ({ "unchecked", "rawtypes" })
public class GamaMap<K, V> extends LinkedHashMap<K, V> implements IMap<K, V> {

	public static final String KEYS = "keys";
	public static final String VALUES = "values";
	public static final String PAIRS = "pairs";

	IContainerType type;

	protected GamaMap(final int capacity, final IType key, final IType content) {
		super(capacity);
		type = Types.MAP.of(key, content);
	}

	@Override
	public IContainerType getGamlType() {
		return type;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == this) { return true; }
		if (!(o instanceof IMap)) { return false; }
		return GamaMapFactory.equals(this, (IMap) o);
	}

	@Override
	public GamaMap reverse(final IScope scope) {
		final GamaMap map = new GamaMap(size(), getGamlType().getContentType(), getGamlType().getKeyType());
		forEach((k, v) -> map.put(v, k));
		return map;
	}

	protected IContainer<?, K> buildIndexes(final IScope scope, final IContainer value) {
		final IList<K> result = GamaListFactory.create(getGamlType().getContentType());
		for (final Object o : value.iterable(scope)) {
			result.add(buildIndex(scope, o));
		}
		return result;
	}

	@Override
	public boolean isOrdered() {
		return true;
	}

}
