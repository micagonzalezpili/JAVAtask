package com.mindhub.App.Homebanking.services;

import com.mindhub.App.Homebanking.dtos.LoanDTO;
import com.mindhub.App.Homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    Loan findByName(String name);
    Loan findById(Long id);
    List<LoanDTO> getAllLoansDTO();
    void save(Loan loan);
}
