package com.tulin.v8.webtools.html;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.reconciler.Reconciler;
import org.eclipse.jface.text.source.projection.ProjectionViewer;

public class HtmlReconciler extends Reconciler {

    private HtmlReconcilerStrategy fStrategy;

    public HtmlReconciler() {
        // TODO this is logic for .project file to fold tags. Replace with your language logic!
        fStrategy = new HtmlReconcilerStrategy();
        this.setReconcilingStrategy(fStrategy, IDocument.DEFAULT_CONTENT_TYPE);
    }

    @Override
    public void install(ITextViewer textViewer) {
        super.install(textViewer);
        ProjectionViewer pViewer =(ProjectionViewer)textViewer;
        fStrategy.setProjectionViewer(pViewer);
    }
}