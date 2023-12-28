package com.tulin.v8.webtools.internal.web.classpath;

import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPageExtension;
import org.eclipse.jdt.ui.wizards.NewElementWizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class WebAppLibrariesContainerPage extends NewElementWizardPage
		implements IClasspathContainerPage, IClasspathContainerPageExtension {
	public static final Path CONTAINER_PATH = new Path("com.tulin.v8.webtools.internal.web.container");

	public WebAppLibrariesContainerPage() {
		super("WebAppLibrariesContainerPage");
		setTitle(Resources.label);
		setDescription(Resources.msg);
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		Text text = new Text(composite, SWT.BORDER);
		text.setText(getDescription());
		text.setLayoutData(new GridData(GridData.FILL_BOTH));
		setControl(composite);
	}

	@Override
	public void initialize(IJavaProject project, IClasspathEntry[] currentEntries) {

	}

	@Override
	public boolean finish() {
		return true;
	}

	@Override
	public IClasspathEntry getSelection() {
		return JavaCore.newContainerEntry(CONTAINER_PATH);
	}

	@Override
	public void setSelection(IClasspathEntry containerEntry) {
	}

	private static final class Resources extends NLS {
		public static String label;
		public static String msg;

		static {
			initializeMessages(WebAppLibrariesContainer.class.getName(), Resources.class);
		}
	}

}
