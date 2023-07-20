package com.mindhub.App.Homebanking.repositories;
import com.mindhub.App.Homebanking.models.Transaction;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBetween(LocalDate startDate, LocalDate endDate);
}
