package com.mindhub.App.Homebanking.services.Implement;

import com.mindhub.App.Homebanking.models.Transaction;
import com.mindhub.App.Homebanking.repositories.TransactionRepository;
import com.mindhub.App.Homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
