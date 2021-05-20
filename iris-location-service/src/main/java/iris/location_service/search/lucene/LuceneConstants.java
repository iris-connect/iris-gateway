package iris.location_service.search.lucene;


public class LuceneConstants {
    public static final String[] FIELDS = {"locations", "id", "name", "publicKey", "contact",
            "officalName", "adress", "street", "city", "zip",
            "ownerEmail", "email", "phone", "contexts"};
    public static final int MAX_SEARCH = 10;
}

