package com.haiziwang.kbook.impl;

import com.haiziwang.kbook.dbo.Book;
import com.haiziwang.kbook.dbo.BorrowRecord;
import com.haiziwang.kbook.mapper.BorrowRecordMapper;
import com.haiziwang.kbook.svc.BorrowRecordService;
import com.paipai.lang.int64;

import net.jplugin.common.kits.http.HttpKit;
import net.jplugin.common.kits.http.HttpStatusException;
import net.jplugin.core.das.api.PageCond;
import net.jplugin.core.das.mybatis.impl.IMybatisService;
import net.jplugin.core.service.api.ServiceFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/23.
 */
public class BorrowRecordServiceImpl implements BorrowRecordService {

    private IMybatisService dbService() {
        return ServiceFactory.getService(IMybatisService.class);
    }

    //用户
    @Override
    public List<BorrowRecord> queryByUserWithPage(HashMap<String, String> recordMap, PageCond pageCond, String custId) {
        return dbService().getMapper(BorrowRecordMapper.class).queryByUserWithPage(recordMap, pageCond, custId);
    }

    //管理员
    @Override
    public List<BorrowRecord> queryByAdmWithPage(HashMap<String, String> recordMap, PageCond pageCond) {
        return dbService().getMapper(BorrowRecordMapper.class).queryByAdmWithPage(recordMap, pageCond);
    }

    @Override
    public int changeBookInfo(BorrowRecord bookInfo) {
        return dbService().getMapper(BorrowRecordMapper.class).changeBookInfo(bookInfo);
    }

    @Override
    public int changeBorrowInfo(BorrowRecord bookInfo) {
        return dbService().getMapper(BorrowRecordMapper.class).changeBorrowInfo(bookInfo);
    }

    @Override
    public BorrowRecord getBookInfo(String id) {
        return dbService().getMapper(BorrowRecordMapper.class).getBookInfo(id);
    }

	@Override
	public List<BorrowRecord> queryRegular(HashMap<String, String> recordMap) {
		 return dbService().getMapper(BorrowRecordMapper.class).queryRegular(recordMap);
	}

	@Override
	public void changeState(HashMap<String, Object> recordMap) {
		dbService().getMapper(BorrowRecordMapper.class).changeState(recordMap);
		
	}
    
}
