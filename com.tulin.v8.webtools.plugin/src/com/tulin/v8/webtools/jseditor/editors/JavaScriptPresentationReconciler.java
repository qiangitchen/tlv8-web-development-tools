package com.tulin.v8.webtools.jseditor.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.RuleBasedScanner;

import com.tulin.v8.webtools.htmleditor.ColorProvider;
import com.tulin.v8.webtools.htmleditor.HTMLPlugin;

public class JavaScriptPresentationReconciler extends PresentationReconciler {
	public JavaScriptPresentationReconciler() {
		DefaultDamagerRepairer dr = null;

		ColorProvider colorProvider = HTMLPlugin.getDefault().getColorProvider();
		JavaScriptScanner defaultScanner = new JavaScriptScanner(colorProvider);
		defaultScanner.setDefaultReturnToken(colorProvider.getToken(HTMLPlugin.PREF_COLOR_FG));
		dr = new DefaultDamagerRepairer(defaultScanner);
		this.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		this.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		RuleBasedScanner commentScanner = new RuleBasedScanner();
		commentScanner.setDefaultReturnToken(colorProvider.getToken(HTMLPlugin.PREF_COLOR_JSCOMMENT));
		dr = new DefaultDamagerRepairer(commentScanner);
		this.setDamager(dr, JavaScriptPartitionScanner.JS_COMMENT);
		this.setRepairer(dr, JavaScriptPartitionScanner.JS_COMMENT);

		RuleBasedScanner jsdocScanner = new RuleBasedScanner();
		jsdocScanner.setDefaultReturnToken(colorProvider.getToken(HTMLPlugin.PREF_COLOR_JSDOC));
		dr = new DefaultDamagerRepairer(jsdocScanner);
		this.setDamager(dr, JavaScriptPartitionScanner.JS_JSDOC);
		this.setRepairer(dr, JavaScriptPartitionScanner.JS_JSDOC);
	}
}
