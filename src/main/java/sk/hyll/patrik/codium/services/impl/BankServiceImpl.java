package sk.hyll.patrik.codium.services.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import sk.hyll.patrik.codium.model.dao.BankCardRepository;
import sk.hyll.patrik.codium.model.dao.CardOwnerRepositry;
import sk.hyll.patrik.codium.services.BankService;
import sk.hyll.patrik.codium.mappers.BankMapper;
import sk.hyll.patrik.codium.model.BankCard;
import sk.hyll.patrik.codium.model.CardOwner;
import sk.hyll.patrik.codium.model.dto.BankDTO;

import java.util.List;
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
    public void createDummyData() {
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
