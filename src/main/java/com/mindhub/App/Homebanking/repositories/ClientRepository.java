package com.mindhub.App.Homebanking.repositories;
import com.mindhub.App.Homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource // va a escuchar y responder la peticion en formato JSON xq asi lo maneja REST
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByLastName(String lastName);
}
