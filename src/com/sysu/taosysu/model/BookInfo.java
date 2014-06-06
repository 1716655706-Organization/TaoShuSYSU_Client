package com.sysu.taosysu.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

import com.sysu.taosysu.R;
/**
 * 书籍信息的实体类
 * @author kuxinwei
 */
public class BookInfo {

	public static final String BOOK_ID = "bookId";
	public static final String BOOK_NAME = "bookName";
	public static final String BOOK_CONTENT = "content";
	public static final String CREATE_TIME = "time";
	public static final String AUTHOR_NAME = "authorName";

	public static final int DEFAULT_ICON = R.drawable.default_book_cover;
	
	private Bitmap bookBitmap;
	private Integer bookId;
	private String bookPicPath;
	private String createTime;
	private String content;
	private String bookname;
	private String authorName;

	public static List<BookInfo> parseList(List<Map<String, Object>> mData) {
		List<BookInfo> bookList = new ArrayList<BookInfo>();
		for (Map<String, Object> data : mData) {
			BookInfo temp = new BookInfo();
			temp.bookId = (Integer) data.get(BOOK_ID);
			temp.bookname = (String) data.get(BOOK_NAME);
			temp.content = (String) data.get(BOOK_CONTENT);
			temp.createTime = (String) data.get(CREATE_TIME);
			temp.authorName = (String) data.get(AUTHOR_NAME);
			bookList.add(temp);
		}
		return bookList;
	}

	public Bitmap getBookBitmap() {
		return bookBitmap;
	}

	public Integer getBookId() {
		return bookId;
	}

	public String getBookPicPath() {
		return bookPicPath;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getContent() {
		return content;
	}

	public String getBookName() {
		return bookname;
	}

	public String getAuthorName() {
		return authorName;
	}
}
