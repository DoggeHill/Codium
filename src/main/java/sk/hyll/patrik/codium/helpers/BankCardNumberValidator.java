package sk.hyll.patrik.codium.helpers;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates BankCard number
 */
public class BankCardNumberValidator implements
        ConstraintValidator<BankCardConstraint, Long> {

    @Override
    public boolean isValid(Long cardNumber,
                           ConstraintValidatorContext cxt) {
        return BankCardValidator.isValid(cardNumber);
    }
}

