package com.mindhub.App.Homebanking;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@SpringBootApplication
public class AppHomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppHomebankingApplication.class, args);

	}

	@Bean // cada vez q se crea la app se crean los clientes debajo
	public CommandLineRunner initData(ClientRepository clientRepository){ // declaro la var
		return (args -> {
			Client client1 = new Client("Melba", "Morel","melba@mindhub.com" );
			Client client2 = new Client("Micaela", "Gonzalez Pili","micagpili@gmail.com" );
			clientRepository.save(client1) ;
			clientRepository.save(client2);

		});
	}
}
