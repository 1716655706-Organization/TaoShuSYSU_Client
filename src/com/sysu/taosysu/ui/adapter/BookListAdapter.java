package com.sysu.taosysu.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sysu.taosysu.R;
import com.sysu.taosysu.model.BookInfo;
import com.sysu.taosysu.utils.StringUtils;

public class BookListAdapter extends BaseAdapter {

	Context mContext;
	List<BookInfo> mData;

	TextView mBookIdTv;
	ImageView mBookCover;
	TextView mBookNameTv;
	TextView mBookContentTv;
	TextView mAuthorTv;
	TextView mCreateTimeTv;

	public BookListAdapter(Context context, List<BookInfo> data) {
		this.mContext = context;
		this.mData = data;
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
		mBookContentTv.setText(StringUtils.getSimpleIntro(mData.get(position)
				.getContent()));
		mCreateTimeTv.setText(mData.get(position).getCreateTime());
		mAuthorTv.setText(mData.get(position).getAuthorName());
		mBookCover.setImageResource(R.drawable.default_book_cover);

		return convertView;
	}

}
