package com.sysu.taosysu.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.sysu.taosysu.R;
import com.sysu.taosysu.model.BookInfo;
import com.sysu.taosysu.network.NetworkRequest;
import com.sysu.taosysu.network.GetBookListAsyncTask.OnRequestListener;
import com.sysu.taosysu.ui.adapter.BookListAdapter;

public class BookListFragment extends Fragment {

	ListView mListView;
	BookListAdapter adapter;
	List<BookInfo> mDataList = new ArrayList<BookInfo>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_book_list, container,
				false);
		mListView = (ListView) view.findViewById(R.id.fragment_book_list);
		init();

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BookInfo book = mDataList.get(position);
				getFragmentManager()
						.beginTransaction()
						.replace(R.id.container,
								BookDetailFragment.newInstance(book))
						.addToBackStack("").commit();
			}
		});

		return view;
	}

	private void init() {
		NetworkRequest.getBookList(-1, 4, new OnRequestListener() {

			@Override
			public void onGetBookListSuccess(List<Map<String, Object>> bookList) {
				mDataList = BookInfo.parseList(bookList);

				adapter = new BookListAdapter(getActivity(), mDataList);
				mListView.setAdapter(adapter);

				Toast.makeText(getActivity(), "LOAD FINISH", Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onGetBookListFail(String errorMessage) {
				Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
}
