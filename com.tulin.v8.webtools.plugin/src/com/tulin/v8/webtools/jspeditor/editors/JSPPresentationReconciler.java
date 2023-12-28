package com.tulin.v8.webtools.jspeditor.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;

import com.tulin.v8.webtools.htmleditor.ColorProvider;
import com.tulin.v8.webtools.htmleditor.HTMLPlugin;
import com.tulin.v8.webtools.htmleditor.editors.HTMLTagDamagerRepairer;
import com.tulin.v8.webtools.htmleditor.editors.HTMLTagScanner;

public class JSPPresentationReconciler extends PresentationReconciler {

	public JSPPresentationReconciler() {
		DefaultDamagerRepairer dr = null;

		ColorProvider colorProvider = HTMLPlugin.getDefault().getColorProvider();

		HTMLTagScanner tagScanner = new HTMLTagScanner(colorProvider, false);
		tagScanner.setDefaultReturnToken(colorProvider.getToken(HTMLPlugin.PREF_COLOR_TAG));
		dr = new HTMLTagDamagerRepairer(tagScanner);
		this.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		this.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
//		this.setDamager(dr, HTMLPartitionScanner.HTML_TAG);
//		this.setRepairer(dr, HTMLPartitionScanner.HTML_TAG);

//		HTMLTagScanner prefixTagScanner = new HTMLTagScanner(colorProvider, true);
//		prefixTagScanner.setDefaultReturnToken(colorProvider.getToken(HTMLPlugin.PREF_COLOR_TAGLIB));
//		dr = new HTMLTagDamagerRepairer(prefixTagScanner);
//		this.setDamager(dr, HTMLPartitionScanner.PREFIX_TAG);
//		this.setRepairer(dr, HTMLPartitionScanner.PREFIX_TAG);

//		HTMLScanner scanner = new HTMLScanner(colorProvider);
//		scanner.setDefaultReturnToken(colorProvider.getToken(HTMLPlugin.PREF_COLOR_FG));
//		dr = new HTMLTagDamagerRepairer(scanner);
//		this.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
//		this.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

//		RuleBasedScanner commentScanner = new RuleBasedScanner();
//		commentScanner.setDefaultReturnToken(colorProvider.getToken(HTMLPlugin.PREF_COLOR_COMMENT));
//		dr = new HTMLTagDamagerRepairer(commentScanner);
//		this.setDamager(dr, HTMLPartitionScanner.HTML_COMMENT);
//		this.setRepairer(dr, HTMLPartitionScanner.HTML_COMMENT);

//		JSPScriptletScanner scriptletScanner = new JSPScriptletScanner(colorProvider);
//		scriptletScanner.setDefaultReturnToken(colorProvider.getToken(HTMLPlugin.PREF_COLOR_FG));
//		dr = new DefaultDamagerRepairer(scriptletScanner);
//		this.setDamager(dr, HTMLPartitionScanner.HTML_SCRIPT);
//		this.setRepairer(dr, HTMLPartitionScanner.HTML_SCRIPT);
//
//		RuleBasedScanner doctypeScanner = new RuleBasedScanner();
//		doctypeScanner.setDefaultReturnToken(colorProvider.getToken(HTMLPlugin.PREF_COLOR_DOCTYPE));
//		dr = new DefaultDamagerRepairer(doctypeScanner);
//		this.setDamager(dr, HTMLPartitionScanner.HTML_DOCTYPE);
//		this.setRepairer(dr, HTMLPartitionScanner.HTML_DOCTYPE);
//
//		JSPDirectiveScanner directiveScanner = new JSPDirectiveScanner(colorProvider);
//		directiveScanner.setDefaultReturnToken(colorProvider.getToken(HTMLPlugin.PREF_COLOR_TAG));
//		dr = new DefaultDamagerRepairer(directiveScanner);
//		this.setDamager(dr, HTMLPartitionScanner.HTML_DIRECTIVE);
//		this.setRepairer(dr, HTMLPartitionScanner.HTML_DIRECTIVE);

//		InnerJavaScriptScanner javaScriptScanner = new InnerJavaScriptScanner(colorProvider);
//		javaScriptScanner.setDefaultReturnToken(colorProvider.getToken(HTMLPlugin.PREF_COLOR_FG));
//		dr = new JavaScriptDamagerRepairer(javaScriptScanner);
//		this.setDamager(dr, HTMLPartitionScanner.JAVASCRIPT);
//		this.setRepairer(dr, HTMLPartitionScanner.JAVASCRIPT);
//
//		InnerCSSScanner cssScanner = new InnerCSSScanner(colorProvider);
//		cssScanner.setDefaultReturnToken(colorProvider.getToken(HTMLPlugin.PREF_COLOR_FG));
//		dr = new JavaScriptDamagerRepairer(cssScanner);
//		this.setDamager(dr, HTMLPartitionScanner.HTML_CSS);
//		this.setRepairer(dr, HTMLPartitionScanner.HTML_CSS);
	}
}
