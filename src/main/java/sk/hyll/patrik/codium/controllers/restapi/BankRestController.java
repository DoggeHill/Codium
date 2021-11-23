package sk.hyll.patrik.codium.controllers.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sk.hyll.patrik.codium.controllers.services.BankService;
import sk.hyll.patrik.codium.model.dto.BankDTO;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest APIs endpoints
 */
@Validated
@RestController
@RequestMapping("/api")
public class BankRestController {

    // functionality
    BankService bankCardService;

    @Autowired
    public BankRestController(BankService bankCardService) {
        this.bankCardService = bankCardService;
    }

    /**
     * Returns list of all CardOwners with their BankCards data
     * @return List of DTOs
     */
    @GetMapping("/list")
    List<BankDTO> all(){
        return bankCardService.getAllData();
    }

    /**
     * Returns one CardOwner with their BankCards data
     * Card owner is selected by his surname, which is an
     * unique identifier
     * @param surname CardOwner's surname
     * @return DTO
     */
    @GetMapping("/find")
    BankDTO cardOwner(@RequestParam(required = false) String surname){
        return bankCardService.getOneUserData(surname);
    }

    /**
     * Creates new CardOwner and assigns him BankCards
     * JSON input is mapped via mapper to a DTO object
     * @param bankCardDTO JSON input
     * @return DTO
     */
    @PostMapping("/save")
    BankDTO addMovie(@Valid @RequestBody BankDTO bankCardDTO){
        return bankCardService.addData(bankCardDTO);
    }
}
