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

import android.os.AsyncTask;

public class GetCommentAsyncTask extends AsyncTask<String, Integer, String> {

	private int serviceId = 2;
	private int commandId = 1;
	private int bookId;
	private OnRequestListener listener;
	
	public GetCommentAsyncTask(int bookId, OnRequestListener listener) {
		this.bookId = bookId;
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
			msg.put("bookId", bookId);
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
					JSONArray jsonCommentList = msg.getJSONArray("commentList");
					List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < jsonCommentList.length(); i++) {
						JSONObject jsonComment = jsonCommentList.getJSONObject(i);
						Map<String, Object> comment = new HashMap<String, Object>();
						comment.put("content", jsonComment.getString("content"));
						comment.put("time", jsonComment.getString("time"));
						comment.put("authorName", jsonComment.getString("authorName"));
						comment.put("authorId", Integer.parseInt(jsonComment.getString("authorId")));
						commentList.add(comment);
					}
					listener.onGetCommentSuccess(commentList);
				} else {
					listener.onGetCommentFail("获取标签失败！");
				}
			} catch (JSONException e) {
				e.printStackTrace();
				listener.onGetCommentFail("返回错误！");
			}
		} else {
			listener.onGetCommentFail("网络连接错误！");
		}
	}

	public interface OnRequestListener {
		void onGetCommentSuccess(List<Map<String, Object>> comments);
		void onGetCommentFail(String errorMessage);
	}
	
}
