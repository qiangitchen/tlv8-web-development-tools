package com.tulin.v8.webtools.htmleditor.editors;

import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.SingleLineRule;

import com.tulin.v8.webtools.htmleditor.ColorProvider;
import com.tulin.v8.webtools.htmleditor.HTMLPlugin;
import com.tulin.v8.webtools.jseditor.editors.JavaScriptScanner;

/**
 * <code>RuleBasedScanner</code> for the inner JavaScript in the HTML.
 *
 * @author Naoki Takezoe
 * @see 2.0.3
 * @author chenqian
 * @update 2021-7-23
 */
public class InnerJavaScriptScanner extends JavaScriptScanner {

	public InnerJavaScriptScanner(ColorProvider colorProvider) {
		super(colorProvider);
	}

	@Override
	protected List<IRule> createRules(ColorProvider colorProvider) {
		IToken tag = colorProvider.getToken(HTMLPlugin.PREF_COLOR_TAG);
		IToken comment = colorProvider.getToken(HTMLPlugin.PREF_COLOR_JSCOMMENT);
		IToken jsdoc = colorProvider.getToken(HTMLPlugin.PREF_COLOR_JSDOC);
		
		List<IRule> rules = super.createRules(colorProvider);
		rules.add(new SingleLineRule("<scrip", "t", tag));
		rules.add(new SingleLineRule("</script", ">", tag));
		
		rules.add(new MultiLineRule("/**", "*/", jsdoc));
		rules.add(new MultiLineRule("/*", "*/", comment));

		return rules;
	}

}
