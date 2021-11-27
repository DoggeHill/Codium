package sk.hyll.patrik.codium.controllers.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.hyll.patrik.codium.model.BankCard;

@Repository
@Profile({"production", "localsql"})
public interface BankCardRepository<T extends BankCard> extends JpaRepository<T, Long> {
}
