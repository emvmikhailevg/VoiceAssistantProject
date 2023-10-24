package ru.urfu.voiceassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.urfu.voiceassistant.model.UserModel;
import ru.urfu.voiceassistant.service.UserService;

/**
 * The controller class is needed to implement HTTP methods, links to HTML pages
 * @author Емельянов Микхаил <emelianov.mikhail@urfu.me>
 * @see UserService
 */
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new UserModel());
        return "register";
    }

    @GetMapping("/login")
    public String getAuthenticatePage(Model model) {
        model.addAttribute("loginRequest", new UserModel());
        return "login";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserModel user) {
        System.out.println("Register Request: " + user);
        UserModel registeredUser = userService.registerUser(user.getEmail(), user.getLogin(), user.getPassword());
        return registeredUser != null ? "error-page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String authenticateUser(@ModelAttribute UserModel user, Model model) {
        System.out.println("Authenticate Request: " + user);
        UserModel authenticatedUser = userService.authenticateUser(user.getLogin(), user.getPassword());
        if (authenticatedUser != null) {
            model.addAttribute("authenticatedUser", authenticatedUser.toString());
            return "personal-page";
        } else {
            return "error-page";
        }
    }
}
