package org.quarkus.samples.petclinic.user;

import io.smallrye.jwt.build.Jwt;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.time.Duration;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    // Hashes a password using BCrypt
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Authenticates a user and returns the JWT if successful
    public String authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.passwordHash)) {
            return generateJwt(email);
        }
        return null;
    }

    private String generateJwt(String email) {
        return Jwt.issuer("JWTToekenIssuer")
                .upn(email)
                .expiresIn(Duration.ofHours(1))  // Optional: Sets an expiry time of 1 hour
                .sign();
    }
}

