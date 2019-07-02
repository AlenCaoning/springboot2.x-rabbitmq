package com.alen.mapper;

import com.alen.entity.BrokerMessageLog;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface BrokerMessageLogMapper {
    int insert(BrokerMessageLog record);

    int insertSelective(BrokerMessageLog record);

    /**
     * 查询状态为0，且已经可以重发的消息
     * @return
     */
    List<BrokerMessageLog> query4StatusAndTimeoutMessage();

    /**
     * 重新发送，统计count次数+1
     * @param updateTime
     * @param messgeId
     * @return
     */
    int update4ReSend(@Param("updateTime") Date updateTime,@Param("messgeId") String messgeId);

    /**
     * 更新最终消息发送结果，成功为1，失败为2
     * @param messageId
     * @param status
     * @param updateTime
     * @return
     */
    int changeStatus(@Param("messageId")String messageId,@Param("status") String status,@Param("updateTime")Date updateTime);
}
