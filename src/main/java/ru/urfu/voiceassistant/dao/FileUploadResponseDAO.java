package ru.urfu.voiceassistant.dao;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponseDAO {

    @NotEmpty(message = "File name cannot be empty")
    private String fileName;

    private Double size;

    @NotNull(message = "URL cannot be null")
    private String downloadURL;
}
