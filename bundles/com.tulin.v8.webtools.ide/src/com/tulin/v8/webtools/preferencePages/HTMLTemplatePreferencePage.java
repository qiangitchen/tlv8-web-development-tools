package com.tulin.v8.webtools.preferencePages;

import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;

import com.tulin.v8.webtools.WebToolsPlugin;
import com.tulin.v8.webtools.template.HTMLTemplateManager;

/**
 * The preference page for HTML code completion templates.
 */
public class HTMLTemplatePreferencePage extends TemplatePreferencePage implements IWorkbenchPreferencePage {

	/**
	 * Constructor.
	 */
	public HTMLTemplatePreferencePage() {
		try {
			setPreferenceStore(WebToolsPlugin.getDefault().getPreferenceStore());
			setTemplateStore(HTMLTemplateManager.getInstance().getTemplateStore());
			setContextTypeRegistry(HTMLTemplateManager.getInstance().getContextTypeRegistry());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected boolean isShowFormatterSetting() {
		return false;
	}

//	public boolean performOk() {
//		boolean ok = super.performOk();
//		WebToolsPlugin.getDefault().savePluginPreferences();
//		return ok;
//	}

}
