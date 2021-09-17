package iris.backend_service.locations.search.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;

/**
 * Analyzer for indexing the location and provider id
 */
public class IdAnalyzer extends Analyzer {

	/**
	 * adds following components to the analyzer: WhitespaceTokenizer(Every space indicates a new token) Lowercase filter
	 */
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer tokenizer = new WhitespaceTokenizer();
		TokenStream result = new LowerCaseFilter(tokenizer);
		return new TokenStreamComponents(tokenizer, result);
	}
}
