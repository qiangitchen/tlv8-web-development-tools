package com.tulin.v8.webtools;

import java.util.Map;

import com.tulin.v8.fuzzyxml.FuzzyXMLElement;
import com.tulin.v8.webtools.jsp.IJSPValidationMarkerCreator;
import com.tulin.v8.webtools.jsp.JSPInfo;

/**
 * An interface to convert taglibs for HTML preview.
 */
public interface ICustomTagValidator {

	public void validate(IJSPValidationMarkerCreator creator, Map<String, String> attrs, FuzzyXMLElement element,
			JSPInfo info);

}