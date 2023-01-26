package org.deblock.exercise.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

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