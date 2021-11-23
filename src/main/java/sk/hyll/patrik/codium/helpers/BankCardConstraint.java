package sk.hyll.patrik.codium.helpers;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom annotation to valid Bank Card number
 */
@Documented
@Constraint(validatedBy = BankCardNumberValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BankCardConstraint {
    String message() default "Invalid bank card number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

