package eus.klimu.users.domain.service.definition;

import eus.klimu.users.domain.model.AppUser;

import java.util.List;

public interface UserService {

    Long countAll();
    AppUser getUser(long id);
    AppUser getUser(String username);
    AppUser getUserFromTelegram(String chatId);
    List<AppUser> findAll();
    String checkUser(AppUser user);
    AppUser saveUser(AppUser user);
    List<AppUser> saveAllUsers(List<AppUser> users);
    AppUser updateUser(AppUser user);
    void deleteUser(AppUser user);

}
