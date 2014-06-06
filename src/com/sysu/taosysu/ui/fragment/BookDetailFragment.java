package com.sysu.taosysu.ui.fragment;

import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sysu.taosysu.R;
import com.sysu.taosysu.model.BookInfo;
import com.sysu.taosysu.model.Comment;
import com.sysu.taosysu.network.GetCommentAsyncTask;
import com.sysu.taosysu.network.GetLabelAsyncTask;
import com.sysu.taosysu.network.NetworkRequest;
import com.sysu.taosysu.utils.StringUtils;

public class BookDetailFragment extends Fragment {

	private Context mContext;
	private TextView mTitle;
	private TextView mContent;
	private TextView mLabelContainer;
	private ImageView mBookCover;
	private ViewGroup commentContainer;
	private List<String> labelList;
	private List<Comment> commentList;

	public static BookDetailFragment newInstance(BookInfo book) {
		BookDetailFragment tFragment = new BookDetailFragment();

		Bundle bundle = new Bundle();
		bundle.putInt(BookInfo.BOOK_ID, book.getBookId());
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
		mContext = getActivity().getApplicationContext();

		Bundle bundle = getArguments();
		int bookId = bundle.getInt(BookInfo.BOOK_ID);

		View rootView = inflater.inflate(R.layout.fragment_book_detail,
				container, false);
		mBookCover = (ImageView) rootView.findViewById(R.id.book_img);
		mTitle = (TextView) rootView.findViewById(R.id.book_name);
		mContent = (TextView) rootView.findViewById(R.id.book_introduce);
		mLabelContainer = (TextView) rootView
				.findViewById(R.id.label_container);

		commentContainer = (ViewGroup) rootView
				.findViewById(R.id.comment_container);

		mBookCover.setImageResource(BookInfo.DEFAULT_ICON);
		mTitle.setText(bundle.getString(BookInfo.BOOK_NAME));
		mContent.setText(bundle.getString(BookInfo.BOOK_CONTENT));

		init(bookId);
		return rootView;
	}

	private void init(int bookId) {
		NetworkRequest.getBookComment(bookId,
				new GetCommentAsyncTask.OnRequestListener() {

					@Override
					public void onGetCommentSuccess(
							List<Map<String, Object>> comments) {
						commentList = Comment.parseList(comments);
					}

					@Override
					public void onGetCommentFail(String errorMessage) {
						Toast.makeText(mContext, errorMessage,
								Toast.LENGTH_SHORT).show();
					}
				});
		NetworkRequest.getBookLabel(bookId,
				new GetLabelAsyncTask.OnRequestListener() {

					@Override
					public void onGetLabelSuccess(List<String> labels) {
						labelList = labels;
						mLabelContainer.setText(StringUtils
								.parseLabelList(labelList));
						mLabelContainer.invalidate();
						Log.v("DATA", StringUtils.parseLabelList(labelList));
					}

					@Override
					public void onGetLabelFail(String errorMessage) {
						Toast.makeText(mContext, errorMessage,
								Toast.LENGTH_SHORT).show();
					}
				});
	}
}
