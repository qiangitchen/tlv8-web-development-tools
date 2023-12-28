package com.tulin.v8.webtools.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.tulin.v8.webtools.formatter.FormatHTMLAction;
import com.tulin.v8.webtools.formatter.FormatXMLAction;
import com.tulin.v8.webtools.formatter.JavascriptFormator;
import com.tulin.v8.webtools.htmleditor.editors.HTMLEditor;
import com.tulin.v8.webtools.htmleditor.editors.HTMLSourceEditor;
import com.tulin.v8.webtools.jseditor.editors.JavaScriptEditor;
import com.tulin.v8.webtools.xmleditor.editors.XMLEditor;

public class FormaterHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = null;
		if (event == null)
			window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		else {
			window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		}
		IWorkbenchPage page = window.getActivePage();
		if (page.getActiveEditor() instanceof MultiPageEditorPart) {
			MultiPageEditorPart editor = (MultiPageEditorPart) page.getActiveEditor();
			if (editor.getSelectedPage() instanceof XMLEditor) {
				XMLEditor xmleditor = (XMLEditor) editor.getSelectedPage();
				new FormatXMLAction(xmleditor).run();
			} else if (editor.getSelectedPage() instanceof HTMLSourceEditor) {
				HTMLSourceEditor htmleditor = (HTMLSourceEditor) editor.getSelectedPage();
				new FormatHTMLAction(htmleditor).run();
			} else if (editor.getSelectedPage() instanceof JavaScriptEditor) {
				JavaScriptEditor jseditor = (JavaScriptEditor) editor.getSelectedPage();
				try {
					String tText = jseditor.getDocumentProvider().getDocument(jseditor.getEditorInput()).get();
					ITextSelection sel = (ITextSelection) jseditor.getSelectionProvider().getSelection();
					String stext = sel.getText().trim();
					if (("".equals(stext)) || (stext == null)) {
						String text = JavascriptFormator.format(tText);
						jseditor.getDocumentProvider().getDocument(jseditor.getEditorInput()).set(text);
						return null;
					}
					String text = JavascriptFormator.format(stext);
					IDocument doc = jseditor.getDocumentProvider().getDocument(jseditor.getEditorInput());
					doc.replace(sel.getOffset(), sel.getLength(), text);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (page.getActiveEditor() instanceof XMLEditor) {
			XMLEditor editor = (XMLEditor) page.getActiveEditor();
			new FormatXMLAction(editor).run();
		} else if (page.getActiveEditor() instanceof HTMLEditor) {
			HTMLSourceEditor editor = ((HTMLEditor) page.getActiveEditor()).getPaletteTarget();
			new FormatHTMLAction(editor).run();
		} else if (page.getActiveEditor() instanceof JavaScriptEditor) {
			JavaScriptEditor editor = (JavaScriptEditor) page.getActiveEditor();
			try {
				String tText = editor.getDocumentProvider().getDocument(editor.getEditorInput()).get();
				ITextSelection sel = (ITextSelection) editor.getSelectionProvider().getSelection();
				String stext = sel.getText().trim();
				if (("".equals(stext)) || (stext == null)) {
					String text = JavascriptFormator.format(tText);
					editor.getDocumentProvider().getDocument(editor.getEditorInput()).set(text);
				} else {
					String text = JavascriptFormator.format(stext);
					IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
					doc.replace(sel.getOffset(), sel.getLength(), text);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
