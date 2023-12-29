package com.tulin.v8.webtools.formatter;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.editors.text.TextEditor;

import com.tulin.v8.webtools.html.editors.HTMLSourceEditor;
import com.tulin.v8.webtools.js.editors.JavaScriptEditor;

/**
 * 通用格式化
 * 
 * @author chenqian
 *
 */
public class Formater {

	public void format(TextEditor editor) throws ExecutionException {
//		if (editor instanceof XMLEditor) {
//			new FormatXMLAction(editor).run();
//		} else 
		if (editor instanceof HTMLSourceEditor) {
			new FormatHTMLAction(editor).run();
		} else if (editor instanceof JavaScriptEditor) {
			try {
				String tText = editor.getDocumentProvider().getDocument(editor.getEditorInput()).get();
				ITextSelection sel = (ITextSelection) editor.getSelectionProvider().getSelection();
				String stext = sel.getText().trim();
				if (("".equals(stext)) || (stext == null)) {
					String text = JavascriptFormator.format(tText);
					editor.getDocumentProvider().getDocument(editor.getEditorInput()).set(text);
					return;
				}
				String text = JavascriptFormator.format(stext);
				IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
				doc.replace(sel.getOffset(), sel.getLength(), text);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
