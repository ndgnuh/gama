/*******************************************************************************************************
 *
 * msi.gama.common.util.StringUtils.java, in plugin msi.gama.core, is part of the source code of the GAMA modeling and
 * simulation platform (v. 1.8)
 *
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 *
 ********************************************************************************************************/
package msi.gama.common.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import msi.gama.common.interfaces.IGamlable;
import msi.gama.util.GamaListFactory;
import msi.gama.util.IList;
import msi.gaml.types.Types;

/**
 * The class StringUtils.
 *
 * @author drogoul
 * @since 13 d�c. 2011
 *
 */
@SuppressWarnings ({ "rawtypes", "unchecked" })
public class StringUtils {

	final static String strings = "'(?:[^\\\\']+|\\\\.)*'";
	final static String operators = "::|<>|!=|>=|<=|//";
	public final static String ponctuation = "\\p{Punct}";
	public final static String literals = "\\w+\\$\\w+|\\#\\w+|\\d+\\.\\d+|\\w+\\.\\w+|\\w+";
	final static String regex = strings + "|" + literals + "|" + operators + "|" + ponctuation;

	static public String toGamlString(final String s) {
		if (s == null) { return null; }
		try (final TextBuilder sb = TextBuilder.create()) {
			sb.append('\'');
			sb.append(s.replace("\\", "\\\\").replace("'", "\\'").replace("\"", "\\\"").replace("/", "\\/"));
			sb.append('\'');
			return sb.toString();
		}
	}

	static public String toJavaString(final String s) {
		if (s == null) { return null; }
		final String t = s.trim();
		if (!isGamaString(t)) { return s; }
		if (t.length() >= 2) { return t.substring(1, t.length() - 1); }
		return s;
	}

	public static List<String> tokenize(final String expression) {
		if (expression == null) { return Collections.EMPTY_LIST; }
		final Pattern p = Pattern.compile(regex);
		final List<String> tokens = new ArrayList<>();
		final Matcher m = p.matcher(expression);
		while (m.find()) {
			tokens.add(expression.substring(m.start(), m.end()));
		}
		return tokens;
	}

	public static String unescapeJava(final String st) {
		if (st == null) { return null; }
		try (TextBuilder sb = TextBuilder.create()) {

			for (int i = 0; i < st.length(); i++) {
				char ch = st.charAt(i);
				if (ch == '\\') {
					final char nextChar = i == st.length() - 1 ? '\\' : st.charAt(i + 1);
					// Octal escape?
					if (nextChar >= '0' && nextChar <= '7') {
						String code = "" + nextChar;
						i++;
						if (i < st.length() - 1 && st.charAt(i + 1) >= '0' && st.charAt(i + 1) <= '7') {
							code += st.charAt(i + 1);
							i++;
							if (i < st.length() - 1 && st.charAt(i + 1) >= '0' && st.charAt(i + 1) <= '7') {
								code += st.charAt(i + 1);
								i++;
							}
						}
						sb.append((char) Integer.parseInt(code, 8));
						continue;
					}
					switch (nextChar) {
						case '\\':
							ch = '\\';
							break;
						case 'b':
							ch = '\b';
							break;
						case 'f':
							ch = '\f';
							break;
						case 'n':
							ch = '\n';
							break;
						case 'r':
							ch = '\r';
							break;
						case 't':
							ch = '\t';
							break;
						case '\"':
							ch = '\"';
							break;
						case '\'':
							ch = '\'';
							break;
						// Hex Unicode: u????
						case 'u':
							if (i >= st.length() - 5) {
								ch = 'u';
								break;
							}
							final int code = Integer.parseInt(
									"" + st.charAt(i + 2) + st.charAt(i + 3) + st.charAt(i + 4) + st.charAt(i + 5), 16);
							sb.append(Character.toChars(code));
							i += 5;
							continue;
					}
					i++;
				}
				sb.append(ch);
			}
			return sb.toString();
		}
	}

	static public boolean isGamaString(final String s) {
		if (s == null) { return false; }
		final int n = s.length();
		if (n == 0 || n == 1) { return false; }
		if (s.charAt(0) != '\'') { return false; }
		return s.charAt(n - 1) == '\'';
	}

	public static final DecimalFormat DEFAULT_DECIMAL_FORMAT;
	public static final DecimalFormatSymbols SYMBOLS;

	static {
		SYMBOLS = new DecimalFormatSymbols();
		SYMBOLS.setDecimalSeparator('.');
		SYMBOLS.setInfinity("#infinity");
		SYMBOLS.setNaN("#nan");
		DEFAULT_DECIMAL_FORMAT = new DecimalFormat("##0.0################", SYMBOLS);
	}

	public static String toGaml(final Object val, final boolean includingBuiltIn) {
		if (val == null) { return "nil"; }
		if (val instanceof IGamlable) { return ((IGamlable) val).serialize(includingBuiltIn); }
		if (val instanceof String) { return toGamlString((String) val); }
		if (val instanceof Double) { return DEFAULT_DECIMAL_FORMAT.format(val); }
		if (val instanceof Collection) {
			final IList l = GamaListFactory.create(Types.STRING);
			l.addAll((Collection) val);
			return toGaml(l, includingBuiltIn);
		}
		return String.valueOf(val);
	}

}
