package de.healthIMIS.irisappapidemo.config;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
