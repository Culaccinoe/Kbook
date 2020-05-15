package com.haiziwang.kbook.mapper;

import java.util.HashMap;
import java.util.List;

import net.jplugin.core.das.api.PageCond;
import org.apache.ibatis.annotations.Param;

import com.haiziwang.kbook.dbo.Book;
import com.haiziwang.kbook.dbo.BorrowRecord;

public interface BookListMapper {

    public List<Book> queryBookWithPage(@Param("bookName") String bookName, @Param("page") PageCond pageCond);

    public void addBorrowRecord(BorrowRecord borrowRecord);

    public Book queryBookById(@Param("bookId") String bookId);

    public void changeBookCount(@Param("bookId") String bookId);

    public List<Book> admQueryBookWithPage(@Param("recordMap") HashMap<String, String> recordMap, @Param("page") PageCond pageCond);
    
    public List<BorrowRecord> borrowBookById( @Param("custId") String custId,@Param("bookId") String bookId);
    
    public List<BorrowRecord> queryBorrowById( @Param("custId") String custId);

    public void putOff(@Param("bookId") String bookId);

    public void putUp(@Param("bookId") String bookId);

	public void addBook(Book book);

	public void modifyBook(Book book);
}
