package com.tulin.v8.webtools.htmleditor;

import org.eclipse.core.resources.IFile;

import com.tulin.v8.webtools.htmleditor.assist.AssistInfo;

public interface IFileAssistProcessor {
	
	public void reload(IFile file);
	
	public AssistInfo[] getAssistInfo(String value);
	
}
