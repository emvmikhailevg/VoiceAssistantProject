package ru.urfu.voiceassistant.service;

import ru.urfu.voiceassistant.controller.dao.FileUploadResponseDAO;
import ru.urfu.voiceassistant.entity.FileEntity;
import ru.urfu.voiceassistant.entity.UserEntity;

import java.util.List;

public interface FileService {

    void saveFile(FileUploadResponseDAO fileUploadResponseDTO, UserEntity user);

    List<FileEntity> findAllFiles();

    List<FileEntity> findFilesById(Long id);
}
