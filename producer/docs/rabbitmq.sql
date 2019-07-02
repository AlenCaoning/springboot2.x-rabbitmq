##订单表
CREATE TABLE IF NOT EXISTS t_order (
  id VARCHAR(128) NOT NULL ,  ##订单id
  name VARCHAR(128),  ## 订单名称
  message_id VARCHAR(128) NOT NULL , ##mq消息唯一标识
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

##消息发送记录表
CREATE TABLE IF NOT EXISTS broker_message_log (
  message_id VARCHAR(128) NOT NULL ,  ##消息唯一标识
  message VARCHAR(4000) DEFAULT NULL ,  ##消息内容
  try_count INT(4) DEFAULT 0, ##重试次数
  status VARCHAR(10) DEFAULT '',  ##消息投递状态，0 投递中，1 投递成功，2 投递失败
  next_retry TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00', ##下次重试的时间
  create_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00', ##创建时间
  update_time TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00', ##修改时间
  PRIMARY KEY (message_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
