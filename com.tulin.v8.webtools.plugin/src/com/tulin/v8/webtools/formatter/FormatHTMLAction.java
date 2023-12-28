package com.tulin.v8.webtools.formatter;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.editors.text.TextEditor;

import com.egen.develop.util.jspFormatter.JSPFormatter;
import com.tulin.v8.webtools.htmleditor.HTMLPlugin;

public class FormatHTMLAction extends Action {
	TextEditor texteditor;

	public FormatHTMLAction(TextEditor texteditor) {
		super(HTMLPlugin.getResourceString("HTMLEditor.Format"));
		this.texteditor = texteditor;
	}

	@Override
	public void run() {
		IPreferenceStore store = HTMLPlugin.getDefault().getPreferenceStore();
		int prefIndent = store.getInt(HTMLPlugin.PREF_FORMATTER_INDENT);
		boolean prefUseTab = store.getBoolean(HTMLPlugin.PREF_FORMATTER_TAB);
		int lineChars = store.getInt(HTMLPlugin.PREF_FORMATTER_LINE);

		String defaultIndent = "";
		if (prefUseTab) {
			for (int i = 0; i < prefIndent; i++) {
				defaultIndent = defaultIndent + " ";
			}
		} else {
			prefIndent = 2;
		}

		try {
			IDocument doc = texteditor.getDocumentProvider().getDocument(texteditor.getEditorInput());
			ITextSelection sel = (ITextSelection) texteditor.getSelectionProvider().getSelection();
			if (sel == null || sel.getLength() == 0) {
				String source = doc.get();
				JSPFormatter formatter = new JSPFormatter(source, prefIndent, lineChars);
				String result = formatter.format();
				if (prefUseTab) {
					result = result.replaceAll(defaultIndent, "\t");
				}
				doc.set(result);

			} else {
				int offset = sel.getOffset();
				int length = sel.getLength();

				int startLine = doc.getLineOfOffset(offset);
				int startLineOffset = doc.getLineOffset(startLine);
				int startLineLength = doc.getLineLength(startLine);
				String lineText = doc.get(startLineOffset, startLineLength);
				String indent = "";
				for (int i = 0; i < lineText.length(); i++) {
					char c = lineText.charAt(i);
					if (c == ' ' || c == '\t') {
						indent = indent + c;
					} else {
						break;
					}
				}

				String source = doc.get(offset, length);

				JSPFormatter formatter = new JSPFormatter(source, prefIndent, lineChars);
				String result = formatter.format();
				result = indent + result.replaceAll("(\r\n|\r|\n)", "$1" + indent);

				if (prefUseTab) {
					result = result.replaceAll(defaultIndent, "\t");
				}

				doc.replace(offset, length, result);
			}
		} catch (Exception ex) {
			HTMLPlugin.openAlertDialog(ex.toString());
		}
	}
}
