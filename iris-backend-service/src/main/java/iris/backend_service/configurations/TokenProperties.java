package iris.backend_service.configurations;

import lombok.Value;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("iris.tokens")
@ConstructorBinding
@Value
@Validated
class TokenProperties {

	private final int datLength;
	private final int catLength;

	private final @NotBlank String datSalt;
	private final @NotBlank String catSalt;

	TokenProperties(
			@DefaultValue("40") int datLength,
			@DefaultValue("40") int catLength,
			String datSalt,
			String catSalt) {

		this.datLength = datLength;
		this.catLength = catLength;
		this.datSalt = datSalt;
		this.catSalt = catSalt;
	}
}
