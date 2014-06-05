package com.sysu.taosysu;

import android.app.Activity;
import android.os.Bundle;

import com.sysu.taosysu.ui.fragment.LaunchFragment;

public class LaunchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_launch);
		getFragmentManager().beginTransaction()
				.replace(R.id.launch_container, new LaunchFragment()).commit();
	}
}
