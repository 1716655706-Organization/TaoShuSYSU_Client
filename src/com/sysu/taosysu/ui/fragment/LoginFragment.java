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
import android.widget.Toast;

import com.sysu.taosysu.MainActivity;
import com.sysu.taosysu.R;
import com.sysu.taosysu.network.LoginAsyncTask;
import com.sysu.taosysu.network.NetworkRequest;
import com.sysu.taosysu.utils.StringUtils;

public class LoginFragment extends Fragment implements View.OnClickListener,
		LoginAsyncTask.OnRequestListener, TextWatcher {

	Button loginBtn;
	EditText nameEt;
	EditText passwordEt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_login, container,
				false);

		nameEt = (EditText) rootView.findViewById(R.id.input_account);
		passwordEt = (EditText) rootView.findViewById(R.id.input_password);

		loginBtn = (Button) rootView.findViewById(R.id.btn_login);
		loginBtn.setOnClickListener(this);

		return rootView;
	}

	private boolean checkInfoHasCompleted() {
		return !(StringUtils.isEmpty(nameEt) || StringUtils.isEmpty(passwordEt));
	}

	@Override
	public void onClick(View v) {
		NetworkRequest.login(nameEt.getText().toString(), passwordEt.getText()
				.toString(), this);
		// startActivity(new Intent(getActivity(), MainActivity.class));
		// getActivity().finish();
	}

	@Override
	public void onLoginSuccess(int userId) {
		Toast.makeText(getActivity(), "" + userId, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onLoginFail(String errorMessage) {
		Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
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
		loginBtn.setEnabled(checkInfoHasCompleted());
	}
}
