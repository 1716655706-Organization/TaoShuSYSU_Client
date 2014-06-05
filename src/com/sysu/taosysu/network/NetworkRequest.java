package com.sysu.taosysu.network;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class NetworkRequest {

	public static HttpClient CLIENT = new DefaultHttpClient();

	private static String SERVER_URI = "http://115.28.223.78/TaoShuSYSU/server.php";
	
	public static void register(String userName, String password, RegisterAsyncTask.OnRequestListener listener) {
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

	public static void getBookList(int startBookId, int Size) {

	}
}
