package com.tulin.v8.webtools.jspeditor.converters;

import java.util.Map;

import com.tulin.v8.webtools.jspeditor.editors.JSPInfo;

import jp.aonir.fuzzyxml.FuzzyXMLNode;

public class NullConverter extends AbstractCustomTagConverter {

	public String process(Map<String, String> attrs, FuzzyXMLNode[] children, JSPInfo info, boolean fixPath) {

		return evalBody(children, info, fixPath);
	}

}
