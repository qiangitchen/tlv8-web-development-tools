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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IAccessRule;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.tulin.v8.webtools.htmleditor.HTMLPlugin;

/**
 * @author <a href="mailto:kosta@bea.com">Konstantin Komissarchik</a>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class FlexibleProjectContainer

		implements IClasspathContainer

{
	protected static final class PathType {
		public static final PathType LIB_DIRECTORY = new PathType(), CLASSES_DIRECTORY = new PathType();
	}

	private static ClasspathDecorationsManager decorations;

	static {
		// Register the resource listener that will listen for changes to
		// resources relevant to flexible project containers across the
		// workspace and refresh them as necessary.

		Listener.register();

		// Read the decorations from the workspace metadata.

		final String plugin = HTMLPlugin.getPluginId();
		decorations = new ClasspathDecorationsManager(plugin);
	}

	private static final String SEPARATOR = "!"; //$NON-NLS-1$

	public static String getDecorationManagerKey(IProject project, String container) {
		return project.getName() + SEPARATOR + container;
	}

	protected final IPath path;
	protected final IJavaProject owner;
	protected final IProject project;
	protected final IPath[] paths;
	protected final PathType[] pathTypes;
	protected final List entries;
	private final IClasspathEntry[] cpentries;
	private static final Set containerTypes = new HashSet();

	public FlexibleProjectContainer(final IPath path, final IJavaProject owner, final IProject project,
			final IPath[] paths, final PathType[] types, boolean ignoremodel) {
		this.path = path;
		this.owner = owner;
		this.project = project;
		this.paths = paths;
		this.pathTypes = types;

		if (!ignoremodel && !isFlexibleProject(this.project)) {
			// Silently noop if the referenced project is not a flexible
			// project. Should I be doing something else here?

			this.entries = Collections.EMPTY_LIST;
			this.cpentries = new IClasspathEntry[0];

			return;
		}

		addFlexibleProjectContainerType(path.segment(0));

		this.entries = computeClasspathEntries();
		this.cpentries = new IClasspathEntry[this.entries.size()];

		for (int i = 0, n = this.entries.size(); i < n; i++) {
			IPath entryPath = (IPath) this.entries.get(i);
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(entryPath);
			if (null != resource && resource.getType() == IResource.PROJECT)
				this.cpentries[i] = JavaCore.newProjectEntry(entryPath, false);
			else
				this.cpentries[i] = newLibraryEntry(entryPath);
		}
	}

	public int getKind() {
		return K_APPLICATION;
	}

	public IPath getPath() {
		return this.path;
	}

	public IClasspathEntry[] getClasspathEntries() {
		return this.cpentries;
	}

	public boolean isOutOfDate(final IResourceDelta delta) {
		if (delta == null) {
			return false;
		}
		return isOutOfDate();
	}

	public boolean isOutOfDate() {
		final List currentEntries = computeClasspathEntries();
		return !this.entries.equals(currentEntries);
	}

	public abstract void refresh();

	/**
	 * If forceUpdate is <code>false</code> then a refresh is made only if
	 * {@link #isOutOfDate()} returns <code>true</code>. If forceUpdate is
	 * <code>true</code> then a refresh is immediately made without checking
	 * {@link #isOutOfDate()}.
	 * 
	 * @param forceUpdate
	 */
	public void refresh(boolean forceUpdate) {
		if (forceUpdate || isOutOfDate()) {
			refresh();
		}
	}

	static ClasspathDecorationsManager getDecorationsManager() {
		return decorations;
	}

