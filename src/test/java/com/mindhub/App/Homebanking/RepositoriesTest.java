package com.mindhub.App.Homebanking;

import com.mindhub.App.Homebanking.models.*;
import com.mindhub.App.Homebanking.models.enums.CardColor;
import com.mindhub.App.Homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {
        @Autowired
        ClientRepository clientRepository;
        @Autowired
        AccountRepository accountRepository;
        @Autowired
        CardRepository cardRepository;
        @Autowired
        LoanRepository loanRepository;
        @Autowired
        TransactionRepository transactionRepository;

        // TEST DE CLIENT REPOSITORIES
        @Test
        public void existClients(){
            List<Client> clients = clientRepository.findAll();
            assertThat(clients, is(not(empty())));
        }
        @Test
        public void clientsContainsMelba(){
           List<Client> clients = clientRepository.findAll();
           boolean containsMelba = clients.stream().anyMatch(client -> client.getFirstName().equals("Melba"));
           assertTrue(containsMelba);
        }

        // TEST DE ACCOUNT REPOSITORIES
        @Test
        public void allHaveTransactions(){
            List<Account> accounts = accountRepository.findAll();
            boolean existsTransactions = accounts.stream().noneMatch(account -> account.getTransactions().isEmpty());
            assertTrue(existsTransactions);
        }

        @Test
        public void accountBalance(){
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts, hasItem(hasProperty("balance", is(5000.0))));
        }

        // TEST DE CARD REPOSITORIES
        @Test
        public void cardsSize(){
            List<Card> cards = cardRepository.findAll();
            assertThat(cards, hasSize(4));
        }


        // TEST DE LOAN REPOSITORIES
        @Test
        public void existLoans(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans,is(not(empty())));
        }

        @Test
        public void existPersonalLoan(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Car"))));
        }
    // TEST DE TRANSACTION REPOSITORIES
   @Test
    public void existTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));
    }
}
