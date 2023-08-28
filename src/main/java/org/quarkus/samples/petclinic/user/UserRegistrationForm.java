package org.quarkus.samples.petclinic.user;

import javax.ws.rs.FormParam;

public class UserRegistrationForm {

    @FormParam("first_name")
    private String firstName;

    @FormParam("last_name")
    private String lastName;

    @FormParam("address")
    private String address;

    @FormParam("city")
    private String city;

    @FormParam("telephone")
    private String telephone;

    @FormParam("email")
    private String email;

    @FormParam("password")
    private String password;

    @FormParam("confirm_password")
    private String confirmPassword;


    public UserRegistrationForm() {
    }

    // I think I won't be using all the below but I wrote them anyway
    public UserRegistrationForm(String firstName, String lastName, String address, String city,
                                String telephone, String email, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Getters and setters
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

