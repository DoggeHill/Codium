package sk.hyll.patrik.codium.controllers.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import sk.hyll.patrik.codium.controllers.services.BankService;
import sk.hyll.patrik.codium.mappers.BankMapper;
import sk.hyll.patrik.codium.model.CardOwner;
import sk.hyll.patrik.codium.model.dto.BankDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("local")
public class ReddisBankServiceImpl implements BankService {

    BankMapper bankCardMapper;
    ObjectMapper objectMapper;
    private final Jedis jedis;

    public ReddisBankServiceImpl( BankMapper bankCardMapper) {
        this.bankCardMapper = bankCardMapper;
        this.jedis = new Jedis();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void createAndAddCard() {
        // TODO: dummy data
    }

    @Override
    public BankDTO addData(BankDTO bankCardDTO) {
        CardOwner cardOwner = this.bankCardMapper.bankCardDTOtoBankCard(bankCardDTO);
        String data = null;
        try {
            data = this.objectMapper.writeValueAsString(cardOwner);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.jedis.set(cardOwner.getSurname(),data);
        return this.bankCardMapper.bankCardToBankCardDTO(cardOwner);
    }

    @Override
    public List<BankDTO> getAllData() {
        List<BankDTO> bankDTOS= new ArrayList<>();
        jedis.keys("*").forEach((user) -> bankDTOS.add(getOneUserData(user)));
        return bankDTOS;
    }

    @Override
    public BankDTO getOneUserData(String surname) {
        String json = jedis.get(surname);
        CardOwner cardOwner = null;
        try {
            cardOwner = this.objectMapper.readValue(json, CardOwner.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bankCardMapper.bankCardToBankCardDTO(cardOwner);
    }
}
