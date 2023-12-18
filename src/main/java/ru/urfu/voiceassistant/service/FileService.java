package ru.urfu.voiceassistant.service;

import org.springframework.web.multipart.MultipartFile;
import ru.urfu.voiceassistant.dto.FileUploadResponseDTO;
import ru.urfu.voiceassistant.entity.FileEntity;
import ru.urfu.voiceassistant.entity.UserEntity;

import java.io.IOException;
import java.util.List;

/**
 * Сервис, предоставляющий функционал операций с файлами.
 * Этот сервис обеспечивает сохранение файлов, поиск файлов пользователя по идентификатору,
 * удаление файлов с проверкой разрешений, и создание нового аудиофайла с сохранением информации в базе данных.
 */
public interface FileService {

    void saveFile(FileUploadResponseDTO fileUploadResponseDTO, UserEntity user);

    List<FileEntity> findFilesById(Long id);

    void deleteFile(Long fileId, UserEntity user) throws IOException;

    FileUploadResponseDTO createNewAudioFile(MultipartFile multipartFile) throws IOException;
}
