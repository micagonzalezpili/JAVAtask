package com.mindhub.App.Homebanking.services;

import com.mindhub.App.Homebanking.dtos.LoanDTO;
import com.mindhub.App.Homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    Loan findByName(String name);
    List<LoanDTO> getAllLoansDTO();
}
