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
import sk.hyll.patrik.codium.model.State;
import sk.hyll.patrik.codium.model.dto.BankDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile("production")
public class BankServiceImpl implements BankService {


    BankCardRepository<BankCard> bankCardsRepository;
    CardOwnerRepositry cardOwnerRepositry;
    BankMapper bankCardMapper;


    public BankServiceImpl(BankCardRepository<BankCard> bankCardsRepository, CardOwnerRepositry cardOwnerRepositry, BankMapper bankCardMapper) {
        this.bankCardsRepository = bankCardsRepository;
        this.cardOwnerRepositry = cardOwnerRepositry;
        this.bankCardMapper = bankCardMapper;
    }

    /**
     * Dummy data are available only in non production version
     */
    @Override
    public void createAndAddCard() {
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
