package ru.urfu.voiceassistant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.service.EmailSenderService;
import ru.urfu.voiceassistant.service.PasswordResetService;

import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private final EmailSenderService emailSenderService;

    @Autowired
    public PasswordResetServiceImpl(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @Override
    public String generateResetToken(UserEntity user) {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public void sendInstructionsToChangePassword(UserEntity user) {
        String message = String.format(
                "Hello, %s. Now you can visit the next link: http://localhost:8080/password_recovery/%s",
                user.getLogin(),
                user.getResetToken()
        );

        emailSenderService.sender(user.getEmail(), "Reset password", message);
    }
}
