package sk.hyll.patrik.codium.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;

/**
 * Debit card
 * Basic BankCard
 */
@JsonTypeName("debit")
@Entity
public class DebitCard extends BankCard {
    public DebitCard() {
    }
    public DebitCard(int cardNumber, byte csv, String brand, State state) {
        super(cardNumber, csv, brand, state);
    }
}
