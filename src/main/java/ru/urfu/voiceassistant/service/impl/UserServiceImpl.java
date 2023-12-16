package ru.urfu.voiceassistant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.urfu.voiceassistant.dto.UserDTO;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.entity.role.Role;
import ru.urfu.voiceassistant.repository.RoleRepository;
import ru.urfu.voiceassistant.repository.UserRepository;
import ru.urfu.voiceassistant.service.EmailSenderService;
import ru.urfu.voiceassistant.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setLogin(userDTO.getLogin());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setActivationCode(UUID.randomUUID().toString());

        Role role = roleRepository.findByLogin("ROLE_USER");

        if (role == null) { role = checkRoleExist(); }

        user.setRoles(List.of(role));

        String message = String.format(
                "Hello, %s. Now you can visit the next link: http://localhost:8080/activate/%s",
                user.getLogin(),
                user.getActivationCode()
        );

        emailSenderService.sender(user.getEmail(), "Activation code", message);

        userRepository.save(user);
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean activateUser(String activationCode) {
        UserEntity currentUser = userRepository.findUserEntityByActivationCode(activationCode);
        return currentUser.getActivationCode() != null;
    }

    @Override
    public void changePassword(UserEntity currentUser, String newPassword) {
        if (passwordEncoder.matches(newPassword, currentUser.getPassword())) {
            throw new IllegalArgumentException("Новый пароль должен отличаться от старого пароля");
        }

        currentUser.setPassword(passwordEncoder.encode(newPassword));
        currentUser.setResetToken(null);

        userRepository.save(currentUser);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setLogin("ROLE_USER");
        return roleRepository.save(role);
    }
}
