package org.quarkus.samples.petclinic.user;


import javax.ws.rs.FormParam;

public class UserForm {

    @FormParam("email")
    private String email;

    @FormParam("password")
    private String password;

    // Constructors, getters, and setters

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
}
