package com.felipe.user.producer;

import com.felipe.user.domain.UserModel;
import com.felipe.user.dto.EmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserProducer
{
    final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void publishEvent(UserModel userModel)
    {
        //The routingKey name must be the same as the queue name.
        final String routingKey = "designed-to-email-ms";

        var emailDto = EmailDto.builder()
                .userId(userModel.getUserId())
                .emailTo(userModel.getEmail())
                .build();

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
}

