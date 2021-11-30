package sk.hyll.patrik.codium.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Debit card
 * Basic BankCard
 */
@JsonTypeName("debit")
@Entity
public class DebitCard extends BankCard {
    public DebitCard() {
    }
    public DebitCard(int cardNumber, Date validity, String csv, String brand, State state) {
        super(cardNumber, validity, csv, brand, state);
    }
}
