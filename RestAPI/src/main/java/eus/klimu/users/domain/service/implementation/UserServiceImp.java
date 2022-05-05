package eus.klimu.users.domain.service.implementation;

import eus.klimu.users.domain.model.AppUser;
import eus.klimu.users.domain.repository.UserRepository;
import eus.klimu.users.domain.service.definition.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving user {} on the database.", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Looking for user with username={}", username);
        return userRepository.findByUsername(username);
    }

    public List<AppUser> createUserList(List<AppUser> list) {
        return userRepository.saveAll(list);
    }

    public List<AppUser> getUserList() {
        return userRepository.findAll();
    }

    public AppUser getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public AppUser updateUserById(AppUser user) {
        Optional<AppUser> userFound = userRepository.findById(user.getId());

        if (userFound.isPresent()) {
            AppUser userUpdate = userFound.get();
            userUpdate.setId(user.getId());
            userUpdate.setUsername(user.getUsername());
            userUpdate.setSurname(user.getSurname());
            userUpdate.setEmail(user.getEmail());

            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public String deleteUserById(long id) {
        userRepository.deleteById(id);
        return "User "+ id +" deleted";
    }
}
