package com.tulin.v8.webtools;

import java.util.Map;

import com.tulin.v8.fuzzyxml.FuzzyXMLNode;
import com.tulin.v8.webtools.jsp.JSPInfo;

/**
 * An interface to convert taglibs for HTML preview.
 */
public interface ICustomTagConverter {

	public String process(Map<String, String> attrs, FuzzyXMLNode[] children, JSPInfo info, boolean fixPath);

}
