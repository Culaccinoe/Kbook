package com.haiziwang.kbook.export;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.jplugin.core.ctx.api.RuleServiceFactory;
import net.jplugin.core.das.api.PageCond;
import net.jplugin.core.kernel.api.ctx.ThreadLocalContextManager;
import net.jplugin.core.rclient.api.RemoteExecuteException;
import net.jplugin.ext.webasic.api.JPluginApi;
import net.jplugin.ext.webasic.api.JPluginApi.CT;
import net.jplugin.ext.webasic.api.JPluginApi.RL;
import net.jplugin.ext.webasic.api.Para;

import com.haiziwang.kbook.dbo.Book;
import com.haiziwang.kbook.dbo.BorrowRecord;
import com.haiziwang.kbook.svc.BookListService;
import com.haiziwang.platform.sso.auth.api.SsoConstants;

public class BookListExport {

    BookListService svc;

    public HashMap<String, Object> queryData(@Para(name = "bookName") String bookName, @Para(name = "pageCond") PageCond pageCond) {
        List<Book> book = service().queryBookWithPage(bookName, pageCond);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list", book);
        map.put("pc", pageCond);

        return map;
    }

    public HashMap<String, Object> admQueryBook(@Para(name = "recordMap") HashMap<String, String> recordMap, @Para(name = "pageCond") PageCond pageCond) {
//        return service().admQueryBook(bookName, bookState,pageCond);
        List<Book> book = service().admQueryBookWithPage(recordMap, pageCond);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list", book);
        map.put("pc", pageCond);

        return map;
    }

    public void putOff(@Para(name = "bookId") String bookId) {
        service().putOff(bookId);
    }

    public void putUp(@Para(name = "bookId") String bookId) {
        service().putUp(bookId);
    }

    @JPluginApi(callerType = CT.SSO, restrictLevel = RL.TK)
    public void addBook(@Para(name = "bookName") String bookName, @Para(name = "author") String author,
                        @Para(name = "publisher") String publisher, @Para(name = "description") String description,
                        @Para(name = "bookPicture") String bookPicture, @Para(name = "bookCount") int bookCount,
                        @Para(name = "link") String link,@Para(name = "bookId") String bookId,@Para(name = "fileName") String fileName) {
    	 Book book = service().queryBookById(bookId);
         if (book!=null) {
             throw new RemoteExecuteException("-1", "该图书ID已存在");
         }
    	String custName = ThreadLocalContextManager.instance.getContext().getAttribute(SsoConstants.USER_NAME).toString();
    	//String custName = "刘迪";
    	Calendar c = Calendar.getInstance();
        Date createTime = c.getTime();
        Date updateTime = c.getTime();
        book = new Book();
    	book.setBookId(bookId);
    	book.setBookName(bookName);
    	book.setAuthor(author);
    	book.setPublisher(publisher);
    	book.setDescription(description);
    	book.setBookPicture(bookPicture);
    	book.setBookCount(bookCount);
    	book.setLink(link);
    	book.setFileName(fileName);
    	book.setCreatorName(custName);
    	book.setCreateTime(createTime);
    	book.setUpdateTime(updateTime);
    	book.setState(0);
    	service().addBook(book);

    }
    
    @JPluginApi(callerType = CT.SSO, restrictLevel = RL.TK)
    public void modifyBook(@Para(name = "bookName") String bookName, @Para(name = "author") String author,
                        @Para(name = "publisher") String publisher, @Para(name = "description") String description,
                        @Para(name = "bookPicture") String bookPicture, @Para(name = "bookCount") int bookCount,
                        @Para(name = "link") String link,@Para(name = "bookId") String bookId,@Para(name = "fileName") String fileName) {
    	String custName = ThreadLocalContextManager.instance.getContext().getAttribute(SsoConstants.USER_NAME).toString();
    	//String custName = "刘迪";
    	Calendar c = Calendar.getInstance();
        Date createTime = c.getTime();
        Date updateTime = c.getTime();
    	Book book = new Book();
    	book.setBookId(bookId);
    	book.setBookName(bookName);
    	book.setAuthor(author);
    	book.setPublisher(publisher);
    	book.setDescription(description);
    	book.setBookPicture(bookPicture);
    	book.setBookCount(bookCount);
    	book.setLink(link);
    	book.setCreatorName(custName);
    	book.setCreateTime(createTime);
    	book.setUpdateTime(updateTime);
    	book.setFileName(fileName);
    	book.setState(0);
    	service().modifyBook(book);

    }


    @JPluginApi(callerType = CT.SSO, restrictLevel = RL.TK)
    public void borrow(@Para(name = "bookId") String bookId) {
        //sso获取
    	String custId = ThreadLocalContextManager.instance.getContext().getAttribute(SsoConstants.LOGIN_NAME).toString();
    	String custName = ThreadLocalContextManager.instance.getContext().getAttribute(SsoConstants.USER_NAME).toString();
    	//String custId = "111";
    	//String custName = "刘迪";
    	List<BorrowRecord> borrowRecord1=service().borrowBookById(custId,bookId);
    	List<BorrowRecord> queryBorrow=service().queryBorrowById(custId);
    	
    	if(queryBorrow.size()>2){
    		throw new RemoteExecuteException("-1", "您只能借3本书");
    	}
    	
    	if(borrowRecord1.size()>0){
    		throw new RemoteExecuteException("-1", "您已借过该书");
    	}else{
    	
        Book book = service().queryBookById(bookId);
        if (book.getBookCount() == 0) {
            throw new RemoteExecuteException("-1", "该书已借完");
        }
        Calendar c = Calendar.getInstance();
        Date startDate = c.getTime();
        c.add(Calendar.MONTH, 1);
        Date endDate = c.getTime();
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setUserId(custId);
        borrowRecord.setUserName(custName);
        borrowRecord.setBookId(bookId);
        borrowRecord.setBookName(book.getBookName());
        borrowRecord.setPublisher(book.getPublisher());
        borrowRecord.setStartTime(startDate);
        borrowRecord.setEndTime(endDate);
        borrowRecord.setState(0);
        service().borrow(borrowRecord, bookId);
    	}
    }

    private BookListService service() {
        if (svc == null) {
            synchronized (this) {
                svc = RuleServiceFactory.getRuleService(BookListService.class);
            }
        }
        return svc;
    }
    
}
