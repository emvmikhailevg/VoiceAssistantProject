package ru.urfu.voiceassistant.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.urfu.voiceassistant.dto.PasswordChangeDTO;
import ru.urfu.voiceassistant.dto.UserDTO;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.repository.UserRepository;
import ru.urfu.voiceassistant.service.PasswordResetService;
import ru.urfu.voiceassistant.service.UserService;

import java.util.Objects;

@Controller
public class AuthenticationController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordResetService passwordResetService;

    @Autowired
    public AuthenticationController(UserService userService,
                                    UserRepository userRepository,
                                    PasswordResetService passwordResetService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordResetService = passwordResetService;
    }

    @GetMapping("/")
    public String getHomePage() { return "index"; }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
        UserEntity uniqueUser = userService.findUserByEmail(userDTO.getEmail());

        if (uniqueUser != null && uniqueUser.getEmail() != null && !uniqueUser.getEmail().isEmpty()) {
            result.rejectValue(
                    "email",
                    null,
                    "Аккаунт с данным email уже существует");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "register";
        }

        userService.saveUser(userDTO);
        return "redirect:/register?success";
    }

    @GetMapping("/activate/{activationCode}")
    public String activationPage(@PathVariable String activationCode, Model model) {
        boolean isActivated = userService.activateUser(activationCode);

        if (isActivated) {
            model.addAttribute("message", "Пользователь успешно активировался");
        } else {
            model.addAttribute("message", "Код аутентификации не найден");
        }

        return "login";
    }

    @GetMapping("/password_reset")
    public String resetPassword() {
        return "passwordResetPage";
    }

    @PostMapping("/password_reset")
    public String resetPassword(@RequestParam String email, Model model) {
        UserEntity currentUser = userRepository.findByEmail(email);

        if (currentUser == null) {
            model.addAttribute("error", "Такого пользователя не существует");
            return "passwordResetPage";
        }

        String resetToken = passwordResetService.generateResetToken(currentUser);
        currentUser.setResetToken(resetToken);
        userRepository.save(currentUser);

        passwordResetService.sendInstructionsToChangePassword(currentUser);

        model.addAttribute(
                "success",
                "Пожалуйста, проверьте свой email для получения инструкции по смене пароля");

        return "passwordResetPage";
    }

    @GetMapping("/password_recovery/{token}")
    public String showPasswordRecoveryPage(@PathVariable String token, Model model) {
        if (token == null) {
            model.addAttribute("error", "Что-то пошло не так");
        }

        model.addAttribute("token", token);
        return "recoveryPasswordPage";
    }

    @PostMapping("/password_recovery/{token}")
    public String recoverPassword(@PathVariable String token,
                                  @Valid PasswordChangeDTO passwordChangeDTO,
                                  BindingResult bindingResult,
                                  Model model) {
        UserEntity user = userRepository.findUserEntityByResetToken(token);

        if (user == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            String validationErrorMessage =
                    Objects.requireNonNull(bindingResult.getFieldError("password")).getDefaultMessage();
            model.addAttribute("error", validationErrorMessage);
            return "recoveryPasswordPage";
        }

        if (!passwordChangeDTO.getPassword().equals(passwordChangeDTO.getConfirmPassword())) {
            model.addAttribute("error", "Пароли не совпадают");
            return "recoveryPasswordPage";
        }

        try {
            userService.changePassword(user, passwordChangeDTO.getPassword());
            return "redirect:/login?success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "recoveryPasswordPage";
        }
    }
}
