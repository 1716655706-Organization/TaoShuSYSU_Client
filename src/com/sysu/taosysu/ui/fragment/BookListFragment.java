package com.sysu.taosysu.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.sysu.taosysu.R;
import com.sysu.taosysu.model.BookInfo;
import com.sysu.taosysu.network.GetBookListAsyncTask.OnRequestListener;
import com.sysu.taosysu.network.NetworkRequest;
import com.sysu.taosysu.ui.adapter.BookListAdapter;

public class BookListFragment extends Fragment implements
		SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

	private Context mContext;
	private int currentId = -1;
	private static int SIZE = 5;
	private static final int GET_OLDER_DATA = 1;

	ListView mListView;
	BookListAdapter adapter;
	SwipeRefreshLayout mSwipeRefreshLayout;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_OLDER_DATA:
				NetworkRequest.getBookList(currentId, SIZE,
						new OnRequestListener() {

							@Override
							public void onGetBookListSuccess(
									List<Map<String, Object>> bookList) {
								mDataList.addAll(BookInfo.parseList(bookList));
								adapter.notifyDataSetChanged();
								Log.i("DATA", mDataList.size() + "");
							}

							@Override
							public void onGetBookListFail(String errorMessage) {

							}
						});
				break;

			default:
				break;
			}
		};
	};

	List<BookInfo> mDataList = new ArrayList<BookInfo>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity().getApplicationContext();

		View rootView = inflater.inflate(R.layout.fragment_book_list,
				container, false);
		mListView = (ListView) rootView.findViewById(R.id.fragment_book_list);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BookInfo book = mDataList.get(position);
				getFragmentManager()
						.beginTransaction()
						.replace(R.id.container,
								BookDetailFragment.newInstance(book))
						.addToBackStack(null).commit();
			}
		});

		mListView.setOnScrollListener(this);

		mSwipeRefreshLayout = (SwipeRefreshLayout) rootView
				.findViewById(R.id.swiper);
		mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_blue_bright);
		mSwipeRefreshLayout.setOnRefreshListener(this);

		init();

		return rootView;
	}

	private void init() {
		mSwipeRefreshLayout.setRefreshing(true);
		NetworkRequest.getBookList(currentId, SIZE, new OnRequestListener() {

			@Override
			public void onGetBookListSuccess(List<Map<String, Object>> bookList) {
				mDataList = BookInfo.parseList(bookList);

				adapter = new BookListAdapter(getActivity(), mDataList);
				mListView.setAdapter(adapter);

				currentId += SIZE + 1;
				mSwipeRefreshLayout.setRefreshing(false);
				Toast.makeText(mContext, "刷新成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onGetBookListFail(String errorMessage) {
				mSwipeRefreshLayout.setRefreshing(false);
				Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	@Override
	public void onRefresh() {
		init();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == mDataList.size()) {

			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (view.getLastVisiblePosition() == totalItemCount) {
			handler.sendEmptyMessage(GET_OLDER_DATA);
		}
	}
}
