package ru.urfu.voiceassistant.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.urfu.voiceassistant.dto.FileUploadResponseDTO;
import ru.urfu.voiceassistant.entity.FileEntity;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.repository.FileRepository;
import ru.urfu.voiceassistant.service.FileService;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void saveFile(FileUploadResponseDTO fileUploadResponseDTO, UserEntity user) {
        FileEntity file = new FileEntity();

        file.setFileName(fileUploadResponseDTO.getFileName());
        file.setSize(fileUploadResponseDTO.getSize());
        file.setDownloadURL(fileUploadResponseDTO.getDownloadURL());
        file.setUser(user);

        fileRepository.save(file);
    }

    @Override
    public List<FileEntity> findFilesById(Long id) {
        return fileRepository.findFilesEntityByUserId(id);
    }

    @Override
    @Transactional
    public void deleteFile(Long fileId, UserEntity user) throws FileNotFoundException {
        Optional<FileEntity> optionalFile = fileRepository.findById(fileId);
        if (optionalFile.isPresent()) {
            FileEntity file = optionalFile.get();
            if (file.getUser().equals(user)) {
                fileRepository.deleteById(fileId);
            } else {
                throw new AccessDeniedException("You do not have permission to delete this file.");
            }
        } else {
            throw new FileNotFoundException("File not found with id: " + fileId);
        }
    }
}
