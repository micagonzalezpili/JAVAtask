package com.mindhub.App.Homebanking.services;

import com.mindhub.App.Homebanking.dtos.TransactionDTO;
import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    void save(Transaction transaction);
    //List<TransactionDTO> getTransactionsDTO();
    List<Transaction> getTransactionsByDate(LocalDate startDate, LocalDate endDate);

    byte[] generatePDF(Account account, List<Transaction> transactions);

}
