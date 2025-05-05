package edu.eci.cvds.auth.repository;

import edu.eci.cvds.auth.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities in MongoDB.
 * <p>
 * This interface extends {@link MongoRepository} and provides additional methods for querying
 * users by their email.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user
     * @return an {@link Optional} containing the user if found, or empty if no user with the given email exists
     */
    Optional<User> findByEmail(String email);
}
