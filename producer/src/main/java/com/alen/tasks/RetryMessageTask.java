/**
 * @author: Alen Cao
 * @description:
 * @time: 2019/7/2 18:02
 */

package com.alen.tasks;

import com.alen.constant.Constants;
import com.alen.entity.BrokerMessageLog;
import com.alen.entity.Order;
import com.alen.mapper.BrokerMessageLogMapper;
import com.alen.producer.OrderSender;
import com.alibaba.fastjson.JSON;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public class RetryMessageTask
{
   @Autowired private OrderSender orderSender;
   @Autowired private BrokerMessageLogMapper brokerMessageLogMapper;

   @Scheduled(initialDelay = 3000, fixedDelay = 10000)
   public void reSend()
   {
      System.err.println("~~~~~定时任务开启~~~~~");
      List<BrokerMessageLog> list = brokerMessageLogMapper.query4StatusAndTimeoutMessage();
      System.err.println(list);
      if (CollectionUtils.isNotEmpty(list))
      {
         list.forEach(brokerMessageLog ->
         {
            if (brokerMessageLog.getTry_count() >= 3)
            {
               //超过三次重发，认为失败
               brokerMessageLogMapper.changeStatus(brokerMessageLog.getMessage_id(), Constants.ORDER_SEND_FAIL, new Date());
            }
            else
            {
               //reSend
               Order order = JSON.toJavaObject(JSON.parseObject(brokerMessageLog.getMessage()), Order.class);
               brokerMessageLogMapper.update4ReSend(new Date(),brokerMessageLog.getMessage_id());
               try
               {
                  orderSender.send(order);
               }
               catch (Exception e)
               {
                  e.printStackTrace();
                  System.err.println("异常发生。。。");
               }
            }
         });
      }
   }
}
