package com.felipe.email.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.felipe.email.domain.EmailModel;
import com.felipe.email.dto.EmailDto;
import com.felipe.email.enums.EmailStatus;
import com.felipe.email.service.EmailService;


@Component
public class EmailConsumer
{
    @Autowired
    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }


    @RabbitListener(queues = "designed-to-email-ms")
    public void listenEmailQueue(@Payload EmailDto emailDto)
    {
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailModel.setStatus(EmailStatus.PENDING);
        emailService.sendEmail(emailModel);
    }
}

