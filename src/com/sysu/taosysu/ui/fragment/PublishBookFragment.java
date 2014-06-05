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

import com.sysu.taosysu.R;
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
//		String content = labelEt.getText().toString();
//		List<String> labelList = StringUtils.parseLabelsFromText(content);
		
	}
}
