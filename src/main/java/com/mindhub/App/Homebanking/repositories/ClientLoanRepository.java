package com.mindhub.App.Homebanking.repositories;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {

}
