package com.mindhub.App.Homebanking.dtos;
import com.mindhub.App.Homebanking.models.Client;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO{
        private long id;
        private Set<AccountDTO> accounts = new HashSet<>();
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


        public String toString() {
            return firstName + " " + lastName + " " + email ;
        }

        public Set<AccountDTO> getAccounts() {
        return accounts;
        }


}
