package com.tulin.v8.webtools.htmleditor;

import java.util.Map;

import com.tulin.v8.webtools.jspeditor.editors.JSPInfo;

import jp.aonir.fuzzyxml.FuzzyXMLNode;

/**
 * An interface to convert taglibs for HTML preview.
 * 
 * @author Naoki Takezoe
 */
public interface ICustomTagConverter {
	
	public String process(Map<String, String> attrs,FuzzyXMLNode[] children,
			JSPInfo info, boolean fixPath);
	
}
