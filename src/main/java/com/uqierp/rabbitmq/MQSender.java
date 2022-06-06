package com.uqierp.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//生产者
@Service
public class MQSender {
	
	@Autowired
	AmqpTemplate amqpTemplate;

    //Direct模式
	public void send(String msg) {
		//第一个参数队列的名字，第二个参数发出的信息
        System.out.println("开始发送消息："+msg);
		amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
	}
	
}
