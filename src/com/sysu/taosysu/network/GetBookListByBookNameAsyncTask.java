package com.sysu.taosysu.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sysu.taosysu.model.BookInfo;

import android.os.AsyncTask;

public class GetBookListByBookNameAsyncTask extends AsyncTask<String, Integer, String> {
	
	private int serviceId = 1;
	private int commandId = 4;
	private String bookName;
	private int startBookId;
	private int size;
	private OnRequestListener listener;
	
	public GetBookListByBookNameAsyncTask(String bookName, int startBookId, int size, OnRequestListener listener) {
		this.bookName = bookName;
		this.startBookId = startBookId;
		this.size = size;
		this.listener = listener;
	}

	@Override
	protected String doInBackground(String... params) {
		HttpPost request = new HttpPost(params[0]);
		HttpResponse response = null;
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		JSONObject msg = new JSONObject();
		try {
			msg.put("sid", serviceId);
			msg.put("cid", commandId);
			msg.put("bookName", bookName);
			msg.put("startBookId", startBookId);
			msg.put("size", size);
			nameValuePair.add(new BasicNameValuePair("msg", msg.toString()));
			request.setEntity(new UrlEncodedFormEntity(nameValuePair,
					HTTP.UTF_8));
			response = NetworkRequest.CLIENT.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result != null) {
			try {
				JSONObject msg = new JSONObject(result);
				int requestCode = msg.getInt("returnCode");
				if (requestCode == 1) {
					JSONArray jsonBookList = msg.getJSONArray("bookList");
					List<Map<String, Object>> bookList = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < jsonBookList.length(); i++) {
						Map<String, Object> map = new HashMap<String, Object>();
						JSONObject jsonBook = jsonBookList.getJSONObject(i);
						map.put(BookInfo.BOOK_ID,
								Integer.parseInt(jsonBook.getString("bookId")));
						map.put(BookInfo.BOOK_NAME,
								jsonBook.getString("bookName"));
						map.put(BookInfo.BOOK_CONTENT,
								jsonBook.getString("content"));
						map.put(BookInfo.CREATE_TIME,
								jsonBook.getString("time"));
						map.put(BookInfo.AUTHOR_NAME,
								jsonBook.getString("authorName"));
						bookList.add(map);
					}
					listener.onGetBookListByBookNameSuccess(bookList);
				} else {
					listener.onGetBookListByBookNameFail("获取书籍列表失败！");
				}
			} catch (JSONException e) {
				e.printStackTrace();
				listener.onGetBookListByBookNameFail("返回错误！");
			}
		} else {
			listener.onGetBookListByBookNameFail("网络连接错误！");
		}
	}

	public interface OnRequestListener {
		void onGetBookListByBookNameSuccess(List<Map<String, Object>> bookList);
		void onGetBookListByBookNameFail(String errorMessage);
	}
}
