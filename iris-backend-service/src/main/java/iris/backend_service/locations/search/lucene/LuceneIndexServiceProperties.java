package iris.backend_service.locations.search.lucene;

import lombok.Data;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@ConfigurationProperties(prefix = "lucene")
@Validated
public class LuceneIndexServiceProperties {

	@NotNull
	private String indexDirectory;

}
