package com.mindhub.App.Homebanking.services;

import com.mindhub.App.Homebanking.models.ClientLoan;

public interface ClientLoanService {
    ClientLoan findById(Long id);
}
