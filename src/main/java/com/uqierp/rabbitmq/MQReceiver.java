package com.uqierp.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.uqierp.bean.Mail;
import com.uqierp.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 消费者
 */
@Service
@Slf4j
public class MQReceiver {

	@Autowired
	private MailUtil mailUtil;

	@RabbitListener(queues = MQConfig.MAIL_QUEUE_NAME)
	public void receive(Message msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
		String message = new String(msg.getBody(), "UTF-8");
		log.info("消费消息:" + message);
		Mail mail = JSON.parseObject(message, Mail.class);
		// 发送邮件
		boolean success = mailUtil.send(mail);
		if (success) {
			//手动确认消息
			channel.basicAck(tag, false);
		} else {
			channel.basicNack(tag, false, true);
		}
	}
	
}

