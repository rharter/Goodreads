package com.ryanharter.android.goodreads.util;

import java.util.List;

public class StringUtils {

	/**
	 * Concatenates a list of strings using comma separation.
	 *
	 * Example: join(["John", "Bob", "Mary"]) => "John, Bob, Mary"
	 */
	public static String join(List<String> strings) {
		return StringUtils.join(strings, ", ");
	}
	
	/**
	 * Concatenates a list of strings using the specified divider.
	 *
	 * Example: join(["John", "Bob", "Mary"], ", ") => "John, Bob, Mary"
	 */
	public static String join(List<String> strings, String divider) {
		String ret = "";
		for (int i = 0; i < strings.size(); i++) {
			if (i > 0) {
				ret += divider;
			}
			
			ret += strings.get(i);
		}
		
		return ret;
	}
}
