package com.haiziwang.kbook.export;


import com.haiziwang.kbook.dbo.Customer;
import com.haiziwang.kbook.svc.CustomService;
import net.jplugin.common.kits.AssertKit;
import net.jplugin.core.ctx.api.RefRuleService;
import net.jplugin.core.ctx.api.RuleServiceFactory;
import net.jplugin.core.das.api.PageQueryResult;
import net.jplugin.core.rclient.api.RemoteExecuteException;
import net.jplugin.ext.webasic.api.AbstractExController;
import net.jplugin.ext.webasic.api.Para;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */


public class LoginExportController {

    CustomService svc;

//    @RefRuleService
//    CustomService service;

    public void login(@Para(name = "custId") String custId) {

        Customer customer = service().queryCustomer(custId);
        if (customer == null) {
            throw new RemoteExecuteException("-1", "用户不存在");

        }
    }


    private CustomService service() {
        if (svc == null) {
            synchronized (this) {
                svc = RuleServiceFactory.getRuleService(CustomService.class);
            }
        }
        return svc;
    }
}


