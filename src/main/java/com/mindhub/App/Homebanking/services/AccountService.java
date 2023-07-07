package com.mindhub.App.Homebanking.services;

import com.mindhub.App.Homebanking.dtos.AccountDTO;
import com.mindhub.App.Homebanking.models.Account;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAllAccountsDTO();
    Account findById(Long id);
    Account findByNumber(String number);
    void save(Account account);
}
