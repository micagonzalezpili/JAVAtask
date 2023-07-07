package com.mindhub.App.Homebanking.repositories;
import com.mindhub.App.Homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {
        Loan findByName(String name);
}
