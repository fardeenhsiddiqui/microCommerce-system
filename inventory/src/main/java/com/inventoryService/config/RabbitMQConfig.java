package com.inventoryService.config;

import com.inventoryService.gateway.QueueKeysName;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Product Restock Queue
    @Bean
    public Queue reStockQueue(){
        return new Queue(QueueKeysName.PRODUCT_RESTOCK);
    }

    // Product Exchange
    @Bean
    public DirectExchange productExchange(){
        return new DirectExchange(QueueKeysName.PRODUCT_EXCHANGE);
    }

    @Bean
    public Binding reStockBinding(Queue reStockQueue, DirectExchange exchange){
        return BindingBuilder.bind(reStockQueue)
                .to(exchange)
                .with(QueueKeysName.PRODUCT_RESTOCK_KEY);
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate getTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }




}
