package com.tulin.v8.webtools.csseditor.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;

import com.tulin.v8.webtools.htmleditor.ColorProvider;
import com.tulin.v8.webtools.htmleditor.HTMLPlugin;

public class CSSPresentationReconciler extends PresentationReconciler {

	public CSSPresentationReconciler() {
		DefaultDamagerRepairer dr = null;

		ColorProvider colorProvider = HTMLPlugin.getDefault().getColorProvider();

		CSSBlockScanner defaultScanner = new CSSBlockScanner(colorProvider);
		defaultScanner.setDefaultReturnToken(colorProvider.getToken(HTMLPlugin.PREF_COLOR_FG));
		dr = new DefaultDamagerRepairer(defaultScanner);
		this.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		this.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
	}
}
