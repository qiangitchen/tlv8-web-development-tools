package com.tulin.v8.webtools.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.tulin.v8.webtools.WebToolsPlugin;
import com.tulin.v8.webtools.html.editors.HTMLEditor;
import com.tulin.v8.webtools.html.editors.HTMLSourceEditor;
import com.tulin.v8.webtools.js.editors.JavaScriptEditor;
import com.tulin.v8.webtools.xml.editors.XMLEditor;

public class UnMultilineCommentHandler extends AbstractHandler {

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
				ToggleCommentHandler.toggleComment(xmleditor);
			} else if (editor.getSelectedPage() instanceof HTMLSourceEditor) {
				HTMLSourceEditor htmleditor = (HTMLSourceEditor) editor.getSelectedPage();
				ToggleCommentHandler.toggleComment(htmleditor);
			} else if (editor.getSelectedPage() instanceof JavaScriptEditor) {
				JavaScriptEditor jseditor = (JavaScriptEditor) editor.getSelectedPage();
				JSComment(jseditor);
			}
		} else if (page.getActiveEditor() instanceof XMLEditor) {
			XMLEditor editor = (XMLEditor) page.getActiveEditor();
			ToggleCommentHandler.toggleComment(editor);
		} else if (page.getActiveEditor() instanceof HTMLEditor) {
			HTMLSourceEditor editor = ((HTMLEditor) page.getActiveEditor()).getPaletteTarget();
			ToggleCommentHandler.toggleComment(editor);
		} else if (page.getActiveEditor() instanceof JavaScriptEditor) {
			JavaScriptEditor editor = (JavaScriptEditor) page.getActiveEditor();
			JSComment(editor);
		}
		return null;
	}

	public static void JSComment(JavaScriptEditor editor) {
		ITextSelection sel = (ITextSelection) editor.getSelectionProvider().getSelection();
		IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		String text = sel.getText();
		if ("".equals(text)) {
			return;
		}
		try {
			if (text.startsWith("/*") && text.indexOf("*/") > 3) {
				text = sel.getText().replaceFirst("/\\*", "");
				text = text.replaceFirst("\\*/", "");
				doc.replace(sel.getOffset(), sel.getLength(), text);
			}
		} catch (BadLocationException e) {
			WebToolsPlugin.logException(e);
		}
	}

}
