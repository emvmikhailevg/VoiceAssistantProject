package ru.urfu.voiceassistant.api.upload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FIleUploadResponse {

    private String fileName;
    private String downloadURL;
    private Long size;
}
