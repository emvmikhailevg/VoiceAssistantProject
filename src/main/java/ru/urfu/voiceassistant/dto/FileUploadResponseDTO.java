package ru.urfu.voiceassistant.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponseDTO {

    @NotEmpty(message = "File name cannot be empty")
    private String fileName;

    private Double size;

    @NotNull(message = "URL cannot be null")
    private String downloadURL;

    private LocalDateTime createdAt;
}
