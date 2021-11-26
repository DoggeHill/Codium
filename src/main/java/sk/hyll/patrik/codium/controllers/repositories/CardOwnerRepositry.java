package sk.hyll.patrik.codium.controllers.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.hyll.patrik.codium.model.CardOwner;

@Repository
@Profile("production")
public interface CardOwnerRepositry extends JpaRepository<CardOwner, Long> {
        CardOwner findBySurname(String surname);
}
