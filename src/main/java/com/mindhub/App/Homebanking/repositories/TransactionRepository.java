package com.mindhub.App.Homebanking.repositories;

import com.mindhub.App.Homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
