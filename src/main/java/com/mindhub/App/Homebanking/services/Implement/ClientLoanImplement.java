package com.mindhub.App.Homebanking.services.Implement;

import com.mindhub.App.Homebanking.models.ClientLoan;
import com.mindhub.App.Homebanking.repositories.ClientLoanRepository;
import com.mindhub.App.Homebanking.services.ClientLoanService;
import com.mindhub.App.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanImplement implements ClientLoanService {
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Override
    public ClientLoan findById(Long id) {
        return clientLoanRepository.findById(id).orElse(null);
    }
}
