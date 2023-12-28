package com.tulin.v8.webtools.jspeditor.converters;

import java.util.Iterator;
import java.util.Map;

import com.tulin.v8.webtools.htmleditor.ICustomTagConverter;
import com.tulin.v8.webtools.jspeditor.editors.JSPInfo;
import com.tulin.v8.webtools.jspeditor.editors.JSPPreviewConverter;

import jp.aonir.fuzzyxml.FuzzyXMLElement;
import jp.aonir.fuzzyxml.FuzzyXMLNode;

public abstract class AbstractCustomTagConverter implements ICustomTagConverter {

	protected String evalBody(FuzzyXMLNode child, JSPInfo info, boolean fixPath) {
		return evalBody(new FuzzyXMLNode[] { child }, info, fixPath);
	}

	protected String evalBody(FuzzyXMLNode[] children, JSPInfo info, boolean fixPath) {
		if (children == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < children.length; i++) {
			if (children[i] == null) {
				continue;
			} else if (children[i] instanceof FuzzyXMLElement) {
				sb.append(JSPPreviewConverter.processElement((FuzzyXMLElement) children[i], info, fixPath));
			} else {
				sb.append(children[i].toXMLString());
			}
		}
		return sb.toString();
	}

	protected String getAttribute(Map<String, String> attrs) {
		StringBuffer sb = new StringBuffer();
		Iterator<String> ite = attrs.keySet().iterator();
		while (ite.hasNext()) {
			String key = ite.next();
			if (key.equals("styleClass")) {
				sb.append(" class=\"" + attrs.get(key) + "\"");
			} else {
				sb.append(" " + key + "=\"" + attrs.get(key) + "\"");
			}
		}
		return sb.toString();
	}

}
