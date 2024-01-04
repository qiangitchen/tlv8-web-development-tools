package com.tulin.v8.webtools.css;

import com.tulin.v8.webtools.StringUtils;

public class CSSInfo {

	private String replaceString;
	private String displayString;

	public CSSInfo(String replaceString) {
		this.replaceString = replaceString;
		this.displayString = replaceString;
	}

	public CSSInfo(String replaceString, String displayString) {
		this.replaceString = replaceString;
		this.displayString = replaceString;
		if (StringUtils.isNotEmpty(displayString)) {
			this.displayString = replaceString + "  -->[" + displayString + "]";
		}
	}

	public String getDisplayString() {
		return displayString;
	}

	public String getReplaceString() {
		return replaceString;
	}

}
