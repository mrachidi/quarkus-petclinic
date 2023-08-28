package org.quarkus.samples.petclinic.owner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.FormParam;

import org.quarkus.samples.petclinic.model.Person;

@Entity
@Table(name = "owners")
public class Owner extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "address")
    @NotEmpty
    @FormParam("address")
    public String address;

    @Column(name = "city")
    @NotEmpty
    @FormParam("city")
    public String city;

    @Column(name = "telephone")
    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    @FormParam("telephone")
    public String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER) // it is eager but probably it could be changed to lazy and make some lazy load before rendering template but we might end up in similar performance
    public Set<Pet> pets;

    public static Collection<Owner> findByLastName(String name) {
        return Owner.list("lastName", name);
    }

    public Owner attach() {
        return getEntityManager().merge(this);
    }

    @Override
    public String toString() {
        return "Owner [address=" + address + ", city=" + city + ", pets=" + pets + ", telephone=" + telephone + "]";
    }

    public void addPet(Pet pet) {
        getPetsInternal().add(pet);
        pet.owner = this;
    }

    protected Set<Pet> getPetsInternal() {
		if (this.pets == null) {
			this.pets = new HashSet<>();
		}
		return this.pets;
	}

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

