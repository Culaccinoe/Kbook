package com.haiziwang.kbook.export;

import com.alibaba.fastjson.JSONObject;
import com.haiziwang.kbook.dbo.Book;
import com.haiziwang.kbook.dbo.BorrowRecord;
import com.haiziwang.kbook.impl.BookListServiceImpl;
import com.haiziwang.kbook.svc.BorrowRecordService;
import com.haiziwang.platform.sso.auth.api.SsoConstants;

import net.jplugin.common.kits.http.HttpKit;
import net.jplugin.common.kits.http.HttpStatusException;
import net.jplugin.core.ctx.api.RefRuleService;
import net.jplugin.core.das.api.PageCond;
import net.jplugin.core.kernel.api.ctx.ThreadLocalContextManager;
import net.jplugin.core.rclient.api.RemoteExecuteException;
import net.jplugin.ext.webasic.api.JPluginApi;
import net.jplugin.ext.webasic.api.Para;
import scala.tools.nsc.typechecker.Macros.Success;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/23.
 */
public class BorrowRecordExport {

    @RefRuleService
    BorrowRecordService borrowRecordService;


    //根据条件查询 用户
    @JPluginApi(callerType = JPluginApi.CT.SSO, restrictLevel = JPluginApi.RL.TK)
    public HashMap<String, Object> queryByUser(@Para(name = "recordMap") HashMap<String, String> recordMap, @Para(name = "pageCond") PageCond pageCond) {
        String custId = ThreadLocalContextManager.instance.getContext().getAttribute(SsoConstants.LOGIN_NAME).toString();
        List<BorrowRecord> recordList = borrowRecordService.queryByUserWithPage(recordMap, pageCond, custId);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list", recordList);
        map.put("pc", pageCond);
        return map;
    }

    //管理员
    public HashMap<String, Object> queryByAdm(@Para(name = "recordMap") HashMap<String, String> recordMap, @Para(name = "pageCond") PageCond pageCond) {
        List<BorrowRecord> recordList = borrowRecordService.queryByAdmWithPage(recordMap, pageCond);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list", recordList);
        map.put("pc", pageCond);
        return map;
    }

    //根据custId和bookId获取该条数据信息
    //@JPluginApi(callerType = JPluginApi.CT.SSO, restrictLevel = JPluginApi.RL.TK)
    public void changeCount(@Para(name = "flag") String flag, @Para(name = "bookId") String bookId, @Para(name = "id") String id) {
        //String custId = ThreadLocalContextManager.instance.getContext().getAttribute(SsoConstants.LOGIN_NAME).toString();
        //根据bookId查询book信息s
        BorrowRecord bookInfo = borrowRecordService.getBookInfo(id);
        if ("0".equals(flag)) {//归还
            bookInfo.setState(2);
            bookInfo.setId(Integer.parseInt(id));
            bookInfo.setBookCount(bookInfo.getBookCount() + 1);
            borrowRecordService.changeBookInfo(bookInfo);//更改书的数量
            //更改借阅记录状态        
            borrowRecordService.changeBorrowInfo(bookInfo);
        } else {//续借
            bookInfo.setState(0);     
            bookInfo.setId(Integer.parseInt(id));
            borrowRecordService.changeBorrowInfo(bookInfo);
        }
    }
    
    @JPluginApi(callerType = JPluginApi.CT.SSO, restrictLevel = JPluginApi.RL.TK)
    public void mailRemind(@Para(name = "custId") String custId) {
    	  
    	  String host = "http://test.kmc.haiziwang.com";
          String url = host+"/kmc-web/mail/sendSystemMail.do";
          Map<String, Object> params = new HashMap<String, Object>();
          params.put("appCode", "k-book");
          params.put("serviceCode", "overdue");
          params.put("loginNames", custId);
          params.put("title", "逾期通知信息");
          params.put("nickName", "孩子王图书管理系统");
          params.put("content", "您借的书已到期,请按时归还");
          try {
			String retStr = HttpKit.post(url, params);
			JSONObject jsonObject=JSONObject.parseObject(retStr);
			if(jsonObject.getString("success").equals("false")){
				throw new RemoteExecuteException("-1",jsonObject.getString("msg") );
			}
						
		} catch (IOException | HttpStatusException e) {		
		      e.printStackTrace();
			
		}
    }
    
    public HashMap<String, Object> regularUpdate(@Para(name = "recordMap") HashMap<String, String> recordMap,String id) {
    	List<BorrowRecord> recordList = borrowRecordService.queryRegular(recordMap);
    	HashMap<String, Object> hashMap=new HashMap<String, Object>();
    	if(recordList.size()>0){
    		hashMap.put("list", recordList);
    		borrowRecordService.changeState(hashMap);
    	}

    	 return hashMap;
        
    }
    
    
}


