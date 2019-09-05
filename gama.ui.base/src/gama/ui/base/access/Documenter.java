package gama.ui.base.access;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Multimap;

import gama.common.interfaces.IGamlDescription;
import gama.common.util.TextBuilder;
import gaml.operators.Strings;

public class Documenter {

	private static String[] HTML_TAGS =
			{ "<br/>", "<br>", "<b>", "</b>", "<i>", "</i>", "<ul>", "</ul>", "<li>", "</li>" };
	private static String[] REPLACEMENTS = { Strings.LN, Strings.LN, "", "", "", "", "", "", Strings.LN + "- ", "" };

	public static String toText(final String s) {
		if (s == null)
			return "";
		return breakStringToLines(StringUtils.replaceEach(s, HTML_TAGS, REPLACEMENTS), 120, Strings.LN);
	}

	/**
	 * Breaks the given string into lines as best possible, each of which no longer than <code>maxLength</code>
	 * characters. By Tomer Godinger.
	 *
	 * @param str
	 *            The string to break into lines.
	 * @param maxLength
	 *            Maximum length of each line.
	 * @param newLineString
	 *            The string to use for line breaking.
	 * @return The resulting multi-line string.
	 */
	public static String breakStringToLines(final String s, final int maxLength, final String newLineString) {
		String str = s;
		try (TextBuilder sb = TextBuilder.create()) {
			while (str.length() > maxLength) {
				// Attempt to break on whitespace first,
				int breakingIndex = lastIndexOfRegex(str, "\\s", maxLength);

				// Then on other non-alphanumeric characters,
				if (breakingIndex == NOT_FOUND) {
					breakingIndex = lastIndexOfRegex(str, "[^a-zA-Z0-9]", maxLength);
				}

				// And if all else fails, break in the middle of the word
				if (breakingIndex == NOT_FOUND) {
					breakingIndex = maxLength;
				}

				// Append each prepared line to the builder
				sb.append(str.substring(0, breakingIndex + 1));
				sb.append(newLineString);

				// And start the next line
				str = str.substring(breakingIndex + 1);
			}

			// Check if there are any residual characters left
			if (str.length() > 0) {
				sb.append(str);
			}

			// Return the resulting string
			return sb.toString();
		}
	}

	public static String getDocumentationOn(final String query) {
		final String keyword = StringUtils.removeEnd(StringUtils.removeStart(query.trim(), "#"), ":");
		final Multimap<GamlIdiomsProvider<?>, IGamlDescription> results = GamlIdiomsProvider.forName(keyword);
		if (results.isEmpty())
			return "No result found";
		try (TextBuilder sb = TextBuilder.create()) {
			final int max = results.keySet().stream().mapToInt(each -> each.name.length()).max().getAsInt();
			final String separator = StringUtils.repeat("—", max + 6).concat(Strings.LN);
			results.asMap().forEach((provider, list) -> {
				sb.append("").append(separator).append("|| ");
				sb.append(StringUtils.rightPad(provider.name, max));
				sb.append(" ||").append(Strings.LN).append(separator);
				for (final IGamlDescription d : list) {
					sb.append("== ").append(toText(d.getTitle())).append(Strings.LN)
							.append(toText(provider.document(d))).append(Strings.LN);
				}
			});

			return sb.toString();
		}

		//
	}

	/**
	 * Indicates that a String search operation yielded no results.
	 */
	public static final int NOT_FOUND = -1;

	/**
	 * Version of lastIndexOf that uses regular expressions for searching. By Tomer Godinger.
	 *
	 * @param str
	 *            String in which to search for the pattern.
	 * @param toFind
	 *            Pattern to locate.
	 * @return The index of the requested pattern, if found; NOT_FOUND (-1) otherwise.
	 */
	public static int lastIndexOfRegex(final String str, final String toFind) {
		final Pattern pattern = Pattern.compile(toFind);
		final Matcher matcher = pattern.matcher(str);

		// Default to the NOT_FOUND constant
		int lastIndex = NOT_FOUND;

		// Search for the given pattern
		while (matcher.find()) {
			lastIndex = matcher.start();
		}

		return lastIndex;
	}

	/**
	 * Finds the last index of the given regular expression pattern in the given string, starting from the given index
	 * (and conceptually going backwards). By Tomer Godinger.
	 *
	 * @param str
	 *            String in which to search for the pattern.
	 * @param toFind
	 *            Pattern to locate.
	 * @param fromIndex
	 *            Maximum allowed index.
	 * @return The index of the requested pattern, if found; NOT_FOUND (-1) otherwise.
	 */
	public static int lastIndexOfRegex(final String str, final String toFind, final int fromIndex) {
		// Limit the search by searching on a suitable substring
		return lastIndexOfRegex(str.substring(0, fromIndex), toFind);
	}

}
