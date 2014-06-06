package com.sysu.taosysu.model;

/**
 * 评论的实体类
 * @author kuxinwei
 */
public class Comment {

	public static final String CONTENT = "content";
	public static final String TIME = "time";
	public static final String AUTHOR_NAME = "authorName";
	public static final String AUTHOR_ID = "authorId";

	private String content;
	private String time;
	private String authorName;
	private String authorId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

}
