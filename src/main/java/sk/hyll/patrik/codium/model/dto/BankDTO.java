package sk.hyll.patrik.codium.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import sk.hyll.patrik.codium.model.BankCard;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankDTO {
    private Long id;
    @Size(min=2, max=15)
    @NotBlank(message = "The name is required.")
    private String name;
    @Size(min=2, max=20)
    @NotBlank(message = "The surname is required.")
    private String surname;

    // save this list also
    @Cascade(CascadeType.ALL)
    @NotEmpty(message = "Input bank cards list cannot be empty.")
    private Set<@Valid  BankCard> bankCards = new HashSet<>();

    // we have to generate getters and setters for jackson to work well
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<BankCard> getBankCards() {
        return bankCards;
    }

    public void setBankCards(Set<BankCard> bankCards) {
        this.bankCards = bankCards;
    }
}
