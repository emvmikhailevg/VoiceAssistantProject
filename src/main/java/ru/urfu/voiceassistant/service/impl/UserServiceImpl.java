package ru.urfu.voiceassistant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.urfu.voiceassistant.dto.UserDTO;
import ru.urfu.voiceassistant.model.User;
import ru.urfu.voiceassistant.model.role.Role;
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
    public void saveUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = roleRepository.findByLogin("ROLE_ADMIN");

        if (role == null) { role = checkRoleExist(); }

        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDTO mapToUserDto(User user){
        UserDTO userDTO = new UserDTO();
        String[] str = user.getLogin().split(" ");
        userDTO.setLogin(str[0]);
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setLogin("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
