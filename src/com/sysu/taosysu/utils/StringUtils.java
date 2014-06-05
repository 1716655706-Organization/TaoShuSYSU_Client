package com.sysu.taosysu.utils;

import java.util.ArrayList;
import java.util.List;

import android.widget.EditText;

public class StringUtils {

	public static boolean isEmpty(final EditText et) {
		String content = et.getText().toString();
		return content.isEmpty();
	}

	public static List<String> parseLabelsFromText(String content) {
		List<String> labels = new ArrayList<String>();

		return labels;
	}

}
