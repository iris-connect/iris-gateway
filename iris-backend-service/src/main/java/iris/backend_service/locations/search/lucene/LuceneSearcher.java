package iris.backend_service.locations.search.lucene;

import iris.backend_service.locations.search.db.model.LocationIdentifier;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

@Slf4j
public class LuceneSearcher {

	private Directory dir;
	private Analyzer analyzer;

	public LuceneSearcher(Directory dir, Analyzer analyzer) throws IOException {
		this.dir = dir;
		this.analyzer = analyzer;
	}

	/**
	 * Search indexed locations
	 *
	 * @param searchString String to search for
	 * @return List<Document> of the best results in order
	 * @throws IOException thrown when updatesearcher has an invalid path
	 * @throws ParseException thrown when the query parser can't parse a term
	 */
	public List<Document> search(String searchString) throws IOException, ParseException {
		IndexSearcher indexSearcher = updateSearcher();
		BooleanQuery.Builder finalQuery = new BooleanQuery.Builder();
		QueryParser queryParser = new MultiFieldQueryParser(LuceneConstants.FIELDS, analyzer);

		searchString = LuceneConstants.escapeLuceneKeywords(searchString);
		String[] terms = searchString.split("( |\\\\-)");
		for (String term : terms) {
			if (!term.equals("")) {
				finalQuery.add(queryParser.parse(term.replace("~", "") + "~2"), BooleanClause.Occur.SHOULD);
			}
		}
		TopDocs searchResult = indexSearcher.search(finalQuery.build(), LuceneConstants.MAX_SEARCH);

		List<Document> result = new ArrayList<>();
		for (ScoreDoc entry : searchResult.scoreDocs) {
			result.add(indexSearcher.doc(entry.doc));
		}
		indexSearcher.getIndexReader().close();
		return result;
	}

	/**
	 * Search an indexed location by id
	 *
	 * @param locationIdentifier LocationIdentifier for provider and location id
	 * @return If the location is found the method returns a Lucene Document with the data of it. Otherwise the method
	 *         will return null
	 */
	public Document searchById(LocationIdentifier locationIdentifier) throws IOException {
		String providerId = locationIdentifier.getProviderId();
		String id = locationIdentifier.getLocationId();

		IndexSearcher indexSearcher = updateSearcher();
		try {
			BooleanQuery.Builder finalQuery = new BooleanQuery.Builder();
			finalQuery.add(new QueryParser("ProviderId", analyzer).parse(providerId), BooleanClause.Occur.MUST);
			finalQuery.add(new QueryParser("Id", analyzer).parse(id), BooleanClause.Occur.MUST);

			TopDocs searchResult = indexSearcher.search(finalQuery.build(), 1);
			ScoreDoc[] scoreDocs = searchResult.scoreDocs;
			if (scoreDocs.length > 0) {
				Document doc = indexSearcher.doc(scoreDocs[0].doc);
				indexSearcher.getIndexReader().close();
				return doc;
			}
		} catch (Exception e) {
			log.error("Error while seacrhById: ", e);
		}
		indexSearcher.getIndexReader().close();
		return null;
	}

	/**
	 * Search indexed locations by provider id
	 *
	 * @param provider id
	 * @return List of found locations.
	 */
	public List<Document> searchByProviderId(String providerId) throws IOException {

		IndexSearcher indexSearcher = updateSearcher();
		try {
			BooleanQuery.Builder finalQuery = new BooleanQuery.Builder();
			finalQuery.add(new QueryParser("ProviderId", analyzer).parse(providerId), BooleanClause.Occur.MUST);

			TopDocs searchResult = indexSearcher.search(finalQuery.build(), LuceneConstants.MAX_SEARCH);
			ScoreDoc[] scoreDocs = searchResult.scoreDocs;
			if (scoreDocs.length > 0) {
				return Arrays.stream(scoreDocs)
						.map(it -> {
							try {
								return indexSearcher.doc(it.doc);
							} catch (IOException e) {
								log.error("Error while seacrhById: ", e);
							}
							return null;
						})
						.filter(Objects::nonNull)
						.collect(Collectors.toList());
			}
		} catch (Exception e) {
			log.error("Error while seacrhById: ", e);
		} finally {
			indexSearcher.getIndexReader().close();
		}
		return List.of();
	}

	/**
	 * creates a new index searcher with the current directory
	 * 
	 * @return the new index searcher object
	 * @throws IOException thrown if directory does not exist
	 */
	private IndexSearcher updateSearcher() throws IOException {
		IndexReader reader = DirectoryReader.open(dir);
		return new IndexSearcher(reader);
	}
}
