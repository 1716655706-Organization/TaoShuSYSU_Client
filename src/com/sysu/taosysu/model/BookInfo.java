package com.sysu.taosysu.model;

import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

import com.sysu.taosysu.R;

public class BookInfo {

	public static final int DEFAULT_ICON = R.drawable.default_book_cover;
	private Bitmap bookBitmap;
	private String uploadUserId;
	private String bookId;
	private String bookPicPath;
	private String createTime;
	private String content;
	private String bookname;

	public static List<BookInfo> parseList(List<Map<String, Object>> mData) {
		
		return null;
	}

	public Bitmap getBookBitmap() {
		return bookBitmap;
	}

	public void setBookBitmap(Bitmap bookBitmap) {
		this.bookBitmap = bookBitmap;
	}

	public String getUploadUserId() {
		return uploadUserId;
	}

	public void setUploadUserId(String uploadUserId) {
		this.uploadUserId = uploadUserId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookPicPath() {
		return bookPicPath;
	}

	public void setBookPicPath(String bookPicPath) {
		this.bookPicPath = bookPicPath;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

}
