package com.tulin.v8.webtools;

import java.util.Map;

import com.tulin.v8.webtools.jsp.editors.IJSPValidationMarkerCreator;
import com.tulin.v8.webtools.jsp.editors.JSPInfo;

import jp.aonir.fuzzyxml.FuzzyXMLElement;

/**
 * An interface to convert taglibs for HTML preview.
 */
public interface ICustomTagValidator {

	public void validate(IJSPValidationMarkerCreator creator, Map<String, String> attrs, FuzzyXMLElement element,
			JSPInfo info);

}