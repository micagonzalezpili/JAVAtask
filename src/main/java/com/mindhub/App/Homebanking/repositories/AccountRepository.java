package com.mindhub.App.Homebanking.repositories;
import com.mindhub.App.Homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> { //tabla y luego primarykey son genericos y pueden ser d cualquier tipo

}
