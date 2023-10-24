package ru.urfu.voiceassistant.dao;

import ru.urfu.voiceassistant.model.UserModel;

import java.util.List;

/**
 * UserModel implementation interface
 * @author Емельянов Микхаил <emelianov.mikhail@urfu.me>
 * @see UserModel
 */
public interface UserDAO {

    List<UserModel> getUsersList();

    void add(UserModel user);
}
