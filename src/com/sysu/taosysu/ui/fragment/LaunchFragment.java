package com.sysu.taosysu.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sysu.taosysu.R;

public class LaunchFragment extends Fragment {

	Button loginBtn;
	Button registerBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_launch, container, false);

		loginBtn = (Button) view.findViewById(R.id.login);
		registerBtn = (Button) view.findViewById(R.id.register);

		loginBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getFragmentManager().beginTransaction()
						.replace(R.id.launch_container, new LoginFragment())
						.addToBackStack("").commit();
			}
		});

		return view;
	}
}
