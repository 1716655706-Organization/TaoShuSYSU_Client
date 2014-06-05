package com.sysu.taosysu.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sysu.taosysu.MainActivity;
import com.sysu.taosysu.R;
import com.sysu.taosysu.network.NetworkRequest;
import com.sysu.taosysu.network.RegisterAsyncTask.OnRequestListener;
import com.sysu.taosysu.utils.StringUtils;

public class RegisterFragment extends Fragment implements TextWatcher,
		OnRequestListener, View.OnClickListener {

	EditText usernameEt;
	EditText passwordEt;
	EditText confirmPwdEt;
	Button confirmButton;
	LinearLayout mProgessView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_register, container,
				false);

		usernameEt = (EditText) rootView.findViewById(R.id.input_user_name);
		passwordEt = (EditText) rootView.findViewById(R.id.input_pwd);
		confirmPwdEt = (EditText) rootView.findViewById(R.id.input_pwd_confirm);
		mProgessView = (LinearLayout) rootView
				.findViewById(R.id.action_progress);

		usernameEt.addTextChangedListener(this);
		passwordEt.addTextChangedListener(this);
		confirmPwdEt.addTextChangedListener(this);

		confirmButton = (Button) rootView
				.findViewById(R.id.btn_confirm_register);

		confirmButton.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		String firstPwd = passwordEt.getText().toString();
		String confirmPwd = confirmPwdEt.getText().toString();
		if (firstPwd.equals(confirmPwd)) {
			mProgessView.setVisibility(View.VISIBLE);
			NetworkRequest.register(usernameEt.getText().toString(), passwordEt
					.getText().toString(), this);
		} else {
			Toast.makeText(getActivity(),
					getString(R.string.error_password_different_from_first),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onRegisterSuccess(int userId) {
		mProgessView.setVisibility(View.GONE);
		getActivity().startActivity(
				new Intent(getActivity(), MainActivity.class));
		getActivity().finish();
	}

	@Override
	public void onRegisterFail(String errorMessage) {
		mProgessView.setVisibility(View.GONE);
		Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
	}
}
