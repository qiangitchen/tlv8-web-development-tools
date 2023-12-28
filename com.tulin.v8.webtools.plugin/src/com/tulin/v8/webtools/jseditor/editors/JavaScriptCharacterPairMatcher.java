package com.tulin.v8.webtools.jseditor.editors;

import org.eclipse.jface.text.IDocument;

import com.tulin.v8.webtools.htmleditor.editors.AbstractCharacterPairMatcher;

public class JavaScriptCharacterPairMatcher extends AbstractCharacterPairMatcher { //implements ICharacterPairMatcher {

	public JavaScriptCharacterPairMatcher() {
		addQuoteCharacter('\'');
		addQuoteCharacter('"');
		addBlockCharacter('{', '}');
		addBlockCharacter('(', ')');
		addBlockCharacter('[', ']');
	}

	protected String getSource(IDocument doc){
		return JavaScriptUtil.removeComments(doc.get());
	}

}
