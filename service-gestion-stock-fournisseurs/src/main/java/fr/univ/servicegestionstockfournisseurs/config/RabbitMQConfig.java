package fr.univ.servicegestionstockfournisseurs.config;


import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;
import org.springframework.stereotype.Component;


@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

//    @Value("${spring.rabbitmq.exchange}")
//    private String exchange;

//    @Value("${spring.rabbitmq.exchange}")
//    private String exchangeFacture;

//    @Value("${spring.rabbitmq.routingkey}")
//    private String routingKey;

    @Value("${spring.rabbitmq.exchange-stock}")
    private String exchangeStock;

    @Value("${spring.rabbitmq.routingkey-stock}")
    private String routingKeyStock;



    // spring bean for rabbitmq queue
    @Bean
    public Queue queue(){
        return new Queue(queue);
    }

    @Bean(name = "service-gestion-stock-fournisseurs")
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    // spring bean for rabbitmq exchange
//    @Bean
//    public TopicExchange exchange(){
//        return new TopicExchange(exchange);
//    }
    @Bean
    public TopicExchange stockExchange(){
        return new TopicExchange(exchangeStock);
    }

//    @Bean
//    public Exchange factureExchange()
//    {
//        return ExchangeBuilder.topicExchange(exchangeFacture).durable(true).build();
//    }
//


    // binding between queue and exchange using routing key
//    @Bean
//    public Binding binding()
//    {
//        return BindingBuilder
//                .bind(queue())
//                .to(exchange())
//                .with(routingKey);
//    }

//    @Bean
//    public Binding factureBinding()
//    {
//        return BindingBuilder
//                .bind(queue())
//                .to(factureExchange())
//                .with(routingKey)
//                .noargs();
//    }
    @Bean
    public Binding stockBinding()
    {
        return BindingBuilder
                .bind(queue())
                .to(stockExchange())
                .with(routingKeyStock);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

// Spring boot autoconfiguration provides following beans
    // ConnectionFactory
    // RabbitTemplate
    // RabbitAdmin
}
