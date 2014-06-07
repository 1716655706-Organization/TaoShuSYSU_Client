package com.sysu.taosysu.ui.fragment;

import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.sysu.taosysu.network.AddCommentAsyncTask;
import com.sysu.taosysu.network.NetworkRequest;
import com.sysu.taosysu.utils.PreferencesUtils;
import com.sysu.taosysu.utils.StringUtils;

public class BookDetailFragment extends Fragment implements
		GetCommentAsyncTask.OnRequestListener,
		GetLabelAsyncTask.OnRequestListener,
		AddCommentAsyncTask.OnRequestListener {

	private int bookId;
	private Context mContext;
	private TextView mTitle;
	private TextView mContent;
	private TextView mLabelContainer;
	private ImageView mBookCover;
	private LinearLayout commentContainer;
	private List<String> labelList;
	private List<Comment> commentList;
	WaitingDialogFragment mProgressDialog = new WaitingDialogFragment();

	LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
			LayoutParams.MATCH_PARENT);
	LayoutInflater inflater;

	private EditText mCommentEt;
	private Button mCommentBtn;

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
		bookId = bundle.getInt(BookInfo.BOOK_ID);

		View rootView = inflater.inflate(R.layout.fragment_book_detail,
				container, false);

		mBookCover = (ImageView) rootView.findViewById(R.id.book_img);
		mTitle = (TextView) rootView.findViewById(R.id.book_name);
		mContent = (TextView) rootView.findViewById(R.id.book_introduce);
		mLabelContainer = (TextView) rootView
				.findViewById(R.id.label_container);

		mCommentBtn = (Button) rootView.findViewById(R.id.send_comment);
		mCommentEt = (EditText) rootView.findViewById(R.id.comment_content);

		commentContainer = (LinearLayout) rootView
				.findViewById(R.id.comment_container);

		initData();
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
				mCommentBtn.setEnabled(!StringUtils.isEmpty(mCommentEt));
			}
		});
		mCommentBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getFragmentManager().beginTransaction()
						.add(mProgressDialog, null).commit();
				appendComment(mCommentEt.getText().toString());
			}
		});

	}

	private void appendComment(String content) {
		int userId = PreferencesUtils.getUserId(mContext);
		NetworkRequest.addComment(bookId, userId, content, this);
	}

	private void initShowContent(Bundle bundle) {
		mBookCover.setImageResource(BookInfo.DEFAULT_ICON);
		mTitle.setText(bundle.getString(BookInfo.BOOK_NAME));
		mContent.setText(bundle.getString(BookInfo.BOOK_CONTENT));
	}

	private void initData() {
		NetworkRequest.getBookComment(bookId, this);
		NetworkRequest.getBookLabel(bookId, this);
	}

	private void initCommentContainer(List<Comment> comments) {
		if (comments == null)
			return;
		TextView authorName;
		TextView content;
		TextView commentTime;
		commentContainer.removeAllViews();
		for (Comment c : comments) {
			View view = inflater.inflate(R.layout.item_comment, null);
			authorName = (TextView) view
					.findViewById(R.id.item_comment_author_name);
			content = (TextView) view.findViewById(R.id.item_comment_content);
			commentTime = (TextView) view.findViewById(R.id.item_comment_time);

			authorName.setText("评论人：" + c.getAuthorName());
			content.setText(c.getContent());
			commentTime.setText(c.getTime());

			commentContainer.addView(view, lp);

		}
		commentContainer.invalidate();
	}

	@Override
	public void onAddCommentSuccess() {
		mProgressDialog.dismiss();
		mCommentEt.setText("");
		NetworkRequest.getBookComment(bookId, this);
	}

	@Override
	public void onAddCommentFail(String errorMessage) {
		mProgressDialog.dismiss();
		showErrorMessage(errorMessage);
	}

	@Override
	public void onGetLabelSuccess(List<String> labels) {
		labelList = labels;
		mLabelContainer.setText(StringUtils.parseLabelList(labelList));
		mLabelContainer.invalidate();
	}

	@Override
	public void onGetLabelFail(String errorMessage) {
		showErrorMessage(errorMessage);
	}

	@Override
	public void onGetCommentSuccess(List<Map<String, Object>> comments) {
		commentList = Comment.parseList(comments);
		initCommentContainer(commentList);
	}

	@Override
	public void onGetCommentFail(String errorMessage) {
		showErrorMessage(errorMessage);
	}

	private void showErrorMessage(String errorMessage) {
		Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
	}
}
