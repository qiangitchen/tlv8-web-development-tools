package com.tulin.v8.webtools.csseditor.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.swt.SWT;

import com.tulin.v8.webtools.htmleditor.ColorProvider;
import com.tulin.v8.webtools.htmleditor.HTMLPlugin;

/**
 * @author Naoki Takezoe
 * @author chenqian
 * @update 2010-7-24
 */
public class CSSBlockScanner extends RuleBasedScanner {

	public CSSBlockScanner(ColorProvider colorProvider) {
		List<IRule> rules = createRules(colorProvider);
		setRules(rules.toArray(new IRule[rules.size()]));
	}

	/**
	 * Creates the list of <code>IRule</code>. If you have to customize rules,
	 * override this method.
	 * 
	 * @param colorProvider ColorProvider
	 * @return the list of <code>IRule</code>
	 */
	protected List<IRule> createRules(ColorProvider colorProvider) {
		IToken tag = colorProvider.getToken(HTMLPlugin.PREF_COLOR_TAG);
		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new MultiLineRule(".", "{", tag));
		rules.add(new MultiLineRule("/*", "*/", colorProvider.getToken(HTMLPlugin.PREF_COLOR_CSSCOMMENT)));
		rules.add(new CSSRule(colorProvider.getToken(HTMLPlugin.PREF_COLOR_TAG, true),
				colorProvider.getToken(HTMLPlugin.PREF_COLOR_CSSPROP),
				colorProvider.getToken(HTMLPlugin.PREF_COLOR_CSSVALUE, SWT.ITALIC)));
		return rules;
	}

}
