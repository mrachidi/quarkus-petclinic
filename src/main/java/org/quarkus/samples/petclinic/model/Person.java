package org.quarkus.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.FormParam;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;


@MappedSuperclass
public class Person extends PanacheEntityBase {
    
    @Column(name = "first_name")
    @NotEmpty
    @FormParam("firstName")
    public String firstName;

    @Column(name = "last_name")
    @NotEmpty
    @FormParam("lastName")
    public String lastName;

    @Override
    public String toString() {
        return "Person [firstName=" + firstName + ", lastName=" + lastName + "]";
    }

}

