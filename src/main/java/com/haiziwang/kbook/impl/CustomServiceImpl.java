package com.haiziwang.kbook.impl;

import com.haiziwang.kbook.dbo.Customer;
import com.haiziwang.kbook.mapper.CustomMapper;
import com.haiziwang.kbook.svc.CustomService;
import net.jplugin.core.das.mybatis.impl.IMybatisService;
import net.jplugin.core.service.api.ServiceFactory;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17.
 */
public class CustomServiceImpl implements CustomService {

    @Override
    public Customer queryCustomer(String custId) {
        return dbService().getMapper(CustomMapper.class).query(custId);
    }


    private IMybatisService dbService() {
        return ServiceFactory.getService(IMybatisService.class);
    }
}
