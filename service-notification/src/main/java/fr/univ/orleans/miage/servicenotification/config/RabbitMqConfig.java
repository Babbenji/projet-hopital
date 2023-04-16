package fr.univ.orleans.miage.servicenotification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingKey;

    @Value("${spring.rabbitmq.queue-notification}")
    private String queueNotif;

    @Value("${spring.rabbitmq.routingkey-notification}")
    private String routingKeyNotif;



    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Bean
    public Queue queue() {
        return new Queue(queue, true);
    }

    @Bean
    public Queue queueNotif() {
        return new Queue(queueNotif, true);
    }


    @Bean
    Exchange exchange() {
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }


    @Bean
    Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey)
                .noargs();
    }

    @Bean
    Binding bindingNotif() {
        return BindingBuilder
                .bind(queueNotif())
                .to(exchange())
                .with(routingKeyNotif)
                .noargs();
    }


    @Bean(name="service-notification")
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }


}
