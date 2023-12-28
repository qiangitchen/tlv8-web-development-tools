package com.tulin.v8.webtools.htmleditor.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.SingleLineRule;

import com.tulin.v8.webtools.csseditor.editors.CSSBlockScanner;
import com.tulin.v8.webtools.htmleditor.ColorProvider;
import com.tulin.v8.webtools.htmleditor.HTMLPlugin;

/**
 * <code>RuleBasedScanner</code> for the inner CSS in the HTML.
 * 
 * @author Naoki Takezoe
 * @see 2.0.3
 * @author chenqian
 * @update 2021-7-23
 */
public class InnerCSSScanner extends CSSBlockScanner {

	public InnerCSSScanner(ColorProvider colorProvider) {
		super(colorProvider);
	}

	@Override
	protected List<IRule> createRules(ColorProvider colorProvider) {
		IToken tag = colorProvider.getToken(HTMLPlugin.PREF_COLOR_TAG);
		IToken comment = colorProvider.getToken(HTMLPlugin.PREF_COLOR_CSSCOMMENT);

		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new SingleLineRule("<styl", "e", tag));
		rules.add(new SingleLineRule("</style", ">", tag));
		rules.add(new MultiLineRule("/*", "*/", comment));
		rules.addAll(super.createRules(colorProvider));

		return rules;
	}
}
