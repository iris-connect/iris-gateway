package iris.backend_service.core.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import iris.backend_service.config.JsonRpcEndpointsConfig;
import iris.backend_service.core.validation.NoSignOfAttack.NoSignOfAttackValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = NoSignOfAttackValidator.class)
@Documented
public @interface NoSignOfAttack {

	String message()

	default "{iris.validation.constraints.NoSignOfAttack.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	public interface Phone extends Payload {}

	static class NoSignOfAttackValidator implements ConstraintValidator<NoSignOfAttack, String> {

		@Autowired
		private AttackDetector validationHelper;

		@Autowired
		HttpServletRequest request;

		private Class<? extends Payload> type;

		@Override
		public void initialize(NoSignOfAttack constraintAnnotation) {

			var payloads = constraintAnnotation.payload();

			Assert.isTrue(payloads.length <= 1, "Only one type can be defined!");

			if (payloads.length == 1) {
				this.type = payloads[0];
			}
		}

		@Override
		public boolean isValid(final String text, final ConstraintValidatorContext context) {

			var epsClient = Objects.toString(request.getAttribute(JsonRpcEndpointsConfig.EPS_CLIENT_ATTRIBUTE), "");

			var path = "";
			if (context instanceof ConstraintValidatorContextImpl impl) {
				path = impl.getConstraintViolationCreationContexts().get(0).getPath().asString();
			}

			if (type == Phone.class) {
				return !validationHelper.isPossibleAttackForPhone(text, path, true, epsClient);
			}

			return !validationHelper.isPossibleAttack(text, path, true, epsClient);
		}
	}
}
