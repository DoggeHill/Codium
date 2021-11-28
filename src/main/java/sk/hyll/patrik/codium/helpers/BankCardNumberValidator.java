package sk.hyll.patrik.codium.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates BankCard number
 * Validation is only allowed in production profile
 */
public class BankCardNumberValidator implements
        ConstraintValidator<BankCardConstraint, Long> {

    @Value("${spring.profiles.active}")
    private String property;
    @Override
    public boolean isValid(Long cardNumber,
                           ConstraintValidatorContext cxt) {

        // in case of no profile set
        if(this.property == null) return true;

        if(this.property.equals("local") || this.property.equals("localsql") ){
            return true;
        }

        return BankCardValidator.isValid(cardNumber);
    }
}

