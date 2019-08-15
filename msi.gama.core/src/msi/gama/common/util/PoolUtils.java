package msi.gama.common.util;

import java.util.ArrayDeque;
import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;

import msi.gama.common.interfaces.IDisposable;
import msi.gama.common.preferences.GamaPreferences;
import ummisco.gama.dev.utils.DEBUG;

public class PoolUtils {

	static Set<ObjectPool<?>> POOLS = new LinkedHashSet<>();
	static boolean POOL = GamaPreferences.External.USE_POOLING.getValue();
	static {
		// DEBUG.ON();
		GamaPreferences.External.USE_POOLING.onChange(v -> {
			POOLS.forEach((p) -> p.dispose());
			POOL = v;
		});
	}

	public static void WriteStats() {
		if (!DEBUG.IS_ON()) { return; }
		DEBUG.SECTION("Pool statistics");
		POOLS.forEach((p) -> {
			DEBUG.OUT(p.name, 30, "accessed " + p.accessed + " times | created " + p.created + " times | released "
					+ p.released + " times | objects size: " + p.objects.get().size());
		});
	}

	public interface ObjectFactory<T> {
		T createNew();
	}

	public interface ObjectCleaner<T> {
		void clean(T object);
	}

	public static class ObjectPool<T> implements IDisposable {

		private final String name;
		private long accessed, released, created;
		private final ObjectFactory<T> factory;
		private final ObjectCleaner<T> cleaner;
		private final ThreadLocal<Queue<T>> objects = ThreadLocal.withInitial(() -> new ArrayDeque<>());
		public boolean active;

		private ObjectPool(final String name, final ObjectFactory<T> factory, final ObjectCleaner<T> cleaner,
				final boolean active) {
			this.name = name;
			this.factory = factory;
			this.cleaner = cleaner;
			this.active = active;
		}

		public T get() {
			if (!POOL || !active) { return factory.createNew(); }
			accessed++;

			T result = objects.get().poll();
			if (result == null) {
				created++;
				result = factory.createNew();
			}
			return result;
		}

		public void release(final T t) {
			if (t == null) { return; }
			if (cleaner != null) {
				cleaner.clean(t);
			}
			if (POOL && active) {
				released++;
				objects.get().offer(t);
			}

		}

		@Override
		public void dispose() {
			objects.get().clear();
		}
	}

	public static <T> ObjectPool<T> create(final String name, final boolean active, final ObjectFactory<T> factory,
			final ObjectCleaner<T> cleaner) {
		DEBUG.OUT("Adding object pool: " + name);
		final ObjectPool<T> result = new ObjectPool<>(name, factory, cleaner, active);
		if (DEBUG.IS_ON()) {
			POOLS.add(result);
		}
		return result;
	}

}
