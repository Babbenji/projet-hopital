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

@Component
public class EmailConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    @Autowired
    EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload EmailDto emailDto) throws MessagingException {

        logger.info("Event consumer email réceptionné: " + emailDto);

        Email email = new Email();

        BeanUtils.copyProperties(emailDto, email);

        if (emailDto.getType().equals("html")) {
            emailService.envoyerEmailHtml(email);
        } else {
            emailService.envoyerEmail(email);
        }

        logger.info("Email envoyé : " + email);
    }

}
