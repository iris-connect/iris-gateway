package iris.location_service.search.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LuceneSearcher {

        private Directory dir;
        private Analyzer analyzer;

        private IndexSearcher indexSearcher;

        public LuceneSearcher(Directory dir, Analyzer analyzer) throws IOException {
            this.dir = dir;
            this.analyzer = analyzer;

            IndexReader reader = DirectoryReader.open(dir);
            indexSearcher = new IndexSearcher(reader);
        }

        public List<Document> search(String searchString) throws IOException, ParseException {
            BooleanQuery.Builder finalQuery = new BooleanQuery.Builder();
            QueryParser queryParser = new MultiFieldQueryParser(LuceneConstants.FIELDS, analyzer);

            String[] terms = searchString.split(" ");
            for (String term : terms){
                if(!term.equals("")) {
                    finalQuery.add(queryParser.parse(term.replace("~","")+"~2"), BooleanClause.Occur.MUST);
                }
            }
            TopDocs searchResult = indexSearcher.search(finalQuery.build(), LuceneConstants.MAX_SEARCH);

            List<Document> result = new ArrayList<>();
            for(ScoreDoc entry:searchResult.scoreDocs){
                result.add(indexSearcher.doc(entry.doc));
            }
            return result;
        }
}
