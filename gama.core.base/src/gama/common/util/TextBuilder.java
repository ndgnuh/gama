package gama.common.util;

import java.io.Closeable;

/**
 * A front-end to a StringBuilder, which can be used in try-with sequences and automatically managed in an internal pool
 *
 * @author drogoul
 *
 */

public class TextBuilder implements Closeable {

	private final static PoolUtils.ObjectPool<TextBuilder> POOL =
			PoolUtils.create("Closeable StringBuilders", true, () -> new TextBuilder(), null);

	final StringBuilder builder = new StringBuilder(100);

	public static TextBuilder create() {
		return POOL.get();
	}

	public StringBuilder append(final Object obj) {
		return append(String.valueOf(obj));
	}

	public StringBuilder append(final String str) {
		return builder.append(str);
	}

	public StringBuilder append(final CharSequence s) {
		return builder.append(s);
	}

	public StringBuilder append(final char c) {
		return builder.append(c);
	}

	public StringBuilder append(final int i) {
		return builder.append(i);
	}

	public StringBuilder append(final char[] c) {
		return builder.append(c);
	}

	@Override
	public String toString() {
		return builder.toString();
	}

	@Override
	public void close() {
		builder.setLength(0);
		POOL.release(this);
	}

	public int length() {
		return builder.length();
	}

	public void setLength(final int i) {
		builder.setLength(i);
	}

	public StringBuilder getBuilder() {
		return builder;
	}

}
