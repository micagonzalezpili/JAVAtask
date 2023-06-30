package com.mindhub.App.Homebanking.repositories;
import com.mindhub.App.Homebanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByNumber(String number);
}
