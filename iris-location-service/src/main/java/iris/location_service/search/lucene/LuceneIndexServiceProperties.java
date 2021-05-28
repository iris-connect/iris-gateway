package iris.location_service.search.lucene;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "lucene")
public class LuceneIndexServiceProperties {

    private String indexDirectory;

}
