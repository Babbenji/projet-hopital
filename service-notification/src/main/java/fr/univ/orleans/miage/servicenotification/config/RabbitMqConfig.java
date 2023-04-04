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
    private String exchange_auth;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingKey_auth;

    @Value("${spring.rabbitmq.exchange-facture}")
    private String exchange_facture;

    @Value("${spring.rabbitmq.routingkey-facture}")
    private String routingKey_facture;

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
    Exchange authExchange() {
        return ExchangeBuilder.directExchange(exchange_auth).durable(true).build();
    }

    @Bean
    Exchange factureExchange() {
        return ExchangeBuilder.directExchange(exchange_facture).durable(true).build();
    }

    @Bean
    Binding binding_auth() {
        return BindingBuilder
                .bind(queue())
                .to(authExchange())
                .with(routingKey_auth)
                .noargs();
    }

    @Bean
    Binding binding_facture() {
        return BindingBuilder
                .bind(queue())
                .to(factureExchange())
                .with(routingKey_facture)
                .noargs();
    }

    @Bean
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
