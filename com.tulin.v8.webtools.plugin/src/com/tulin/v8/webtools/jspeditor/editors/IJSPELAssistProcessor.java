package com.tulin.v8.webtools.jspeditor.editors;

import com.tulin.v8.webtools.htmleditor.assist.AssistInfo;

import jp.aonir.fuzzyxml.FuzzyXMLElement;

/**
 * 
 * @author Naoki Takezoe
 * @since 2.0.6
 */
public interface IJSPELAssistProcessor {
    
    public AssistInfo[] getCompletionProposals(
            FuzzyXMLElement element, String expression);
    
}
