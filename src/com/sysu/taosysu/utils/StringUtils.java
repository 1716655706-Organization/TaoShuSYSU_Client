package com.sysu.taosysu.utils;

import java.util.List;

import android.widget.EditText;

public class StringUtils {

	public static boolean isEmpty(final EditText et) {
		String content = et.getText().toString();
		return content.isEmpty();
	}

	public static String parseLabelList(List<String> labelList) {

		StringBuilder sb = new StringBuilder();
		for (String label : labelList) {
			sb.append(label).append(" | ");
		}

		return sb.toString();
	}

	public static String getSimpleIntro(String content) {
		if (content.length() > 30) {
			content = content.substring(0, 30);
			content += "...";
		}
		return content;
	}

}
