package sk.hyll.patrik.codium.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.redis.core.RedisHash;
import sk.hyll.patrik.codium.helpers.BankCardConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * Abstract class BankCard
 * BankCard is in manyToOne relationship with CardOwner
 * JsonType tells Jackson from which instance to create class since
 * we cannot construct instance of abstract class
 *
 * @link https://stackoverflow.com/questions/30362446/deserialize-json-with-jackson-into-polymorphic-types-a-complete-example-is-giv/30386694#30386694
 */
@RedisHash("BankCard")
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore all properties that cannot be mapped
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreditCard.class, name = "credit"),
        @JsonSubTypes.Type(value = DebitCard.class, name = "debit")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Put all instances of BankCard into one table
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"card_number"})) // Card number is unique value
public abstract class BankCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @BankCardConstraint // allow only specific card numbers
    @Column(name = "card_number") // custom column name
    @NotNull(message = "The bank card number is required.")
    private long cardNumber;

    @NotNull(message = "The date is required.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/YYYY", lenient = OptBoolean.FALSE)
    private Date validity;

    // Csv field is String because of leading 0 problem
    @NotBlank(message = "The csv is required.")
    @Column(length=3)
    private String csv;

    @NotBlank(message = "The name is required.")
    @Length(min=4, max=15)
    private String brand;

    @Enumerated(EnumType.STRING) // store Enums as Strings
    private State state;

    @JsonBackReference // omit circular reference
    @ManyToOne
    @JoinColumn(name = "card_id")
    private CardOwner cardOwner;

    // Constructors
    public BankCard() {
    }

    protected BankCard(int cardNumber, Date validity, String csv, String brand, State state) {
        this.cardNumber = cardNumber;
        this.validity = validity;
        this.csv = csv;
        this.brand = brand;
        this.state = state;
    }

    // Getters/ setters
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

    public Date getValidity() {
        return this.validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public String getCsv() {
        return csv;
    }

    public void setCsv(String csv) {
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

    // Overrides
    @Override
    public String toString() {
        return "BankCard{" +
                ", cardNumber=" + cardNumber +
                ", csv=" + csv +
                ", brand='" + brand + '\'' +
                ", state=" + state +
                '}';
    }
}
