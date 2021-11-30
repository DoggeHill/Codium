package sk.hyll.patrik.codium.controllers.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import sk.hyll.patrik.codium.controllers.services.BankService;
import sk.hyll.patrik.codium.mappers.BankMapper;
import sk.hyll.patrik.codium.model.BankCard;
import sk.hyll.patrik.codium.model.CardOwner;
import sk.hyll.patrik.codium.model.CreditCard;
import sk.hyll.patrik.codium.model.State;
import sk.hyll.patrik.codium.model.dto.BankDTO;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        this.objectMapper.setDateFormat(new SimpleDateFormat("dd MM yyyy"));
    }

    @Override
    public void createDummyData() {
        CardOwner cardOwner = new CardOwner();
        cardOwner.setName("Dummy");
        cardOwner.setSurname("Dummy");
        String dateString = "12 10 2010";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy", Locale.ENGLISH);
        LocalDate localeDate = LocalDate.parse(dateString, formatter);

        BankCard creditCard= new CreditCard(44057790, java.sql.Date.valueOf(localeDate), "001", "visa", State.ACTIVE, 100.5);
        cardOwner.addCard(creditCard);
        String data = null;
        try {
            data = this.objectMapper.writeValueAsString(cardOwner);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.jedis.set(cardOwner.getSurname(),data);
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
