package com.sysu.taosysu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sysu.taosysu.ui.fragment.LaunchFragment;
import com.sysu.taosysu.utils.PreferencesUtils;

public class LaunchActivity extends Activity {

	@Override
	protected void onStart() {
		super.onStart();

		if (PreferencesUtils.getUserId(getApplicationContext()) != -1) {
			startActivity(new Intent(LaunchActivity.this, MainActivity.class));
			this.finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_launch);
		getFragmentManager().beginTransaction()
				.replace(R.id.launch_container, new LaunchFragment()).commit();
	}
}
