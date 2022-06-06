package com.uqierp.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

//消费者
@Service
public class MQReceiver {
	
	@RabbitListener(queues=MQConfig.QUEUE)//指明监听的是哪一个queue
	public void receive(byte[] bytes) {
		try{
			String msg = new String(bytes,"GBK");
			System.out.println("消费消息："+msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

