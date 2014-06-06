package com.sysu.taosysu.network;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class NetworkRequest {

	public static HttpClient CLIENT = new DefaultHttpClient();

	private static String SERVER_URI = "http://115.28.223.78/TaoshuSYSU/server.php";

	public static void register(String userName, String password,
			RegisterAsyncTask.OnRequestListener listener) {
		new RegisterAsyncTask(userName, password, listener).execute(SERVER_URI);
	}

	public static void login(String userName, String password,
			LoginAsyncTask.OnRequestListener listener) {
		new LoginAsyncTask(userName, password, listener).execute(SERVER_URI);
	}

	public static void uploadBook(int userId, String bookName, String content,
			String[] label, UploadBookAsyncTask.OnRequestListener listener) {
		new UploadBookAsyncTask(userId, bookName, content, label, listener)
				.execute(SERVER_URI);
	}

	public static void getBookList(int startBookId, int size,
			GetBookListAsyncTask.OnRequestListener listener) {
		new GetBookListAsyncTask(startBookId, size, listener)
				.execute(SERVER_URI);
	}

	public static void getBookLabel(int bookId,
			GetLabelAsyncTask.OnRequestListener listener) {
		new GetLabelAsyncTask(bookId, listener).execute(SERVER_URI);
	}

	public static void getBookComment(int bookId,
			GetCommentAsyncTask.OnRequestListener listener) {
		new GetCommentAsyncTask(bookId, listener).execute(SERVER_URI);
	}

	public static void getBookListByUserId(int userId, int startBookId,
			int size, GetBookListByUserIdAsyncTask.OnRequestListener listener) {
		new GetBookListByUserIdAsyncTask(userId, startBookId, size, listener)
				.execute(SERVER_URI);
	}

	public static void getBookListByBookName(String bookName, int startBookId,
			int size, GetBookListByBookNameAsyncTask.OnRequestListener listener) {
		new GetBookListByBookNameAsyncTask(bookName, startBookId, size, listener)
				.execute(SERVER_URI);
	}

	public static void getBookListByLabel(String label, int startBookId,
			int size, GetBookListByLabelAsyncTask.OnRequestListener listener) {
		new GetBookListByLabelAsyncTask(label, startBookId, size, listener)
				.execute(SERVER_URI);
	}
}
