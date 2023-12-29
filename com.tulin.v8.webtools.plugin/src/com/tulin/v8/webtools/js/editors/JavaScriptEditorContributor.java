package com.tulin.v8.webtools.js.editors;

import com.tulin.v8.webtools.html.editors.HTMLSourceEditorContributer;

public class JavaScriptEditorContributor extends HTMLSourceEditorContributer {

	public JavaScriptEditorContributor() {
		addActionId(JavaScriptEditor.ACTION_COMMENT);
		addActionId(JavaScriptEditor.ACTION_OUTLINE);
	}

}
