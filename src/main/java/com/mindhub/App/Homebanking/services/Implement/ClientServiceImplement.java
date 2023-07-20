package com.mindhub.App.Homebanking.services.Implement;

import com.mindhub.App.Homebanking.dtos.ClientDTO;
import com.mindhub.App.Homebanking.models.Card;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.repositories.CardRepository;
import com.mindhub.App.Homebanking.repositories.ClientRepository;
import com.mindhub.App.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service // si no especificamos no esta dentro del app context , puede usar component
public class ClientServiceImplement implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;
    @Override
    public List<ClientDTO> getAllClientsDTO() {
        return clientRepository
                .findAll()
                .stream()
                .map(client -> new ClientDTO(client))
                .collect(toList());
    }
    @Override
    public ClientDTO getClientDTO(Long id) {
        return new ClientDTO(this.findById(id));
    }
    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client findByCardNumber(String cardNumber) {
        Card card = cardRepository.findByNumber(cardNumber);
        if (card == null) {
            return null;
        }
        Client client = card.getClient();
        if (client == null) {
            return null;
        }
        return client;
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }




}
