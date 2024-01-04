package com.tulin.v8.webtools.css;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ContextInformationValidator;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import com.tulin.v8.webtools.WebToolsPlugin;
import com.tulin.v8.webtools.html.HTMLUtil;

/**
 * The implementaion of IContentAssistProcessor for the CSS Editor.
 */
public class CSSAssistProcessor implements IContentAssistProcessor {

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		String text = getSource(viewer).substring(0, offset);
		String word = getLastWord(text);
		List<ICompletionProposal> list = new ArrayList<ICompletionProposal>();
		if (word != null) {
			if (word.endsWith(":")) {
				for (int i = 0; i < CSSDefinition.CSS_VALUES.length; i++) {
					if (CSSDefinition.CSS_VALUES[i].getName().startsWith(word)) {
						List<CSSInfo> values = CSSDefinition.CSS_VALUES[i].getValues();
						for (CSSInfo value : values) {
							list.add(new CompletionProposal(value.getReplaceString() + ";", offset, 0,
									value.getReplaceString().length() + 1,
									WebToolsPlugin.getDefault().getImageRegistry().get(WebToolsPlugin.ICON_CSS_PROP),
									value.getDisplayString(), null, null));
						}
					}
				}
			} else {
				for (int i = 0; i < CSSDefinition.CSS_KEYWORDS.length; i++) {
					if (CSSDefinition.CSS_KEYWORDS[i].getReplaceString().startsWith(word)) {
						String replaceString = CSSDefinition.CSS_KEYWORDS[i].getReplaceString();
						if (replaceString.indexOf(":") < 0) {
							replaceString += ": ";
						}
						list.add(new CompletionProposal(replaceString, offset - word.length(), word.length(),
								replaceString.length(),
								WebToolsPlugin.getDefault().getImageRegistry().get(WebToolsPlugin.ICON_CSS_PROP),
								CSSDefinition.CSS_KEYWORDS[i].getDisplayString(), null, null));
					}
				}
			}
		}

		// sort
		HTMLUtil.sortCompilationProposal(list);
		ICompletionProposal[] prop = list.toArray(new ICompletionProposal[list.size()]);
		return prop;
	}

	protected String getSource(ITextViewer viewer) {
		return viewer.getDocument().get();
	}

	private String getLastWord(String text) {

		text = HTMLUtil.cssComment2space(text);

		int index1 = text.lastIndexOf(';');
		int index2 = text.lastIndexOf('{');

		if (index1 >= 0 && index1 > index2) {
			return text.substring(index1 + 1).trim();
		} else if (index2 >= 0) {
			return text.substring(index2 + 1).trim();
		}

		return null;
	}

	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		ContextInformation[] info = new ContextInformation[0];
		return info;
	}

	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[0];
	}

	public char[] getContextInformationAutoActivationCharacters() {
		return new char[0];
	}

	public String getErrorMessage() {
		return "error";
	}

	public IContextInformationValidator getContextInformationValidator() {
		return new ContextInformationValidator(this);
	}

}
