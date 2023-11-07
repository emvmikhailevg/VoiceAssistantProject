package ru.urfu.voiceassistant.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.urfu.voiceassistant.controller.dao.UserDAO;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.service.UserService;

@Controller
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage() { return "index"; }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDAO userDAO = new UserDAO();
        model.addAttribute("user", userDAO);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") UserDAO userDAO, BindingResult result, Model model) {
        UserEntity uniqueUser = userService.findUserByEmail(userDAO.getEmail());

        if (uniqueUser != null && uniqueUser.getEmail() != null && !uniqueUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDAO);
            return "register";
        }

        userService.saveUser(userDAO);
        return "redirect:/register?success";
    }
}
