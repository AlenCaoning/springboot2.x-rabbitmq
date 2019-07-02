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
   private static final long serialVersionUID = 6826705142160901833L;
   private String id;

   private String name;
   //存储消息发送的唯一标识
   private String message_id;

   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id == null ? null : id.trim();
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name == null ? null : name.trim();
   }

   public String getMessage_id()
   {
      return message_id;
   }

   public void setMessage_id(String message_id)
   {
      this.message_id = message_id == null ? null : message_id.trim();
   }
}
