package sk.hyll.patrik.codium.controllers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.hyll.patrik.codium.model.BankCard;

@Repository
public interface BankCardRepository<T extends BankCard> extends JpaRepository<T, Long> {
}
