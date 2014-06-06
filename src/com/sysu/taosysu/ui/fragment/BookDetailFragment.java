package com.sysu.taosysu.ui.fragment;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sysu.taosysu.R;
import com.sysu.taosysu.model.BookInfo;
import com.sysu.taosysu.model.Comment;
import com.sysu.taosysu.network.NetworkRequest;

public class BookDetailFragment extends Fragment {

	private TextView mTitle;
	private TextView mContent;
	private TextView mLabel;
	private ViewGroup commentContainer;
	private List<String> labelList;
	private List<Comment> commentList;

	public static BookDetailFragment newInstance(BookInfo book) {
		BookDetailFragment tFragment = new BookDetailFragment();

		Bundle bundle = new Bundle();
		bundle.putString(BookInfo.BOOK_ID, book.getBookId().toString());
		bundle.putString(BookInfo.BOOK_NAME, book.getBookName());
		bundle.putString(BookInfo.BOOK_CONTENT, book.getContent());
		bundle.putString(BookInfo.AUTHOR_NAME, book.getAuthorName());
		bundle.putString(BookInfo.CREATE_TIME, book.getCreateTime());
		
		tFragment.setArguments(bundle);

		return tFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		String bookId = bundle.getString(BookInfo.BOOK_ID);
		init(bookId);
		
		View view = inflater.inflate(R.layout.fragment_book_detail, container,
				false);

		return view;
	}

	private void init(String bookId) {
	}
}
