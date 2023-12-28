/******************************************************************************
 * Copyright (c) 2005, 2006 BEA Systems, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *    Konstantin Komissarchik - initial API and implementation
 ******************************************************************************/

package com.tulin.v8.webtools.internal.web.classpath;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * @author <a href="mailto:kosta@bea.com">Konstantin Komissarchik</a>
 */

public abstract class FlexibleProjectContainerInitializer

		extends ClasspathContainerInitializer

{
	private static final ClasspathDecorationsManager decorations = FlexibleProjectContainer.getDecorationsManager();

	@Override
	public boolean canUpdateClasspathContainer(final IPath containerPath, final IJavaProject project) {
		return true;
	}

	@Override
	public void requestClasspathContainerUpdate(final IPath containerPath, final IJavaProject project,
			final IClasspathContainer sg)

			throws CoreException

	{
		final String key = FlexibleProjectContainer.getDecorationManagerKey(project.getProject(),
				containerPath.toString());

		final IClasspathEntry[] entries = sg.getClasspathEntries();

		decorations.clearAllDecorations(key);

		for (int i = 0; i < entries.length; i++) {
			final IClasspathEntry entry = entries[i];

			final IPath srcpath = entry.getSourceAttachmentPath();
			final IPath srcrootpath = entry.getSourceAttachmentRootPath();
			final IClasspathAttribute[] attrs = entry.getExtraAttributes();

			if (srcpath != null || attrs.length > 0) {
				final String eid = entry.getPath().toString();
				final ClasspathDecorations dec = new ClasspathDecorations();

				dec.setSourceAttachmentPath(srcpath);
				dec.setSourceAttachmentRootPath(srcrootpath);
				dec.setExtraAttributes(attrs);

				decorations.setDecorations(key, eid, dec);
			}
		}

		decorations.save();

		final IClasspathContainer container = JavaCore.getClasspathContainer(containerPath, project);

		((FlexibleProjectContainer) container).refresh();
	}

}
