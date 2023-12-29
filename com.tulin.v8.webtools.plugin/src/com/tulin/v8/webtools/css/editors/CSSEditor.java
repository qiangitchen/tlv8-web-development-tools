package com.tulin.v8.webtools.css.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.tulin.v8.webtools.ColorProvider;
import com.tulin.v8.webtools.WebToolsPlugin;
import com.tulin.v8.webtools.css.ChooseColorAction;
import com.tulin.v8.webtools.html.editors.FoldingInfo;

/**
 * CSS Editor
 */
public class CSSEditor extends TextEditor {

	private ColorProvider colorProvider;
	private CSSOutlinePage outline;

	public static final String GROUP_CSS = "_css";
	public static final String ACTION_CHOOSE_COLOR = "_choose_color";

	public CSSEditor() {
		super();
		colorProvider = WebToolsPlugin.getDefault().getColorProvider();
		setSourceViewerConfiguration(new CSSConfiguration(colorProvider));
		setPreferenceStore(new ChainedPreferenceStore(
				new IPreferenceStore[] { getPreferenceStore(), WebToolsPlugin.getDefault().getPreferenceStore() }));

		outline = new CSSOutlinePage(this);

		setAction(ACTION_CHOOSE_COLOR, new ChooseColorAction(this));

		setEditorContextMenuId("#AmaterasCSSEditor");
	}

	@Override
	public Image getTitleImage() {
		return WebToolsPlugin.getIcon("css.gif");
	}

	@Override
	protected final void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		menu.add(new Separator(GROUP_CSS));
		addGroup(menu, ITextEditorActionConstants.GROUP_EDIT, GROUP_CSS);
		addAction(menu, GROUP_CSS, ACTION_CHOOSE_COLOR);
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		if (input instanceof IFileEditorInput) {
			setDocumentProvider(new CSSTextDocumentProvider());
		} else if (input instanceof IStorageEditorInput) {
			setDocumentProvider(new CSSFileDocumentProvider());
		} else {
			setDocumentProvider(new CSSTextDocumentProvider());
		}
		super.doSetInput(input);
	}

	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave(progressMonitor);
		outline.update();
		updateFolding();
	}

	@Override
	public void doSaveAs() {
		super.doSaveAs();
		outline.update();
		updateFolding();
	}

	@Override
	protected boolean affectsTextPresentation(PropertyChangeEvent event) {
		return super.affectsTextPresentation(event) || colorProvider.affectsTextPresentation(event);
	}

	@Override
	protected void createActions() {
		super.createActions();
		// Add a content assist action
		IAction action = new ContentAssistAction(WebToolsPlugin.getDefault().getResourceBundle(),
				"ContentAssistProposal", this);
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		setAction("ContentAssistProposal", action);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getAdapter(Class adapter) {
		if (IContentOutlinePage.class.equals(adapter)) {
			return outline;
		}
		return super.getAdapter(adapter);
	}

	private static final int FOLDING_NONE = 0;
	private static final int FOLDING_STYLE = 1;
	private static final int FOLDING_COMMENT = 2;

	/**
	 * Update folding informations.
	 */
	private void updateFolding() {
		try {
			ProjectionViewer viewer = (ProjectionViewer) getSourceViewer();
			if (viewer == null) {
				return;
			}
			ProjectionAnnotationModel model = viewer.getProjectionAnnotationModel();
			if (model == null) {
				return;
			}

			List<FoldingInfo> list = new ArrayList<FoldingInfo>();
			IDocument doc = getDocumentProvider().getDocument(getEditorInput());
			String source = doc.get();

			int type = FOLDING_NONE;
			int start = -1;
			int startBackup = -1;

			for (int i = 0; i < source.length(); i++) {
				char c = source.charAt(i);
				// start comment
				if (c == '/' && type != FOLDING_COMMENT && source.length() > i + 1) {
					if (source.charAt(i + 1) == '*') {
						if (type == FOLDING_STYLE) {
							startBackup = start;
						}
						type = FOLDING_COMMENT;
						start = i;
						i++;
					}
					// end comment
				} else if (c == '*' && type == FOLDING_COMMENT && source.length() > i + 1) {
					if (source.charAt(i + 1) == '/') {
						if (doc.getLineOfOffset(start) != doc.getLineOfOffset(i)) {
							list.add(new FoldingInfo(start, i + 2 + FoldingInfo.countUpLineDelimiter(source, i + 2)));
						}
						if (startBackup != -1) {
							type = FOLDING_STYLE;
							start = startBackup;
						} else {
							type = FOLDING_NONE;
						}
						startBackup = -1;
						i++;
					}
					// start blace
				} else if (c == '{' && type == FOLDING_NONE) {
					if (type == FOLDING_COMMENT) {
						startBackup = start;
					}
					start = i;
					type = FOLDING_STYLE;
					// end blace
				} else if (type == FOLDING_STYLE && c == '}') {
					if (doc.getLineOfOffset(start) != doc.getLineOfOffset(i)) {
						list.add(new FoldingInfo(start, i + 1 + FoldingInfo.countUpLineDelimiter(source, i + 1)));
					}
					if (startBackup != -1) {
						type = FOLDING_COMMENT;
						start = startBackup;
					}
					startBackup = -1;
					type = FOLDING_NONE;
				}
			}

			FoldingInfo.applyModifiedAnnotations(model, list);

		} catch (Exception ex) {
			WebToolsPlugin.logException(ex);
		}
	}

}
