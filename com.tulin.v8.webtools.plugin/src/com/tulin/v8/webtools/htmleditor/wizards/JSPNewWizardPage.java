package com.tulin.v8.webtools.htmleditor.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;

import com.tulin.v8.webtools.htmleditor.HTMLPlugin;
import com.tulin.v8.webtools.htmleditor.HTMLUtil;
import com.tulin.v8.webtools.htmleditor.IOUtil;

public class JSPNewWizardPage extends HTMLNewWizardPage {
	
	public JSPNewWizardPage(ISelection selection) {
		super(selection);
		setTitle(HTMLPlugin.getResourceString("JSPNewWizardPage.Title"));
		setDescription(HTMLPlugin.getResourceString("JSPNewWizardPage.Description"));
	}
	
	public void createControl(Composite parent) {
		super.createControl(parent);
		this.setFileName("newfile.jsp");
	}
	
	protected InputStream getInitialContents() {
		InputStream in = super.getInitialContents();
		// charset
		String projectName = getContainerFullPath().segment(0);
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		String charset = HTMLUtil.getProjectCharset(project);
//		try {
//			String projectName = getContainerFullPath().segment(0);
//			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
//			charset = project.getDefaultCharset();
//		} catch(CoreException ex){
//		}
		StringBuffer sb = new StringBuffer();
		if(charset==null){
			sb.append("<%@page contentType=\"text/html\" %>\n");
		} else {
			sb.append("<%@page pageEncoding=\""+charset+"\" contentType=\"text/html; charset="+charset+"\" %>\n");
		}
		try {
			sb.append(new String(IOUtil.readStream(in)));
		} catch(IOException ex){
			HTMLPlugin.logException(ex);
		}
		
		return new ByteArrayInputStream(sb.toString().getBytes());
	}
}
