package com.haiziwang.kbook.impl;

import java.util.HashMap;
import java.util.List;

import net.jplugin.core.das.api.PageCond;
import net.jplugin.core.das.mybatis.impl.IMybatisService;
import net.jplugin.core.service.api.ServiceFactory;

import com.haiziwang.kbook.dbo.Book;
import com.haiziwang.kbook.dbo.BorrowRecord;
import com.haiziwang.kbook.dbo.Customer;
import com.haiziwang.kbook.mapper.BookListMapper;
import com.haiziwang.kbook.mapper.BorrowRecordMapper;
import com.haiziwang.kbook.mapper.CustomMapper;
import com.haiziwang.kbook.svc.BookListService;

public class BookListServiceImpl implements BookListService{
	 
		@Override
		public List<Book> queryBookWithPage(String bookName, PageCond pageCond) {
			return dbService().getMapper(BookListMapper.class).queryBookWithPage(bookName,pageCond);
		}

		@Override
		public void borrow(BorrowRecord borrowRecord, String bookId) {
			dbService().getMapper(BookListMapper.class).addBorrowRecord(borrowRecord);
			dbService().getMapper(BookListMapper.class).changeBookCount(bookId);
		}

		@Override
		public Book queryBookById(String bookId) {
			return dbService().getMapper(BookListMapper.class).queryBookById(bookId);
		}

		@Override
		public List<Book> admQueryBookWithPage(HashMap<String, String> recordMap, PageCond pageCond) {
			return dbService().getMapper(BookListMapper.class).admQueryBookWithPage(recordMap,pageCond);
		}
		
		@Override
	    public List<BorrowRecord> borrowBookById(String custId,String bookId) {
	        return dbService().getMapper(BookListMapper.class).borrowBookById(custId,bookId);
	    }

		@Override
		public void putOff(String bookId) {
			dbService().getMapper(BookListMapper.class).putOff(bookId);
		}

		@Override
		public void putUp(String bookId) {
			dbService().getMapper(BookListMapper.class).putUp(bookId);
			
		}
		
		private IMybatisService dbService() {
	        return ServiceFactory.getService(IMybatisService.class);
	    }

		@Override
		public void addBook(Book book) {
			dbService().getMapper(BookListMapper.class).addBook(book);
			
		}

		@Override
		public void modifyBook(Book book) {
			dbService().getMapper(BookListMapper.class).modifyBook(book);
		}

		@Override
		public List<BorrowRecord> queryBorrowById(String custId) {
			return dbService().getMapper(BookListMapper.class).queryBorrowById(custId);
		}

		
}
