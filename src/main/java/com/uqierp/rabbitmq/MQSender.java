package com.uqierp.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.uqierp.bean.Mail;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 生产者
 */
@Service
public class MQSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(Mail mail) {
		String msgId = UUID.randomUUID().toString();
		mail.setMsgId(msgId);
		// 发送消息
		CorrelationData correlationData = new CorrelationData(msgId);
		rabbitTemplate.convertAndSend(MQConfig.MAIL_EXCHANGE_NAME, MQConfig.MAIL_ROUTING_KEY_NAME, JSON.toJSONString(mail), correlationData);
	}
	
}
