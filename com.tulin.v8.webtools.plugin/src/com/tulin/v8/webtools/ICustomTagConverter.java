package com.tulin.v8.webtools;

import java.util.Map;

import com.tulin.v8.webtools.jsp.editors.JSPInfo;

import jp.aonir.fuzzyxml.FuzzyXMLNode;

/**
 * An interface to convert taglibs for HTML preview.
 */
public interface ICustomTagConverter {

	public String process(Map<String, String> attrs, FuzzyXMLNode[] children, JSPInfo info, boolean fixPath);

}
