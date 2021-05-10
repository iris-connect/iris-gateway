package iris.location_service.search.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import java.util.Arrays;

/**
 * Analyzer consists of :
 *      WhitespaceTokenizer(Every space indicates a new token)
 *      Lowercase filter
 *      Stop Words(this words are not tokenized )
 */
public class LocationAnalyzer  extends Analyzer {

    /**
     * ":", "[" , "]" , "," "{" , "}" vielleicht auch zu Stop words


    String[]words = {"locations", "id", "name", "publicKey", "contact",
                     "officalName", "adress", "street", "city", "zip",
                     "ownerEmail", "email", "phone", "contexts"};

    CharArraySet stop_words = new CharArraySet(Arrays.asList(words),false);
    */
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new WhitespaceTokenizer();
        TokenStream result = new LowerCaseFilter(tokenizer);
        //result = new StopFilter(result,stop_words);
        return new TokenStreamComponents(tokenizer, result);
    }
}
