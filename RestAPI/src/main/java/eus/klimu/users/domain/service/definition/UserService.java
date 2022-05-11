package eus.klimu.users.domain.service.definition;

import eus.klimu.users.domain.model.AppUser;

import java.util.List;

public interface UserService {

    AppUser saveUser(AppUser user);
    List<AppUser> saveAllUsers(List<AppUser> users);
    AppUser getUser(long id);
    AppUser getUser(String username);
    String checkUser(AppUser user);
    AppUser updateUser(AppUser user);
    void deleteUser(AppUser user);

}
