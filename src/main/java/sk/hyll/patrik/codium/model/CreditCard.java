package sk.hyll.patrik.codium.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Credit card
 * Bank card with limitations
 */
@JsonTypeName("credit")
@Entity
public class CreditCard extends BankCard {
    @Transient
    private Double limit;

    public CreditCard() {
    }

    public CreditCard(int cardNumber, byte csv, String brand, State state, double limit) {
        super(cardNumber, csv, brand, state);
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
