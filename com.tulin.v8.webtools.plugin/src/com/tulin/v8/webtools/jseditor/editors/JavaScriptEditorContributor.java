package com.tulin.v8.webtools.jseditor.editors;

import com.tulin.v8.webtools.htmleditor.editors.HTMLSourceEditorContributer;

public class JavaScriptEditorContributor extends HTMLSourceEditorContributer {

	public JavaScriptEditorContributor(){
		addActionId(JavaScriptEditor.ACTION_COMMENT);
		addActionId(JavaScriptEditor.ACTION_OUTLINE);
	}

}
