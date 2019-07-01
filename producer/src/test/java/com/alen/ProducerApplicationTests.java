package com.alen;

import com.alen.entity.Order;
import com.alen.producer.OrderSender;

import org.junit.Test;
import org.junit.runner.RunWith;
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
}
