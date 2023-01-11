package sk.hyll.patrik.codium.model.dao;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.hyll.patrik.codium.model.CardOwner;

@Repository
@Profile({"production", "localsql"})
public interface CardOwnerRepositry extends JpaRepository<CardOwner, Long> {
        CardOwner findBySurname(String surname);
}
