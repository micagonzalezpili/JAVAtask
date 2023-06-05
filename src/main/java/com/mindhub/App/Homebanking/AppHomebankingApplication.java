package com.mindhub.App.Homebanking;
import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.repositories.AccountRepository;
import com.mindhub.App.Homebanking.repositories.ClientRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;


@SpringBootApplication
public class AppHomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppHomebankingApplication.class, args);

	}

	@Bean // cada vez q se crea la app se crean los clientes debajo
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){ // declaro la var
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
		});
	}
}
