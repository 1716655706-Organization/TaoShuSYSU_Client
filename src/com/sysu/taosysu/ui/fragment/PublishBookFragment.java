package com.sysu.taosysu.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sysu.taosysu.R;
import com.sysu.taosysu.network.NetworkRequest;
import com.sysu.taosysu.network.UploadBookAsyncTask;
import com.sysu.taosysu.utils.PreferencesUtils;
import com.sysu.taosysu.utils.StringUtils;

public class PublishBookFragment extends Fragment implements TextWatcher,
		View.OnClickListener {

	EditText booknameEt;
	EditText labelEt;
	EditText descriptEt;

	Button confirmBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_publishbook,
				container, false);
		booknameEt = (EditText) rootView.findViewById(R.id.add_book_name);
		labelEt = (EditText) rootView.findViewById(R.id.add_label);
		descriptEt = (EditText) rootView.findViewById(R.id.add_introduce);

		booknameEt.addTextChangedListener(this);
		labelEt.addTextChangedListener(this);
		descriptEt.addTextChangedListener(this);

		confirmBtn = (Button) rootView.findViewById(R.id.confirm_upload);

		confirmBtn.setOnClickListener(this);
		return rootView;
	}

	/** 检查所有文本框是否已填充 */
	public boolean checkInfoHasCompleted() {
		return !(StringUtils.isEmpty(booknameEt)
				|| StringUtils.isEmpty(descriptEt) || StringUtils
					.isEmpty(labelEt));
	}

	private void clearAllContent() {
		booknameEt.setText("");
		descriptEt.setText("");
		labelEt.setText("");
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void afterTextChanged(Editable s) {
		confirmBtn.setEnabled(checkInfoHasCompleted());
	}

	@Override
	public void onClick(View v) {
		int userId = PreferencesUtils.getUserId(getActivity());
		String bookName = booknameEt.getText().toString();
		String content = descriptEt.getText().toString();
		String[] label = StringUtils.parseToStringArray(labelEt.getText()
				.toString());

		NetworkRequest.uploadBook(userId, bookName, content, label,
				new UploadBookAsyncTask.OnRequestListener() {

					@Override
					public void onUploadBookSuccess() {
						Toast.makeText(getActivity(), "上传成功",
								Toast.LENGTH_SHORT).show();
						clearAllContent();
					}

					@Override
					public void onUploadBookFail(String errorMessage) {
						Toast.makeText(getActivity(), errorMessage,
								Toast.LENGTH_SHORT).show();
					}
				});
	}
}
