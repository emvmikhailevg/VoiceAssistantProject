package ru.urfu.voiceassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.urfu.voiceassistant.util.FileUploadUtil;
import ru.urfu.voiceassistant.controller.dto.FileUploadResponseDTO;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.repository.UserRepository;
import ru.urfu.voiceassistant.service.FileService;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/upload_file")
public class FileUploadController {

    private final FileService fileService;
    private final UserRepository userRepository;

    @Autowired
    public FileUploadController(FileService fileService, UserRepository userRepository) {
        this.fileService = fileService;
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public String uploadFile(
            @RequestParam("file") MultipartFile multipartFile, Principal principal) throws IOException {
        UserEntity uniqueUser = userRepository.findByEmail(principal.getName());

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Double size = Math.round((multipartFile.getSize() / Math.pow(2, 20)) * 10.0) / 10.0;
        String fileCode = FileUploadUtil.saveFile(fileName, multipartFile);

        FileUploadResponseDTO fileUploadResponseDTO = new FileUploadResponseDTO();
        fileUploadResponseDTO.setFileName(fileName);
        fileUploadResponseDTO.setSize(size);
        fileUploadResponseDTO.setDownloadURL("/download_file/" + fileCode);

        if (uniqueUser.getId() == null) {
            return "redirect:/login";
        }

        fileService.saveFile(fileUploadResponseDTO, uniqueUser);

        return "redirect:/upload_file";
    }

    @GetMapping("")
    public ModelAndView GetUploadFilePage(Principal principal) {
        UserEntity uniqueUser = userRepository.findByEmail(principal.getName());

        ModelAndView modelAndView = new ModelAndView("uploadFile");
        modelAndView.addObject("files", fileService.findFilesById(uniqueUser.getId()));
        modelAndView.addObject("user", uniqueUser.getLogin());

        return modelAndView;
    }
}
