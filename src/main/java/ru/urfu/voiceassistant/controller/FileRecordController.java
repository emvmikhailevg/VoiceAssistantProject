package ru.urfu.voiceassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.urfu.voiceassistant.dao.FileUploadResponseDAO;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.repository.UserRepository;
import ru.urfu.voiceassistant.service.FileService;
import ru.urfu.voiceassistant.util.FileUploadUtil;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/record")
public class FileRecordController {

    private final FileService fileService;
    private final UserRepository userRepository;

    @Autowired
    public FileRecordController(FileService fileService,
                                UserRepository userRepository) {
        this.fileService = fileService;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String getMainRecordPage() {
        return "recordFilePage";
    }

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> saveRecord(MultipartFile multipartFile, Principal principal) throws IOException {
        UserEntity uniqueUser = userRepository.findByEmail(principal.getName());

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Double size = Math.round((multipartFile.getSize() / Math.pow(2, 20)) * 10.0) / 10.0;
        String fileCode = FileUploadUtil.saveFile(fileName, multipartFile);

        FileUploadResponseDAO fileUploadResponseDTO = new FileUploadResponseDAO();
        fileUploadResponseDTO.setFileName(fileName);
        fileUploadResponseDTO.setSize(size);
        fileUploadResponseDTO.setDownloadURL("/download_file/" + fileCode);

        fileService.saveFile(fileUploadResponseDTO, uniqueUser);
        return ResponseEntity.ok("File was successfully saved");
    }
}
