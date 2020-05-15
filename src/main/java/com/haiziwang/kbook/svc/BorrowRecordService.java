package com.haiziwang.kbook.svc;

import com.haiziwang.kbook.dbo.Book;
import com.haiziwang.kbook.dbo.BorrowRecord;
import net.jplugin.core.ctx.api.Rule;
import net.jplugin.core.das.api.PageCond;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */
public interface BorrowRecordService {

    @Rule
    List<BorrowRecord> queryByUserWithPage(HashMap<String, String> recordMap,PageCond pageCond,String custId);

    @Rule
    List<BorrowRecord> queryByAdmWithPage(HashMap<String, String> recordMap,PageCond pageCond);

    @Rule
    int changeBookInfo(BorrowRecord bookInfo);

    @Rule
    int changeBorrowInfo(BorrowRecord bookInfo);

    @Rule
    BorrowRecord getBookInfo(String id);

   
    List<BorrowRecord> queryRegular(HashMap<String, String> recordMap);
    
    void changeState(HashMap<String, Object> recordMap);

}
