package com.tulin.v8.webtools.xml.editors;

import jp.aonir.fuzzyxml.FuzzyXMLElement;
import jp.aonir.fuzzyxml.FuzzyXMLNode;

import org.eclipse.swt.graphics.Image;

import com.tulin.v8.webtools.WebToolsPlugin;
import com.tulin.v8.webtools.html.editors.HTMLOutlinePage;

/**
 * The content outline page implementation for the <code>XMLEditor</code>.
 */
public class XMLOutlinePage extends HTMLOutlinePage {

	public XMLOutlinePage(XMLEditor editor) {
		super(editor);
	}

	@Override
	protected Image getNodeImage(FuzzyXMLNode element) {
		if (element instanceof FuzzyXMLElement) {
			return WebToolsPlugin.getDefault().getImageRegistry().get(WebToolsPlugin.ICON_TAG);
		}
		return super.getNodeImage(element);
	}

	@Override
	protected boolean isHTML() {
		return false;
	}
}
