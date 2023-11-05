package ru.urfu.voiceassistant.service;

import ru.urfu.voiceassistant.controller.dto.UserDTO;
import ru.urfu.voiceassistant.entity.UserEntity;

import java.util.List;

public interface UserService {

    void saveUser(UserDTO userDTO);

    UserEntity findUserByEmail(String email);

    List<UserDTO> findAllUsers();
}
