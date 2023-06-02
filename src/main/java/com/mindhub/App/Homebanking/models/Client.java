
package com.mindhub.App.Homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity // le digo a springboot q me cree una table en la base de datos
public class Client {

    @Id // le asigno un id a cada cliente y diferenciarlos (primary key)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // genera un id automaticamente
    @GenericGenerator(name = "native", strategy = "native")// la strategy la tiene la base de datos genericamente
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    public Client() { } // lo exije asi JPA polimorfismo

    public Client(String first, String last, String mail) {
        firstName = first;
        lastName = last;
        email = mail;
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

    public String toString() {
        return firstName + " " + lastName + " " + email ;
    }

    public interface ClientRepository extends JpaRepository<Client, Long>{
        List<Client> findByLastName(String lastName);
    }
}