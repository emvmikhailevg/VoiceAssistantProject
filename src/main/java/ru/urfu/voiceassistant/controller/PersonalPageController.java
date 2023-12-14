package ru.urfu.voiceassistant.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.urfu.voiceassistant.dto.UserDTO;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.repository.UserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/personal_page")
public class PersonalPageController {

    private final UserRepository userRepository;

    @Autowired
    public PersonalPageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public ModelAndView getPersonalPage(Principal principal) {
        UserEntity uniqueUser = userRepository.findByEmail(principal.getName());

        ModelAndView modelAndViewPersonalPage = new ModelAndView("personalPage");
        ModelAndView modelAndViewLogin = new ModelAndView("login");

        modelAndViewPersonalPage.addObject("user", uniqueUser);

        if (uniqueUser == null) {
            return modelAndViewLogin;
        }

        return modelAndViewPersonalPage;
    }

    @PostMapping("/update")
    public String updatePersonalInfo(@Valid @ModelAttribute UserDTO userDTO,
                                     BindingResult bindingResult,
                                     Principal principal,
                                     Model model) {
        model.addAttribute("userDAO", userDTO);

        if (bindingResult.hasErrors() && userDTO.getPassword() != null) {
            return "personalPage";
        }

        UserEntity uniqueUser = userRepository.findByEmail(principal.getName());
        uniqueUser.setName(userDTO.getName());
        uniqueUser.setSurname(userDTO.getSurname());
        uniqueUser.setBirthday(userDTO.getBirthday());
        uniqueUser.setNumber(userDTO.getNumber());

        userRepository.save(uniqueUser);

        return "redirect:/personal_page";
    }
}
