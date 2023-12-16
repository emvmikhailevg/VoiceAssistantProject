package ru.urfu.voiceassistant.service;

import ru.urfu.voiceassistant.entity.UserEntity;

public interface PasswordResetService {

    String generateResetToken(UserEntity user);

    void sendInstructionsToChangePassword(UserEntity user);
}
