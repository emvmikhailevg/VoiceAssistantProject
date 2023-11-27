package ru.urfu.voiceassistant.service;

import ru.urfu.voiceassistant.dao.UserDAO;
import ru.urfu.voiceassistant.entity.UserEntity;

public interface UserService {

    void saveUser(UserDAO userDTO);

    UserEntity findUserByEmail(String email);
}
