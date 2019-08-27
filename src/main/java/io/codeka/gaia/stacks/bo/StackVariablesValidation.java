package io.codeka.gaia.stacks.bo;

import io.codeka.gaia.stacks.service.StackVariablesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StackVariablesValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StackVariablesValidation {

    String message() default "mandatory variables should not be blank";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
