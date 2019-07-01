/**
 * @author: Alen Cao
 * @description:
 * @time: 2019/7/1 17:37
 */

package com.alen.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable//mq传递消息，需要序列化
{
   private static final long serialVersionUID = -3754514622481267331L;
   private String id;
   private String name;
   //存储消息发送的唯一标识
   private String messgeId;

}
