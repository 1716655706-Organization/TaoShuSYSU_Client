package com.sysu.taosysu.ui.fragment;

import com.sysu.taosysu.R;
import com.sysu.taosysu.ui.adapter.BookListAdapter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BookListFragment extends Fragment {

	ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_book_list, container,
				false);
		mListView = (ListView) view.findViewById(R.id.fragment_book_list);

		mListView.setAdapter(new BookListAdapter(getActivity(),
				getFragmentManager()));

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

		return view;
	}
}
