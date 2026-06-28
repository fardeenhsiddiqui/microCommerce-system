package com.notificationService.common.config;

import com.notificationService.common.constants.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public AmqpAdmin amqpAdmin(
            ConnectionFactory connectionFactory) {

        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public TopicExchange notificationExchange() {

        return new TopicExchange(
                RabbitMQConstants.EMAIL_EXCHANGE,
                true,
                false
        );
    }

    @Bean
    public Queue emailQueue() {

        return QueueBuilder
                .durable(
                        RabbitMQConstants.EMAIL_QUEUE)
                .build();
    }

    @Bean
    public Binding binding(Queue emailQueue, TopicExchange notificationExchange) {

        return BindingBuilder
                .bind(emailQueue)
                .to(notificationExchange)
                .with(
                        RabbitMQConstants.EMAIL_ROUTING_KEY
                );
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    /*
    @Bean
    public Queue userRegisteredQueue() {

        return QueueBuilder
                .durable(
                        RabbitMQConstants.USER_REGISTERED_QUEUE)
                .build();
    }
    @Bean
    public Binding userRegisteredBinding(Queue userRegisteredQueue, TopicExchange notificationExchange) {

        return BindingBuilder
                .bind(userRegisteredQueue)
                .to(notificationExchange)
                .with(
                        RabbitMQConstants.USER_REGISTERED_ROUTING_KEY
                );
    }
     */
}
