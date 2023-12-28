package com.tulin.v8.webtools.htmleditor.editors;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.WhitespaceRule;

import com.tulin.v8.webtools.htmleditor.ColorProvider;
import com.tulin.v8.webtools.htmleditor.HTMLPlugin;

public class HTMLTagScanner extends RuleBasedScanner {

	public HTMLTagScanner(ColorProvider colorProvider, boolean bold) {
		IToken string = null;
		if (bold) {
			string = colorProvider.getToken(HTMLPlugin.PREF_COLOR_TAGLIB_ATTR);
		} else {
			string = colorProvider.getToken(HTMLPlugin.PREF_COLOR_STRING);
		}
		IToken attr = colorProvider.getToken(HTMLPlugin.PREF_COLOR_ATTR);
		IToken val = colorProvider.getToken(HTMLPlugin.PREF_COLOR_VALUE);

		IRule[] rules = new IRule[4];

		rules[0] = new MultiLineRule("\"", "\"", val, '\\');
		rules[1] = new MultiLineRule("'", "'", string, '\\');
		rules[2] = new MultiLineRule(" ", "=", attr, '\\');
		rules[3] = new WhitespaceRule(new HTMLWhitespaceDetector());

		setRules(rules);
	}
}
