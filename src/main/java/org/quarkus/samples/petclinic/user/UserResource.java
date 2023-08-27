package org.quarkus.samples.petclinic.user;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import org.quarkus.samples.petclinic.system.TemplatesLocale;

@Path("/users")
public class UserResource {

    @Inject
    UserService userService; // Your user service for authentication and user management

    @Inject
    TemplatesLocale templates;

    @GET
    @Path("/register")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance registerTemplate() {
        return templates.registerUserForm();
    }

    @POST
    @Path("/register")
    @Produces(MediaType.TEXT_HTML)
    @Transactional
    public TemplateInstance registerUser(@BeanParam UserForm userForm) {
        User newUser = new User();
        newUser.email = userForm.getEmail();
        newUser.passwordHash = userService.hashPassword(userForm.getPassword());

        userService.registerUser(newUser);

        return templates.registrationSuccess();
    }

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance loginTemplate() {
        return templates.loginUserForm();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance loginUser(@BeanParam UserForm userForm) {
        if (userService.authenticateUser(userForm.getEmail(), userForm.getPassword())) {
            // User authenticated successfully, create session or token
            return templates.loginSuccess();
        } else {
            return templates.loginFailure();
        }
    }
}
