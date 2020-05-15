package com.haiziwang.kbook;

import net.jplugin.core.ctx.ExtensionCtxHelper;
import net.jplugin.core.das.mybatis.api.ExtensionMybatisDasHelper;
import net.jplugin.core.das.mybatis.api.MysqlPageInterceptor;
import net.jplugin.core.kernel.api.AbstractPlugin;
import net.jplugin.ext.webasic.ExtensionWebHelper;

import com.haiziwang.kbook.export.BookListExport;
import com.haiziwang.kbook.export.BorrowRecordExport;
import com.haiziwang.kbook.export.LoginExportController;
import com.haiziwang.kbook.impl.BookListServiceImpl;
import com.haiziwang.kbook.impl.BorrowRecordServiceImpl;
import com.haiziwang.kbook.impl.CustomServiceImpl;
import com.haiziwang.kbook.mapper.BookListMapper;
import com.haiziwang.kbook.mapper.BorrowRecordMapper;
import com.haiziwang.kbook.mapper.CustomMapper;
import com.haiziwang.kbook.svc.BookListService;
import com.haiziwang.kbook.svc.BorrowRecordService;
import com.haiziwang.kbook.svc.CustomService;

public class Plugin extends AbstractPlugin{
	public Plugin(){

		//登录
		ExtensionWebHelper.addServiceExportExtension(this, "/login", LoginExportController.class);
		ExtensionMybatisDasHelper.addMappingExtension(this, CustomMapper.class);
		ExtensionCtxHelper.addRuleExtension(this, CustomService.class, CustomServiceImpl.class);

		//借阅记录
		ExtensionWebHelper.addServiceExportExtension(this, "/borrow", BorrowRecordExport.class);
		ExtensionMybatisDasHelper.addMappingExtension(this,BorrowRecordMapper.class);
		ExtensionCtxHelper.addRuleExtension(this, BorrowRecordService.class, BorrowRecordServiceImpl.class);
		//图书列表
		ExtensionWebHelper.addServiceExportExtension(this, "/bookList", BookListExport.class);
		ExtensionCtxHelper.addRuleExtension(this, BookListService.class, BookListServiceImpl.class);
		ExtensionMybatisDasHelper.addMappingExtension(this, BookListMapper.class);
		// 分页
		ExtensionMybatisDasHelper.addInctprorExtension(this, MysqlPageInterceptor.class);

	}

	@Override
	public void init() {

	}

	@Override
	public int getPrivority() {
		return 0;
	}
	
}