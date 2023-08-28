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

}

// Create a new class to wrap the JWT token in the JSON response
class TokenResponse {
    public String token;
    public TokenResponse(String token) {
        this.token = token;
    }
}

