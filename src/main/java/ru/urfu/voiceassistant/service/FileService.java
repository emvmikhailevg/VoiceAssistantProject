package ru.urfu.voiceassistant.service;

import ru.urfu.voiceassistant.controller.dto.FileUploadResponseDTO;
import ru.urfu.voiceassistant.entity.FileEntity;
import ru.urfu.voiceassistant.entity.UserEntity;

import java.util.List;

public interface FileService {

    void saveFile(FileUploadResponseDTO fileUploadResponseDTO, UserEntity user);

    List<FileEntity> findAllFiles();

    List<FileEntity> findFilesById(Long id);
}
