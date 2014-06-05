package com.sysu.taosysu.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sysu.taosysu.R;
import com.sysu.taosysu.network.LoginAsyncTask;
import com.sysu.taosysu.network.NetworkRequest;

public class LoginFragment extends Fragment implements View.OnClickListener,
		LoginAsyncTask.OnRequestListener {

	Button login;
	EditText nameEt;
	EditText passwordEt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_login, container,
				false);

		nameEt = (EditText) rootView.findViewById(R.id.input_account);
		passwordEt = (EditText) rootView.findViewById(R.id.input_password);

		login = (Button) rootView.findViewById(R.id.btn_login);
		login.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		NetworkRequest.login(nameEt.getText().toString(), passwordEt.getText()
				.toString(), this);
	}

	@Override
	public void onLoginSuccess(int userId) {
		Toast.makeText(getActivity(), "" + userId, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onLoginFail(String errorMessage) {
		Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
	}
}
