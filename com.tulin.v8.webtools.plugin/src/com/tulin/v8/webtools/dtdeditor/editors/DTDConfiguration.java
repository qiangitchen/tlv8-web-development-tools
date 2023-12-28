package com.tulin.v8.webtools.dtdeditor.editors;

import com.tulin.v8.webtools.htmleditor.ColorProvider;
import com.tulin.v8.webtools.htmleditor.HTMLPlugin;
import com.tulin.v8.webtools.htmleditor.assist.HTMLAssistProcessor;
import com.tulin.v8.webtools.htmleditor.editors.HTMLConfiguration;
import com.tulin.v8.webtools.htmleditor.editors.HTMLTagScanner;

/**
 * @author Naoki Takezoe
 */
public class DTDConfiguration extends HTMLConfiguration {

	private HTMLTagScanner tagScanner;

	public DTDConfiguration(ColorProvider colorProvider) {
		super(colorProvider);
	}

	@Override
	protected HTMLTagScanner getTagScanner() {
		if (tagScanner == null) {
			tagScanner = new DTDTagScanner(getColorProvider());
			tagScanner.setDefaultReturnToken(getColorProvider().getToken(HTMLPlugin.PREF_COLOR_TAG));
		}
		return tagScanner;
	}

	@Override
	protected HTMLAssistProcessor createAssistProcessor() {
		return new DTDAssistProcessor();
	}
}
