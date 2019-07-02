/**
 * @author: Alen Cao
 * @description:
 * @time: 2019/7/1 17:42
 */

package com.alen.producer;

import com.alen.constant.Constants;
import com.alen.entity.Order;
import com.alen.mapper.BrokerMessageLogMapper;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class OrderSender
{
   @Autowired
   private RabbitTemplate rabbitTemplate;

   @Autowired
   private BrokerMessageLogMapper brokerMessageLogMapper;


   final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback()
   {
      @Override
      public void confirm(CorrelationData correlationData, boolean ack, String cause)
      {
         System.err.println("correlationData---> " + correlationData);
         System.err.println("cause---> " + cause);
         String messageId = correlationData.getId();
         if (ack)
         {
              //如果ack成功，即消费成功，改变消息状态
            brokerMessageLogMapper.changeStatus(messageId, Constants.ORDER_SEND_SUCCESS,new Date());
         }
         else
         {
            //ack失败则进行具体的后续操作：重试，或者补偿？
            System.err.println("异常处理。。。");
         }
      }
   };

   public void send(Order order)throws Exception
   {
      //指定rabbitTemplate的confirm回调函数
      rabbitTemplate.setConfirmCallback(confirmCallback);

      CorrelationData correlationData = new CorrelationData();
      correlationData.setId(order.getMessage_id());

      rabbitTemplate.convertAndSend("order-exchange",//exchange
            "order.alentest",//routingKey
            order,//消息体内容
            correlationData);//消息唯一id
   }
}
