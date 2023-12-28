package com.tulin.v8.webtools.csseditor.editors;

import org.eclipse.jface.text.IDocument;

import com.tulin.v8.webtools.htmleditor.HTMLUtil;
import com.tulin.v8.webtools.htmleditor.editors.AbstractCharacterPairMatcher;

/**
 * @author Naoki Takezoe
 */
public class CSSCharacterPairMatcher extends AbstractCharacterPairMatcher {

	public CSSCharacterPairMatcher() {
		addBlockCharacter('{', '}');
	}

	@Override
	protected String getSource(IDocument document) {
		return HTMLUtil.cssComment2space(document.get());
	}

}
