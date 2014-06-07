package com.sysu.taosysu.ui.fragment;

import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sysu.taosysu.R;
import com.sysu.taosysu.model.BookInfo;
import com.sysu.taosysu.network.GetBookListByLabelAsyncTask;
import com.sysu.taosysu.network.GetBookListByBookNameAsyncTask;
import com.sysu.taosysu.network.NetworkRequest;
import com.sysu.taosysu.ui.adapter.BookListAdapter;
import com.sysu.taosysu.utils.StringUtils;

public class SearchFragment extends Fragment implements
		GetBookListByLabelAsyncTask.OnRequestListener,
		GetBookListByBookNameAsyncTask.OnRequestListener {

	private static final String LABEL_STRING = "标签";
	private static final String BOOK_STRING = "书名";

	private int startBookId = -1;
	private int size = 5;

	TextView mNoResultTv;
	ListView mResultList;
	Spinner mTypeSpinner;
	Button searchBtn;
	EditText searchEt;

	List<BookInfo> mBookList;
	BookListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container,
				false);

		mNoResultTv = (TextView) rootView.findViewById(R.id.no_result);
		mResultList = (ListView) rootView.findViewById(R.id.search_result_list);
		mTypeSpinner = (Spinner) rootView
				.findViewById(R.id.search_condition_spinner);
		searchBtn = (Button) rootView.findViewById(R.id.search_button);
		searchEt = (EditText) rootView.findViewById(R.id.search_content);

		mResultList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				getFragmentManager()
						.beginTransaction()
						.add(R.id.container,
								BookDetailFragment.newInstance(mBookList
										.get(position))).commit();
			}
		});
		initSearchAction();
		return rootView;
	}

	private void initSearchAction() {
		searchEt.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				searchBtn.setEnabled(!StringUtils.isEmpty(searchEt));
			}
		});
		searchBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startSearch(mTypeSpinner.getSelectedItem().toString(), searchEt
						.getText().toString());
			}
		});
	}

	private void startSearch(String type, String content) {
		if (type == LABEL_STRING) {
			NetworkRequest.getBookListByLabel(content, startBookId, size, this);
		} else if (type == BOOK_STRING) {
			NetworkRequest.getBookListByBookName(content, startBookId, size,
					this);
		}
	}

	@Override
	public void onGetBookListByLabelSuccess(List<Map<String, Object>> bookList) {
		showBookList(bookList);
	}

	@Override
	public void onGetBookListByLabelFail(String errorMessage) {
		showErrorToast(errorMessage);
	}

	@Override
	public void onGetBookListByBookNameSuccess(
			List<Map<String, Object>> bookList) {
		showBookList(bookList);
	}

	@Override
	public void onGetBookListByBookNameFail(String errorMessage) {
		showErrorToast(errorMessage);
	}

	private void showErrorToast(String errorMessage) {
		Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
	}

	private void showBookList(List<Map<String, Object>> bookList) {
		mBookList = BookInfo.parseList(bookList);
		if (mBookList == null) {
			mNoResultTv.setVisibility(View.VISIBLE);
		} else {
			mNoResultTv.setVisibility(View.GONE);
			adapter = new BookListAdapter(getActivity(), mBookList);
			mResultList.setAdapter(adapter);
			mResultList.invalidate();
		}
	}

}
