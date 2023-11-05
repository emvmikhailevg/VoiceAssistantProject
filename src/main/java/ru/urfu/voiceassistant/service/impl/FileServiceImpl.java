package ru.urfu.voiceassistant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.urfu.voiceassistant.controller.dto.FileUploadResponseDTO;
import ru.urfu.voiceassistant.controller.dto.UserDTO;
import ru.urfu.voiceassistant.entity.FileEntity;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.repository.FileRepository;
import ru.urfu.voiceassistant.service.FileService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
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
    public List<FileEntity> findAllFiles() {
        List<FileEntity> allFiles = fileRepository.findAll();
        return allFiles
                .stream()
                .map(this::mapToFileDTO)
                .collect(Collectors.toList());
    }

    private FileEntity mapToFileDTO(FileEntity file) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getFileName());
        fileEntity.setSize(file.getSize());
        fileEntity.setDownloadURL(file.getDownloadURL());
        return fileEntity;
    }
}
