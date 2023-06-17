
package com.mindhub.App.Homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity // le digo a springboot q me cree una table en la base de datos
public class Client {

    @Id // le asigno un id a cada cliente y diferenciarlos (primary key)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // genera un id automaticamente
    @GenericGenerator(name = "native", strategy = "native")// la strategy la tiene la base de datos genericamente y este es el encargado de ejecutar la linea d arriba
    private long id;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString() {
        return firstName + " " + lastName + " " + email ;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

   public void addAccount(Account account){
        account.setClient(this);
        accounts.add(account);
   }
}