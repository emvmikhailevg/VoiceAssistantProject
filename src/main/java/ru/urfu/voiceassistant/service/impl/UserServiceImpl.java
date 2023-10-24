package ru.urfu.voiceassistant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.urfu.voiceassistant.dao.UserDAO;
import ru.urfu.voiceassistant.model.UserModel;
import ru.urfu.voiceassistant.repository.UserRepository;
import ru.urfu.voiceassistant.service.UserService;

/**
 * Implementation of business logic via the UserService interface
 * @author Емельянов Михаил <emelianov.mikhail@urfu.me>
 * @see UserRepository
 * @see UserModel
 */
@Service
@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserDAO userDAO) {
        this.userRepository = userRepository;
        this.userDAO = userDAO;
    }

    @Override
    public UserModel registerUser(String email, String login, String password) {
        if (login == null || password == null) {
            return null;
        } else {
            if (userRepository.hasDuplicates(login)) {
                System.out.println("Duplicate");
                return null;
            }

            UserModel user = new UserModel();
            user.setEmail(email);
            user.setLogin(login);
            user.setPassword(password);

            userDAO.add(user);

            return userRepository.save(user);
        }
    }

    @Override
    public UserModel authenticateUser(String login, String password) {
        return userRepository.findUserByLoginAndPassword(login, password);
    }
}
