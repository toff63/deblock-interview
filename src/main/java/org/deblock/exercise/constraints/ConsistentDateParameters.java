package org.deblock.exercise.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation to validate if a return date is either the same day, either after the return date
 */
@Constraint(validatedBy = ConsistentDateParameterValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConsistentDateParameters {

    String message() default
            "Return date must be after departure date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}