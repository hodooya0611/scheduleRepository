package com.spring.service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface MailService {

    void createMimeMessage(String to, String subject, String htmlContent) throws MessagingException;

}

