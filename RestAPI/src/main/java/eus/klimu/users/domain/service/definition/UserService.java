package eus.klimu.users.domain.service.definition;

import eus.klimu.users.domain.model.AppUser;

import java.util.List;

public interface UserService {

    AppUser getUser(long id);
    AppUser getUser(String username);
    AppUser getUser(String username, String password);
    String checkUser(AppUser user);
    AppUser saveUser(AppUser user);
    List<AppUser> saveAllUsers(List<AppUser> users);
    AppUser updateUser(AppUser user);
    void deleteUser(AppUser user);

}
