package com.sysu.taosysu.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sysu.taosysu.R;
import com.sysu.taosysu.model.BookInfo;
import com.sysu.taosysu.network.GetBookListAsyncTask.OnRequestListener;
import com.sysu.taosysu.network.NetworkRequest;

public class BookListAdapter extends BaseAdapter {

	Context mContext;
	List<BookInfo> mData;

	TextView mBookIdTv;
	ImageView mBookCover;
	TextView mBookNameTv;
	TextView mBookContentTv;
	TextView mAuthorTv;
	TextView mCreateTimeTv;

	public BookListAdapter(Context context) {
		this.mContext = context;
		init();
	}

	private void init() {
		mData = new ArrayList<BookInfo>();

		NetworkRequest.getBookList(-1, 4, new OnRequestListener() {

			@Override
			public void onGetBookListSuccess(List<Map<String, Object>> bookList) {
				mData = BookInfo.parseList(bookList);
				notifyDataSetChanged();
			}

			@Override
			public void onGetBookListFail(String errorMessage) {
				Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.listitem_bookinfo, null);
		}

		mBookIdTv = (TextView) convertView.findViewById(R.id.list_bookid);
		mBookNameTv = (TextView) convertView.findViewById(R.id.list_book_name);
		mAuthorTv = (TextView) convertView.findViewById(R.id.list_author_user);
		mBookContentTv = (TextView) convertView
				.findViewById(R.id.list_book_content);
		mCreateTimeTv = (TextView) convertView
				.findViewById(R.id.list_create_date);
		mBookCover = (ImageView) convertView.findViewById(R.id.list_bookimg);

		mBookIdTv.setText(mData.get(position).getBookId().toString());
		mBookNameTv.setText(mData.get(position).getBookName());
		mBookContentTv.setText(mData.get(position).getContent());
		mCreateTimeTv.setText(mData.get(position).getCreateTime());
		mAuthorTv.setText(mData.get(position).getAuthorName());
		mBookCover.setImageResource(R.drawable.default_book_cover);

		return convertView;
	}

}
