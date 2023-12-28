package com.tulin.v8.webtools.htmleditor;

import java.util.Map;

import com.tulin.v8.webtools.jspeditor.editors.IJSPValidationMarkerCreator;
import com.tulin.v8.webtools.jspeditor.editors.JSPInfo;

import jp.aonir.fuzzyxml.FuzzyXMLElement;

/**
 * An interface to convert taglibs for HTML preview.
 * 
 * @author Naoki Takezoe
 */
public interface ICustomTagValidator {
	
	public void validate(IJSPValidationMarkerCreator creator, 
			Map<String, String> attrs,FuzzyXMLElement element,JSPInfo info);
	
}