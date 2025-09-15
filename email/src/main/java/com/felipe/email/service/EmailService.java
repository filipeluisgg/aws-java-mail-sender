package com.felipe.email.service;

import com.felipe.email.domain.EmailModel;
import com.felipe.email.enums.EmailStatus;
import com.felipe.email.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.*;

import java.time.LocalDateTime;

@Service
public class EmailService
{
    @Value("${email.sender}")
    private String emailSender;

    private final EmailRepository emailRepository;

    private final SesV2Client sesV2Client;

    public EmailService(SesV2Client sesV2Client, EmailRepository emailRepository) {
        this.sesV2Client = sesV2Client;
        this.emailRepository = emailRepository;
    }


    public void sendEmail(EmailModel emailModel)
    {
        Destination destination = Destination.builder()
                .toAddresses(emailModel.getEmailTo())
                .build();

        Content content = Content.builder()
                .data("<h3>Hello! Thanks for registering in email/user microservice application!</h3>")
                .build();

        Content sub = Content.builder()
                .data("Welcome!")
                .build();

        Body body = Body.builder()
                .html(content)
                .build();

        Message msg = Message.builder()
                .subject(sub)
                .body(body)
                .build();


        EmailContent emailContent = EmailContent.builder()
                .simple(msg)
                .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(destination)
                .content(emailContent)
                .fromEmailAddress(emailSender)
                .build();

        try {
            sesV2Client.sendEmail(emailRequest);
            emailModel.setSendDate(LocalDateTime.now());
            emailModel.setStatus(EmailStatus.SENT);
            emailModel.setEmailFrom(emailSender);
            emailModel.setBody(String.valueOf(msg.body()));
            emailModel.setSubject(String.valueOf(msg.subject()));

            saveEmail(emailModel);
            System.out.println("Greeting message sent to destiny: " + emailModel.getEmailTo());
        }
        catch (SesV2Exception e) {
            emailModel.setStatus(EmailStatus.FAILED);
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

    private void saveEmail(EmailModel emailModel)
    {
        emailRepository.save(emailModel);
    }
}

