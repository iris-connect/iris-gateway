package iris.demo.contact_diary_app.config;

import lombok.AllArgsConstructor;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ResourceHelper {

	private ResourceLoader resourceLoader;

	public Resource[] loadResources(String pattern) throws IOException {
		return ResourcePatternUtils
				.getResourcePatternResolver(resourceLoader)
				.getResources(pattern);
	}

	public Resource loadResource(String path) throws IOException {
		return ResourcePatternUtils
				.getResourcePatternResolver(resourceLoader)
				.getResource(path);
	}

}
