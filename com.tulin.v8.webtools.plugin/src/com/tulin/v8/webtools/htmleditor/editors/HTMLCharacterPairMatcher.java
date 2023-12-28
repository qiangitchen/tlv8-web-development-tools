package com.tulin.v8.webtools.htmleditor.editors;

import jp.aonir.fuzzyxml.internal.FuzzyXMLUtil;

import org.eclipse.jface.text.IDocument;

import com.tulin.v8.webtools.htmleditor.HTMLUtil;

/**
 * @author Naoki Takezoe
 */
public class HTMLCharacterPairMatcher extends AbstractCharacterPairMatcher {

	public HTMLCharacterPairMatcher() {
		addQuoteCharacter('\'');
		addQuoteCharacter('"');
		addBlockCharacter('{', '}');
		addBlockCharacter('(', ')');
		addBlockCharacter('<', '>');
	}
	
	protected String getSource(IDocument doc){
		String text = doc.get();
		text = FuzzyXMLUtil.escapeString(text);
		text = HTMLUtil.comment2space(text, true);
		text = HTMLUtil.scriptlet2space(text, true);
		return text;
	}

}
