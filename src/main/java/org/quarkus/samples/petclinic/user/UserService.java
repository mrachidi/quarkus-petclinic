package org.quarkus.samples.petclinic.user;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt; // You'll need to add the BCrypt library to your project

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository; // Your repository for user data access

    // Hashes a password using BCrypt
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Registers a new user
    @Transactional
    public void registerUser(User newUser) {
        userRepository.persist(newUser);
    }

    // Authenticates a user
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return BCrypt.checkpw(password, user.passwordHash);
        }
        return false;
    }
}
