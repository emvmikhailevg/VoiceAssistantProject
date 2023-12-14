package ru.urfu.voiceassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.urfu.voiceassistant.dto.FileUploadResponseDTO;
import ru.urfu.voiceassistant.entity.FileEntity;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.repository.FileRepository;
import ru.urfu.voiceassistant.repository.UserRepository;
import ru.urfu.voiceassistant.service.FileService;
import ru.urfu.voiceassistant.util.FileUploadUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/record")
public class FileRecordController {

    private final FileService fileService;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Autowired
    public FileRecordController(FileService fileService,
                                UserRepository userRepository,
                                FileRepository fileRepository) {
        this.fileService = fileService;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    @GetMapping("")
    public ModelAndView getMainRecordPage() {
        ModelAndView mainPage = new ModelAndView("recordFilePage");

        List<FileEntity> allFilesList = fileRepository.findAll();

        mainPage.addObject("files", allFilesList);

        return mainPage;
    }

    @PostMapping("/save")
    public String saveRecord(MultipartFile multipartFile, Principal principal) throws IOException {
        UserEntity uniqueUser = userRepository.findByEmail(principal.getName());

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Double size = Math.round((multipartFile.getSize() / Math.pow(2, 20)) * 10.0) / 10.0;
        String fileCode = FileUploadUtil.saveFile(fileName, multipartFile);

        FileUploadResponseDTO fileUploadResponseDTO = new FileUploadResponseDTO();
        fileUploadResponseDTO.setFileName(fileName);
        fileUploadResponseDTO.setSize(size);
        fileUploadResponseDTO.setDownloadURL("/download_file/" + fileCode);

        fileService.saveFile(fileUploadResponseDTO, uniqueUser);
        return "redirect:/record";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Long fileId, Principal principal, Model model) {
        UserEntity uniqueUser = userRepository.findByEmail(principal.getName());

        try {
            String linkToDownload = fileRepository.findFileEntityById(fileId).getDownloadURL();
            String fileCode = linkToDownload.substring(linkToDownload.lastIndexOf("/") + 1);
            FileUploadUtil.deleteFileByCode(fileCode);
            fileService.deleteFile(fileId, uniqueUser);
        } catch (FileNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/record";
    }
}
