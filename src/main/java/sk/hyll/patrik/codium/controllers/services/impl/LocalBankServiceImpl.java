package sk.hyll.patrik.codium.controllers.services.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import sk.hyll.patrik.codium.controllers.repositories.BankCardRepository;
import sk.hyll.patrik.codium.controllers.repositories.CardOwnerRepositry;
import sk.hyll.patrik.codium.controllers.services.BankService;
import sk.hyll.patrik.codium.mappers.BankMapper;
import sk.hyll.patrik.codium.model.BankCard;
import sk.hyll.patrik.codium.model.CardOwner;
import sk.hyll.patrik.codium.model.CreditCard;
import sk.hyll.patrik.codium.model.DebitCard;
import sk.hyll.patrik.codium.model.State;
import sk.hyll.patrik.codium.model.dto.BankDTO;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Profile("localsql")
public class LocalBankServiceImpl implements BankService {

    BankCardRepository<BankCard> bankCardsRepository;
    CardOwnerRepositry cardOwnerRepositry;
    BankMapper bankCardMapper;

    @PersistenceContext
    private EntityManager entityManager;


    public LocalBankServiceImpl(BankCardRepository<BankCard> bankCardsRepository, CardOwnerRepositry cardOwnerRepositry, BankMapper bankCardMapper) {
        this.bankCardsRepository = bankCardsRepository;
        this.cardOwnerRepositry = cardOwnerRepositry;
        this.bankCardMapper = bankCardMapper;
    }

    /**
     * Dummy data are available only in non production version
     */
    @Override
    public void createDummyData() {
        CardOwner cardOwner = new CardOwner();
        cardOwner.setName("Dummy");
        cardOwner.setSurname("Dummy");
        String dateString = "12 10 2010";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy", Locale.ENGLISH);
        LocalDate localeDate = LocalDate.parse(dateString, formatter);

        BankCard creditCard= new CreditCard(44057790, java.sql.Date.valueOf(localeDate), "001", "visa", State.ACTIVE, 100.5);
        BankCard debitCard= new DebitCard(44057790, java.sql.Date.valueOf(localeDate), "001", "visa", State.DISABLED);

        cardOwner.addCard(creditCard);
        cardOwner.addCard(debitCard);
    }

    @Override
    public BankDTO addData(BankDTO bankCardDTO) {
        CardOwner cardOwner = bankCardMapper.bankCardDTOtoBankCard(bankCardDTO);
        return bankCardMapper.bankCardToBankCardDTO(
                cardOwnerRepositry.save(cardOwner)
        );
    }

    @Override
    public List<BankDTO> getAllData() {
        return cardOwnerRepositry.findAll().stream().map(bankCardMapper::bankCardToBankCardDTO).collect(Collectors.toList());
    }

    @Override
    public BankDTO getOneUserData(String surname) {
        CardOwner cardOwner = new CardOwner();
        cardOwner.setSurname(surname);
        return bankCardMapper.bankCardToBankCardDTO(cardOwnerRepositry.findBySurname(surname));
    }

}
