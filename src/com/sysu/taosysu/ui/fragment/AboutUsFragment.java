package com.sysu.taosysu.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.sysu.taosysu.R;

public class AboutUsFragment extends DialogFragment implements
		DialogInterface.OnClickListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		Dialog dialog = builder.setTitle(getString(R.string.quit))
				.setMessage(getString(R.string.ask_is_quit)).create();
		return dialog;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		dialog.dismiss();
	}
}
