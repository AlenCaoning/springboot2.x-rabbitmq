/**
 * @author: Alen Cao
 * @description:
 * @time: 2019/7/1 17:42
 */

package com.alen.producer;

import com.alen.entity.Order;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OrderSender
{
   @Autowired
   private RabbitTemplate rabbitTemplate;

   public void send(Order order)throws Exception
   {
      CorrelationData correlationData = new CorrelationData();
      correlationData.setId(order.getMessgeId());

      rabbitTemplate.convertAndSend("order-exchange",//exchange
            "order.alentest",//routingKey
            order,//消息体内容
            correlationData);//消息唯一id
   }
}
