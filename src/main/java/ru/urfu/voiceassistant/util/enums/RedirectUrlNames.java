package ru.urfu.voiceassistant.util.enums;

import lombok.Getter;

/**
 * Enum для хранения URL.
 */
@Getter
public enum RedirectUrlNames {

    INDEX("index"),
    LOGIN("login"),
    PERSONAL_PAGE("personal_page"),
    RECORD("record"),
    REGISTER("register"),
    UPLOAD_FILE("upload_file");

    private final String urlAddress;

    RedirectUrlNames(String urlAddress) {
        this.urlAddress = urlAddress;
    }
}
