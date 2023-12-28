package com.tulin.v8.webtools.xmleditor.editors;

import com.tulin.v8.webtools.htmleditor.ColorProvider;
import com.tulin.v8.webtools.htmleditor.HTMLHyperlinkDetector;
import com.tulin.v8.webtools.htmleditor.assist.HTMLAssistProcessor;
import com.tulin.v8.webtools.htmleditor.editors.HTMLConfiguration;

/**
 * The editor configuration for the <code>XMLEditor</code>.
 * 
 * @author Naoki Takezoe
 * @see com.tulin.v8.webtools.xmleditor.editors.XMLAssistProcessor
 */
public class XMLConfiguration extends HTMLConfiguration {

	private ClassNameHyperLinkProvider classNameHyperlinkProvider = null;

	public XMLConfiguration(ColorProvider colorProvider) {
		super(colorProvider);
	}

	/**
	 * Returns the <code>XMLAssistProcessor</code> as the assist processor.
	 * 
	 * @return the <code>XMLAssistProcessor</code>
	 */
	protected HTMLAssistProcessor createAssistProcessor() {
		return new XMLAssistProcessor();
	}

	public ClassNameHyperLinkProvider getClassNameHyperlinkProvider() {
		return this.classNameHyperlinkProvider;
	}

	/**
	 * Returns the <code>HTMLHyperlinkDetector</code> which has
	 * <code>ClassNameHyperLinkProvider</code>.
	 * <p>
	 * Provides the classname hyperlink for the following attributes.
	 * <ul>
	 * <li>type</li>
	 * <li>class</li>
	 * <li>classname</li>
	 * <li>bean</li>
	 * <li>component</li></li>
	 */
	@Override
	protected HTMLHyperlinkDetector createHyperlinkDetector() {
		if (this.classNameHyperlinkProvider == null) {
			this.classNameHyperlinkProvider = new ClassNameHyperLinkProvider();
		}
		HTMLHyperlinkDetector detector = super.createHyperlinkDetector();
		detector.addHyperlinkProvider(this.classNameHyperlinkProvider);
		return detector;
	}

}
