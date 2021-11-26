package sk.hyll.patrik.codium.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates BankCard number
 */
public class BankCardNumberValidator implements
        ConstraintValidator<BankCardConstraint, Long> {

    @Autowired
    private Environment env;

    @Override
    public boolean isValid(Long cardNumber,
                           ConstraintValidatorContext cxt) {
        if(env.getProperty("spring.profiles.active").equals("local")){
            return true;
        }

        return BankCardValidator.isValid(cardNumber);
    }
}

