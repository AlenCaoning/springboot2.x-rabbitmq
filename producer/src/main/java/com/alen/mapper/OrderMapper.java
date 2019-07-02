package com.alen.mapper;


import com.alen.entity.Order;


public interface OrderMapper {
    int insert(Order record);

    int insertSelective(Order record);
}
