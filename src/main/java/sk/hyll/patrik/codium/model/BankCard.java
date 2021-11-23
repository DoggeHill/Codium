package sk.hyll.patrik.codium.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import sk.hyll.patrik.codium.helpers.BankCardConstraint;

import javax.persistence.*;


/**
 * Abstract class BankCard
 * BankCard is in manyToOne relationship with CardOwner
 * JsonType tells Jackson from which instance to create class since
 * we cannot construct instance of abstract class
 * @link https://stackoverflow.com/questions/30362446/deserialize-json-with-jackson-into-polymorphic-types-a-complete-example-is-giv/30386694#30386694
 */
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore all properties that cannot be mapped
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreditCard.class, name = "credit"),
        @JsonSubTypes.Type(value = DebitCard.class, name = "debit")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Put all instances of BankCard into one table
@Table(uniqueConstraints= @UniqueConstraint(columnNames={"card_number"})) // Card number is unique value
public abstract class BankCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @BankCardConstraint // allow only specific card numbers
    @Column(name="card_number") // custom column name
    private long cardNumber;

    /* TODO: add expiration

        @Basic
        @Temporal(TemporalType.DATE)
        protected Date valididy;
    */

    private byte csv;
    private String brand;

    @Enumerated(EnumType.STRING) // store Enums as Strings
    private State state;

    @JsonBackReference // ommit circular reference
    @ManyToOne
    @JoinColumn(name = "card_id")
    //@JoinColumn(foreignKey = @ForeignKey(name = "fk_telefon_osoba_id"))
    private CardOwner cardOwner;

    public BankCard() {
    }

    protected BankCard(int cardNumber, byte csv, String brand, State state) {
        this.cardNumber = cardNumber;
        //TODO: this.valididy = valididy;
        this.csv = csv;
        this.brand = brand;
        this.state = state;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public long getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    /*
    TODO: add
    public Date getValididy() {
        return valididy;
    }

    public void setValididy(Date valididy) {
        this.valididy = valididy;
    }
    */
    public byte getCsv() {
        return csv;
    }
    public void setCsv(byte csv) {
        this.csv = csv;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }

    public CardOwner getCardOwner() {
        return cardOwner;
    }
    public void setCardOwner(CardOwner cardOwner) {
        this.cardOwner = cardOwner;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "id=" + id +
                ", cardNumber=" + cardNumber +
                ", csv=" + csv +
                ", brand='" + brand + '\'' +
                ", state=" + state +
                '}';
    }
}
