package ru.urfu.voiceassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.urfu.voiceassistant.entity.FileEntity;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.repository.UserRepository;
import ru.urfu.voiceassistant.service.FileService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/personal_page")
public class PersonalPageController {

    private final UserRepository userRepository;
    private final FileService fileService;

    @Autowired
    public PersonalPageController(UserRepository userRepository, FileService fileService) {
        this.userRepository = userRepository;
        this.fileService = fileService;
    }

    @GetMapping("")
    public ModelAndView getPersonalPage(Principal principal) {
        UserEntity uniqueUser = userRepository.findByEmail(principal.getName());

        ModelAndView modelAndViewPersonalPage = new ModelAndView("personalPage");
        ModelAndView modelAndViewLogin = new ModelAndView("login");

        if (uniqueUser == null) {
            return modelAndViewLogin;
        }

        List<FileEntity> allFiles = fileService.findFilesById(uniqueUser.getId());

        modelAndViewPersonalPage.addObject("user", uniqueUser);

        if (allFiles.size() < 5) {
            modelAndViewPersonalPage.addObject("recentUploadedFiles", allFiles);
        } else {
            modelAndViewPersonalPage.addObject(
                    "recentUploadedFiles", allFiles.subList(allFiles.size() - 5, allFiles.size()));
        }

        return modelAndViewPersonalPage;
    }
}
