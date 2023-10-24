package ru.urfu.voiceassistant.service;

import ru.urfu.voiceassistant.model.UserModel;

/**
 * Service implementation interface
 * @author Емельянов Михаил <emelianov.mikhail@urfu.me>
 * @see UserModel User model
 */
public interface UserService {
    /**
     * Method for user registration
     * @param email User email
     * @param login User login
     * @param password User password
     * @return User Object
     */
    UserModel registerUser(String email, String login, String password);
    /**
     * Method for user authentication
     * @param login User login
     * @param password User password
     * @return User Object
     */
    UserModel authenticateUser(String login, String password);
}
