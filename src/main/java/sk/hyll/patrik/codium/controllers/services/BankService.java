package sk.hyll.patrik.codium.controllers.services;

import sk.hyll.patrik.codium.model.dto.BankDTO;

import java.util.List;

public interface BankService {
    void createAndAddCard();
    BankDTO addData(BankDTO bankCardDTO);
    List<BankDTO> getAllData();
    BankDTO getOneUserData(String surname);
}
