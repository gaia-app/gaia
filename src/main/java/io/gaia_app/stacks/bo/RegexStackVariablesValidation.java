package io.gaia_app.stacks.bo;

import io.gaia_app.stacks.service.RegexStackVariablesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RegexStackVariablesValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegexStackVariablesValidation {

    String message() default "variables should match the regex";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
