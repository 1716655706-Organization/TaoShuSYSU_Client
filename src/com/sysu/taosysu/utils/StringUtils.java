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
		if (labelList != null) {
			sb.append("| ");
			for (String label : labelList) {
				sb.append(label).append(" | ");
			}
		}

		return sb.toString();
	}

	public static String getSimpleIntro(String content) {
		if (content.length() > 30) {
			content = content.substring(0, 30);
			content += "...";
		}
		return "简介：" + content;
	}

	public static String[] parseToStringArray(String labels) {
		return labels.split(" ");
	}

	public static String getWrappedBookName(String bookName) {
		if (bookName.length() > 10) {
			bookName = bookName.substring(0, 10);
			bookName = "书名：《" + bookName + "...";
		} else {
			bookName = "书名：《" + bookName + "》";
		}
		return bookName;
	}

}
