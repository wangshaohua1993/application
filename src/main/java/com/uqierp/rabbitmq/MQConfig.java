package com.uqierp.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MQConfig {

	public static final String MAIL_QUEUE_NAME = "mail.queue";
	public static final String MAIL_EXCHANGE_NAME = "mail.exchange";
	public static final String MAIL_ROUTING_KEY_NAME = "mail.routing.key";

	@Autowired
	private CachingConnectionFactory connectionFactory;

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());
		// 消息是否成功发送到Exchange
		rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
			if (ack) {
				log.info("消息发送到exchange成功");
			} else {
				log.info("消息发送到exchange失败, {}, cause: {}", data, cause);
			}
		});
		// 触发setReturnCallback回调须设置mandatory=true
		rabbitTemplate.setMandatory(true);
		// 消息是否从Exchange路由到Queue
		rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
			log.info("消息从Exchange路由到Queue失败: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}", exchange, routingKey, replyCode, replyText, message);
		});
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Queue mailQueue() {
		return new Queue(MAIL_QUEUE_NAME, true);
	}

	@Bean
	public DirectExchange mailExchange() {
		return new DirectExchange(MAIL_EXCHANGE_NAME, true, false);
	}

	@Bean
	public Binding mailBinding() {
		return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MAIL_ROUTING_KEY_NAME);
	}
	
}

