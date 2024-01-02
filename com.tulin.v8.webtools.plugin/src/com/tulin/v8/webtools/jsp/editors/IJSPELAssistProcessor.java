package com.tulin.v8.webtools.jsp.editors;

import com.tulin.v8.webtools.assist.AssistInfo;

import jp.aonir.fuzzyxml.FuzzyXMLElement;

public interface IJSPELAssistProcessor {

	public AssistInfo[] getCompletionProposals(FuzzyXMLElement element, String expression);

}
