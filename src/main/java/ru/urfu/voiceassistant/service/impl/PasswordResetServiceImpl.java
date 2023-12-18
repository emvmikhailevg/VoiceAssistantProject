package ru.urfu.voiceassistant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.voiceassistant.entity.UserEntity;
import ru.urfu.voiceassistant.service.EmailSenderService;
import ru.urfu.voiceassistant.service.PasswordResetService;

import java.util.UUID;

/**
 * Реализация интерфейса {@link PasswordResetService}.
 */
@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private final EmailSenderService emailSenderService;

    /**
     * Конструирует новый объект {@link PasswordResetServiceImpl} с указанным сервисом отправки электронных писем.
     *
     * @param emailSenderService Сервис отправки электронных писем.
     */
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
        String messageToResetPassword = buildMessageToResetPassword(user);

        emailSenderService.sender(user.getEmail(), "Сброс пароля", messageToResetPassword);
    }

    /**
     * Строит текстовое сообщение для обновления пароля у пользователя.
     *
     * @param user Сущность пользователя, для которого создается сообщение.
     * @return Текстовое сообщение с инструкциями по сбросу пароля.
     */
    private String buildMessageToResetPassword(UserEntity user) {
        return String.format(
                "Hello, %s. Now you can visit the next link: http://localhost:8080/activate/%s",
                user.getLogin(),
                user.getActivationCode()
        );
    }
}
