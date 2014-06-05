package com.sysu.taosysu.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sysu.taosysu.R;
import com.sysu.taosysu.model.BookInfo;

public class BookListAdapter extends BaseAdapter {

	Context mContext;
	FragmentManager fm;
	List<BookInfo> mData;
	int resouces = R.layout.listitem_bookinfo;

	public BookListAdapter(Context context, FragmentManager fm) {
		this.mContext = context;
		this.fm = fm;
		init();
	}

	private void init() {
		mData = new ArrayList<BookInfo>();
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
			convertView = LayoutInflater.from(mContext).inflate(resouces, null);
		}

		return convertView;
	}

}
