package com.mindhub.App.Homebanking.services.Implement;

import com.mindhub.App.Homebanking.dtos.LoanDTO;
import com.mindhub.App.Homebanking.models.Loan;
import com.mindhub.App.Homebanking.repositories.LoanRepository;
import com.mindhub.App.Homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Override
    public Loan findByName(String name) {
        return loanRepository.findByName(name);
    }

    @Override
    public Loan findById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public List<LoanDTO> getAllLoansDTO() {
        return loanRepository.findAll()
                .stream()
                .map(loan -> new LoanDTO(loan))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Loan loan) {
        loanRepository.save(loan);
    }
}
