package com.tulin.v8.webtools.htmleditor;

import jp.aonir.fuzzyxml.FuzzyXMLDocument;
import jp.aonir.fuzzyxml.FuzzyXMLElement;

import org.eclipse.core.resources.IFile;

import com.tulin.v8.webtools.htmleditor.editors.HTMLHyperlinkInfo;

/**
 * This provides additional hyperlinks to the <code>HTMLSourceEditor</code>.
 * 
 * @author Naoki Takezoe
 * @see com.tulin.v8.webtools.HTMLHyperlinkDetector
 */
public interface IHyperlinkProvider {
	
	/**
	 * This method returns a target object of hyperlink.
	 * If this provider doesn't support specified arguments, returns null.
	 * 
	 * @param file      the <code>IFile</code> instance
	 * @param doc       a document object of FuzzyXML
	 * @param element   an element that are calet position
	 * @param attrName  an attribute name that are calet position
	 * @param attrValue an attribute value that are calet position
	 * @return 
	 *   <ul>
	 *     <li><code>IFile</code></li>
	 *     <li><code>IJavaElement</code></li>
	 *     <li><code>null</code></li>
	 *   </ul>
	 */
	public HTMLHyperlinkInfo getHyperlinkInfo(IFile file,FuzzyXMLDocument doc,
			FuzzyXMLElement element,String attrName,String attrValue,int offset);
	
}
