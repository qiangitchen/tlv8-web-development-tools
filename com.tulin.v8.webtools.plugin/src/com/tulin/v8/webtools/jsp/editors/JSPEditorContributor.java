package com.tulin.v8.webtools.jsp.editors;

import com.tulin.v8.webtools.html.editors.HTMLEditorContributor;

public class JSPEditorContributor extends HTMLEditorContributor {

	@Override
	protected void init() {
		super.init();
		contributer.addActionId(JSPSourceEditor.ACTION_JSP_COMMENT);
	}

}
