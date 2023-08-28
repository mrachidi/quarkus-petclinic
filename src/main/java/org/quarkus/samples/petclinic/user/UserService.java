package org.quarkus.samples.petclinic.user;

import io.smallrye.jwt.build.Jwt;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;


import org.mindrot.jbcrypt.BCrypt;
import org.quarkus.samples.petclinic.owner.Owner;
import org.quarkus.samples.petclinic.owner.OwnerRepository;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Inject
    UserRepository userRepository;

    @Inject
    OwnerRepository ownerRepository;


    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public String authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.passwordHash)) {
            return generateJwt(email);
        }
        return null;
    }

    @Transactional  // To make sure both operations are in a single transaction
    public boolean registerUser(UserRegistrationForm registrationForm) {
        try {
            logger.info("Checking if user already exists...");
            if (userRepository.findByEmail(registrationForm.getEmail()) != null) {
                logger.warn("Email already exists.");
                return false;  // Email already exists
            }

            logger.info("Creating and saving new Owner...");
            Owner owner = new Owner();
            owner.setFirstName(registrationForm.getFirstName());
            owner.setLastName(registrationForm.getLastName());
            owner.setAddress(registrationForm.getAddress());
            owner.setCity(registrationForm.getCity());
            owner.setTelephone(registrationForm.getTelephone());
            ownerRepository.persist(owner);

            logger.info("Creating and saving new User...");
            User user = new User();
            user.setEmail(registrationForm.getEmail());
            user.setPasswordHash(hashPassword(registrationForm.getPassword()));
            userRepository.persist(user);

            logger.info("Registration successful.");
            return true;  // Registration successful
        } catch (Exception e) {
            logger.error("An exception occurred during registration: {}", e.getMessage(), e);
            throw e;
        }
    }


    private String generateJwt(String email) {
        return Jwt.issuer("JWTToekenIssuer")
                .upn(email)
                .expiresIn(Duration.ofHours(1))  // Optional: Sets an expiry time of 1 hour
                .sign();
    }
}

