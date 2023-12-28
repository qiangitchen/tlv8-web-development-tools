package com.tulin.v8.webtools.dtdeditor.editors;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.WhitespaceRule;

import com.tulin.v8.webtools.htmleditor.ColorProvider;
import com.tulin.v8.webtools.htmleditor.HTMLPlugin;
import com.tulin.v8.webtools.htmleditor.editors.HTMLTagScanner;
import com.tulin.v8.webtools.htmleditor.editors.HTMLWhitespaceDetector;

/**
 * @author Naoki Takezoe
 */
public class DTDTagScanner extends HTMLTagScanner {

	public DTDTagScanner(ColorProvider colorProvider) {
		super(colorProvider, false);

		IToken string = colorProvider.getToken(HTMLPlugin.PREF_COLOR_STRING);

		IRule[] rules = new IRule[3];
		rules[0] = new MultiLineRule("\"", "\"", string, '\\');
		rules[1] = new MultiLineRule("(", ")", string);
		rules[2] = new WhitespaceRule(new HTMLWhitespaceDetector());

		setRules(rules);
	}

}
