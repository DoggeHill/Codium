package sk.hyll.patrik.codium.controllers.services.impl;

import org.springframework.stereotype.Service;
import sk.hyll.patrik.codium.controllers.repositories.BankCardRepository;
import sk.hyll.patrik.codium.controllers.repositories.CardOwnerRepositry;
import sk.hyll.patrik.codium.controllers.services.BankService;
import sk.hyll.patrik.codium.mappers.BankMapper;
import sk.hyll.patrik.codium.model.BankCard;
import sk.hyll.patrik.codium.model.CardOwner;
import sk.hyll.patrik.codium.model.CreditCard;
import sk.hyll.patrik.codium.model.State;
import sk.hyll.patrik.codium.model.dto.BankDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {


    BankCardRepository<BankCard> bankCardsRepository;
    CardOwnerRepositry cardOwnerRepositry;
    BankMapper bankCardMapper;


    public BankServiceImpl(BankCardRepository<BankCard> bankCardsRepository, CardOwnerRepositry cardOwnerRepositry, BankMapper bankCardMapper) {
        this.bankCardsRepository = bankCardsRepository;
        this.cardOwnerRepositry = cardOwnerRepositry;
        this.bankCardMapper = bankCardMapper;
    }

    @Override
    public void createAndAddCard() {
        BankCard creditCard= new CreditCard(4405, (byte) 45, "visa", State.ACTIVE, 15.0);
        CardOwner cardOwner = new CardOwner();
        cardOwner.setName("Jožo");
        cardOwner.setSurname("Fero");
        cardOwner.addCard(creditCard);
        creditCard.setCardOwner(cardOwner);

        BankCard creditCard2= new CreditCard(4405, (byte) 45, "visax", State.ACTIVE, 15.0);
        CardOwner cardOwner2 = new CardOwner();
        cardOwner.setName("Jožo");
        cardOwner.setSurname("Fero");
        cardOwner.addCard(creditCard);
        creditCard.setCardOwner(cardOwner);

        cardOwnerRepositry.save(cardOwner);
        bankCardsRepository.save(creditCard);
        bankCardsRepository.save(creditCard);
    }

    @Override
    public BankDTO addData(BankDTO bankCardDTO) {
        CardOwner cardOwner = bankCardMapper.bankCardDTOtoBankCard(bankCardDTO);
        // TODO: toto netreba
        Set<BankCard> bankCards = cardOwner.getBankCards();
        for (BankCard bankCard : bankCards) {
            bankCard.setCardOwner(cardOwner);
        }
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
