package com.haiziwang.kbook.mapper;

import com.haiziwang.kbook.dbo.Customer;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17.
 */
public interface CustomMapper {

    public Customer query(@Param("custId") String custId);

}
