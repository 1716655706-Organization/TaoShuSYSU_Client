package com.sysu.taosysu.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sysu.taosysu.R;
import com.sysu.taosysu.model.BookInfo;
import com.sysu.taosysu.network.GetBookListByUserIdAsyncTask.OnRequestListener;
import com.sysu.taosysu.ui.adapter.BookListAdapter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalInfoFragment extends Fragment implements OnRequestListener {

	TextView nameTv;
	TextView timeTv;
	ListView mBookListView;
	List<BookInfo> mBookList;
	BookListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_personal_info, null);

		nameTv = (TextView) rootView.findViewById(R.id.user_name);
		timeTv = (TextView) rootView.findViewById(R.id.user_join_time);
		mBookListView = (ListView) rootView.findViewById(R.id.book_user_upload);
		mBookListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						getFragmentManager()
								.beginTransaction()
								.add(R.id.container,
										BookDetailFragment
												.newInstance(mBookList
														.get(position)))
								.commit();
					}
				});
		return rootView;
	}

	@Override
	public void onGetBookListByUserIdSuccess(List<Map<String, Object>> bookList) {
		mBookList = new ArrayList<BookInfo>();

		mBookList = BookInfo.parseList(bookList);
		if (mBookList == null)
			return;
		adapter = new BookListAdapter(getActivity(), mBookList);
		mBookListView.setAdapter(adapter);
		mBookListView.invalidate();
	}

	@Override
	public void onGetBookListByUserIdFail(String errorMessage) {
		Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
	}
}
