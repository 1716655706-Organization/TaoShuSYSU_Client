package com.sysu.taosysu.network;

public class GetBookListAsyncTask {



	public interface OnRequestListener {
		void onGetBookListSuccess(int userId);
		void onGetBookListFail(String errorMessage);
	}
}
