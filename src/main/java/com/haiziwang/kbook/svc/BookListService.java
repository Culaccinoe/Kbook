package com.haiziwang.kbook.svc;

import java.util.HashMap;
import java.util.List;

import net.jplugin.core.ctx.api.Rule;
import net.jplugin.core.ctx.api.Rule.TxType;

import com.haiziwang.kbook.dbo.Book;
import com.haiziwang.kbook.dbo.BorrowRecord;
import com.haiziwang.kbook.dbo.Customer;

import net.jplugin.core.das.api.PageCond;

public interface BookListService {

		List<Book> queryBookWithPage(String bookName, PageCond pageCond);
		
		@Rule(methodType = TxType.REQUIRED)
		void borrow(BorrowRecord borrowRecord, String bookId);
		
		Book queryBookById(String bookId);

		List<Book> admQueryBookWithPage(HashMap<String, String> recordMap, PageCond pageCond);
		
		List<BorrowRecord> borrowBookById(String custId,String bookId);
		
		List<BorrowRecord> queryBorrowById(String custId);

		void putOff(String bookId);

		void putUp(String bookId);

		void addBook(Book book);

		void modifyBook(Book book);

		

}
