/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package iris.backend_service.core.logging;

import static org.assertj.core.api.Assertions.*;

import org.apache.commons.lang3.Range;
import org.junit.jupiter.api.Test;

/**
 * @author Jens Kutzsche
 */
class LoggingHelperTest {

	/**
	 * Test method for {@link iris.backend_service.core.logging.LoggingHelper#obfuscateEndPart(java.lang.String)}.
	 */
	@Test
	void testObfuscateEndPart() {
		assertThat(LoggingHelper.obfuscateEndPart("abcdefg")).isEqualTo("abcd***");
		assertThat(LoggingHelper.obfuscateEndPart("ab")).isEqualTo("***");
		assertThat(LoggingHelper.obfuscateEndPart("")).isEqualTo("***");
		assertThat(LoggingHelper.obfuscateEndPart(null)).isNull();
	}

	/**
	 * Test method for
	 * {@link iris.backend_service.core.logging.LoggingHelper#obfuscateOutsiteExtRange(java.lang.String, org.apache.commons.lang3.Range)}.
	 */
	@Test
	void testObfuscateOutsiteExtRange() {

		assertThat(LoggingHelper.obfuscateOutsiteExtRange(null, Range.between(2, 4))).isNull();
		assertThat(LoggingHelper.obfuscateOutsiteExtRange("", Range.between(2, 4))).isEmpty();
		assertThat(LoggingHelper.obfuscateOutsiteExtRange(null, null)).isNull();
		assertThat(LoggingHelper.obfuscateOutsiteExtRange("", null)).isEmpty();
		assertThat(LoggingHelper.obfuscateOutsiteExtRange("abcdefg", null)).isEqualTo("*******");
		assertThat(LoggingHelper.obfuscateOutsiteExtRange("abcdefg", Range.between(2, 4))).isEqualTo("abcdefg");
		assertThat(LoggingHelper.obfuscateOutsiteExtRange("abcdefg", Range.between(0, 1))).isEqualTo("abcde**");
		assertThat(LoggingHelper.obfuscateOutsiteExtRange("abcdefg", Range.between(5, 6))).isEqualTo("**cdefg");
		assertThat(LoggingHelper.obfuscateOutsiteExtRange("abcdefghi", Range.between(4, 4))).isEqualTo("*bcdefgh*");
	}
}
