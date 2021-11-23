package sk.hyll.patrik.codium.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import sk.hyll.patrik.codium.controllers.services.BankService;

/**
 * Creates dummy content
 */
@Component
@Profile("local")
public class DataInit implements ApplicationListener<ContextRefreshedEvent> {

    BankService bankCardService;
    public DataInit(BankService bankCardService) {
        this.bankCardService = bankCardService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
       // bankCardService.createAndAddCard();
    }
}
