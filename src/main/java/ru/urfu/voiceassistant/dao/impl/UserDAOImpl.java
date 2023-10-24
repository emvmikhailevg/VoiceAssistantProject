package ru.urfu.voiceassistant.dao.impl;

import org.springframework.stereotype.Component;
import ru.urfu.voiceassistant.dao.UserDAO;
import ru.urfu.voiceassistant.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * UserModelImpl class describes the user model
 * @author Емельянов Микхаил <emelianov.mikhail@urfu.me>
 * @see UserModel
 */
@Component
public class UserDAOImpl implements UserDAO {

    private final List<UserModel> usersList = new ArrayList<>();

    public List<UserModel> getUsersList() {
        return usersList;
    }

    @Override
    public void add(UserModel user) {
        usersList.add(user);
    }
}
