package ru.urfu.voiceassistant.service;

public interface EmailSenderService {

    void sender(String email, String subject, String message);
}
