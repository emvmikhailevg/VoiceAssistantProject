package ru.urfu.voiceassistant.service;

import ru.urfu.voiceassistant.dto.FileUploadResponseDTO;
import ru.urfu.voiceassistant.entity.FileEntity;
import ru.urfu.voiceassistant.entity.UserEntity;

import java.io.IOException;
import java.util.List;

public interface FileService {

    void saveFile(FileUploadResponseDTO fileUploadResponseDTO, UserEntity user);

    List<FileEntity> findFilesById(Long id);

    void deleteFile(Long fileId, UserEntity user) throws IOException;
}
