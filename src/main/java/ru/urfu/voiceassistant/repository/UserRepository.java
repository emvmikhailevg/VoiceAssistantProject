package ru.urfu.voiceassistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.urfu.voiceassistant.model.UserModel;

/**
 * UserRepository interface is used to set a list
 * of necessary tasks for searching, obtaining and storing data
 * @author Емельянов Михаил <emelianov.mikhail@urfu.me>
 * @see UserModel User model
 */
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    /**
     * Method for searching for a user by login and password
     * @param login incoming login
     * @param password incoming password
     * @return specific user
     */
    UserModel findUserByLoginAndPassword(String login, String password);
    /**
     * Method for searching duplicates
     * @param login incoming login
     * @return Optional list of duplicates
     */
    Boolean hasDuplicates(String login);
}
