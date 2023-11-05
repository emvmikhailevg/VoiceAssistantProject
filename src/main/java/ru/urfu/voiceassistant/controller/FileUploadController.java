package ru.urfu.voiceassistant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.urfu.voiceassistant.api.upload.FIleUploadResponse;
import ru.urfu.voiceassistant.api.upload.FileUploadUtil;

import java.io.IOException;
import java.util.Objects;

@RestController
public class FileUploadController {

    @PostMapping("/upload_file")
    public ResponseEntity<FIleUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Long size = multipartFile.getSize();

        String fileCode = FileUploadUtil.saveFile(fileName, multipartFile);

        FIleUploadResponse fileUploadResponse = new FIleUploadResponse();
        fileUploadResponse.setFileName(fileName);
        fileUploadResponse.setSize(size);
        fileUploadResponse.setDownloadURL("/download_file/" + fileCode);

        return new ResponseEntity<>(fileUploadResponse, HttpStatus.OK);
    }
}
