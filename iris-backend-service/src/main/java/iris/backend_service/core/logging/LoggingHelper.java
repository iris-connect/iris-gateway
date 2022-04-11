package iris.backend_service.core.logging;

import java.util.stream.IntStream;

import org.apache.commons.lang3.Range;

public class LoggingHelper {

	private LoggingHelper() {}

	public static String obfuscateEndPart(String s) {
		return (s == null) ? null : s.replaceFirst(".{0,3}$", "***");
	}

	/**
	 * Extends unobfuscated range with 3 to both sides.
	 */
	public static String obfuscateOutsiteExtRange(String s, Range<Integer> range) {

		if (s == null) {
			return null;
		}

		var extRange = range != null
				? Range.between(range.getMinimum() - 3, range.getMaximum() + 3)
				: Range.is(-1);

		return IntStream.range(0, s.length())
				.map(i -> extRange.contains(i) ? s.codePointAt(i) : '*')
				.collect(StringBuilder::new,
						StringBuilder::appendCodePoint,
						StringBuilder::append)
				.toString();
	}
}
