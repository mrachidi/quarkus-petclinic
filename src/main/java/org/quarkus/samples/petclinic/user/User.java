package org.quarkus.samples.petclinic.user;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.FormParam;



@Entity
@Table(name = "users")
public class User extends PanacheEntity {

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
}
