package org.quarkus.samples.petclinic.user;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.FormParam;



@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "email", unique = true)
    @NotEmpty
    @FormParam("email")
    public String email;

    @Column(name = "password_hash")
    @NotEmpty
    @FormParam("passwordHash")
    public String passwordHash;

    // Other fields and methods related to User

    @Override
    public String toString() {
        return "User [email=" + email + "]";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String hashPassword) {
        this.passwordHash = hashPassword;
    }
}

