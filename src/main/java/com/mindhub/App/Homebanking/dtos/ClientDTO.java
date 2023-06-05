package com.mindhub.App.Homebanking.dtos;
import com.mindhub.App.Homebanking.models.Client;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO{
        private long id;
        Set<AccountDTO> accounts = new HashSet<>();
        private String firstName;
        private String lastName;
        private String email;

        public ClientDTO(Client client) {

            this.id = client.getId();

            this.firstName = client.getFirstName();

            this.lastName = client.getLastName();

            this.email = client.getEmail();

            this.accounts = client.getAccounts().stream()
                    .map(AccountDTO::new)
                    .peek(accountDTO -> accountDTO.setClient(this))
                    .collect(Collectors.toSet());

        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public long getId() {
        return id;
        }

        public void setId(long id) {
        this.id = id;
        }

        public String toString() {
            return firstName + " " + lastName + " " + email ;
        }

        public Set<AccountDTO> getAccounts() {
        return accounts;
        }
        public void addAccount(AccountDTO account){
            account.setClient(this);
            accounts.add(account);
        }

}
