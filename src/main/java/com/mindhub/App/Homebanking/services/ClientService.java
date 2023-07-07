package com.mindhub.App.Homebanking.services;

import com.mindhub.App.Homebanking.dtos.ClientDTO;
import com.mindhub.App.Homebanking.dtos.ClientLoanDTO;
import com.mindhub.App.Homebanking.models.Client;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getAllClientsDTO();
    Client findById(Long id);
    ClientDTO getClientDTO(Long id);
    void save(Client client);
    Client findByEmail(String email);

}
