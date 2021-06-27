package iris.location_service.search.lucene;


public class LuceneConstants {
    public static final String[] FIELDS = {"Id", "ProviderId", "Name", "ContactOfficialName", "ContactRepresentative", "ContactAddressStreet", "ContactAddressCity", "ContactAddressZip", "ContactOwnerEmail", "ContactEmail", "ContactPhone"};
    public static final int MAX_SEARCH = 10;
    public static final String[] SPECIAL_CHARACTERS = {"+","-","&&","||","!","(",")","{","}","[","]","^","\"","~","*","?",":","\\"};

    public static String escapeLuceneKeywords(String input){
        for (String specialChar : SPECIAL_CHARACTERS){
            input = input.replace(specialChar,"\\"+ specialChar);
        }
        return input;
    }
}

