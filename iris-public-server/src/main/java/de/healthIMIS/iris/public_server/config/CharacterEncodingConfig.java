package de.healthIMIS.iris.public_server.config;

import static org.apache.commons.lang3.StringUtils.*;
import static org.springframework.http.HttpMethod.*;

import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class CharacterEncodingConfig {

	@Bean
	CharacterEncodingFilter characterEncodingFilter() {

		var filter = new UpdateRequestNeedCharacterEncodingFilter();

		filter.setEncoding("UTF-8");
		filter.setForceResponseEncoding(true);

		return filter;
	}

	static final class UpdateRequestNeedCharacterEncodingFilter extends OrderedCharacterEncodingFilter {

		static final EnumSet<HttpMethod> updateMethods = EnumSet.of(PUT, POST, PATCH);

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {

			if (isUpdateMethod(request) && isUtf8Missing(request)) {

				response.sendError(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "Character encoding must be UTF-8!");

				return;
			}

			super.doFilterInternal(request, response, filterChain);
		}

		private boolean isUpdateMethod(HttpServletRequest request) {
			return updateMethods.contains(resolve(request.getMethod()));
		}

		private boolean isUtf8Missing(HttpServletRequest request) {
			return !equalsIgnoreCase(request.getCharacterEncoding(), "UTF-8");
		}
	}
}
