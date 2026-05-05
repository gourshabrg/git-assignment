package com.Capstone.InterviewTracking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.Capstone.InterviewTracking.constant.AppConstants;
import com.Capstone.InterviewTracking.exception.EmailSendingException;
import com.Capstone.InterviewTracking.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendVerificationMail(String email, String name , String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); 
        helper.setTo(email);
        helper.setSubject(AppConstants.EMAIL_SUBJECT);
         helper.setText(AppConstants.buildEmailMessage(name, token), true);  
        mailSender.send(message);
    } catch (Exception e) {
        e.printStackTrace();
         throw new EmailSendingException("Failed to send verification email");   
     }
    }
}