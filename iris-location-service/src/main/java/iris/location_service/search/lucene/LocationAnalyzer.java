package iris.location_service.search.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;

/**
 * Analyzer consists of WhitespaceTokenizer(Every space indicates a new token) and the lowercase filter
 */
public class LocationAnalyzer  extends Analyzer {


    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new WhitespaceTokenizer();
        TokenStream result = new LowerCaseFilter(tokenizer);
        return new TokenStreamComponents(tokenizer, result);
    }
}
