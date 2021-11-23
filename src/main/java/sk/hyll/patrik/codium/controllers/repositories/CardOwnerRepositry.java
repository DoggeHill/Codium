package sk.hyll.patrik.codium.controllers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.hyll.patrik.codium.model.CardOwner;

@Repository
public interface CardOwnerRepositry extends JpaRepository<CardOwner, Long> {
        CardOwner findBySurname(String surname);
}
