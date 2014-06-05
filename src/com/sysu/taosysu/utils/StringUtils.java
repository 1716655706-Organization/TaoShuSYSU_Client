package com.sysu.taosysu.utils;

import android.widget.EditText;

public class StringUtils {

	public static boolean isEmpty(EditText et) {
		String content = et.getText().toString();
		return content.isEmpty();
	}

}
