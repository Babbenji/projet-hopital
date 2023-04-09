package fr.univ.orleans.miage.servicenotification.consumer;

import fr.univ.orleans.miage.servicenotification.dto.EmailDto;
import fr.univ.orleans.miage.servicenotification.modele.Email;
import fr.univ.orleans.miage.servicenotification.service.EmailService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static fr.univ.orleans.miage.servicenotification.consumer.EmailConsumer.logger;

@Component
public class NotifConsumer
{



    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen() throws MessagingException {


    }

}
