package com.sysu.taosysu.ui.fragment;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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

	private static final int COMMENT = 1;
	private Context mContext;
	private TextView mTitle;
	private TextView mContent;
	private TextView mLabelContainer;
	private ImageView mBookCover;
	private LinearLayout commentContainer;
	private List<String> labelList;
	private List<Comment> commentList;

	LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
			LayoutParams.MATCH_PARENT);
	LayoutInflater inflater;

	private EditText mCommentEt;
	private ImageButton mCommentBtn;

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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		inflater = LayoutInflater.from(getActivity());
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

		mCommentBtn = (ImageButton) rootView.findViewById(R.id.send_comment);
		mCommentEt = (EditText) rootView.findViewById(R.id.comment_content);

		commentContainer = (LinearLayout) rootView
				.findViewById(R.id.comment_container);

		initData(bookId);
		initShowContent(bundle);
		initCommentAction();
		return rootView;
	}

	private void initCommentAction() {
		mCommentEt.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int b, int c) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int c,
					int a) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				mCommentBtn.setEnabled(true);
				mCommentBtn.setImageResource(R.id.send_comment);
			}
		});
		mCommentBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String comment = mCommentEt.getText().toString();
			}
		});

	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case COMMENT:

				break;

			default:
				break;
			}
		};
	};

	private void initShowContent(Bundle bundle) {
		mBookCover.setImageResource(BookInfo.DEFAULT_ICON);
		mTitle.setText(bundle.getString(BookInfo.BOOK_NAME));
		mContent.setText(bundle.getString(BookInfo.BOOK_CONTENT));
	}

	private void initData(int bookId) {
		NetworkRequest.getBookComment(bookId,
				new GetCommentAsyncTask.OnRequestListener() {

					@Override
					public void onGetCommentSuccess(
							List<Map<String, Object>> comments) {
						commentList = Comment.parseList(comments);
						initCommentContainer(commentList);
						Log.v("DATA", StringUtils.parseLabelList(labelList));
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
					}

					@Override
					public void onGetLabelFail(String errorMessage) {
						Toast.makeText(mContext, errorMessage,
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	private void initCommentContainer(List<Comment> comments) {
		if (comments == null)
			return;
		TextView authorName;
		TextView content;
		TextView commentTime;

		for (Comment c : comments) {
			Log.i("NULL", inflater.toString()); 
			View view = inflater.inflate(R.layout.item_comment, null);
			authorName = (TextView) view
					.findViewById(R.id.item_comment_author_name);
			content = (TextView) view.findViewById(R.id.item_comment_content);
			commentTime = (TextView) view.findViewById(R.id.item_comment_time);

			authorName.setText(c.getAuthorName());
			content.setText(c.getContent());
			commentTime.setText(c.getTime());

			commentContainer.addView(view, lp);

		}
		commentContainer.invalidate();
	}
}
