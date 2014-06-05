package com.sysu.taosysu.ui.fragment;

import com.sysu.taosysu.LaunchActivity;
import com.sysu.taosysu.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class QuitDialogFragment extends DialogFragment implements
		DialogInterface.OnClickListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		Dialog dialog = builder.setTitle(getString(R.string.quit))
				.setMessage(getString(R.string.ask_is_quit))
				.setPositiveButton(getString(R.string.confirm), this)
				.setNegativeButton(getString(R.string.cancel), this).create();
		return dialog;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			startActivity(new Intent(getActivity(), LaunchActivity.class));
			getActivity().finish();
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			break;
		default:
			break;
		}
		dialog.dismiss();
	}
}
