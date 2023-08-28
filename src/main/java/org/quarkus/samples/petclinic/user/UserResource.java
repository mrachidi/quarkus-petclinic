package org.quarkus.samples.petclinic.user;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import io.quarkus.qute.TemplateInstance;
import org.quarkus.samples.petclinic.system.TemplatesLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/users")
public class UserResource {

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Inject
    UserService userService;

    @Inject
    TemplatesLocale templates;

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance loginTemplate() {
        logger.info("Rendering login template");
        return templates.loginUserForm();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public Response loginUser(@BeanParam UserForm userForm) {
        logger.info("Attempting to login user with email: {}", userForm.getEmail());
        String jwt = userService.authenticateUser(userForm.getEmail(), userForm.getPassword());

        if (jwt != null) {
            NewCookie cookie = new NewCookie("token", jwt); // set the token as a cookie
            return Response.ok(templates.loginSuccess()).cookie(cookie).build(); // add cookie to response
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity(templates.loginFailure()).build();
        }
    }

    @GET
    @Path("/register")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance registerTemplate() {
        logger.info("Rendering registration template");
        return templates.registerUserForm();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response registerUser(@BeanParam UserRegistrationForm userRegistrationForm) {
        try {
            logger.info("Starting registration process...");
            if (userService.registerUser(userRegistrationForm)) {
                logger.info("Registration successful.");
                return Response.ok(templates.registerSuccessful()).build();
            } else {
                logger.info("Registration failed. Email already exists.");
                return Response.status(Response.Status.BAD_REQUEST).entity(templates.registerFailure()).build();
            }
        } catch (Exception e) {
            logger.error("An exception occurred during registration: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred").build();
        }
    }

}

