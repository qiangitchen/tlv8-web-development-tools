package com.tulin.v8.webtools.ide.html;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.SingleLineRule;

import com.tulin.v8.webtools.ide.WebToolsPlugin;
import com.tulin.v8.webtools.ide.color.ColorProvider;
import com.tulin.v8.webtools.ide.css.CSSBlockScanner;

/**
 * <code>RuleBasedScanner</code> for the inner CSS in the HTML.
 */
public class InnerCSSScanner extends CSSBlockScanner {

	public InnerCSSScanner(ColorProvider colorProvider) {
		super(colorProvider);
	}

	@Override
	protected List<IRule> createRules(ColorProvider colorProvider) {
		IToken tag = colorProvider.getToken(WebToolsPlugin.PREF_COLOR_TAG);
		IToken comment = colorProvider.getToken(WebToolsPlugin.PREF_COLOR_CSSCOMMENT);

		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new SingleLineRule("<styl", "e", tag));
		rules.add(new SingleLineRule("</style", ">", tag));
		rules.add(new MultiLineRule("/*", "*/", comment));
		rules.addAll(super.createRules(colorProvider));

		return rules;
	}
}
