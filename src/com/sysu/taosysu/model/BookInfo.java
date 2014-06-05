package com.sysu.taosysu.model;

import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

import com.sysu.taosysu.R;

public class BookInfo {

	private static final int DEFAULT_ICON = R.drawable.default_book_cover;
	private Bitmap bookBitmap;
	private String uploadUsername;
	private String createTime;
	private String simpleIntroduce;
	private String bookname;

	public static BookInfo parse(List<Map<String, Object>> mData) {
		return null;
	}

	public Bitmap getBookBitmap() {
		if (bookBitmap == null) {

		}
		return bookBitmap;
	}

	public void setBookBitmap(Bitmap bookBitmap) {
		this.bookBitmap = bookBitmap;
	}

	public String getUploadUsername() {
		return uploadUsername;
	}

	public void setUploadUsername(String uploadUsername) {
		this.uploadUsername = uploadUsername;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSimpleIntroduce() {
		return simpleIntroduce;
	}

	public void setSimpleIntroduce(String simpleIntroduce) {
		this.simpleIntroduce = simpleIntroduce;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

}