//	protected IVirtualReference[] computeReferences(IVirtualComponent vc) {
//		return vc.getReferences();
//	}

	protected List computeClasspathEntries() {
		final List entries = new ArrayList();

//		final IVirtualComponent vc = ComponentCore.createComponent(this.project);
//
//		if (vc == null) {
//			return entries;
//		}
//
//		IVirtualReference[] refs = computeReferences(vc);
//		IVirtualComponent comp = null;
//		Set jarsHandled = new HashSet();
//		String jarName = null;
//		for (int i = 0; i < refs.length; i++) {
//			comp = refs[i].getReferencedComponent();
//			if (!refs[i].getRuntimePath().equals(paths[0].makeAbsolute()))
//				continue;
//			jarName = refs[i].getArchiveName();
//			if (null != jarName) {
//				jarsHandled.add(jarName);
//			}
//			IPath newPath = null;
//			if (comp.isBinary()) {
//				if (comp instanceof IClasspathDependencyComponent)
//					continue;
//				newPath = comp.getAdapter(IPath.class);
//			} else
//				newPath = comp.getProject().getFullPath();
//
//			if (newPath != null && !entries.contains(newPath))
//				entries.add(newPath);
//		}
//
//		for (int i = 0; i < this.paths.length; i++) {
//			final IVirtualFolder rootFolder = vc.getRootFolder();
//			final IVirtualFolder vf = rootFolder.getFolder(paths[i]);
//
//			if (this.pathTypes[i] == PathType.LIB_DIRECTORY) {
//				final IVirtualResource[] contents;
//
//				try {
//					contents = members(vf);
//				} catch (CoreException e) {
//					HTMLPlugin.logError(e);
//					continue;
//				}
//
//				for (int j = 0; j < contents.length; j++) {
//					final IResource r = contents[j].getUnderlyingResource();
//					final IPath p = r.getLocation();
//
//					if (!jarsHandled.contains(p.lastSegment()) && isJarFile(r)) {
//						jarsHandled.add(p.lastSegment());
//						entries.add(p);
//					}
//				}
//			} else {
//				final IContainer[] uf = vf.getUnderlyingFolders();
//
//				for (int j = 0; j < uf.length; j++) {
//					final IPath p = uf[j].getFullPath();
//
//					if (!jarsHandled.contains(p.lastSegment()) && !isSourceOrOutputDirectory(p)) {
//						jarsHandled.add(p.lastSegment());
//						entries.add(p);
//					}
//				}
//			}
//		}

		return entries;
	}

	// TODO: This method was created to provide a safe last-minute workaround
	// for the issue described in
	// https://bugs.eclipse.org/bugs/show_bug.cgi?id=162974.
	// This code needs to be revisited in a future release to find a more
	// permanent solution.

