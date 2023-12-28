package com.tulin.v8.webtools.jspeditor.editors;

import com.tulin.v8.webtools.htmleditor.editors.HTMLEditorContributor;

public class JSPEditorContributor extends HTMLEditorContributor {

	@Override
	protected void init() {
		super.init();
		contributer.addActionId(JSPSourceEditor.ACTION_JSP_COMMENT);
	}

}
