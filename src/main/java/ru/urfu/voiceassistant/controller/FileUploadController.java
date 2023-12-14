package ru.urfu.voiceassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.urfu.voiceassistant.exception.UnsupportedFileTypeException;
import ru.urfu.voiceassistant.repository.FileRepository;
import ru.urfu.voiceassistant.util.FileUploadUtil;
import ru.urfu.voiceassistant.dto.FileUploadResponseDTO;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.repository.UserRepository;
import ru.urfu.voiceassistant.service.FileService;

import org.apache.commons.io.FilenameUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

import static ru.urfu.voiceassistant.util.FileUploadUtil.deleteFileByCode;

@Controller
@RequestMapping("/upload_file")
public class FileUploadController {

    private final FileService fileService;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Autowired
    public FileUploadController(FileService fileService,
                                UserRepository userRepository,
                                FileRepository fileRepository) {
        this.fileService = fileService;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    @PostMapping("")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile,
                             Principal principal,
                             Model model) throws IOException {
        UserEntity uniqueUser = userRepository.findByEmail(principal.getName());

        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

            String fileExtension = FilenameUtils.getExtension(fileName);
            if (!("mp3".equalsIgnoreCase(fileExtension) || "wav".equalsIgnoreCase(fileExtension))) {
                throw new UnsupportedFileTypeException("Unsupported file type. Only mp3 and wav files are allowed.");
            }

            Double size = Math.round((multipartFile.getSize() / Math.pow(2, 20)) * 10.0) / 10.0;
            String fileCode = FileUploadUtil.saveFile(fileName, multipartFile);

            FileUploadResponseDTO fileUploadResponseDTO = new FileUploadResponseDTO();
            fileUploadResponseDTO.setFileName(fileName);
            fileUploadResponseDTO.setSize(size);
            fileUploadResponseDTO.setDownloadURL("/download_file/" + fileCode);

            fileService.saveFile(fileUploadResponseDTO, uniqueUser);

            return "redirect:/upload_file";
        } catch (UnsupportedFileTypeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("files", fileService.findFilesById(uniqueUser.getId()));
            model.addAttribute("user", uniqueUser.getLogin());

            return "uploadFile";
        }
    }

    @GetMapping("")
    public ModelAndView GetUploadFilePage(Principal principal) {
        UserEntity uniqueUser = userRepository.findByEmail(principal.getName());

        ModelAndView modelAndView = new ModelAndView("uploadFile");
        ModelAndView modelAndViewLogin = new ModelAndView("login");

        if (uniqueUser == null) {
            return modelAndViewLogin;
        }

        modelAndView.addObject("files", fileService.findFilesById(uniqueUser.getId()));
        modelAndView.addObject("user", uniqueUser.getLogin());

        return modelAndView;
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Long fileId, Principal principal, Model model) {
        UserEntity uniqueUser = userRepository.findByEmail(principal.getName());

        try {
            String linkToDownload = fileRepository.findFileEntityById(fileId).getDownloadURL();
            String fileCode = linkToDownload.substring(linkToDownload.lastIndexOf("/") + 1);
            deleteFileByCode(fileCode);
            fileService.deleteFile(fileId, uniqueUser);
        } catch (FileNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/upload_file";
    }
}
