package com.mindhub.App.Homebanking.dtos;
import com.mindhub.App.Homebanking.models.Card;
import com.mindhub.App.Homebanking.models.Client;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO{
        private long id;
        private Set<AccountDTO> accounts;
        private Set<ClientLoanDTO> clientLoans;
        private Set<CardDTO> cards;
        private String firstName;
        private String lastName;
        private String email;

        public ClientDTO(Client client) {
            this.id = client.getId();
            this.firstName = client.getFirstName();
            this.lastName = client.getLastName();
            this.email = client.getEmail();
            this.accounts = client.getAccounts()
                    .stream()
                    .map(AccountDTO::new)
                    .collect(Collectors.toSet());
            this.clientLoans = client.getLoans()
                    .stream()
                    .map(ClientLoanDTO::new)
                    .collect(Collectors.toSet());
            this.cards = client.getCards()
                    .stream()
                    .map(CardDTO::new)
                    .collect(Collectors.toSet());

        }

        public String getFirstName() {
            return firstName;
        }
        public String getLastName() {
            return lastName;
        }
        public String getEmail() {
            return email;
        }
        public long getId() {
        return id;
        }
        public Set<AccountDTO> getAccounts() {
        return accounts;
        }
        public Set<ClientLoanDTO> getClientLoans(){ return clientLoans; }
        public Set<CardDTO> getCards(){ return cards; }

}
