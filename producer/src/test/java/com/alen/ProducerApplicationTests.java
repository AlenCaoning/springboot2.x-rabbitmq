package com.alen;

import com.alen.entity.Order;
import com.alen.producer.OrderSender;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	OrderSender orderSender;

	@Test
	public void testSend1()throws Exception
	{
		Order order = new Order("2019070100000001",
				"测试订单1",
				System.currentTimeMillis() + "$" + UUID.randomUUID().toString());

		orderSender.send(order);
	}

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Test
	public void testSend2()throws Exception
	{
		Order order = new Order("2019070100000003",
				"测试订单3",
				System.currentTimeMillis() + "$" + UUID.randomUUID().toString());

		CorrelationData correlationData = new CorrelationData();
		correlationData.setId(order.getMessgeId());

		rabbitTemplate.convertAndSend("order-exchange1",//exchange
				"order.123",//routingKey
				order,//消息体内容
				correlationData);//消息唯一id);
	}
}
