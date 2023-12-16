package ru.urfu.voiceassistant.service;

import ru.urfu.voiceassistant.dto.UserDTO;
import ru.urfu.voiceassistant.entity.UserEntity;

public interface UserService {

    void saveUser(UserDTO userDTO);

    UserEntity findUserByEmail(String email);

    boolean activateUser(String activationCode);

    void changePassword(UserEntity currentUser, String password);
}
