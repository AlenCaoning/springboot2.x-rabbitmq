/**
 * @author: Alen Cao
 * @description:
 * @time: 2019/7/2 17:52
 */

package com.alen.service;

import com.alen.constant.Constants;
import com.alen.entity.BrokerMessageLog;
import com.alen.entity.Order;
import com.alen.mapper.BrokerMessageLogMapper;
import com.alen.mapper.OrderMapper;
import com.alen.producer.OrderSender;
import com.alibaba.fastjson.JSON;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService
{
   @Autowired
   private OrderMapper orderMapper;
   @Autowired
   private BrokerMessageLogMapper brokerMessageLogMapper;
   @Autowired
   private OrderSender orderSender;


   public void createOrder(Order order)throws Exception
   {
      Date orderDate = new Date();
      orderMapper.insert(order);

      BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
      brokerMessageLog.setMessage_id(order.getMessage_id());
      brokerMessageLog.setMessage(JSON.toJSON(order).toString());
      brokerMessageLog.setStatus(Constants.ORDER_SENDING);
      brokerMessageLog.setNext_retry(DateUtils.addMinutes(orderDate, Constants.ORDER_TIMEOUT));
      brokerMessageLog.setCreate_time(orderDate);
      brokerMessageLog.setUpdate_time(orderDate);
      brokerMessageLog.setTry_count(0);
      brokerMessageLogMapper.insert(brokerMessageLog);

      orderSender.send(order);
   }
}
