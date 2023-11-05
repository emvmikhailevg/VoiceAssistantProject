package ru.urfu.voiceassistant.service;

import ru.urfu.voiceassistant.controller.dto.UserDTO;
import ru.urfu.voiceassistant.model.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDTO userDTO);

    User findUserByEmail(String email);

    List<UserDTO> findAllUsers();
}
