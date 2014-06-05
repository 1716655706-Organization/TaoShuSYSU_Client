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

public class RegisterFragment extends Fragment implements TextWatcher {

	EditText usernameEt;
	EditText passwordEt;
	EditText confirmPwdEt;
	Button confirmButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_register, container,
				false);

		usernameEt = (EditText) rootView.findViewById(R.id.input_user_name);
		passwordEt = (EditText) rootView.findViewById(R.id.input_pwd);
		confirmPwdEt = (EditText) rootView.findViewById(R.id.input_pwd_confirm);

		usernameEt.addTextChangedListener(this);
		passwordEt.addTextChangedListener(this);
		confirmPwdEt.addTextChangedListener(this);

		confirmButton = (Button) rootView
				.findViewById(R.id.btn_confirm_register);

		return rootView;
	}

	private boolean checkInfoHasCompleted() {

		return !(StringUtils.isEmpty(usernameEt)
				|| StringUtils.isEmpty(passwordEt) || StringUtils
					.isEmpty(confirmPwdEt));
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
		confirmButton.setEnabled(checkInfoHasCompleted());
	}
}
