package com.tulin.v8.webtools.ide.css;

import com.tulin.v8.webtools.ide.utils.StringUtils;

public class CSSInfo {

	private String replaceString;
	private String displayString;

	public CSSInfo(String replaceString) {
		this.replaceString = replaceString;
		this.displayString = CSSStyles.getString(replaceString);
		if (!replaceString.equals(displayString)) {
			this.displayString = replaceString + "  -->[" + displayString + "]";
		}
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
