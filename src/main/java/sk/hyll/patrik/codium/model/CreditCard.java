package sk.hyll.patrik.codium.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Credit card
 * Bank card with limitations
 */
@JsonTypeName("credit")
@Entity
public class CreditCard extends BankCard {

    @Transient
    @NotNull(message = "The credit card is required. Did you mean to insert a debit card?")
    private Double limit;

    public CreditCard() {
    }

    public CreditCard(int cardNumber, Date validity, String csv, String brand, State state, double limit) {
        super(cardNumber, validity, csv, brand, state);
        this.setLimit(limit);
    }

    private void setLimit(double limit) {
        this.limit = limit;
    }
    public Double getLimit() {return this.limit;}

    @Override
    public String toString() {
        return super.toString() +"CreditCard{" +
                "limit=" + limit +
                '}';
    }
}
