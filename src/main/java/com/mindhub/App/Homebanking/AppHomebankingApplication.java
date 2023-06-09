package com.mindhub.App.Homebanking;
import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.models.Transaction;
import com.mindhub.App.Homebanking.models.enums.TransactionType;
import com.mindhub.App.Homebanking.repositories.AccountRepository;
import com.mindhub.App.Homebanking.repositories.ClientRepository;

import com.mindhub.App.Homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.time.LocalDateTime;


@SpringBootApplication
public class AppHomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppHomebankingApplication.class, args);

	}

	@Bean // cada vez q se crea la app se crean los clientes debajo
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){ // declaro la var
		return (args -> {
			Client melba = new Client("Melba", "Morel","melba@mindhub.com" );
			Client client2 = new Client("Micaela", "Gonzalez Pili","micagpili@gmail.com" );
			clientRepository.save(melba) ;
			clientRepository.save(client2);
			Account account1 = new Account("VIN001", LocalDate.now() , 5000.0, melba);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1) , 7500.0, melba);
			Account account3 = new Account("VIN003", LocalDate.now(), 5000.0, client2);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			melba.addAccount(account1);
			melba.addAccount(account2);
			client2.addAccount(account3);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT, 500.0, "CashFlow refund", LocalDateTime.now(), account1);
			Transaction transaction2 = new Transaction(TransactionType.DEBIT, -1000.0, "Pharmacy", LocalDateTime.now(), account1);
			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 500.0, "Salary", LocalDateTime.now(), account1);
			Transaction transaction4 = new Transaction(TransactionType.DEBIT, -700.0, "Kiosk", LocalDateTime.now(), account1);
			Transaction transaction5 = new Transaction(TransactionType.DEBIT, -100.0, "PetShop ElClubDeLasMascotas", LocalDateTime.now(), account1);
			Transaction transaction6 = new Transaction(TransactionType.CREDIT, 2000.0, "CashFlow refund", LocalDateTime.now(), account2);
			Transaction transaction7 = new Transaction(TransactionType.DEBIT, -500.0, "Bakery", LocalDateTime.now(), account2);
			Transaction transaction8 = new Transaction(TransactionType.CREDIT, 1000.0, "Job Bonus", LocalDateTime.now(), account2);
			Transaction transaction9 = new Transaction(TransactionType.DEBIT, -500.0, "McDonalds", LocalDateTime.now(), account2);
			Transaction transaction10 = new Transaction(TransactionType.DEBIT, -300.0, "Flower Shop", LocalDateTime.now(), account2);
			Transaction transaction11 = new Transaction(TransactionType.DEBIT, -900.0, "Credit card payment", LocalDateTime.now(), account3);
			Transaction transaction12 = new Transaction(TransactionType.CREDIT, 300.0, "Glovo refund", LocalDateTime.now(), account3);
			Transaction transaction13 = new Transaction(TransactionType.DEBIT, -1200.0, "Cake Shop", LocalDateTime.now(), account3);
			Transaction transaction14 = new Transaction(TransactionType.DEBIT, -600.0, "Clothing Store", LocalDateTime.now(), account3);
			Transaction transaction15 = new Transaction(TransactionType.DEBIT, -800.0, "Cosmetics Store", LocalDateTime.now(), account3);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
			transactionRepository.save(transaction7);
			transactionRepository.save(transaction8);
			transactionRepository.save(transaction9);
			transactionRepository.save(transaction10);
			transactionRepository.save(transaction11);
			transactionRepository.save(transaction12);
			transactionRepository.save(transaction13);
			transactionRepository.save(transaction14);
			transactionRepository.save(transaction15);


			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account1.addTransaction(transaction3);
			account1.addTransaction(transaction4);
			account1.addTransaction(transaction5);
			account2.addTransaction(transaction6);
			account2.addTransaction(transaction7);
			account2.addTransaction(transaction8);
			account2.addTransaction(transaction9);
			account2.addTransaction(transaction10);
			account3.addTransaction(transaction11);
			account3.addTransaction(transaction12);
			account3.addTransaction(transaction13);
			account3.addTransaction(transaction14);
			account3.addTransaction(transaction15);



		});
	}
}
