package ru.urfu.voiceassistant.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.urfu.voiceassistant.dto.UserDTO;
import ru.urfu.voiceassistant.model.User;
import ru.urfu.voiceassistant.service.UserService;

@Controller
@RequestMapping("")
public class AuthenticationController {

    private final UserService userService;
    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String getHomePage() { return "index"; }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") UserDTO userDTO,
                           BindingResult result,
                           Model model) {
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

    @GetMapping("/personal_page")
    public String showUsers(@ModelAttribute UserDTO userDTO,
                            Model model){
        User uniqueUser = userService.findUserByEmail(userDTO.getEmail());
        model.addAttribute("user", uniqueUser);
        return "personalPage";
    }
}
