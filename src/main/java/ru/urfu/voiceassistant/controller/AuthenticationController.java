package ru.urfu.voiceassistant.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.urfu.voiceassistant.controller.dto.UserDTO;
import ru.urfu.voiceassistant.model.User;
import ru.urfu.voiceassistant.service.UserService;

import java.util.UUID;

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
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
        User uniqueUser = userService.findUserByEmail(userDTO.getEmail());

        if (uniqueUser != null && uniqueUser.getEmail() != null && !uniqueUser.getEmail().isEmpty()) {
            result.rejectValue("email", "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "register";
        }

        userService.saveUser(userDTO);
        return "redirect:/login";
    }

    @PostMapping("/login/auth")
    public String login(@ModelAttribute UserDTO userDTO, HttpServletResponse response) {
        String token = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        response.addCookie(cookie);
        response.setContentType("text/plain");

        User uniqueUser = userService.findUserByEmail(userDTO.getEmail());

        userService.updateUserToken(uniqueUser.getId(), token);

        return "redirect:/personal_page";
    }

    @GetMapping("/personal_page")
    public String getPersonalPage() {
//         достать куку с токеном - если есть в бд - работать
//        if (token.idGoof()) {
//            return personalPage
//        } else {
//            return unautherrorpage
//                    // login
//        }
        return "personalPage";
    }
}
