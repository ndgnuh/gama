/*******************************************************************************************************
 *
 * gama.util.ICollector.java, in plugin gama.core, is part of the source code of the GAMA modeling and simulation
 * platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package gama.common.interfaces;

import java.io.Closeable;
import java.util.Collection;

import gama.common.util.Collector;
import gama.common.util.RandomUtils;

public interface ICollector<E> extends Collection<E>, Closeable {

	Collection<E> items();

	@Override
	default void close() {
		Collector.release(this);
	}

	default void shuffleInPlaceWith(final RandomUtils random) {
		random.shuffleInPlace(items());
	}
}