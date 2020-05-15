package com.haiziwang.kbook.mapper;

import com.haiziwang.kbook.dbo.Book;
import com.haiziwang.kbook.dbo.BorrowRecord;
import net.jplugin.core.das.api.PageCond;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */
public interface BorrowRecordMapper {

    List<BorrowRecord> queryByUserWithPage(@Param("recordMap") HashMap<String, String> recordMap, @Param("page") PageCond pageCond, @Param("custId") String custId);

    List<BorrowRecord> queryByAdmWithPage(@Param("recordMap") HashMap<String, String> recordMap, @Param("page") PageCond pageCond);

    int changeBookInfo(@Param("bookInfo") BorrowRecord bookInfo);

    BorrowRecord getBookInfo(@Param("id") String id);

    int changeBorrowInfo(@Param("bookInfo") BorrowRecord bookInfo);
    
    List <BorrowRecord> queryRegular(@Param("recordMap") HashMap<String, String> recordMap);
    
     void changeState( HashMap<String, Object> recordMap);

}