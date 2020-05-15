package com.haiziwang.kbook.svc;

import com.haiziwang.kbook.dbo.Customer;
import net.jplugin.core.ctx.api.Rule;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17.
 */
public interface CustomService {

    @Rule
    Customer queryCustomer(String custId);

}
