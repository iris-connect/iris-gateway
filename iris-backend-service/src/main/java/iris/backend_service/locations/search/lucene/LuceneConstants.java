package iris.backend_service.locations.search.lucene;

/**
 * Contains all necessary constants for setting up lucene
 */
public class LuceneConstants {
	public static final String[] FIELDS = { "Id", "ProviderId", "Name", "ContactOfficialName", "ContactRepresentative",
			"ContactAddressStreet", "ContactAddressCity", "ContactAddressZip", "ContactOwnerEmail", "ContactEmail",
			"ContactPhone" };
	public static final int MAX_SEARCH = 10;
	public static final String[] SPECIAL_CHARACTERS = { "+", "-", "&&", "||", "!", "(", ")", "{", "}", "[", "]", "^",
			"\"", "~", "*", "?", ":", "\\" };

	/**
	 * Prepends an escape character (\) before each special character
	 * 
	 * @param input keyword to escape
	 * @return the keyword containing escaped characters
	 */
	public static String escapeLuceneKeywords(String input) {
		for (String specialChar : SPECIAL_CHARACTERS) {
			input = input.replace(specialChar, "\\" + specialChar);
		}
		return input;
	}
}
