package eus.klimu.users.domain.repository;

import eus.klimu.users.domain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByUsernameAndPassword(String username, String password);

}
