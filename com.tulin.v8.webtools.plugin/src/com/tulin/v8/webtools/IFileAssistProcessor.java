package com.tulin.v8.webtools;

import org.eclipse.core.resources.IFile;

import com.tulin.v8.webtools.assist.AssistInfo;

public interface IFileAssistProcessor {

	public void reload(IFile file);

	public AssistInfo[] getAssistInfo(String value);

}
