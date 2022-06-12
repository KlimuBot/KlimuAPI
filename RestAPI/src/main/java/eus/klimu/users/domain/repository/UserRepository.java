package eus.klimu.users.domain.repository;

import eus.klimu.users.domain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Access to the database for the AppUsers.
 * Extends from a JpaRepository.
 */
public interface UserRepository extends JpaRepository<AppUser, Long> {

    /**
     * Find an AppUser based on its username.
     * @param username The username of the AppUser.
     * @return An optional value that contains the AppUser if found.
     */
    Optional<AppUser> findByUsername(String username);

    /**
     * Find an AppUser based on its telegram identification number.
     * @param telegramId The telegram ID of the user.
     * @return An optional value that contains the AppUser if found.
     */
    Optional<AppUser> findByTelegramId(String telegramId);

}
