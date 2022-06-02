package eus.klimu.users.domain.service.implementation;

import eus.klimu.security.PasswordManager;
import eus.klimu.users.domain.model.AppUser;
import eus.klimu.users.domain.repository.UserRepository;
import eus.klimu.users.domain.service.definition.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {
            log.info("User {} found on the database", username);

            // Get the user roles as SimpleGrantedAuthorities for Spring Security.
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

            // Create a Spring Security user to check on with.
            return new User(
                    user.getUsername(),
                    user.getPassword(),
                    authorities
            );
        } else {
            log.error("User {} was not found!", username);
            throw new UsernameNotFoundException("Username " + " was not found");
        }
    }

    @Override
    public Long countAll() {
        long count = userRepository.count();
        log.info("Found {} users on the database", count);
        return count;
    }

    @Override
    public AppUser getUser(long id) {
        log.info("Looking for user with id={}", id);
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Looking for user with username={}", username);
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public AppUser getUserFromTelegram(String chatId) {
        log.info("Fetching user with chatId={}", chatId);
        return userRepository.findByTelegramId(chatId).orElse(null);
    }

    @Override
    public List<AppUser> findAll() {
        log.info("Fetching all users from the database");
        return userRepository.findAll();
    }

    /**
     * Check if a user meets all the criteria to be properly created. The user must meet the following:
     * <ul>
     *     <li>The user can't be null.</li>
     *     <li>The user must have at least a username and a password.</li>
     *     <li>The username must be unique.</li>
     *     <li>
     *         The password must be at least 8 characters long and not more than 32 characters long, have at least an
     *         uppercase and a lowercase letter and a number.
     *     </li>
     * </ul>
     * @param user The user that is being checked.
     * @return If the user is correct the function returns an OK message. If there is any problem, the function
     * will return what the problem is.
     */
    @Override
    public String checkUser(AppUser user) {
        String message;

        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            // Check if the user has any parameter that is null.
            message = "Hay un elemento que no deberia de ser nulo.";
        }
        else if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            // Check if the username is available.
            message = "El nombre de usuario ya existe.";
        } else {
            // Check if the password is good.
            message = PasswordManager.check(user.getPassword());
        }
        return message;
    }

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving user {} on the database", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public List<AppUser> saveAllUsers(List<AppUser> users) {
        log.info("Saving {} user(s) on the database", users.size());
        return userRepository.saveAll(users);
    }

    @Override
    public AppUser updateUser(AppUser user) {
        log.info("Updating user {}", user.getUsername());
        AppUser userFound = userRepository.findById(user.getId()).orElse(null);

        if (userFound != null) {
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public void deleteUser(AppUser user) {
        log.info("Removing user {} from database", user.getUsername());
        userRepository.deleteById(user.getId());
    }
}
