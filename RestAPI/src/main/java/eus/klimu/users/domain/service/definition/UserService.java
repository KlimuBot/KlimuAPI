package eus.klimu.users.domain.service.definition;

import eus.klimu.users.domain.model.AppUser;

import java.util.List;

/**
 * Definition of the different functions available for a role.
 */
public interface UserService {

    /**
     * Count all the AppUsers.
     * @return The number of AppUsers.
     */
    Long countAll();

    /**
     * Get an AppUser based on its identification number.
     * @param id The identification number of the AppUser.
     * @return The AppUser with that identification number, if found.
     */
    AppUser getUser(long id);

    /**
     * Get an AppUser based on its identification number.
     * @param username The username of the AppUser.
     * @return The AppUser with that username, if found.
     */
    AppUser getUser(String username);

    /**
     * Get an AppUser based on its telegram identification number.
     * @param chatId The telegram identification number of the AppUser.
     * @return The AppUser with that telegram identification number.
     */
    AppUser getUserFromTelegram(String chatId);

    /**
     * Get all the AppUsers.
     * @return A list with all the AppUsers.
     */
    List<AppUser> findAll();

    /**
     * Check if an AppUsers checks all the politics.
     * @param user The AppUser that is going to be checked.
     * @return If the user is correct the function returns an OK message. If there is any problem, the function
     * will return what the problem is.
     */
    String checkUser(AppUser user);

    /**
     * Save a new AppUser.
     * @param user The AppUser that is going to be saved.
     * @return The AppUser after being saved.
     */
    AppUser saveUser(AppUser user);

    /**
     * Save a list of new AppUsers.
     * @param users The list of AppUsers that is going to be saved.
     * @return The AppUsers after being saved.
     */
    List<AppUser> saveAllUsers(List<AppUser> users);

    /**
     * Update an AppUser.
     * @param user The AppUser that is going to be saved.
     * @return The AppUser after being updated.
     */
    AppUser updateUser(AppUser user);

    /**
     * Delete an AppUser.
     * @param user The AppUser that is going to be deleted.
     */
    void deleteUser(AppUser user);

}
