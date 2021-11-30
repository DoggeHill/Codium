package sk.hyll.patrik.codium.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

/**
 * Card owner
 * Card owner is in OneToMany relation ship with BankCard
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore all properties that cannot be mapped
@Table(uniqueConstraints= @UniqueConstraint(columnNames={"surname"})) // surname is unique
public class CardOwner{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "The name is required.")
    @Length(min=2, max=15)
    private String name;

    @Length(min=2, max=15)
    @NotBlank(message = "The surname is required.")
    private String surname;

    @OneToMany(mappedBy = "cardOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotEmpty(message = "Input bank cards list cannot be empty.")
    private final Set<BankCard> bankCards = new HashSet<>();

    /**
     * Assign new BankCard to the CardOwner
     * @param bankCard BankCard
     */
    public void addCard(BankCard bankCard){
        bankCards.add(bankCard);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Omit circular reference
    @JsonManagedReference
    public Set<BankCard> getBankCards() {
        return bankCards;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "CardOwner{" +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", bankCards=" + bankCards +
                '}';
    }
}