//	protected IVirtualResource[] members(final IVirtualFolder vf)
//
//			throws CoreException
//
//	{
//		return vf.members();
//	}

	private IClasspathEntry newLibraryEntry(final IPath p) {
		IPath srcpath = null;
		IPath srcrootpath = null;
		IClasspathAttribute[] attrs = {};
		IAccessRule[] access = {};

		final ClasspathDecorations dec = decorations
				.getDecorations(getDecorationManagerKey(project, getPath().toString()), p.toString());

		if (dec != null) {
			srcpath = dec.getSourceAttachmentPath();
			srcrootpath = dec.getSourceAttachmentRootPath();
			attrs = dec.getExtraAttributes();
		}

		return JavaCore.newLibraryEntry(p, srcpath, srcrootpath, access, attrs, false);

	}

	boolean isSourceOrOutputDirectory(final IPath aPath) {
		if (isJavaProject(this.project)) {
			try {
				final IJavaProject jproject = JavaCore.create(this.project);
				final IClasspathEntry[] cp = jproject.getRawClasspath();

				for (int i = 0; i < cp.length; i++) {
					final IClasspathEntry cpe = cp[i];

					if (cpe.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
						final IPath src = cpe.getPath();
						final IPath output = cpe.getOutputLocation();

						if (src.equals(aPath) || output != null && output.equals(aPath)) {
							return true;
						}
					}
				}

				if (jproject.getOutputLocation().equals(aPath)) {
					return true;
				}
			} catch (JavaModelException e) {
				HTMLPlugin.logError(e);
			}
		}

		return false;
	}

	private static boolean isJavaProject(final IProject pj) {
		try {
			return pj.getNature(JavaCore.NATURE_ID) != null;
		} catch (CoreException e) {
			return false;
		}
	}

	private static boolean isFlexibleProject(final IProject pj) {
		try {
			IProjectDescription desc = pj.getDescription();
			String[] natures = desc.getNatureIds();
			for (int i = 0; i < natures.length; i++) {
				if (natures[i].equals(IModuleConstants.MODULE_NATURE_ID)) {
					return true;
				}
			}
			// return pj.getNature(IModuleConstants.MODULE_NATURE_ID) != null;
		} catch (CoreException e) {
			return false;
		}
		return false;
	}

	private static boolean isJarFile(final IResource f) {
		if (f.getType() == IResource.FILE) {
			final String fname = f.getName();

			if (fname.endsWith(".jar") || fname.endsWith(".zip")) //$NON-NLS-1$ //$NON-NLS-2$
			{
				return true;
			}
		}

		return false;
	}

	private static boolean isMetadataFile(final IResource f) {
		if (f.getType() == IResource.FILE) {
			final String fname = f.getName();

			if (fname.equals(".component") || //$NON-NLS-1$
					fname.equals("org.eclipse.wst.common.component") || //$NON-NLS-1$
					fname.equals(".classpath")) //$NON-NLS-1$
			{
				return true;
			}
		}

		return false;
	}

	private static boolean isFlexibleProjectContainer(final IPath path) {
		synchronized (containerTypes) {
			return containerTypes.contains(path.segment(0));
		}
	}

	private static void addFlexibleProjectContainerType(final String type) {
		synchronized (containerTypes) {
			containerTypes.add(type);
		}
	}

	private static final class Listener

			implements IResourceChangeListener

	{
		public static void register() {
			final Listener listener = new Listener();
			final IWorkspace ws = ResourcesPlugin.getWorkspace();
			ws.addResourceChangeListener(listener, IResourceChangeEvent.PRE_BUILD);
		}

		public void resourceChanged(final IResourceChangeEvent event) {
			// Screen the delta before going any further.

			if (!isInterestingEvent(event)) {
				return;
			}

			// Locate all of the flexible project containers.

			final ArrayList containers = new ArrayList();

			final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

			for (int i = 0; i < projects.length; i++) {
				final IProject project = projects[i];

				try {
					if (isJavaProject(project)) {
						final IJavaProject jproj = JavaCore.create(project);
						final IClasspathEntry[] cpes = jproj.getRawClasspath();

						for (int j = 0; j < cpes.length; j++) {
							final IClasspathEntry cpe = cpes[j];
							final IPath path = cpe.getPath();

							if (cpe.getEntryKind() == IClasspathEntry.CPE_CONTAINER
									&& isFlexibleProjectContainer(path)) {
								final IClasspathContainer cont = JavaCore.getClasspathContainer(path, jproj);

								containers.add(cont);
							}
						}
					}
				} catch (JavaModelException e) {
					HTMLPlugin.logError(e);
				}
			}

			// Refresh the containers that are out of date.

			final IResourceDelta delta = event.getDelta();

			for (int i = 0, n = containers.size(); i < n; i++) {
				final FlexibleProjectContainer c = (FlexibleProjectContainer) containers.get(i);

				if (c.isOutOfDate(delta)) {
					c.refresh();
				}
			}
		}

		private static boolean isInterestingEvent(final IResourceChangeEvent event) {
			final boolean[] result = new boolean[1];

			final IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
				public boolean visit(final IResourceDelta delta) {
					final IResource r = delta.getResource();

					switch (r.getType()) {
					case IResource.ROOT: {
						return true;
					}
					case IResource.PROJECT: {
						return isFlexibleProject((IProject) r);
					}
					case IResource.FOLDER: {
						final int kind = delta.getKind();

						if (kind == IResourceDelta.ADDED || kind == IResourceDelta.REMOVED) {
							result[0] = true;
							return false;
						}
						return true;
					}
					case IResource.FILE: {
						if (isJarFile(r) || isMetadataFile(r)) {
							result[0] = true;
						}

						return false;
					}
					default: {
						return false;
					}
					}
				}
			};

			try {
				event.getDelta().accept(visitor, false);
			} catch (CoreException e) {
				HTMLPlugin.logError(e);
			}

			return result[0];
		}
	}

}