package de.healthIMIS.iris.public_server.web;

import static org.springframework.hateoas.MediaTypes.*;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Enables serialization/deserialization with HAL syntax for the vendor media type.
 * 
 * @author Jens Kutzsche
 */
@Component
@RequiredArgsConstructor
public class HalMediaTypeEnabler {

	private static final MediaType CUSTOM_MEDIA_TYPE = new MediaType("application", "*+hal+json");

	private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	@PostConstruct
	public void enableVndHalJson() {
		for (HttpMessageConverter<?> converter : requestMappingHandlerAdapter.getMessageConverters()) {
			if (converter instanceof MappingJackson2HttpMessageConverter
					&& converter.getSupportedMediaTypes().contains(HAL_JSON)) {
				MappingJackson2HttpMessageConverter messageConverter = (MappingJackson2HttpMessageConverter) converter;
				messageConverter.setSupportedMediaTypes(Arrays.asList(HAL_JSON, CUSTOM_MEDIA_TYPE));
			}
		}
	}
}
