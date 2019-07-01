/**
 * Created by Alen Cao on 2019/7/1.
 */

package com.alen.consumer;

import com.alen.entity.Order;
import com.rabbitmq.client.Channel;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RabbitListener(
      bindings = {@QueueBinding(
            value = @Queue(value = "order-queue",durable = "true"),
            exchange = @Exchange(value = "order-exchange",durable = "true",type = "topic"),
            key = "order.#"),@QueueBinding(
            value = @Queue(value = "order-queue1",durable = "true"),
            exchange = @Exchange(value = "order-exchange1",durable = "true",type = "topic"),
            key = "order.*"),@QueueBinding(
            value = @Queue(value = "order-queque",durable = "true"),
            exchange = @Exchange(value = "order-exchange",durable = "true",type = "topic"),
            key = "order.*")}
)
public class OrderReceiver
{

   @RabbitHandler
   public void receiveOrder(@Payload Order order,//消息的Message分为body和properties。@Payload声明这是一个body
         @Headers Map<String,Object> headers,//消息的Message分为body和properties。@Payload声明这是一个properties。
         //properties用来对消息进行修饰，比如消息的优先级，消息的延迟等高级特性。也可以写自定义属性
         Channel channel//网络通道，类似于jdbc连接的session，代表一个会话
   )throws Exception
   {
      System.err.println("~~~收到消息，开始消费~~~");
      System.err.println("~~~订单ID：" + order.getId() + "~~~");

      Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
      //ACK,即签收消息
      /*
      void	basicAck​(long deliveryTag, boolean multiple)
      Acknowledge one or several received messages.
      */
      channel.basicAck(deliveryTag,false);
   }
}
