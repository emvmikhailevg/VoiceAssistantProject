package ru.urfu.voiceassistant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.urfu.voiceassistant.controller.dao.UserDAO;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.entity.role.Role;
import ru.urfu.voiceassistant.repository.RoleRepository;
import ru.urfu.voiceassistant.repository.UserRepository;
import ru.urfu.voiceassistant.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDAO userDAO) {
        UserEntity user = new UserEntity();
        user.setLogin(userDAO.getLogin());
        user.setEmail(userDAO.getEmail());
        user.setPassword(passwordEncoder.encode(userDAO.getPassword()));

        Role role = roleRepository.findByLogin("ROLE_ADMIN");

        if (role == null) { role = checkRoleExist(); }

        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDAO> findAllUsers() {
        List<UserEntity> allUsers = userRepository.findAll();
        return allUsers
                .stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.toList());
    }

    private UserDAO mapToUserDTO(UserEntity user) {
        UserDAO userDAO = new UserDAO();
        String[] str = user.getLogin().split(" ");
        userDAO.setLogin(str[0]);
        userDAO.setEmail(user.getEmail());
        return userDAO;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setLogin("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
