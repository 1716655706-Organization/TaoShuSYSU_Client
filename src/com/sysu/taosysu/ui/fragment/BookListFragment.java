package com.sysu.taosysu.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.R.bool;
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
import com.sysu.taosysu.network.GetBookListAsyncTask;
import com.sysu.taosysu.network.NetworkRequest;
import com.sysu.taosysu.ui.adapter.BookListAdapter;

public class BookListFragment extends Fragment implements
		SwipeRefreshLayout.OnRefreshListener,
		GetBookListAsyncTask.OnRequestListener, AbsListView.OnScrollListener {

	private Context mContext;
	private static final int NEWEST = -1;
	private int currentId = NEWEST;

	private static int SIZE = 5;
	// private static final int GET_OLDER_DATA = 1;

	ListView mListView;
	BookListAdapter adapter;
	SwipeRefreshLayout mSwipeRefreshLayout;

	List<BookInfo> mDataList = new ArrayList<BookInfo>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity().getApplicationContext();

		View rootView = inflater.inflate(R.layout.fragment_book_list,
				container, false);
		mListView = (ListView) rootView.findViewById(R.id.fragment_book_list);
		adapter = new BookListAdapter(getActivity(), mDataList);
		mListView.setAdapter(adapter);
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

		update(NEWEST);

		return rootView;
	}

	private void update(int currentId) {
		mSwipeRefreshLayout.setRefreshing(true);
		NetworkRequest.getBookList(currentId, SIZE, this);
	}

	/*
	 * 下拉刷新时获取最新内容
	 * 
	 * @see
	 * android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener#onRefresh
	 */
	@Override
	public void onRefresh() {
		update(NEWEST);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == SCROLL_STATE_IDLE) {
			if (view.getLastVisiblePosition() == (mDataList.size() - 1)) {
				Log.i("DATA", "Scroll changed UPDATE");
				update(currentId);
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}

	@Override
	public void onGetBookListSuccess(List<Map<String, Object>> bookList) {
		List<BookInfo> tBookList = BookInfo.parseList(bookList);
		if (tBookList == null || tBookList.isEmpty())
			return;

		else if (mDataList.isEmpty())
			mDataList.addAll(tBookList);
		else {
			for (BookInfo tBook : tBookList) {
				if (!mDataList.contains(tBook))
					mDataList.add(tBook);
			}
		}

		((BookListAdapter) mListView.getAdapter()).notifyDataSetChanged();
		if (currentId == NEWEST) {
			currentId += 1;
			showMessage("刷新成功");
		}

		currentId += SIZE;

		mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void onGetBookListFail(String errorMessage) {
		mSwipeRefreshLayout.setRefreshing(false);
		showMessage(errorMessage);
	}

	private void showMessage(String errorMessage) {
		Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();

	}
}
