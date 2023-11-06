package ru.urfu.voiceassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
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

        ModelAndView modelAndView = new ModelAndView("personalPage");

        modelAndView.addObject("user", uniqueUser);

        return modelAndView;
    }
}
